package com.amit.blogfeed.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amit.blogfeed.databinding.RecycleViewBlogItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter Class to display the BlogFeed on the Home Page
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomeBlogFeedAdapter extends RecyclerView.Adapter<BlogFeedViewHolder> {

    private Context mContext;
    private List<BlogFeedViewModel> mBlogFeedList;
    private LayoutInflater mLayoutInflater;

    //Parameterized Class constructor
    public HomeBlogFeedAdapter(Context context, List<BlogFeedViewModel> responseList) {
        this.mContext = context;
        this.mBlogFeedList = responseList;
    }

    @NonNull
    @Override
    public BlogFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }

        //Create Binding View Holder
        RecycleViewBlogItemBinding blogItemBinding = RecycleViewBlogItemBinding.inflate(mLayoutInflater, parent, false);
        return new BlogFeedViewHolder(blogItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogFeedViewHolder holder, int position) {
        int itemPos = holder.getAdapterPosition();

        //Required NULL checks & other parameters before Binding DATA to view Holder
        if (mBlogFeedList != null && itemPos < mBlogFeedList.size() && mBlogFeedList.get(itemPos) != null) {
            final BlogFeedViewModel blogFeedViewModel = mBlogFeedList.get(itemPos);
            holder.bindDataToHolder(blogFeedViewModel);
        }

        //Item click listener event
        RecycleViewBlogItemBinding blogItemBinding = holder.getBlogDataBinding();
        blogItemBinding.setClickHandle(() -> {
            //On click Just display the Toast Message
            if (blogItemBinding.tItemBlogTitle != null && !TextUtils.isEmpty(blogItemBinding.tItemBlogTitle.getText().toString())) {
                Toast.makeText(mContext, blogItemBinding.tItemBlogTitle.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBlogFeedList == null ? 0 : mBlogFeedList.size();
    }

    /**
     * Method call to update the BlogFeed Adapter. It's mainly used for REFRESH commands & Pagination situations
     *
     * @param updatedBlogList
     */
    public void updateAdapterDataSet(List<BlogFeedViewModel> updatedBlogList) {
        if (updatedBlogList != null && updatedBlogList.size() > 0) {

            //The DiffUtil updating & new list creation logic will change for APIs with PAGINATION
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BlogFeedDiffUtils(mBlogFeedList, updatedBlogList));
            mBlogFeedList = updatedBlogList;
            diffResult.dispatchUpdatesTo(this);
        }
    }
}
