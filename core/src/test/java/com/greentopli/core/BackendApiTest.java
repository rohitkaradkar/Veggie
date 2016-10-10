package com.greentopli.core;


import com.google.api.client.http.HttpStatusCodes;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.BackendResult;
import com.greentopli.model.ProductInfo;
import com.greentopli.model.ProductType;
import com.greentopli.model.PurchaseType;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackendApiTest {
	private BackendService service = ServiceGenerator.createService(BackendService.class);
	private ProductInfo productInfo = new ProductInfo("lemon","limbu",ProductType.FRUIT_VEGETABLE,PurchaseType.QUANTITY);
	List<ProductInfo> mProductInfoList;

	// Save
	@Test public void test_a(){
		final CountDownLatch latch = new CountDownLatch(1);
		try {
			Call<Void> callSave = service.saveProductInfo(productInfo);
			callSave.enqueue(new Callback<Void>() {
				@Override
				public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
					assertEquals("Error Saving Product",HttpStatusCodes.STATUS_CODE_NO_CONTENT,response.code());
					latch.countDown();
				}

				@Override
				public void onFailure(Call<Void> call, Throwable t) {
					assertTrue("ERROR! Failed To Store",false);
					latch.countDown();
				}
			});
			latch.await();
		}catch (Exception e){
			assertTrue("Connection Failed",false);
			e.printStackTrace();
		}
	}
	public void test_b(){
		final CountDownLatch latch = new CountDownLatch(1);
		try {
			// use proper id here
			final String product_id = productInfo.getId();
			Call<ProductInfo> callRetrieve = service.getProductInfo(product_id);
				callRetrieve.enqueue(new Callback<ProductInfo>() {
					@Override
					public void onResponse(Call<ProductInfo> call, retrofit2.Response<ProductInfo> response) {
						assertTrue("Different OBJ Found"
								,response.body().getId().equals(product_id));
						latch.countDown();
					}

					@Override
					public void onFailure(Call<ProductInfo> call, Throwable t) {
						assertTrue("ERROR! Retrieve",false);
						latch.countDown();
					}
				});
			latch.await();
		}catch (Exception e){
			assertTrue("Connection Failed "+e.getMessage(),false);
			e.printStackTrace();
			latch.countDown();
		}
	}

	// DELETE
	@Test
	public void test_c(){
		final CountDownLatch latch = new CountDownLatch(1);
		try {
			final String product_id = "63003834-b3cd-44ca-aa97-c00770f5f817";
			Call<BackendResult> callDelete = service.deleteProductInfo(product_id);
			callDelete.enqueue(new Callback<BackendResult>() {
				@Override
				public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
					assertTrue(response.body().toString(),
							response.body().getResult());
					latch.countDown();
				}

				@Override
				public void onFailure(Call<BackendResult> call, Throwable t) {
					assertTrue("ERROR! Delete",false);
					latch.countDown();
				}
			});
			latch.await();
		}catch (Exception e){
			assertTrue(e.getMessage(),false);
			latch.countDown();
		}
	}

	 public void test_e(){
		try {
			final CountDownLatch latch = new CountDownLatch(1);
			Call<List<ProductInfo>> listCall = service.getProductInfoList();

			listCall.enqueue(new Callback<List<ProductInfo>>() {
				@Override
				public void onResponse(Call<List<ProductInfo>> call, Response<List<ProductInfo>> response) {
					assertNotNull("Code: "+response.code(),response.body());
					latch.countDown();
				}

				@Override
				public void onFailure(Call<List<ProductInfo>> call, Throwable t) {
					latch.countDown();
				}
			});
			latch.await();
		}catch (Exception e){
			assertTrue("Connection Failed",false);
			e.printStackTrace();
		}
	}
	/**
	 *
	 try {
	 final CountDownLatch latch = new CountDownLatch(1);
	 latch.await();
	 }catch (Exception e){
	 assertTrue("Connection Failed",false);
	 e.printStackTrace();
	 }
	 */
}