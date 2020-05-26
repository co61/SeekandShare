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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private LinearLayout layout;
    private DatabaseReference reff;
    private DatabaseReference reffaff;
    private String currentUserId;
    private ArrayList<String[]> wayTabreff = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //change title action Bar
        getSupportActionBar().setTitle("Favoris");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = (LinearLayout) findViewById(R.id.dynamic_layout_favorite);
    }

    @Override
    protected void onStart() {
        super.onStart();

        wayTabreff=new ArrayList<>();

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }

        reff = FirebaseDatabase.getInstance().getReference().child("Favorite");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.getKey().equals(currentUserId)) {
                        for (DataSnapshot childfav : child.getChildren()) {
                            String[] way = childfav.getKey().split("~");
                            //wayTabreff.add(FirebaseDatabase.getInstance().getReference().child("Posts").child(way[0]).child(way[1]).child(way[2]));
                            wayTabreff.add(way);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        View layoutremove=(View) findViewById(R.id.dynamic_layout_favorite);
        ((ViewGroup) layoutremove).removeAllViews();
        reffaff = FirebaseDatabase.getInstance().getReference().child("Posts");
        reffaff.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (String[] w : wayTabreff) {
                    DataSnapshot s =dataSnapshot.child(w[0]).child(w[1]);
                    String key = w[2];
                    //get the announce of the current user on the screen
                    LinearLayout Aview = Function.takePost(s, key, FavoriteActivity.this, layout);
                    final String finalI = s.getRef().getParent().getKey() + "~" + s.getKey() + "~" + String.valueOf(key);
                    Aview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //switch to Announce Fragment to show the announce published
                            Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("ParentActivity","FavoriteActivity");
                            bundle.putString("ID",finalI);
                            act.putExtras(bundle);
                            startActivity(act);

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();

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
