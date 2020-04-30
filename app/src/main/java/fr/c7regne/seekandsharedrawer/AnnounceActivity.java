package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
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

public class AnnounceActivity extends AppCompatActivity {

    public static final String EXTRA_ID="fr.c7regne.seekandsharedrawer";


    int Childnb;
    DatabaseReference reff;
    String currentUserName, currentUserEmail, currentUserId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserName = signInAccount.getDisplayName();
            currentUserEmail = signInAccount.getEmail();
            currentUserId = signInAccount.getId();
        }
        //reed children posts count
        reff=FirebaseDatabase.getInstance().getReference().child("Posts");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("test",dataSnapshot.getValue().toString());
                Childnb= (int) dataSnapshot.getChildrenCount();
                int decalage =0;
                for (int i=1;i<=Childnb+decalage;i++) {
                    while (String.valueOf(dataSnapshot.child(String.valueOf(i)).getValue()) == "null") {
                        i += 1;
                        decalage+=1;
                    }

                    String userID = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("userId").getValue());

                    //get the announce of the current user on the screen
                    if(currentUserId.equals(userID)){
                        LinearLayout layout=(LinearLayout) findViewById(R.id.linearlayout_announce_list);
                        String title = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("title").getValue());
                        String content = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("content").getValue());
                        String dpchoice = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("dpchoice").getValue());
                        String spchoice = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("spchoice").getValue());
                        String publicationDate = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("publicationDate").getValue());
                        String userName = String.valueOf(dataSnapshot.child(String.valueOf(i)).child("userName").getValue());

                        //sending to put on screen
                        LinearLayout Aview =new AddViewListAnnounce().addAnnounceUser(AnnounceActivity.this,title,publicationDate,dpchoice,spchoice,content,userName,userID);
                        layout.addView(Aview);
                        final String finalI =  String.valueOf(i);
                        Aview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //switch to Announce Fragment to show the announce published
                                Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                                startActivity(act);
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