package com.greentopli.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.greentopli.Constants;
import com.greentopli.core.storage.helper.UserDbHelper;
import com.greentopli.core.presenter.base.MvpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * referred from https://firebase.google.com/docs/auth/android/google-signin?utm_source=studio
 */
public class AuthenticatorActivity extends AppCompatActivity implements
	GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener, MvpView,
	GoogleApiClient.ConnectionCallbacks{
	private GoogleApiClient mGoogleApiClient;

	private static final int RC_SIGN_IN = 9015;
	public static final String EXTRA_SIGNOUT = "signout_request";
	private FirebaseAuth mAuth;
	private static final String TAG = AuthenticatorActivity.class.getSimpleName();
	@BindView(R.id.default_progressbar)ProgressBar mProgressBar;
	@BindView(R.id.button_sign_in) SignInButton mSignInButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authenticator);
		ButterKnife.bind(this);

		// Configure Google Sign In
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.requestProfile()
				.build();

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();

		mAuth = FirebaseAuth.getInstance();

		if (getIntent().hasExtra(EXTRA_SIGNOUT) && getIntent().getBooleanExtra(EXTRA_SIGNOUT,false))
			signOut();
	}

	@Override
	protected void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mAuth.removeAuthStateListener(this);
		mGoogleApiClient.disconnect();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				// Google Sign In was successful, authenticate with Firebase
				GoogleSignInAccount account = result.getSignInAccount();
				firebaseAuthWithGoogle(account);
			} else {
				// Google Sign In failed, update UI appropriately
				showProgressbar(false);
				Toast.makeText(getApplicationContext(), Constants.ERROR_SIGNIN,Toast.LENGTH_LONG).show();
				FirebaseCrash.log(Constants.ERROR_SIGNIN);
			}
		}

	}


	@Override
	public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
		FirebaseUser user = firebaseAuth.getCurrentUser();
		showProgressbar(false);
		if (user != null) {
			// User is signed in
			Log.d(TAG,"Signed In "+user.getDisplayName());
			returnActivityResult();
		} else {
			// User is signed out
			Log.d(TAG,"Signed Out");
		}
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Toast.makeText(getApplicationContext(),connectionResult.toString(),Toast.LENGTH_LONG).show();
	}
	private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
		Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

		AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
		mAuth.signInWithCredential(credential)
				.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
						// If sign in fails, display a message to the user. If sign in succeeds
						// the auth state listener will be notified and logic to handle the
						// signed in user can be handled in the listener.
						if (!task.isSuccessful()) {
							Log.e(TAG, "signInWithCredential", task.getException());
							Toast.makeText(getApplicationContext(), "Authentication failed.",
									Toast.LENGTH_SHORT).show();
							FirebaseCrash.report(task.getException());
							showProgressbar(false);
						}
					}
				});
	}

	@Override
	public void showProgressbar(boolean show) {
		mProgressBar.setVisibility(show?View.VISIBLE:View.GONE);
		mSignInButton.setEnabled(!show);
	}

	@OnClick(R.id.button_sign_in)
	void signIn(){
		showProgressbar(true);
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	private void signOut(){
		try{
			showProgressbar(true);

			// Firebase Logout
			FirebaseAuth.getInstance().signOut();

			// Configure Google Sign In
			GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					.requestIdToken(getString(R.string.default_web_client_id))
					.requestEmail()
					.requestProfile()
					.build();

			mGoogleApiClient.connect(); // wait for connection

			// delete local data
			UserDbHelper userDbHelper = new UserDbHelper(getApplicationContext());
			userDbHelper.removeUserInfo();

			returnActivityResult();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		// on GoogleApiClient connection SignOut
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(@NonNull Status status) {

					}
				}
		);
	}

	@Override
	public void onConnectionSuspended(int i) {}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setResult(RESULT_CANCELED);
	}

	public static boolean isUserSignedIn(){
		return FirebaseAuth.getInstance().getCurrentUser()!=null;
	}
	public static boolean isUserInfoRegistered(Context context){
		if (isUserSignedIn()){
			UserDbHelper userDbHelper = new UserDbHelper(context);
			return userDbHelper.isUserInfoAvailable(
					FirebaseAuth.getInstance().getCurrentUser().getEmail()
			);
		}
		return false;
	}
	private void returnActivityResult(){
		if (getParent() == null)
			setResult(Activity.RESULT_OK);
		else {
			getParent().setResult(RESULT_OK);
		}
		finish();
	}
}
