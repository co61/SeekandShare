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

public class AffichagePostActivity extends AppCompatActivity implements DeleteConfirm.DeleteConfirmListener, EditConfirm.EditConfirmListener {

    DatabaseReference reff;
    DatabaseReference refffavorite;
    String currentUserId;
    String ID;
    String[] way;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affichage_announce);
        //change title action Bar
        getSupportActionBar().setTitle("Mon annonce");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras=getIntent().getExtras();
        String parentActivity = extras.getString("ParentActivity");

        ID = getIntent().getExtras().getString("ID");
        way = ID.split("~");

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }

        ImageView edit=(ImageView)findViewById(R.id.edit_announce_btn);
        ImageView delete=(ImageView)findViewById(R.id.delete_announce_btn);
        if(String.valueOf(parentActivity).equals("AnnounceActivity")){
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        }



        final TextView titleView = (TextView) findViewById(R.id.announce_title);
        final TextView dateView = (TextView) findViewById(R.id.announce_date);
        final TextView DpView = (TextView) findViewById(R.id.announce_DP);
        final TextView SpView = (TextView) findViewById(R.id.announce_SP);
        final TextView contentView = (TextView) findViewById(R.id.announce_content);
        final TextView usernameView = (TextView) findViewById(R.id.announce_username);


        reff = FirebaseDatabase.getInstance().getReference().child("Posts").child(way[0]).child(way[1]);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot keyContext = dataSnapshot.child(way[2]);
                String title = String.valueOf(keyContext.child("title").getValue());
                String content = String.valueOf(keyContext.child("content").getValue());
                String dpchoice = String.valueOf(keyContext.child("dpchoice").getValue());
                String spchoice = String.valueOf(keyContext.child("spchoice").getValue());
                String publicationDate = String.valueOf(keyContext.child("publicationDate").getValue());
                String userName = String.valueOf(keyContext.child("userName").getValue());

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
        refffavorite.addListenerForSingleValueEvent(new ValueEventListener() {
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
               final boolean[] check = {false};
                refffavorite.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            check[0] = true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        //new AddFavorite().addToFavorite(AffichagePostActivity.this,ID, addfavoritebtn);

        if(String.valueOf(parentActivity).equals("AnnounceActivity")){
            Log.i("Atcivity parent ","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog("delete");

                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog("edit");
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

    public void openDialog(String arg){
        if(arg=="delete") {

            DeleteConfirm confirmDelete = new DeleteConfirm();
            confirmDelete.show(getSupportFragmentManager(), "DeleteConfirm");
        }
        if(arg=="edit") {
            EditConfirm confirmEdit = new EditConfirm();
            confirmEdit.show(getSupportFragmentManager(), "EditConfirm");
        }
    }

    @Override
    public void onYesDeleteClikcked() {
        Log.i("raa","pet");

        reff.child(way[2]).setValue(null);
        refffavorite = FirebaseDatabase.getInstance().getReference().child("Favorite");
        Log.i("peut etre","pet");
        refffavorite.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()){

                    Log.i("enfant",String.valueOf(child));
                    for (DataSnapshot idchild : child.getChildren()) {
                        Toast.makeText(getApplicationContext(),String.valueOf(idchild.getKey()),Toast.LENGTH_SHORT).show();
                        if (idchild.getKey().equals(ID)) {

                            refffavorite.child(child.getKey()).child(ID).setValue(null);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        finish();
    }


    @Override
    public void onYesEditClikcked() {
        Bundle bundle=new Bundle();
        bundle.putString("ID",ID);
        Intent act = new Intent(getApplicationContext(), EditAnnounceActivity.class);
        act.putExtras(bundle);
        startActivity(act);
        finish();
    }

}
