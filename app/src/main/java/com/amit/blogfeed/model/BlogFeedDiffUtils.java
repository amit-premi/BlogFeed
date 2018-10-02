package com.amit.blogfeed.model;

import com.amit.blogfeed.pojomodel.BlogResponse;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

/**
 * DifFUtil class to compare the two list old & new passed to the Adapter.
 * It helps to identify the difference between both list & update Adapter only for CHANGED DATA SET
 *
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogFeedDiffUtils extends DiffUtil.Callback {

    private List<BlogResponse.BlogDetails> oldBlogFeedList, newBlogFeedList;

    public BlogFeedDiffUtils(List<BlogResponse.BlogDetails> oldBlogList, List<BlogResponse.BlogDetails> newBlogList) {
        this.oldBlogFeedList = oldBlogList;
        this.newBlogFeedList = newBlogList;
    }

    @Override
    public int getOldListSize() {
        return oldBlogFeedList == null ? 0 : oldBlogFeedList.size();
    }

    @Override
    public int getNewListSize() {
        return newBlogFeedList == null ? 0 : newBlogFeedList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBlogFeedList.get(oldItemPosition).getTitle().equalsIgnoreCase(newBlogFeedList.get(newItemPosition).getTitle());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBlogFeedList.get(oldItemPosition).equals(newBlogFeedList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
