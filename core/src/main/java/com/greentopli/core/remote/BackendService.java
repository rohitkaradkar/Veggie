package com.greentopli.core.remote;

import com.greentopli.model.BackendResult;
import com.greentopli.model.ProductInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BackendService {
	@POST("/_ah/api/backend/v1/saveProductInfo")
	Call<BackendResult> saveProductInfo(@Body ProductInfo productInfo);

	@POST("/_ah/api/backend/v1/updateProductInfo")
	Call<BackendResult> updateProductInfo(@Body ProductInfo productInfo);

	@GET("/_ah/api/backend/v1/getProductInfo")
	Call<ProductInfo> getProductInfo(@Query("product_id") String product_id);

	@POST("/_ah/api/backend/v1/deleteProductInfo")
	Call<BackendResult> deleteProductInfo(@Query("product_id") String product_id);

	// get All product Info Items
	@GET("/_ah/api/backend/v1/getProductInfoList")
	Call<List<ProductInfo>> getProductInfoList();
}
