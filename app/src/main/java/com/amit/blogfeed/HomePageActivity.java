package com.amit.blogfeed;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amit.blogfeed.adapter.BlogFeedViewModel;
import com.amit.blogfeed.adapter.HomeBlogFeedAdapter;
import com.amit.blogfeed.apiutils.ServiceResponseStatus;
import com.amit.blogfeed.model.HomePageViewModel;
import com.amit.blogfeed.pojomodel.BlogLiveDataSet;
import com.amit.blogfeed.pojomodel.BlogResponse;
import com.amit.blogfeed.utility.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * HomePage Activity, it's the Landing Page of the Application
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomePageActivity extends AppCompatActivity implements IHomePageView {

    //View variables
    private RelativeLayout layoutNetworkError;
    private Button btnRefresh;
    private RecyclerView recycleViewBlogFeed;

    //Instance variables
    private HomePageViewModel mHomePageViewModel;
    private HomeBlogFeedAdapter mHomeBlogFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the view
        initializeView();
        //Initialize the View Model Parameters
        initializeViewModel();
        //Check for Network Availability & Initiate API call for Blog Feed Data Set
        if (NetworkUtils.isNetworkConnected(this)) {
            getBlogFeedsAPI(false);
        }
    }

    @Override
    public void initializeView() {

        //Refresh Button & it's click listener
        btnRefresh = findViewById(R.id.btn_home_refresh);
        btnRefresh.setOnClickListener(view -> {
            //Check for Network Availability & Call for Blog Feed Refresh
            if (NetworkUtils.isNetworkConnected(this)) {
                getBlogFeedsAPI(true);

                //Disable the Refresh Button to avoid multiple simultaneous clicks
                btnRefresh.setEnabled(false);
            }
        });

        //RecyclerView & Layout Manager for Blog feed Adapter
        recycleViewBlogFeed = findViewById(R.id.recycle_view_blog);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        recycleViewBlogFeed.setLayoutManager(layoutManager);

        //Network Error View & Network Retry click listener
        layoutNetworkError = findViewById(R.id.layout_home_no_network);
        findViewById(R.id.t_home_network_retry).setOnClickListener(retryView -> {
            //Check for network & Call for Blog Feed DataSet
            if (NetworkUtils.isNetworkConnected(this)) {
                getBlogFeedsAPI(true);
            }
        });
    }

    @Override
    public void initializeViewModel() {
        //Initializing view model with Activity LifeCycle
        mHomePageViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
    }

    @Override
    public void getBlogFeedsAPI(boolean isRefreshCall) {
        if (mHomePageViewModel == null) return;

        //Separation of LiveData & Observer helps to have more control, such as it helps to cancel the api call & UI updates anytime
        LiveData<BlogLiveDataSet> blogLiveData = mHomePageViewModel.getBlogFeedsData(isRefreshCall);
        Observer<BlogLiveDataSet> observerLiveData = blogLiveDataSet -> {
            if (blogLiveDataSet != null) {
                //Update the UI after API response
                updateBlogAdapterUI(blogLiveDataSet);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.api_unexpected_error), Toast.LENGTH_SHORT).show();
            }
        };
        blogLiveData.observe(this, observerLiveData);
    }

    @Override
    public void updateBlogAdapterUI(BlogLiveDataSet blogFeedResponse) {
        if (isDestroyed() || recycleViewBlogFeed == null) return;

        //Enable the Refresh Button, which was disabled to avoid multiple simultaneous clicks provided it was disabled
        if (!btnRefresh.isEnabled()) {
            btnRefresh.setEnabled(true);
        }

        //When response is not NULL & Blog List is NOT NULL
        if (blogFeedResponse.getBlogResponse() != null && blogFeedResponse.getBlogResponse().getBlogDetailsList() != null) {

            //Update the Header Title
            if (!TextUtils.isEmpty(blogFeedResponse.getBlogResponse().getTitle())) {
                ((TextView) findViewById(R.id.t_home_title)).setText(blogFeedResponse.getBlogResponse().getTitle());
            }

            //Create/Update the BlogFeed Adapter
            if (mHomeBlogFeedAdapter == null) {
                //Check for whether the received Blog List is SIZE ZERO or NOT simultaneously update the VIEW
                if (blogFeedResponse.getBlogResponse().getBlogDetailsList().size() > 0) {

                    mHomeBlogFeedAdapter = new HomeBlogFeedAdapter(HomePageActivity.this, BlogFeedViewModel.getBlogFeedAdapterList(blogFeedResponse.
                            getBlogResponse().getBlogDetailsList()));
                    recycleViewBlogFeed.setVisibility(View.VISIBLE);
                    recycleViewBlogFeed.setAdapter(mHomeBlogFeedAdapter);
                } else {
                    findViewById(R.id.t_home_recycle_empty).setVisibility(View.VISIBLE);
                    recycleViewBlogFeed.setVisibility(View.GONE);
                }
            } else {
                //Else case to handle the REFRESH & API providing PAGINATION functionality
               mHomeBlogFeedAdapter.updateAdapterDataSet(BlogFeedViewModel.getBlogFeedAdapterList(blogFeedResponse.
                       getBlogResponse().getBlogDetailsList()));
            }

        } else if (blogFeedResponse.getResponseStatus() != -1) {
            //ELSE case to Handle the API Error/Issue situations
            String message = "";
            switch (blogFeedResponse.getResponseStatus()) {
                case ServiceResponseStatus.SUCCESS:
                    break;

                case ServiceResponseStatus.FAILURE:
                    message = getString(R.string.api_failure);
                    break;

                case ServiceResponseStatus.ERROR:
                    message = getString(R.string.api_unexpected_error);
                    break;

                case ServiceResponseStatus.INVALID:
                    message = getString(R.string.api_unexpected_error);
                    break;

                case ServiceResponseStatus.NETWORK_ERROR:
                    layoutNetworkError.setVisibility(View.VISIBLE);
                    break;
            }

            //Show/Notify the relevant error messages to user
            if (!TextUtils.isEmpty(message)) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void updateUIForNetworkCheck(boolean isNetworkConnect) {
        if (layoutNetworkError == null) return;

        if (isNetworkConnect) {
            if (layoutNetworkError.getVisibility() == View.VISIBLE) {
                layoutNetworkError.setVisibility(View.GONE);
            }
        } else {
            layoutNetworkError.setVisibility(View.VISIBLE);
        }
    }
}
