package com.amit.blogfeed.model;

import android.text.TextUtils;

import com.amit.blogfeed.apiutils.RetrofitClient;
import com.amit.blogfeed.apiutils.ServiceResponseStatus;
import com.amit.blogfeed.pojomodel.BlogLiveDataSet;
import com.amit.blogfeed.pojomodel.BlogResponse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Service Model Class to make the API calls
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

                            //Passing the Valid Blog Feed Data Set
                            blogLiveData.postValue(new BlogLiveDataSet(getBlogFeedValidDataSet(response.body()), ServiceResponseStatus.SUCCESS));
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

    /**
     * Method call to getValid BlogFeed DATA SET
     * All the Blog should MANDATORY HAVE A TITLE OR DESCRIPTION
     *
     * @param blogResponse
     * @return
     */
    private BlogResponse getBlogFeedValidDataSet(BlogResponse blogResponse) {
        if (blogResponse == null) return null;

        if (blogResponse.getBlogDetailsList() != null && blogResponse.getBlogDetailsList().size() > 0) {
            List<BlogResponse.BlogDetails> tempBlogDetailsList = new ArrayList<>();

            //Find the list items which doesn't have neither title or description. Those DATA set will be removed
            for (BlogResponse.BlogDetails blogDetails : blogResponse.getBlogDetailsList()) {
                if (TextUtils.isEmpty(blogDetails.getTitle()) && TextUtils.isEmpty(blogDetails.getDescription())) {
                    tempBlogDetailsList.add(blogDetails);
                }
            }

            if (tempBlogDetailsList.size() > 0) {
                //Updating the Blog Feed response list with removal of those data set which doesn't have title or description
                for (BlogResponse.BlogDetails tempBlogDetails : tempBlogDetailsList) {
                    blogResponse.getBlogDetailsList().remove(tempBlogDetails);
                }
            }
        }
        return blogResponse;
    }
}
