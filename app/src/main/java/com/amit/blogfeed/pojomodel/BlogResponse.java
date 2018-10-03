package com.amit.blogfeed.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * BlogFeed Data Model for the API response
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogResponse {

    private String title;

    @SerializedName("rows")
    @Expose
    private List<BlogDetails> blogDetailsList;

    public String getTitle() {
        return title;
    }

    public List<BlogDetails> getBlogDetailsList() {
        return blogDetailsList;
    }

    /**
     * BlogDetails class
     */
    public class BlogDetails {
        private String title;
        private String description;
        private String imageHref;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImageHref() {
            return imageHref;
        }
    }
}
