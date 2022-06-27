package com.moringaschool.thenewsapi.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.thenewsapi.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = CreateAccountActivity.class.getSimpleName();

    @BindView(R.id.logInTextView) TextView mLoginTextView;
    @BindView(R.id.emailEditText) EditText mEmail;
    @BindView(R.id.nameEditText) EditText mUserName;
    @BindView(R.id.passwordEditText) EditText mPassword;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPassword;
    @BindView(R.id.signUpBtn) Button mRegisterButton;

    @BindView(R.id.firebaseProgressBar)
    ProgressBar mSignInProgressBar;
    @BindView(R.id.loadingTextView) TextView mLoadingSignUp;
    @BindView(R.id.cameraImageView)
    ImageView mImage;


    private String mName;

    private FirebaseAuth mAuth; //member var to get the instance of firebase Auth object
    private FirebaseAuth.AuthStateListener mAuthListener; //Used to listen for an account being succesfully authenticated through firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        ButterKnife.bind(this);

        FirebaseApp.initializeApp(this); //Important
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        mLoginTextView.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        createAuthStateListener(); //calling the method to authenticate the user

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(CreateAccountActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start(100);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            mImage.setImageURI(imageUri);
        }else {
            Toast.makeText(this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
        }

    }

    //progresss bar
    private void showProgressBar() {
        mSignInProgressBar.setVisibility(View.VISIBLE);
        mLoadingSignUp.setVisibility(View.VISIBLE);
        mLoadingSignUp.setText("Sign Up process in Progress");
    }

    private void hideProgressBar() {
        mSignInProgressBar.setVisibility(View.GONE);
        mLoadingSignUp.setVisibility(View.GONE);
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
        final String name = mUserName.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String confirmPassword = mConfirmPassword.getText().toString().trim();

        //Validating the methods
        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(name);
        boolean validPassword = isValidPassword(password, confirmPassword);


        if (!validEmail || !validName || !validPassword) return;
        showProgressBar();


        //a method to create the new user in firebase

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        hideProgressBar();
                        if(task.isSuccessful()){
                            Log.d(TAG, "Authentication succesful");
                                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();


                        }else {
                            Toast.makeText(CreateAccountActivity.this, "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }




    private boolean isValidEmail(String email){
        boolean isEmailOk = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isEmailOk){
            mEmail.setError("Please enter a valid email address");
            return false;
        }
        return isEmailOk;
    }

    private boolean isValidName(String name){
        if (name.equals("")){
            mUserName.setError("Please enter Your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword){
        if (password.length() < 6){
            mPassword.setError("Password must be more than 5 characters");
            return false;
        } else if (!password.equals(confirmPassword)){
            mPassword.setError("Password do not match");
            return false;
        }
        return true;
    }


    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}