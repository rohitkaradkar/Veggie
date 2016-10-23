package com.greentopli.core.presenter;

import com.greentopli.core.presenter.base.MvpView;

/**
 * Created by rnztx on 23/10/16.
 */

public interface SignUpView extends MvpView{
	void onRegistrationError(String message);
	void onRegistrationSuccess();
}
