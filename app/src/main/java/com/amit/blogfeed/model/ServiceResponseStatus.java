package com.amit.blogfeed.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * Modal @IntDef for possible Retrofit Service call response status
 * Created by Amit PREMI on 02-Oct-18.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef(value = {ServiceResponseStatus.SUCCESS, ServiceResponseStatus.FAILURE, ServiceResponseStatus.ERROR, ServiceResponseStatus.INVALID,
        ServiceResponseStatus.NETWORK_ERROR})
public @interface ServiceResponseStatus {
    int SUCCESS = 1111;
    int FAILURE = 2222;
    int ERROR = 3333;
    int INVALID = 4444;
    int NETWORK_ERROR = 5555;
}