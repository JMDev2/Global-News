package com.moringaschool.thenewsapi;

import static org.junit.Assert.assertEquals;

import android.widget.EditText;

import com.moringaschool.thenewsapi.ui.CreateAccountActivity;
import com.moringaschool.thenewsapi.ui.LoginActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CreateAccountActivityTest {
    private CreateAccountActivity createAccountActivity;

    @Before
    public void setUp() throws Exception {
        createAccountActivity = Robolectric.buildActivity(CreateAccountActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void validateEmailTextViewContent() {

        EditText emailTextView = createAccountActivity.findViewById(R.id.emailEditText);
        assertEquals("Email:", emailTextView.getHint());
    }
    @Test
    public void validateNameTextViewContent() {

        EditText nameTextView = createAccountActivity.findViewById(R.id.nameEditText);
        assertEquals("Name:", nameTextView.getHint());
    }

    @Test
    public void validatePasswordTextViewCuontent() {
        EditText passwordTextView = createAccountActivity.findViewById(R.id.passwordEditText);
        assertEquals("Password:", passwordTextView.getHint());

    }
    @Test
    public void validateConfirmPasswordTextViewCuontent() {
        EditText confirmPasswordTextView = createAccountActivity.findViewById(R.id.confirmPasswordEditText);
        assertEquals("Confirm Password:", confirmPasswordTextView.getHint());

    }

}


