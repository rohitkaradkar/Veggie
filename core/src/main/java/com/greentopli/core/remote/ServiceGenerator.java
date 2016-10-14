package com.greentopli.core.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rnztx on 9/10/16.
 * https://futurestud.io/tutorials/retrofit-getting-started-and-android-client
 */

public class ServiceGenerator {
	private static final String BASE_URL = "http://192.168.43.155:8080";
	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

	private static Retrofit.Builder builder =
			new Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create());

	public static <S> S createService(Class<S> serviceClass) {
		Retrofit retrofit = builder.client(httpClient.build()).build();
		return retrofit.create(serviceClass);
	}
}
