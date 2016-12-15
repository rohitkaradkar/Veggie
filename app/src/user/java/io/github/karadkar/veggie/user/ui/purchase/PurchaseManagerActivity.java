package io.github.karadkar.veggie.user.ui.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.github.karadkar.veggie.AuthenticatorActivity;
import com.greentopli.app.R;
import io.github.karadkar.veggie.user.ui.OrderHistoryActivity;
import io.github.karadkar.veggie.user.ui.UserInfoActivity;
import com.greentopli.core.service.OrderHistoryService;

public class PurchaseManagerActivity extends AppCompatActivity implements OnFragmentInteractionListener {
	private static final int REQUEST_SIGNIN = 210;
	private static final int REQUEST_SIGNOUT = 220;
	private static final int REQUEST_USER_DETAILS = 230;
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		if (!AuthenticatorActivity.isUserSignedIn()) {
			signIn();
		} else if (!AuthenticatorActivity.isUserInfoRegistered(getApplicationContext())) {
			registerUserDetails();
		}

		setContentView(R.layout.activity_purchase_manager);

		if (savedInstanceState == null) {
			BrowseProductsFragment fragment = BrowseProductsFragment.getInstance();

			getSupportFragmentManager().beginTransaction()
					.add(R.id.activity_purchase_manager_container, fragment)
					.commit();
			// first fragment should not have addToBackStack("")
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_sign_out:
				signOut();
				return true;
			case R.id.menu_order_history:
				startActivity(new Intent(mContext, OrderHistoryActivity.class));
				return true;
			case R.id.menu_user_details:
				Intent intent = new Intent(mContext, UserInfoActivity.class);
				startActivityForResult(intent, REQUEST_USER_DETAILS);
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onFragmentInteraction() {
		//http://stackoverflow.com/a/9856449/2804351
		getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left,
						R.anim.slide_in_from_left, R.anim.slide_out_to_right)
				.replace(R.id.activity_purchase_manager_container, CartCheckoutFragment.newInstance())
				.addToBackStack(null)
				.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_SIGNIN) {
				// request user information
				if (!AuthenticatorActivity.isUserInfoRegistered(getApplicationContext()))
					registerUserDetails();

			} else if (requestCode == REQUEST_SIGNOUT) {
				signIn();
			} else if (requestCode == REQUEST_USER_DETAILS) {
				// download User Order history in background
				OrderHistoryService.start(mContext);
			}
		} else if (resultCode == RESULT_CANCELED) {
			// onBackPressed while SignIn
			if (requestCode == REQUEST_SIGNIN && !AuthenticatorActivity.isUserSignedIn()) {
				finish();
			}
			// onBackPressed while registering User Details
			else if (requestCode == REQUEST_USER_DETAILS && !AuthenticatorActivity.isUserInfoRegistered(getApplicationContext())) {
				finish();
			}
		}
	}

	private void signIn() {
		Intent intent = new Intent(this, AuthenticatorActivity.class);
		startActivityForResult(intent, REQUEST_SIGNIN);
	}

	private void signOut() {
		Intent signOutIntent = new Intent(this, AuthenticatorActivity.class);
		signOutIntent.putExtra(AuthenticatorActivity.EXTRA_SIGNOUT, true);
		startActivityForResult(signOutIntent, REQUEST_SIGNOUT);
	}

	private void registerUserDetails() {
		startActivityForResult(new Intent(mContext, UserInfoActivity.class), REQUEST_USER_DETAILS);
	}
}
