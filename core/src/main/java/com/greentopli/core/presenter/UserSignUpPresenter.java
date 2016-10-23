package com.greentopli.core.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.greentopli.core.handler.UserDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.model.User;

/**
 * Created by rnztx on 23/10/16.
 */

public class UserSignUpPresenter extends BasePresenter<SignUpView> {
	private UserDbHandler userDbHandler;
	public static UserSignUpPresenter bind(SignUpView view, Context context){
		UserSignUpPresenter presenter = new UserSignUpPresenter();
		presenter.attachView(view,context);
		return presenter;
	}

	@Override
	public void attachView(SignUpView mvpView, Context context) {
		super.attachView(mvpView, context);
		userDbHandler = new UserDbHandler(context);
	}

	public void signUp(@NonNull User user) throws RuntimeException{

		if (userDbHandler.storeUserInfo(user)<=0)
			getmMvpView().onRegistrationError("Failed to store");
		else
			getmMvpView().onRegistrationSuccess();

	}
}
