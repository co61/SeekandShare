package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class SearchActivity extends AppCompatActivity {
    int Childnb;
    DatabaseReference reff;
    String currentUserName, currentUserEmail, currentUserId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //change title action Bar
        getSupportActionBar().setTitle("Recherches");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        final String input = intent.getStringExtra(SearchFragment.SearchInput);

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserName = signInAccount.getDisplayName();
            currentUserEmail = signInAccount.getEmail();
            currentUserId = signInAccount.getId();
        }
        //reed children posts count
        reff=FirebaseDatabase.getInstance().getReference().child("Tanguy");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.child("content").getValue().toString().contains(input) | child.child("title").getValue().toString().contains(input)){
                        String key = child.getKey().toString();
                        String userID = String.valueOf(dataSnapshot.child(key).child("userId").getValue());

                        //get the announce of the current user on the screen

                        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_search_list);
                        String title = String.valueOf(dataSnapshot.child(key).child("title").getValue());
                        String content = String.valueOf(dataSnapshot.child(key).child("content").getValue());
                        String dpchoice = String.valueOf(dataSnapshot.child(key).child("dpchoice").getValue());
                        String spchoice = String.valueOf(dataSnapshot.child(key).child("spchoice").getValue());
                        String publicationDate = String.valueOf(dataSnapshot.child(key).child("publicationDate").getValue());
                        String userName = String.valueOf(dataSnapshot.child(key).child("userName").getValue());
                        //sending to put on screen
                        layout.addView(new AddViewListAnnounce().addAnnounceUser(SearchActivity.this,title,publicationDate,dpchoice,spchoice,content,userName,userID));

                    }
                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }

    //if click onretrun android button then go back to home
    @Override
    public void onBackPressed() {
        finish();
    }


    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }



}