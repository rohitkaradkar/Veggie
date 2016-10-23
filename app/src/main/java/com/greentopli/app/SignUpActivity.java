package com.greentopli.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.greentopli.core.presenter.SignUpView;
import com.greentopli.core.presenter.UserSignUpPresenter;
import com.greentopli.model.User;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity implements SignUpView{
	private static final String TAG = SignUpActivity.class.getSimpleName();
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
	private UserSignUpPresenter mPresenter;
	private FirebaseUser mFirebaseUser;
	private User mUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ButterKnife.bind(this);
		mPresenter = UserSignUpPresenter.bind(this,getApplicationContext());
		mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		userNameEditText.setText(mFirebaseUser.getDisplayName());
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
	@OnClick(R.id.fab_signUp_activity)
	public void onFabClick(View view) {
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
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
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
					}
				});
	}

	private void setInstanceId(){
		if (FirebaseInstanceId.getInstance()!=null)
			if(FirebaseInstanceId.getInstance().getToken()!=null){
				mUser.setInstanceId(
						FirebaseInstanceId.getInstance().getToken()
				);
				mPresenter.signUp(mUser);
			}
		else{
				showProgressbar(false);
				Log.e(TAG,"Failed to get Instance Id");
		}
	}

	@Override
	public void onRegistrationError(String message) {

	}

	@Override
	public void onRegistrationSuccess() {
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
	}
}
