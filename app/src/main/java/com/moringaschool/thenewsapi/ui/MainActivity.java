package com.moringaschool.thenewsapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mSeatrchedNewsReference;

    @BindView(R.id.submitBtn) Button mButton;
    @BindView(R.id.categorySearch) EditText mCategory;
    @BindView(R.id.textView) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //instatntiating the databasereference
        mSeatrchedNewsReference = FirebaseDatabase
                .getInstance()
                        .getReference()
                                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == mButton){
                    String news = mCategory.getText().toString();
                    saveNewsToFirebase(news);
                }


                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("news", mCategory.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void saveNewsToFirebase(String news){
        mSeatrchedNewsReference.push().setValue(news);
    }

    public void onResume() {


        super.onResume();
    }
}