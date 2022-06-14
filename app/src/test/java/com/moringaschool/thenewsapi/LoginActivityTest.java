package com.moringaschool.thenewsapi;

import static org.junit.Assert.assertEquals;

import android.widget.EditText;

import com.moringaschool.thenewsapi.ui.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {
    private LoginActivity loginActivity;

    @Before
    public void setUp() throws Exception {
        loginActivity = Robolectric.buildActivity(LoginActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void validateEmailTextViewContent() {

        EditText emailTextView = loginActivity.findViewById(R.id.emailLoginEditText);
        assertEquals("Email: ", emailTextView.getHint());

    }

    @Test
    public void validateEmailTextViewCuontent() {
        EditText passwordTextView = loginActivity.findViewById(R.id.passwordLoginEditText);
        assertEquals("Password: ", passwordTextView.getHint());


}

}
