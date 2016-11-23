package com.greentopli.app.user.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.greentopli.Constants;
import com.greentopli.app.R;
import com.greentopli.core.storage.helper.UserDbHelper;
import com.greentopli.core.presenter.signup.SignUpView;
import com.greentopli.core.presenter.signup.UserSignUpPresenter;
import com.greentopli.model.User;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends AppCompatActivity implements SignUpView{
	private static final String TAG = UserInfoActivity.class.getSimpleName();
	@BindView(R.id.container_userInfo_activity) CoordinatorLayout mContainer;
	@BindView(R.id.fab_userInfo_activity) FloatingActionButton fab;
	@BindView(R.id.user_avatar) ImageView avatarImageView;
	@BindView(R.id.user_name_editText)EditText userNameEditText;
	@BindView(R.id.user_address_editText)EditText userAddressEditText;
	@BindView(R.id.user_pinCode_editText)EditText userPinCodeEditText;
	@BindView(R.id.user_mobileNo_editText)EditText userMobileNoEditText;
	@BindView(R.id.progressbar_signUp_activity) ProgressBar progressBar;
	@BindViews({R.id.user_name_editText,R.id.user_address_editText,R.id.user_pinCode_editText,R.id.user_mobileNo_editText})
	List<EditText> signUpViews;
	@BindString(R.string.error_empty_field)String error_empty;
	@BindString(R.string.error_mobile_digit)String error_mobile_digit;
	@BindString(R.string.error_picCode_digit)String error_pinCode_digit;
	@BindString(R.string.error_registration)String error_registration;
	@BindString(R.string.error_not_registered) String error_not_registered;
	@BindString(R.string.action_retry)String action_retry;

	private UserSignUpPresenter mPresenter;
	private FirebaseUser mFirebaseUser;
	private User mUser;
	private UserDbHelper mUserDbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ButterKnife.bind(this);
		mPresenter = UserSignUpPresenter.bind(this,getApplicationContext());
		mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		mUserDbHelper = new UserDbHelper(getApplicationContext());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		User user = mUserDbHelper.getSignedUserInfo();
		if (user!=null){
			userNameEditText.setText(user.getName());
			userAddressEditText.setText(user.getAddress());
			userPinCodeEditText.setText(String.valueOf(user.getPincode()));
			userMobileNoEditText.setText(String.valueOf(user.getMobileNo()));
		}
		else
			userNameEditText.setText(mFirebaseUser.getDisplayName());

		Glide.with(getApplicationContext())
				.load(mFirebaseUser.getPhotoUrl())
				.asBitmap().into(new BitmapImageViewTarget(avatarImageView){
			// refer : http://stackoverflow.com/a/32390715/2804351
			@Override
			protected void setResource(Bitmap resource) {
				RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),resource);
				drawable.setCircular(true);
				avatarImageView.setImageDrawable(drawable);
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home){
			User user = mUserDbHelper.getSignedUserInfo();
			if (user==null){
				Toast.makeText(getApplicationContext(), error_not_registered,Toast.LENGTH_LONG)
						.show();
			}else
				onSignUpSuccess();
			return true;
		}
		return false;
	}

	private boolean isInputValidated() {
		boolean valid = true;
//		If any field is empty
		for (EditText view : signUpViews) {
			if (view.getText().toString().isEmpty()) {
				view.setError(error_empty);
				valid = false;
			}
		}
//		mobile no should be of 10 Digit
		int length_mobile = userMobileNoEditText.getText().length();
		if (length_mobile > 10 || length_mobile < 10){
			userMobileNoEditText.setError(error_mobile_digit);
			valid = false;
		}
//		pin code should be of 6 digit
		int lenght_pin = userPinCodeEditText.getText().length();
		if (lenght_pin > 6 || lenght_pin < 6){
			userPinCodeEditText.setError(error_pinCode_digit);
			valid = false;
		}
		return valid;
	}
	@OnClick(R.id.fab_userInfo_activity)
	public void onSaveUserInfo() {
		if (mFirebaseUser !=null && isInputValidated()){
			showProgressbar(true);
			mUser = new User(mFirebaseUser.getEmail());
			mUser.setName(mFirebaseUser.getDisplayName());
			mUser.setPhotoUrl(mFirebaseUser.getPhotoUrl().toString());
			mUser.setAddress(userAddressEditText.getText().toString());
			mUser.setPincode(Integer.valueOf(userPinCodeEditText.getText().toString()));
			mUser.setMobileNo(Long.valueOf(userMobileNoEditText.getText().toString()));
			setAuthToken();
		}
	}
	private void setAuthToken(){
		mFirebaseUser.getToken(true)
				.addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
					@Override
					public void onSuccess(GetTokenResult getTokenResult) {
						mUser.setAuthToken(getTokenResult.getToken());
						setInstanceId();
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						e.printStackTrace();
						showProgressbar(false);
						// Report Firebase Crash
						FirebaseCrash.report(e);
						retrySnackbar();
					}
				});
	}

	private void setInstanceId(){
		if (FirebaseInstanceId.getInstance()!=null) {
			String token = FirebaseInstanceId.getInstance().getToken();
			if (token != null && !token.isEmpty()) {
				mUser.setInstanceId(token);
				mPresenter.signUp(mUser);
			} else {
				showProgressbar(false);
				Log.e(TAG, "Failed to get Instance Id");
				// log Firebase Crash
				FirebaseCrash.log(Constants.ERROR_INSTANCEID);
				retrySnackbar();
			}
		}
	}

	@Override
	public void onSignUpError(String message) {
		Log.e(TAG, Constants.ERROR_REGISTERING_USERINFO+message);
		Toast.makeText(getApplicationContext(),Constants.ERROR_REGISTERING_USERINFO,Toast.LENGTH_SHORT).show();
		// log Firebase Crash
		FirebaseCrash.log(Constants.ERROR_REGISTERING_USERINFO+message);
		retrySnackbar();
	}

	@Override
	public void onSignUpSuccess() {
		if (getParent() == null)
			setResult(Activity.RESULT_OK);
		else {
			getParent().setResult(RESULT_OK);
		}
		finish();
	}

	@Override
	public void showProgressbar(boolean show) {
		progressBar.setVisibility(show?View.VISIBLE:View.GONE);
		fab.setEnabled(!show);
	}

	private void retrySnackbar(){
		Snackbar.make(mContainer, error_registration,Snackbar.LENGTH_LONG)
				.setAction(action_retry, new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// retry User Registration
						onSaveUserInfo();
					}
				})
				.show();
	}
}
