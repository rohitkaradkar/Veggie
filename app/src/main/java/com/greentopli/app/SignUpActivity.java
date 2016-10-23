package com.greentopli.app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greentopli.app.R;
import com.greentopli.core.presenter.SignUpView;
import com.greentopli.core.presenter.UserSignUpPresenter;
import com.greentopli.model.User;

public class SignUpActivity extends AppCompatActivity implements SignUpView{
	UserSignUpPresenter presenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		presenter = UserSignUpPresenter.bind(this,getApplicationContext());
		FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
		if (firebaseUser!=null){
			User user = new User(firebaseUser.getEmail());
			user.setName(firebaseUser.getDisplayName());

			presenter.signUp(user);
		}

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}

	@Override
	public void onRegistrationError(String message) {

	}

	@Override
	public void showProgressbar(boolean show) {

	}
}
