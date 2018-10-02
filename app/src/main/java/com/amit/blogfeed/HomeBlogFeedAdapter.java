package com.amit.blogfeed;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amit.blogfeed.model.BlogFeedDiffUtils;
import com.amit.blogfeed.pojomodel.BlogResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class HomeBlogFeedAdapter extends RecyclerView.Adapter<HomeBlogFeedAdapter.BlogViewHolder> {

    private Context mContext;
    private List<BlogResponse.BlogDetails> mBlogFeedList;

    HomeBlogFeedAdapter(Context context, List<BlogResponse.BlogDetails> responseList) {
        this.mContext = context;
        this.mBlogFeedList = responseList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_blog_details, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        final int itemPos = holder.getAdapterPosition();

        if (mBlogFeedList != null && itemPos < mBlogFeedList.size() && mBlogFeedList.get(itemPos) != null) {
            holder.bindDataToHolder(mBlogFeedList.get(itemPos));
        }
    }

    @Override
    public int getItemCount() {
        return mBlogFeedList == null ? 0 : mBlogFeedList.size();
    }

    /**
     * Method call to update the BlogFeed Adapter
     *
     * @param updatedBlogList
     */
    public void updateAdapterDataSet(List<BlogResponse.BlogDetails> updatedBlogList) {
        if (updatedBlogList != null && updatedBlogList.size() > 0) {

            //The DiffUtil updating & new list creation logic will change for APIs with PAGINATION
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new BlogFeedDiffUtils(mBlogFeedList, updatedBlogList));
            mBlogFeedList = updatedBlogList;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    /**
     * ViewHolder class used for the BlogFeed Adapter
     */
    class BlogViewHolder extends RecyclerView.ViewHolder {

        private TextView tBlogTitle, tBlogDescription;
        private ImageView imgBlogProfile;

        BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            tBlogTitle = itemView.findViewById(R.id.t_item_blog_title);
            tBlogDescription = itemView.findViewById(R.id.t_item_blog_description);
            imgBlogProfile = itemView.findViewById(R.id.iv_item_blog_image);
        }

        private void bindDataToHolder(BlogResponse.BlogDetails blogResponse) {

            if (!TextUtils.isEmpty(blogResponse.getTitle())) {
                tBlogTitle.setText(blogResponse.getTitle());
            }

            if (!TextUtils.isEmpty(blogResponse.getDescription())) {
                tBlogDescription.setText(blogResponse.getDescription());
            }

            if (!TextUtils.isEmpty(blogResponse.getImageHref())) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(mContext.getDrawable(R.drawable.placeholder_image))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

                Glide.with(mContext)
                        .load(blogResponse.getImageHref())
                        .apply(requestOptions)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgBlogProfile);
            }
        }
    }
}
