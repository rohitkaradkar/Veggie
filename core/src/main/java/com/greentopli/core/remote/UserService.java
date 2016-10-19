package com.greentopli.core.remote;

import com.greentopli.model.BackendResult;
import com.greentopli.model.PurchaseEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by rnztx on 19/10/16.
 */

public interface UserService {
	@POST("/_ah/api/server/v1/purchase")
	Call<BackendResult> purchaseProduct(@Body PurchaseEntity entity);
}
