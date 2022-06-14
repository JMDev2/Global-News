package com.moringaschool.thenewsapi;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.moringaschool.thenewsapi.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void ValidateLogin(){
        String email = "antony@gmail.com";
        String password = "antony";
        onView(withId(R.id.emailLoginEditText)).perform(typeText(email))
                .check(matches(withText(email)));



//        onView(withId(R.id.passwordLoginEditText)).perform(typeText(password))
//                .check(matches(withText(password)));



        onView(withId(R.id.loginInBtn)).perform(click());


    }
}
