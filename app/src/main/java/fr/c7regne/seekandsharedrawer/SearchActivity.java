package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
    /***
     *SearchActivity show the announces correspondent to the research argument of the SearchFragment
     *
     *
     */


    DatabaseReference reff;
    String currentUserName, currentUserEmail, currentUserId;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //change title action Bar and show the return arrow
        getSupportActionBar().setTitle("Recherches");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        //get if its "Prêt" or "Service" and "Proposition" or "Demande" and the content of the research
        final String[] input = intent.getStringExtra(SearchFragment.SearchInput).split(" ");

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserName = signInAccount.getDisplayName();
            currentUserEmail = signInAccount.getEmail();
            currentUserId = signInAccount.getId();
        }
        //reed children posts/Pret or Service/Demande or Proposition in Firebase DataBase
        reff=FirebaseDatabase.getInstance().getReference().child("Posts").child(input[0]).child(input[1]);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //test = true if announce correspond to the research arguments
                boolean test =false;
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    test = false;

                    //test if the current announce contains the words of the research
                    if(input.length > 2 | test){
                        for(int i = 2; i<(input.length); i++) {
                            //test on announce title, content and place
                            if(child.child("content").getValue().toString().contains(input[i]) | child.child("title").getValue().toString().contains(input[i])
                            | child.child("place").getValue().toString().contains(input[i])){
                                test = true;}
                        }
                    }
                    else{test = true;}

                    //if test true, the announce correspond to the research and we can show it
                    if(test){
                        //current announce
                        String key = child.getKey().toString();

                        //We add the current announce to layout of research
                        LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_search_list);
                        LinearLayout Aview = Function.takePost(dataSnapshot, key, SearchActivity.this, layout);
                        //formalize the way of the announce in DataBase
                        final String finalI = dataSnapshot.getRef().getParent().getKey() + "~" + dataSnapshot.getKey() + "~" +key;

                        Aview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //If the user click on an announce, launch the activity to show the Announce information
                                Intent act = new Intent(getApplicationContext(), AffichagePostActivity.class);
                                Bundle bundle=new Bundle();
                                //send the information of the clicked announce (way in database) to the activity
                                bundle.putString("ParentActivity","SearchActivity");
                                bundle.putString("ID",finalI);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //if click on return android button finish activity and go back to home
    @Override
    public void onBackPressed() {
        finish();
    }
}