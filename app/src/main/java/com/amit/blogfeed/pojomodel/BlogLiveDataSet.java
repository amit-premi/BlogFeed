package com.amit.blogfeed.pojomodel;

import com.amit.blogfeed.apiutils.ServiceResponseStatus;

/**
 * BlogLiveData set to be used for Passing to the Activity from API response.
 * It contains extra parameter about Service Call Status
 * <p>
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogLiveDataSet {

    private BlogResponse blogResponse;
    private @ServiceResponseStatus
    int responseStatus;

    public BlogLiveDataSet(BlogResponse blogResponse, @ServiceResponseStatus int serviceStatus) {
        this.blogResponse = blogResponse;
        this.responseStatus = serviceStatus;
    }

    public BlogResponse getBlogResponse() {
        return blogResponse;
    }

    public int getResponseStatus() {
        return responseStatus;
    }
}
