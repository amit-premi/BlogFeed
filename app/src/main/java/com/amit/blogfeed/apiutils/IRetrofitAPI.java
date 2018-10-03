package com.amit.blogfeed.apiutils;

import com.amit.blogfeed.pojomodel.BlogResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API Client Interface Callback Class
 * <p>
 * Created by Amit PREMI on 02-Oct-18.
 */
public interface IRetrofitAPI {

    @GET("facts.json")
    Call<BlogResponse> getBlogFeeds();
}
