package com.greentopli.core;


import com.google.api.client.http.HttpStatusCodes;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.ProductInfo;
import com.greentopli.model.ProductType;
import com.greentopli.model.PurchaseType;
import com.greentopli.model.Response;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BackendApiTest {
	final CountDownLatch latch = new CountDownLatch(2);
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void data_store() throws Exception{
		BackendService service = ServiceGenerator.createService(BackendService.class);
		final ProductInfo productInfo = new ProductInfo("lemon","limbu",ProductType.FRUIT_VEGETABLE,PurchaseType.QUANTITY);
		Call<Void> callSave = service.saveProductInfo(productInfo);
		Call<ProductInfo> callRetrieve = service.getProductInfo(productInfo.getId());

		try {
			callSave.enqueue(new Callback<Void>() {
				@Override
				public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
					System.out.print(" SAVE Code: "+response.code());
					assertEquals("Error Saving Product",HttpStatusCodes.STATUS_CODE_NO_CONTENT,response.code());
					latch.countDown();
				}

				@Override
				public void onFailure(Call<Void> call, Throwable t) {
					assertTrue("ERROR! Failed To Store",false);
					latch.countDown();
				}
			});

			callRetrieve.enqueue(new Callback<ProductInfo>() {
				@Override
				public void onResponse(Call<ProductInfo> call, retrofit2.Response<ProductInfo> response) {
					assertEquals("Different OBJ Found",HttpStatusCodes.STATUS_CODE_OK,response.code());
					System.out.print(" Retrieve Code: "+response.code());
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
			assertTrue("Connection Failed",false);
			e.printStackTrace();
		}
	}
}