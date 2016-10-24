package com.greentopli.app.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.greentopli.app.AuthenticatorActivity;
import com.greentopli.app.R;
import com.greentopli.app.SignUpActivity;

public class PurchaseManagerActivity extends AppCompatActivity implements OnFragmentInteractionListener {
	private static final int REQUEST_SIGNIN = 210;
	private static final int REQUEST_SIGNOUT = 220;
	private static final int REQUEST_SIGNUP = 230;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!AuthenticatorActivity.isUserSignedIn())
			signIn();
		else if (!AuthenticatorActivity.isUserSignedUp(getApplicationContext()))
			signUp();

		setContentView(R.layout.activity_purchase_manager);

		if (savedInstanceState == null){
			BrowseProductsFragment fragment = BrowseProductsFragment.getInstance();

			getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_purchase_manager_container,fragment)
					.addToBackStack(null)
					.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu,menu);
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

	@Override
	public void onFragmentInteraction() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.activity_purchase_manager_container, CartCheckoutFragment.newInstance())
				.addToBackStack(null)
				.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			if (requestCode == REQUEST_SIGNIN){
				if (!AuthenticatorActivity.isUserSignedUp(getApplicationContext()))
					signUp();
			}
			else if (requestCode == REQUEST_SIGNOUT){
				signIn();
			}

		}
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
	private void signUp(){
		startActivityForResult(new Intent(getApplicationContext(), SignUpActivity.class),REQUEST_SIGNUP);
	}
}
