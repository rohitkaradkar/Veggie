package com.greentopli.core.presenter.signup;

import com.greentopli.core.presenter.base.MvpView;

/**
 * Created by rnztx on 23/10/16.
 */

public interface SignUpView extends MvpView{
	void onSignUpError(String message);
	void onSignUpSuccess();
}
