package com.amit.blogfeed.pojomodel;

import com.amit.blogfeed.model.ServiceResponseStatus;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogLiveDataSet {

    private BlogResponse blogResponse;
    private @ServiceResponseStatus int responseStatus;

    public BlogLiveDataSet(BlogResponse blogResponse, @ServiceResponseStatus int serviceStatus){
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
