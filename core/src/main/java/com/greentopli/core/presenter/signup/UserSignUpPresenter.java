package com.greentopli.core.presenter.signup;

import android.content.Context;
import android.support.annotation.NonNull;

import com.greentopli.core.handler.UserDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.UserService;
import com.greentopli.model.BackendResult;
import com.greentopli.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 23/10/16.
 */

public class UserSignUpPresenter extends BasePresenter<SignUpView> {
	private UserDbHandler userDbHandler;
	private Call<BackendResult> signUpCall;

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

	public void signUp(@NonNull final User user){
		UserService service = ServiceGenerator.createService(UserService.class);
		signUpCall = service.signUpUser(user);
		signUpCall.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
				// Stored on server
				if (response.body()!=null && response.body().isResult()){
					// now store locally
					if (userDbHandler.storeUserInfo(user)<=0)
						getmMvpView().onSignUpError("Failed to store Locally");
					else
						getmMvpView().onSignUpSuccess();
				}
				else { // Failed to store on server
					getmMvpView().onSignUpError("Error uploading data");
				}

				getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<BackendResult> call, Throwable t) {
				getmMvpView().onSignUpError(" Connection Error "+t.getMessage());
				getmMvpView().showProgressbar(false);
			}
		});

	}
}
