package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AffichagePostActivity extends AppCompatActivity {

    DatabaseReference reff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        Intent intent = getIntent();
        final String ID = intent.getStringExtra(AnnounceActivity.EXTRA_ID);

        final TextView titleView = (TextView) findViewById(R.id.announce_title);
        final TextView dateView = (TextView) findViewById(R.id.announce_date);
        final TextView DpView = (TextView) findViewById(R.id.announce_DP);
        final TextView SpView = (TextView) findViewById(R.id.announce_SP);
        final TextView contentView = (TextView) findViewById(R.id.announce_content);
        final TextView usernameView = (TextView) findViewById(R.id.announce_username);



        reff = FirebaseDatabase.getInstance().getReference().child("Posts");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),"test"+String.valueOf(dataSnapshot.child("1").child("title").getValue()),Toast.LENGTH_SHORT).show();
                String title = String.valueOf(dataSnapshot.child(ID).child("title").getValue());
                String content = String.valueOf(dataSnapshot.child(ID).child("content").getValue());
                String dpchoice = String.valueOf(dataSnapshot.child(ID).child("dpchoice").getValue());
                String spchoice = String.valueOf(dataSnapshot.child(ID).child("spchoice").getValue());
                String publicationDate = String.valueOf(dataSnapshot.child(ID).child("publicationDate").getValue());
                String userName = String.valueOf(dataSnapshot.child(ID).child("userName").getValue());

                titleView.setText(title);
                dateView.setText(publicationDate);
                DpView.setText(dpchoice);
                SpView.setText(spchoice);
                contentView.setText(content);
                usernameView.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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