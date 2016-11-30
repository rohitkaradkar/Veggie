package com.greentopli.core.remote;

import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;
import com.greentopli.model.PurchasedItem;
import com.greentopli.model.User;
import com.greentopli.model.list.EntityList;
import com.greentopli.model.list.UserOrders;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rnztx on 19/10/16.
 */

public interface BackendConnectionService {
	@POST("/_ah/api/server/v1/purchase")
	Call<BackendResult> storePurchasedItems(@Body UserOrders items);

	@POST("/_ah/api/server/v1/signUpUser")
	Call<BackendResult> signUpUser(@Body User user);

	@GET("/_ah/api/server/v1/getPurchasedItemList")
	Call<EntityList<PurchasedItem>> getUserOrderHistory(@Query("user_id") String user_id);

	@GET("/_ah/api/backend/v1/getProductInfoList")
	Call<EntityList<Product>> getProductInfoList();
}
