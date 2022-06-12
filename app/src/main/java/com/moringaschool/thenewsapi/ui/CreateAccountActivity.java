package com.moringaschool.thenewsapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.moringaschool.thenewsapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    @BindView(R.id.logInTextView)
    TextView mLoginTextView;
    @BindView(R.id.emailEditText)
    EditText mEmail;
    @BindView(R.id.nameEditText)
    EditText mName;
    @BindView(R.id.passwordEditText)
    EditText mPassword;
    @BindView(R.id.confirmPasswordEditText)
    EditText mConfirmPassword;
    @BindView(R.id.signUpBtn)
    Button mRegisterButton;

    private FirebaseAuth mAuth; //member var to get the instance of firebase Auth object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        mLoginTextView.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        if (v == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
        //calling the createNewUser method once the submit button is clicked
        if (v == mRegisterButton) {
            createNewUser();
        }
    }

    //create the new user method
    private void createNewUser() {
        //transforming the variables into strings
        final String name = mName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String confirmPassword = mConfirmPassword.getText().toString().trim();

        //a method to create the new user in firebase

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        Log.d(TAG, "Authentication succesful");
                    }else {
                        Toast.makeText(CreateAccountActivity.this, "AUthentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}