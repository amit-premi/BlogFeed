package com.amit.blogfeed.model;

import com.amit.blogfeed.apiutils.RetrofitClient;
import com.amit.blogfeed.pojomodel.BlogLiveDataSet;
import com.amit.blogfeed.pojomodel.BlogResponse;

import java.net.UnknownHostException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
class HomePageAPIServiceModel {

    HomePageAPIServiceModel() {
    }

    /**
     * API method call to get the BlogFeeds Data set from the service endpoint
     *
     * @return
     */
    LiveData<BlogLiveDataSet> getBlogFeedsAPICall() {
        final MutableLiveData<BlogLiveDataSet> blogLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getBlogFeeds()
                .enqueue(new Callback<BlogResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<BlogResponse> call, @NonNull Response<BlogResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            blogLiveData.postValue(new BlogLiveDataSet(response.body(), ServiceResponseStatus.SUCCESS));
                        } else {
                            if (response.errorBody() != null) {
                                blogLiveData.postValue(new BlogLiveDataSet(null, ServiceResponseStatus.ERROR));
                            } else {
                                blogLiveData.postValue(new BlogLiveDataSet(null, ServiceResponseStatus.INVALID));
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<BlogResponse> call, @NonNull Throwable t) {
                        if (t instanceof UnknownHostException) {
                            blogLiveData.postValue(new BlogLiveDataSet(null, ServiceResponseStatus.NETWORK_ERROR));
                        } else {
                            blogLiveData.postValue(new BlogLiveDataSet(null, ServiceResponseStatus.FAILURE));
                        }
                    }
                });

        return blogLiveData;
    }
}
