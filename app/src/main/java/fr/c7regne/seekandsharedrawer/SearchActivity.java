package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
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


public class SearchActivity extends AppCompatActivity {
    int Childnb;
    public static final String EXTRA_ID="fr.c7regne.seekandsharedrawer";
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
        final String[] input = intent.getStringExtra(SearchFragment.SearchInput).split(" ");

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserName = signInAccount.getDisplayName();
            currentUserEmail = signInAccount.getEmail();
            currentUserId = signInAccount.getId();
        }
        //reed children posts count
        reff=FirebaseDatabase.getInstance().getReference().child("test").child(input[0]).child(input[1]);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean test =false;
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    test = false;

                    if(input.length > 2 | test){
                        for(int i = 2; i<(input.length); i++) {
                            if(child.child("content").getValue().toString().contains(input[i]) | child.child("title").getValue().toString().contains(input[i])){
                                test = true;}
                        }
                    }
                    else{test = true;}


                    if(test){
                        String key = child.getKey().toString();

                        //get the announce of the current user on the screen
                        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_search_list);
                        //take info of the post in a LinearLayout
                        LinearLayout Aview = Function.takePost(dataSnapshot, key, SearchActivity.this, layout);
                        final String finalI = String.valueOf(key);

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


    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }



}