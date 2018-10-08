package com.amit.blogfeed;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amit.blogfeed.adapter.BlogFeedViewModel;
import com.amit.blogfeed.adapter.HomeBlogFeedAdapter;
import com.amit.blogfeed.apiutils.ServiceResponseStatus;
import com.amit.blogfeed.model.HomePageViewModel;
import com.amit.blogfeed.pojomodel.BlogLiveDataSet;
import com.amit.blogfeed.utility.NetworkUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * HomePage Activity, it's the Landing Page of the Application
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomePageActivity extends AppCompatActivity implements IHomePageView {

    //View variables
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycleViewBlogFeed;
    private LinearLayoutManager layoutManagerRecycler;
    private TextView tEmptyRecyclerView;
    private RelativeLayout layoutNetworkError;

    //Instance variables
    private HomePageViewModel mHomePageViewModel;
    private HomeBlogFeedAdapter mHomeBlogFeedAdapter;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the view
        initializeView();
        //Initialize the View Model Parameters
        initializeViewModel();
        //Check for Network Availability & Initiate API call for Blog Feed Data Set
        getBlogFeedsAPI(false, NetworkUtils.isNetworkConnected(this));
    }

    @Override
    public void initializeView() {

        //SwipeRefresh Layout: it's color changes & Refresh Listener
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.swipeRefreshColors));
        swipeRefreshLayout.setDistanceToTriggerSync(20);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            //Check for Network Availability & Call for Blog Feed Refresh
            getBlogFeedsAPI(true, NetworkUtils.isNetworkConnected(this));
        });

        //RecyclerView & Layout Manager for Blog feed Adapter
        recycleViewBlogFeed = findViewById(R.id.recycle_view_blog);
        layoutManagerRecycler = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManagerRecycler.setSmoothScrollbarEnabled(true);
        recycleViewBlogFeed.setLayoutManager(layoutManagerRecycler);
        tEmptyRecyclerView = findViewById(R.id.t_home_recycle_empty);

        //Scroll-Listener on the Recycler View to enable/disable the SwipeRefresh based upon item visibility
        recycleViewBlogFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //This recycler view position check to enable/disable the SwipeRefresh Layout
                if (layoutManagerRecycler.findFirstCompletelyVisibleItemPosition() == 0)
                    swipeRefreshLayout.setEnabled(true);
                else
                    swipeRefreshLayout.setEnabled(false);
            }
        });

        //Network Error View & Network Retry click listener
        layoutNetworkError = findViewById(R.id.layout_home_no_network);
        findViewById(R.id.t_home_network_retry).setOnClickListener(retryView -> {
            //Check for network & Call for Blog Feed DataSet
            getBlogFeedsAPI(true, NetworkUtils.isNetworkConnected(this));
        });
    }

    @Override
    public void initializeViewModel() {
        //Initializing view model with Activity LifeCycle
        mHomePageViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
    }

    @Override
    public void getBlogFeedsAPI(boolean isRefreshCall, boolean isNetworkConnected) {
        if (mHomePageViewModel == null) return;

        //Show the SwipeRefresh Refresh Animation if not visible. Possible case of first time load or Retry Network
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        //Separation of LiveData & Observer helps to have more control, such as it helps to cancel the api call & UI updates anytime
        LiveData<BlogLiveDataSet> blogLiveData = mHomePageViewModel.getBlogFeedsData(isRefreshCall, isNetworkConnected);
        Observer<BlogLiveDataSet> observerLiveData = blogLiveDataSet -> {
            //Update the Adapter
            if (blogLiveDataSet != null) {
                //Update the UI after API response
                updateBlogAdapterUI(blogLiveDataSet);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.api_unexpected_error), Toast.LENGTH_SHORT).show();
            }

            //Remove the SwipeRefresh Refresh Animation
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        };
        blogLiveData.observe(this, observerLiveData);
    }

    @Override
    public void updateBlogAdapterUI(BlogLiveDataSet blogFeedResponse) {
        if (isDestroyed() || recycleViewBlogFeed == null) return;

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

                    //Hiding the Empty Adapter Text Indicator
                    if (tEmptyRecyclerView.getVisibility() == View.VISIBLE) {
                        tEmptyRecyclerView.setVisibility(View.GONE);
                    }
                } else {
                    tEmptyRecyclerView.setVisibility(View.VISIBLE);
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
