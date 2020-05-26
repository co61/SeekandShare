package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;

public class AnnounceActivity extends AppCompatActivity {
    /**
     * Display the list of posts published by the current user
     * when a posts is clicked, it open a new activity where the announce is displayed.
     */

    String currentUserId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_announce);

        //change title action Bar
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // remove last views to reload the change on the database
        View layoutremove = (View) findViewById(R.id.linearlayout_announce_list);
        ((ViewGroup) layoutremove).removeAllViews();

        //reed children posts count
        DatabaseReference[] tabReff = Function.Parcours();

        // read the tabReff and display the announce
        for (DatabaseReference data : tabReff) {
            data.addValueEventListener(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String key = child.getKey().toString();
                        String userID = String.valueOf(dataSnapshot.child(key).child("userId").getValue());
                            //get the announce of the current user on the screen
                            if (currentUserId.equals(userID)) {
                                LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_announce_list);
                                LinearLayout Aview = Function.takePost(dataSnapshot, key, AnnounceActivity.this, layout);
                                final String finalI = dataSnapshot.getRef().getParent().getKey() + "~" + dataSnapshot.getKey() + "~" + String.valueOf(key);
                                Aview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //switch to AffichagePostActivity to show the announce published
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ParentActivity", "AnnounceActivity");
                                        bundle.putString("ID", finalI);
                                        Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                                        act.putExtras(bundle);
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

        if (item.getItemId() == android.R.id.home) {
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