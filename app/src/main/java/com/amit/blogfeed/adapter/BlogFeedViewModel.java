package com.amit.blogfeed.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.amit.blogfeed.R;
import com.amit.blogfeed.pojomodel.BlogResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

/**
 * BlogFeed ViewModel it's used for Adapter List Item Data Binding & some other purpose
 * Created by Amit PREMI on 04-Oct-18.
 */
public class BlogFeedViewModel {

    public String title;
    public String description;
    public final ObservableField<String> imageURL = new ObservableField<>();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ObservableField<String> getImageURL() {
        return imageURL;
    }

    /**
     * Binding Adapter method to Bind the Blog ImageView
     *
     * @param imgView
     * @param imageUrl
     */
    @BindingAdapter("blogImage")
    public static void setImageSrc(ImageView imgView, String imageUrl) {

        String imageURL = !TextUtils.isEmpty(imageUrl) ? imageUrl : String.valueOf(imgView.getContext().getDrawable(R.drawable.placeholder_image));
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(imgView.getContext().getDrawable(R.drawable.placeholder_image))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        //Setting up the ImageView using Glide
        Glide.with(imgView.getContext())
                .load(imageURL)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgView);
    }

    /**
     * Method which returns the BlogFeed List required to Update & Bind the Adapter Data Set
     *
     * @param blogDetailsList
     * @return
     */
    public static List<BlogFeedViewModel> getBlogFeedAdapterList(List<BlogResponse.BlogDetails> blogDetailsList) {
        if (blogDetailsList == null || blogDetailsList.size() == 0) return null;

        List<BlogFeedViewModel> blogFeedViewModels = new ArrayList<>();
        for (BlogResponse.BlogDetails blogDetails : blogDetailsList) {
            BlogFeedViewModel blogFeedViewModel = new BlogFeedViewModel();
            blogFeedViewModel.title = blogDetails.getTitle();
            blogFeedViewModel.description = blogDetails.getDescription();
            blogFeedViewModel.imageURL.set(blogDetails.getImageHref());
            blogFeedViewModels.add(blogFeedViewModel);
        }
        return blogFeedViewModels;
    }
}
