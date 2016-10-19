package com.greentopli.app.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.greentopli.app.AuthenticatorActivity;
import com.greentopli.app.R;

public class PurchaseManagerActivity extends AppCompatActivity {
	private static final int REQUEST_SIGNIN = 210;
	private static final int REQUEST_SIGNOUT = 220;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!AuthenticatorActivity.isUserSignedIn())
			signIn();

		setContentView(R.layout.activity_purchase_manager);
	}
	private void signIn(){
		Intent intent = new Intent(this,AuthenticatorActivity.class);
		startActivityForResult(intent,REQUEST_SIGNIN);
	}
	private void signOut(){
		Intent signOutIntent = new Intent(this,AuthenticatorActivity.class);
		signOutIntent.putExtra(AuthenticatorActivity.EXTRA_SIGNOUT,true);
		startActivityForResult(signOutIntent,REQUEST_SIGNOUT);
	}
}
