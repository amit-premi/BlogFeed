package com.amit.blogfeed.adapter;

import android.text.TextUtils;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

/**
 * DifFUtil class to compare the two list old & new passed to the Adapter.
 * It helps to identify the difference between both list & update Adapter only for CHANGED DATA SET
 * <p>
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogFeedDiffUtils extends DiffUtil.Callback {

    private List<BlogFeedViewModel> oldBlogFeedList, newBlogFeedList;

    BlogFeedDiffUtils(List<BlogFeedViewModel> oldBlogList, List<BlogFeedViewModel> newBlogList) {
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
        //Sine the List doesn't have any Id concept for Uniqueness.
        //So first considering Title for Unique & than Description
        if (!TextUtils.isEmpty(oldBlogFeedList.get(oldItemPosition).getTitle()) && !TextUtils.isEmpty(newBlogFeedList.get(oldItemPosition).getTitle())) {
            return oldBlogFeedList.get(oldItemPosition).getTitle().equalsIgnoreCase(newBlogFeedList.get(newItemPosition).getTitle());
        } else if (!TextUtils.isEmpty(oldBlogFeedList.get(oldItemPosition).getDescription()) &&
                !TextUtils.isEmpty(newBlogFeedList.get(oldItemPosition).getDescription())) {
            return oldBlogFeedList.get(oldItemPosition).getDescription().equalsIgnoreCase(newBlogFeedList.get(newItemPosition).getDescription());
        }
        return false;
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
