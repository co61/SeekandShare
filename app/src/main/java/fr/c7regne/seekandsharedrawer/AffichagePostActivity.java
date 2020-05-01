package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AffichagePostActivity extends AppCompatActivity {

    DatabaseReference reff;
    DatabaseReference refffavorite;
    String currentUserId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affichage_announce);
        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String ID = intent.getStringExtra(AnnounceActivity.EXTRA_ID);

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }

        final TextView titleView = (TextView) findViewById(R.id.announce_title);
        final TextView dateView = (TextView) findViewById(R.id.announce_date);
        final TextView DpView = (TextView) findViewById(R.id.announce_DP);
        final TextView SpView = (TextView) findViewById(R.id.announce_SP);
        final TextView contentView = (TextView) findViewById(R.id.announce_content);
        final TextView usernameView = (TextView) findViewById(R.id.announce_username);


        reff = FirebaseDatabase.getInstance().getReference().child("Tanguy");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

        final ImageView addfavoritebtn = (ImageView) findViewById(R.id.add_favorite_btn);
        refffavorite = FirebaseDatabase.getInstance().getReference().child("Favorite");
        refffavorite.addValueEventListener(new ValueEventListener() {
            boolean exists = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.child(currentUserId).getChildren()) {
                    Log.i("childkey", child.getKey());
                    Log.i("ID", ID);
                    if (child.getKey().equals(ID)) {
                        exists = true;
                        Log.i("exists", "maketrue");
                        break;
                    }
                }
                Log.i("exists", String.valueOf(exists));
                if (exists) {
                    //construction struct to send into database with auto increment depending on number of member in this branch
                    addfavoritebtn.setImageResource(R.drawable.ic_favorite_full);
                    exists = true;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        addfavoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
àà               final boolean[] check = {false};
                refffavorite.addValueEventListener(new ValueEventListener() {
                    boolean exists = false;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.child(currentUserId).getChildren()) {
                            Log.i("childkey", child.getKey());
                            Log.i("ID", ID);
                            if (child.getKey().equals(ID)) {
                                exists = true;
                                break;
                            }
                        }
                        Log.i("exists", String.valueOf(exists));
                        if (!exists && !check[0]) {
                            //construction struct to send into database with auto increment depending on number of member in this branch
                            refffavorite.child(currentUserId).child(ID).setValue("true");
                            Toast.makeText(getApplicationContext(), "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
                            addfavoritebtn.setImageResource(R.drawable.ic_favorite_full);
                            check[0] = true;

                        }
                        if (exists && !check[0]) {
                            addfavoritebtn.setImageResource(R.drawable.ic_favorite_border);
                            refffavorite.child(currentUserId).child(ID).setValue(null);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    check[0] = true;
                                }
                            }, 500);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        //new AddFavorite().addToFavorite(AffichagePostActivity.this,ID, addfavoritebtn);


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


    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }


}