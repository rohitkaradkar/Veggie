package com.greentopli.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greentopli.core.presenter.base.MvpView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
	GoogleApiClient.ConnectionCallbacks, MvpView{
	GoogleApiClient mGoogleApiClient;
	@BindView(R.id.default_progressbar)ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		verifyUserAuth();
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}
	@OnClick(R.id.action_button)
	public void OnAction(){


	}
	private void verifyUserAuth(){
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		if (user==null)
			startActivity(new Intent(this,SignInActivity.class));
		else
			Toast.makeText(this,user.getEmail(),Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_sign_out:
				signOut();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void signOut(){
		showProgressbar(true);
		// Firebase Logout
		FirebaseAuth.getInstance().signOut();

		// Configure Google Sign In
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestIdToken(getString(R.string.default_web_client_id))
				.requestEmail()
				.requestProfile()
				.build();

		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.addConnectionCallbacks(this)
				.build();

		mGoogleApiClient.connect(); // wait for connection
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		// on GoogleApiClient connection SignOut
		Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
				new ResultCallback<Status>() {
					@Override
					public void onResult(@NonNull Status status) {
						verifyUserAuth();
					}
				}
		);
	}

	@Override
	public void onConnectionSuspended(int i) {showProgressbar(false);}
	@Override
	public void showProgressbar(boolean show) {
		if (show)
			progressBar.setVisibility(View.VISIBLE);
		else
			progressBar.setVisibility(View.GONE);
	}
}
