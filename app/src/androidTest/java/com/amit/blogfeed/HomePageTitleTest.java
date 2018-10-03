package com.amit.blogfeed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Class to Test the Home Page Title text value
 * Created by Amit PREMI on 03-Oct-18.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class HomePageTitleTest {

    //Initial value of the Title before the API call
    private static final String TITLE_INITIAL = "BlogFeed";

    //Title text value which is coming from the BlogFeed API at the moment
    //If the value return by the API changes, then same need to be Updated Here
    private static final String TITLE_AFTER_API_UPDATE = "About Canada";

    @Rule
    public ActivityTestRule<HomePageActivity> mHomePageTestActivity = new ActivityTestRule<>(HomePageActivity.class);

    /**
     * Test method to check the Title text value before & After the API call
     *
     * @throws InterruptedException
     */
    @Test
    public void homeTitleTest() throws InterruptedException {

        //Initial Title Value
        onView(withId(R.id.t_home_title)).check(matches(withText(TITLE_INITIAL)));

        //Thread sleep to wait for the API call to get Over
        Thread.sleep(5000);

        //Title value after API update
        onView(withId(R.id.t_home_title)).check(matches(withText(TITLE_AFTER_API_UPDATE)));
    }
}
