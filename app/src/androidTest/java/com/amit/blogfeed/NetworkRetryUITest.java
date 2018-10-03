package com.amit.blogfeed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Class to Test the Network Retry UI visibility in-case of No Network
 * Created by Amit PREMI on 03-Oct-18.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class NetworkRetryUITest {

    @Rule
    public ActivityTestRule<HomePageActivity> mHomePageTestActivity = new ActivityTestRule<>(HomePageActivity.class);

    /**
     * Method to create the Test Case for Network UI visibility
     *
     * @throws InterruptedException
     */
    @Test
    public void checkNetworkRetryUIVisibility() throws InterruptedException {

        //Making Thread sleep as on the load of Activity API call starts which check for network availability
        Thread.sleep(2000);

        //Calling the Activity Method & for Test purpose PASSING isNetworkConnected as FALSE
        mHomePageTestActivity.getActivity().runOnUiThread(() -> {
            (mHomePageTestActivity.getActivity()).updateUIForNetworkCheck(false);
        });

        //Checking the Network UI visibility for the NO NETWORK CASE as simulated above
        onView(withId(R.id.layout_home_no_network)).check(matches(isDisplayed()));
    }
}
