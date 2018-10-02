package com.amit.blogfeed;

import com.amit.blogfeed.pojomodel.BlogLiveDataSet;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public interface IHomePageView {

    void initializeView();

    void initializeViewModel();

    void getBlogFeedsAPI();

    void updateBlogAdapterUI(BlogLiveDataSet blogFeedResponse);
}
