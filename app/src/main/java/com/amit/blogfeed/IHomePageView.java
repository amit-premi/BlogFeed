package com.amit.blogfeed;

import com.amit.blogfeed.pojomodel.BlogLiveDataSet;

/**
 * Interface class for the HomePage stating all the required functionality
 * Created by Amit PREMI on 02-Oct-18.
 */
public interface IHomePageView {

    /**
     * Method to initialize the view elements of the Activity
     */
    void initializeView();

    /**
     * Method to initialize the ViewModel instance
     */
    void initializeViewModel();

    /**
     * Method call to make the API call for getting the Blog Feed Data set from the Server
     *
     * @param isRefreshCall
     * @param isNetworkConnected
     */
    void getBlogFeedsAPI(boolean isRefreshCall, boolean isNetworkConnected);

    /**
     * Method to update the Adapter UI after the response of the API Call
     *
     * @param blogFeedResponse
     */
    void updateBlogAdapterUI(BlogLiveDataSet blogFeedResponse);

    /**
     * Method to show/hide the Network Error view based upon Device Network Availability
     *
     * @param isNetworkConnect
     */
    void updateUIForNetworkCheck(boolean isNetworkConnect);
}
