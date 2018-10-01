package com.amit.blogfeed.apiutils;

import com.amit.blogfeed.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class RetrofitClient {

    private static IRetrofitAPI iRetrofitAPI = null;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    /**
     * Method call which returns the Retrofit client instance to make API call
     *
     * @return
     */
    public static IRetrofitAPI getInstance() {
        if (iRetrofitAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.FEED_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            iRetrofitAPI = retrofit.create(IRetrofitAPI.class);
        }
        return iRetrofitAPI;
    }
}
