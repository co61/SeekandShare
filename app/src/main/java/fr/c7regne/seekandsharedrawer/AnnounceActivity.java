package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


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

public class AnnounceActivity extends AppCompatActivity {

    public static final String EXTRA_ID="fr.c7regne.seekandsharedrawer";

    String  currentUserId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.i("test4","test4");
        super.onCreate(savedInstanceState);

        Log.i("test4","test4");
        setContentView(R.layout.activity_announce);

        Log.i("test4","test4");
        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }

        //reed children posts count
        Log.i("test4.5","test4.5");

        DatabaseReference[] tabReff = Function.Parcours();

        Log.i("test5","test5");
        for (DatabaseReference data : tabReff) {
            data.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String key = child.getKey().toString();
                        String userID = String.valueOf(dataSnapshot.child(key).child("userId").getValue());

                        //get the announce of the current user on the screen

                        if (currentUserId.equals(userID)) {

                            LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_announce_list);
                            LinearLayout Aview = Function.takePost(dataSnapshot, key, AnnounceActivity.this, layout);
                            final String finalI = dataSnapshot.getRef().getParent().getKey() + " " + dataSnapshot.getKey() + " " +String.valueOf(key);
                            Aview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //switch to Announce Fragment to show the announce published
                                    Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                                    act.putExtra(EXTRA_ID, finalI);
                                    startActivity(act);

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }

            });

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //if click onretrun android button then go back to home
    @Override
    public void onBackPressed() {
        finish();

    }

}