package com.greentopli.core.remote;

import android.os.AsyncTask;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.greentopli.backend.productApi.ProductApi;
import com.greentopli.core.model.Constants;
import com.greentopli.core.model.ProductInfo;


import java.io.IOException;

/**
 * Created by rnztx on 9/10/16.
 */

public class ProductTask extends AsyncTask<Void,Void,Boolean>{
	private ProductInfo productInfo;
	private ProductApi productApi;
	private ResultListener listener;

	public ProductTask(ProductInfo productInfo){
		this.productInfo = productInfo;
	}

	public void setListener(ResultListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onPostExecute(Boolean success) {
		super.onPostExecute(success);
		if (listener!=null){
			if (success)
				listener.onSuccess();
			else
				listener.onError();
		}
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		if (productApi==null){
			ProductApi.Builder builder = new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),
					new JacksonFactory(),null)
					.setRootUrl(Constants.SERVER_ADDRESS)
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
						@Override
						public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
							request.setDisableGZipContent(true);
						}
					});
			productApi = builder.build();
			try {
//				productApi.saveProductInfo(productInfo).execute();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	public interface ResultListener{
		public void onSuccess();
		public void onError();
	}
}
