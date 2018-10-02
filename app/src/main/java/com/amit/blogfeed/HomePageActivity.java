package com.amit.blogfeed;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.amit.blogfeed.model.HomePageViewModel;
import com.amit.blogfeed.model.ServiceResponseStatus;
import com.amit.blogfeed.pojomodel.BlogLiveDataSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomePageActivity extends AppCompatActivity implements IHomePageView {

    private HomePageViewModel mHomePageViewModel;
    private RecyclerView mRecycleViewBlogFeed;
    private HomeBlogFeedAdapter mHomeBlogFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initialize the view
        initializeView();
        //Initialize the View Model Parameters
        initializeViewModel();
        //Initiate API call for Blog Feed Data Set
        getBlogFeedsAPI();
    }

    @Override
    public void initializeView() {
        mRecycleViewBlogFeed = findViewById(R.id.recycle_view_blogs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecycleViewBlogFeed.setLayoutManager(layoutManager);
    }

    @Override
    public void initializeViewModel() {
        //Initializing view model with Activity LifeCycle
        mHomePageViewModel = ViewModelProviders.of(this).get(HomePageViewModel.class);
    }

    @Override
    public void getBlogFeedsAPI() {
        if (mHomePageViewModel == null) return;

        //Separation of LiveData & Observer helps to have more control, such as it helps to cancel the api call & UI updates anytime
        LiveData<BlogLiveDataSet> blogLiveData = mHomePageViewModel.getBlogFeedsData();
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
        if (isDestroyed() || mRecycleViewBlogFeed == null) return;

        if (blogFeedResponse.getBlogResponse() != null && blogFeedResponse.getBlogResponse().getBlogDetailsList() != null) {

            //Update the Header Title
            if (getSupportActionBar() != null && !TextUtils.isEmpty(blogFeedResponse.getBlogResponse().getTitle())) {
                getSupportActionBar().setTitle(blogFeedResponse.getBlogResponse().getTitle());
            }

            if(mHomeBlogFeedAdapter == null){
                //Update the BlogFeed Adapter
                mHomeBlogFeedAdapter = new HomeBlogFeedAdapter(this, blogFeedResponse.getBlogResponse().getBlogDetailsList());
                mRecycleViewBlogFeed.setAdapter(mHomeBlogFeedAdapter);
            }else{
                //Else case to handle the REFRESH & API providing PAGINATION functionality
                mHomeBlogFeedAdapter.updateAdapterDataSet(blogFeedResponse.getBlogResponse().getBlogDetailsList());
            }
        } else if (blogFeedResponse.getResponseStatus() != -1){

            String message = "";
            switch (blogFeedResponse.getResponseStatus()){
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
                    break;
            }

            if(!TextUtils.isEmpty(message)){
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
