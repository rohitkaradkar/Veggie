package io.github.karadkar.veggie.user.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.greentopli.core.presenter.signup.SignUpView;
import com.greentopli.core.presenter.signup.UserSignUpPresenter;

/**
 * Created by rnztx on 20/11/16.
 */

public class InstanceIdService extends FirebaseInstanceIdService implements SignUpView {
	private static final String TAG = InstanceIdService.class.getSimpleName();
	private UserSignUpPresenter mPresenter;

	@Override
	public void onTokenRefresh() {
		super.onTokenRefresh();
		// this ID identifies user while sending Notification from server.
		String newInstanceId = FirebaseInstanceId.getInstance().getToken();
		if (newInstanceId != null && !newInstanceId.isEmpty()) {
			mPresenter = UserSignUpPresenter.bind(this, getApplicationContext());
			mPresenter.updateInstanceId(newInstanceId);
		}
	}

	@Override
	public void onSignUpError(String message) {
		Log.d(TAG, message);
	}

	@Override
	public void onSignUpSuccess() {
		Log.d(TAG, "Instance id updated");
	}

	@Override
	public void showProgressbar(boolean show) {

	}
}
