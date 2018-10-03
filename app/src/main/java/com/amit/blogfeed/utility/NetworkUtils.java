package com.amit.blogfeed.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.amit.blogfeed.HomePageActivity;

/**
 * Network Utility class to check the Device Network Availability
 * <p>
 * Created by Amit PREMI on 03-Oct-18.
 */
public class NetworkUtils {

    public static boolean isNetworkConnected(Activity activity) {
        if (activity == null) return false;

        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        //Update the UI for Network Availability
        if (activity instanceof HomePageActivity) {
            ((HomePageActivity) activity).updateUIForNetworkCheck(isConnected);
        }
        return isConnected;
    }
}
