package com.amit.blogfeed.apiutils;

import com.amit.blogfeed.pojomodel.BlogModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public interface IRetrofitAPI {

    @GET("facts.json")
    Call<BlogModel> getBlogFeeds();
}
