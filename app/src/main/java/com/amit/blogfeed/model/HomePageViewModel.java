package com.amit.blogfeed.model;

import com.amit.blogfeed.pojomodel.BlogLiveDataSet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel class for the HomePage, which helps to retain & fetch the Blog Feed Data required for the HomePage
 * <p>
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomePageViewModel extends ViewModel {

    private HomePageAPIServiceModel homePageAPIServiceModel;
    private static LiveData<BlogLiveDataSet> mBlogFeedDataSet;

    public HomePageViewModel() {
        homePageAPIServiceModel = new HomePageAPIServiceModel();
    }

    /**
     * Method call through ViewModel to get the Blog Feed Data Set
     *
     * @param isCallForRefresh
     * @param isNetworkConnected
     * @return
     */
    public LiveData<BlogLiveDataSet> getBlogFeedsData(boolean isCallForRefresh, boolean isNetworkConnected) {
        if (homePageAPIServiceModel == null) return null;

        //Trying to use the existing DATA but it can be made more efficient based upon the Requirement by using ROOM database or Other means
        if (mBlogFeedDataSet == null || (isCallForRefresh && isNetworkConnected)) {
            mBlogFeedDataSet = homePageAPIServiceModel.getBlogFeedsAPICall();
        }
        return mBlogFeedDataSet;
    }
}
