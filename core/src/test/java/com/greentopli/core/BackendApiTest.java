//package com.greentopli.core;
//
//
//import com.greentopli.core.remote.BackendService;
//import com.greentopli.core.remote.ServiceGenerator;
//import com.greentopli.model.BackendResult;
//import com.greentopli.model.Product;
//import com.greentopli.model.ProductList;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static org.junit.Assert.*;
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class BackendApiTest {
//	private BackendService service = ServiceGenerator.createService(BackendService.class);
//	// to avoid multiple instances make it static
//	private static Product product;
////	= new Product("Lemon","limbu", Product.Type.FRUIT_VEGETABLE,
////			Product.Volume.WEIGHT);
//
//	// Sqave
//	@Test public void test_a_save(){
//		final CountDownLatch latch = new CountDownLatch(1);
//		try {
//			Call<BackendResult> callSave = service.saveProductInfo(product);
//			callSave.enqueue(new Callback<BackendResult>() {
//				@Override
//				public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
//					assertTrue(response.body().getMessage(),
//							response.body().isResult());
//					System.out.print(response.body().getMessage());
//					latch.countDown();
//				}
//
//				@Override
//				public void onFailure(Call<BackendResult> call, Throwable t) {
//
//					assertTrue("ERROR! Failed To Store",false);
//					latch.countDown();
//
//				}
//			});
//			latch.await();
//		}catch (Exception e){
//			assertTrue("Connection Failed",false);
//			e.printStackTrace();
//		}
//	}
//	@Test public void test_b_update(){
//		final CountDownLatch latch = new CountDownLatch(1);
//		Product product = this.product;
//		product.setName_english("Orange");
//		try {
//			Call<BackendResult> callSave = service.updateProductInfo(product);
//			callSave.enqueue(new Callback<BackendResult>() {
//				@Override
//				public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
//					assertTrue(response.body().getMessage(),
//							response.body().isResult());
//					System.out.print(response.body().getMessage());
//					latch.countDown();
//				}
//
//				@Override
//				public void onFailure(Call<BackendResult> call, Throwable t) {
//
//					assertTrue("ERROR! Failed To Store",false);
//					latch.countDown();
//
//				}
//			});
//			latch.await();
//		}catch (Exception e){
//			assertTrue("Connection Failed",false);
//			e.printStackTrace();
//		}
//	}
//	// Retrieve
//	@Test public void test_c_retrieve(){
//		final CountDownLatch latch = new CountDownLatch(1);
//		try {
//			// use proper id here
//			final String product_id = product.getId();
//			Call<Product> callRetrieve = service.getProductInfo(product_id);
//				callRetrieve.enqueue(new Callback<Product>() {
//					@Override
//					public void onResponse(Call<Product> call, retrofit2.Response<Product> response) {
//						latch.countDown();
//						assertTrue("Different OBJ Found"
//								,response.body().getId().equals(product_id));
//					}
//
//					@Override
//					public void onFailure(Call<Product> call, Throwable t) {
//						assertTrue("ERROR! Retrieve",false);
//						latch.countDown();
//					}
//				});
//			latch.await();
//		}catch (Exception e){
//			assertTrue("Connection Failed "+e.getMessage(),false);
//			e.printStackTrace();
//			latch.countDown();
//		}
//	}
//
//	// Get List
//	@Test public void test_d_get_list(){
//		try {
//			final CountDownLatch latch = new CountDownLatch(1);
//			Call<ProductList> listCall = service.getProductInfoList();
//
//			listCall.enqueue(new Callback<com.greentopli.model.ProductList>() {
//				@Override
//				public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//					assertTrue("Code: "+response.code(),response.body().size()>0);
//					latch.countDown();
//				}
//
//				@Override
//				public void onFailure(Call<List<Product>> call, Throwable t) {
//					latch.countDown();
//				}
//			});
//			latch.await();
//		}catch (Exception e){
//			assertTrue("Connection Failed",false);
//			e.printStackTrace();
//		}
//	}
//
//	// DELETE
//
//	@Test public void test_e_delete(){
//		final CountDownLatch latch = new CountDownLatch(1);
//		try {
//			final String product_id = product.getId();
//			Call<BackendResult> callDelete = service.deleteProductInfo(product_id);
//			callDelete.enqueue(new Callback<BackendResult>() {
//				@Override
//				public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
//					assertTrue(response.body().toString(),
//							response.body().isResult());
//					latch.countDown();
//				}
//
//				@Override
//				public void onFailure(Call<BackendResult> call, Throwable t) {
//					assertTrue("ERROR! Delete",false);
//					latch.countDown();
//				}
//			});
//			latch.await();
//		}catch (Exception e){
//			assertTrue(e.getMessage(),false);
//			latch.countDown();
//		}
//	}
//
//	/**
//	 *
//	 try {
//	 final CountDownLatch latch = new CountDownLatch(1);
//	 latch.await();
//	 }catch (Exception e){
//	 assertTrue("Connection Failed",false);
//	 e.printStackTrace();
//	 }
//	 */
//}