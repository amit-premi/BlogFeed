package com.amit.blogfeed.model;

import com.amit.blogfeed.pojomodel.BlogLiveDataSet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomePageViewModel extends ViewModel {

    private HomePageAPIServiceModel homePageAPIServiceModel;
    private static LiveData<BlogLiveDataSet> mBlogFeedDataSet;

    public HomePageViewModel() {
        homePageAPIServiceModel = new HomePageAPIServiceModel();
    }

    /**
     * Method call to get the Blog Feed Data Set
     *
     * @return
     */
    public LiveData<BlogLiveDataSet> getBlogFeedsData() {
        if (homePageAPIServiceModel == null) return null;

        if (mBlogFeedDataSet == null) {
            mBlogFeedDataSet = homePageAPIServiceModel.getBlogFeedsAPICall();
        }
        return mBlogFeedDataSet;
    }
}
