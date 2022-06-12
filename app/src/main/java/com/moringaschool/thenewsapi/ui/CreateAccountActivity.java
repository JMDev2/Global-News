package com.moringaschool.thenewsapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.moringaschool.thenewsapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.logInTextView) TextView mLoginTextView;
    @BindView(R.id.emailEditText) EditText mEmail;
    @BindView(R.id.nameEditText) EditText mName;
    @BindView(R.id.passwordEditText) EditText mPassword;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @BindView(R.id.signUpBtn) Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        mLoginTextView.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == mLoginTextView){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }
}