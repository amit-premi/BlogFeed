package com.amit.blogfeed.pojomodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Amit PREMI on 02-Oct-18.
 */
public class BlogModel {

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

    @NonNull
    @Override
    public String toString() {
        return "BlogModel{" +
                "title='" + title + '\'' +
                ", blogDetailsList=" + blogDetailsList +
                '}';
    }

    /**
     * BlogDetails class
     */
    class BlogDetails {
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

        @NonNull
        @Override
        public String toString() {
            return "BlogDetails{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", imageHref='" + imageHref + '\'' +
                    '}';
        }
    }
}
