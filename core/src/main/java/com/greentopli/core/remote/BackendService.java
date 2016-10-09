package com.greentopli.core.remote;

import com.greentopli.model.ProductInfo;
import com.greentopli.model.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rnztx on 9/10/16.
 */

public interface BackendService {
	@POST("/_ah/api/backend/v1/saveProductInfo")
	Call<Void> saveProductInfo(@Body ProductInfo productInfo);

	@GET("/_ah/api/backend/v1/getProductInfo")
	Call<ProductInfo> getProductInfo(@Query("product_id") String product_id);
}
