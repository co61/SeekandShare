package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnounceActivity extends AppCompatActivity {

    TextView a;
    TextView b;
    TextView c;
    TextView d;

    DatabaseReference reff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce);

        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        a=(TextView)findViewById(R.id.a);
        b=(TextView)findViewById(R.id.b);
        c=(TextView)findViewById(R.id.c);
        d=(TextView)findViewById(R.id.d);

        reff= FirebaseDatabase.getInstance().getReference().child("Posts").child("1");
        /*reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title=(String)dataSnapshot.child("title").getValue();
                String content=dataSnapshot.child("content").getValue().toString();
                String dpchoice=dataSnapshot.child("dpchoice").getValue().toString();
                String spchoice=dataSnapshot.child("spchoice").getValue().toString();

                a.setText(title);
                b.setText(content);
                c.setText(dpchoice);
                d.setText(spchoice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


    }
    //if click onretrun android button then go back to home
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
