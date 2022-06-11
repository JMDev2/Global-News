package com.moringaschool.thenewsapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.thenewsapi.Constants;
import com.moringaschool.thenewsapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mSeatrchedNewsReference;
    private ValueEventListener mSeatrchedNewsReferenceListener; //Attached to onDestroy method

    @BindView(R.id.submitBtn) Button mButton;
    @BindView(R.id.categorySearch) EditText mCategory;
    @BindView(R.id.textView) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //instatntiating the databasereference
        mSeatrchedNewsReference = FirebaseDatabase
                .getInstance()
                        .getReference()
                                .child(Constants.FIREBASE_CHILD_SEARCHED_LOCATION); //Points to news code

        //Adding the value event Listener methods
        mSeatrchedNewsReference.addValueEventListener(new ValueEventListener() {
            @Override

            //Changing the data in the node
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot newsSnapshot : snapshot.getChildren()){
                    String news = newsSnapshot.getValue().toString();
                    Log.d("news updated", "news: " + news);
                }
            }
            //called when the listener is unsuccesful
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saving news when the button is clicked
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

    //OnDestroy method that will kill the value event listener
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSeatrchedNewsReference.removeEventListener(mSeatrchedNewsReferenceListener);
    }

    public void onResume() {


        super.onResume();
    }
}