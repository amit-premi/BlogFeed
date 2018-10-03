package com.amit.blogfeed.adapter;

import com.amit.blogfeed.databinding.RecycleViewBlogItemBinding;

import androidx.recyclerview.widget.RecyclerView;

/**
 * ViewHolder class used for the BlogFeed Adapter
 * <p>
 * Created by Amit PREMI on 04-Oct-18.
 */
public class BlogFeedViewHolder extends RecyclerView.ViewHolder {

    private RecycleViewBlogItemBinding mBlogDataBinding;

    //Parameterized constructor
    BlogFeedViewHolder(RecycleViewBlogItemBinding blogDataBinding) {
        super(blogDataBinding.getRoot());
        this.mBlogDataBinding = blogDataBinding;
    }

    /**
     * Method to Bind the BlogFeed List data set to the Adapter View
     */
    void bindDataToHolder(BlogFeedViewModel blogFeedViewModel) {
        this.mBlogDataBinding.setBlogViewModel(blogFeedViewModel);
    }

    /**
     * Method to get the BlogFeed Binding Instance
     *
     * @return
     */
    public RecycleViewBlogItemBinding getBlogDataBinding() {
        return mBlogDataBinding;
    }
}
