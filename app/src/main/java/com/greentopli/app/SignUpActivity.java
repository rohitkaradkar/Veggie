package com.greentopli.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
	@BindView(R.id.user_name_editText)EditText userNameEditText;
	@BindView(R.id.user_address_editText)EditText userAddressEditText;
	@BindView(R.id.user_pinCode_editText)EditText userPinCodeEditText;
	@BindView(R.id.user_mobileNo_editText)EditText userMobileNoEditText;
	@BindViews({R.id.user_name_editText,R.id.user_address_editText,R.id.user_pinCode_editText,R.id.user_mobileNo_editText})
	List<EditText> signUpViews;
	@BindString(R.string.error_empty_field)String error_empty;
	@BindString(R.string.error_mobile_digit)String error_mobile_digit;
	@BindString(R.string.error_picCode_digit)String error_pinCode_digit;
	UserSignUpPresenter presenter;
	FirebaseUser firebaseUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ButterKnife.bind(this);
		presenter = UserSignUpPresenter.bind(this,getApplicationContext());
		firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		userNameEditText.setText(firebaseUser.getDisplayName());
	}
	private void isInputValidated(){
		boolean valid  = true;
//		If any field is empty
		for (EditText view: signUpViews){
			if (view.getText().toString().isEmpty()) {
				view.setError(error_empty);
				valid
			}
		}
//		mobile no should be of 10 Digit
		int length_mobile = userMobileNoEditText.getText().length();
		if (length_mobile > 10 || length_mobile < 10)
			userMobileNoEditText.setError(error_mobile_digit);

//		pin code should be of 6 digit
		int lenght_pin = userPinCodeEditText.getText().length();
		if (lenght_pin > 6 || lenght_pin < 6)
			userPinCodeEditText.setError(error_pinCode_digit);
	}
	@OnClick(R.id.fab_signUp_activity)
	public void onFabClick(View view) {
		if (firebaseUser!=null){
			User user = new User(firebaseUser.getEmail());
			user.setName(firebaseUser.getDisplayName());
			user.setPhotoUrl(firebaseUser.getPhotoUrl().toString());
			presenter.signUp(user);
		}
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
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

	}
}
