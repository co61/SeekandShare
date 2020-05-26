package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.widget.Toast.LENGTH_SHORT;

public class SettingsActivity extends AppCompatActivity {

    TextView name,email,count_announce;
    Button logout, contact, send;
    String currentUserId;
    EditText contact_txt;
    int count;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //change title action Bar and back arrow
        getSupportActionBar().setTitle("Paramètres");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //load layout element
        logout=findViewById(R.id.logout_button);
        name = findViewById(R.id.account_name_txt);
        email = findViewById(R.id.account_email_txt);
        send = findViewById(R.id.send_settings_button);
        count_announce = findViewById(R.id.count_announce);
        contact_txt = findViewById(R.id.contact_txt);
        contact = findViewById(R.id.contact_btn);
        //display google account information about user
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            email.setText(signInAccount.getEmail());
            //get information on user
            currentUserId = signInAccount.getId();

        }
        //logout from user account yet connected
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact_txt.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
                Log.i("test",String.valueOf(contact_txt.getHeight()));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact_txt.setVisibility(View.INVISIBLE);
                send.setVisibility(View.INVISIBLE);
                String msg = contact_txt.getText().toString();

                Calendar calendar = Calendar.getInstance();;
                String Date = new SimpleDateFormat("yyyy MM dd kk mm ss").format(calendar.getTime());
                FirebaseDatabase.getInstance().getReference().child("Assistance").child(currentUserId +"~"+name.getText().toString()).child(Date).setValue(msg);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                contact_txt.setText(null);

                StyleableToast.makeText(getApplicationContext(), "Message envoyé", LENGTH_SHORT, R.style.publishedToast).show();
            }
        });


        DatabaseReference[] tabReff = Function.Parcours();
        count = 0;
        for (DatabaseReference data : tabReff) {
            data.addListenerForSingleValueEvent(new ValueEventListener() {

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String key = child.getKey().toString();
                        String userID = String.valueOf(dataSnapshot.child(key).child("userId").getValue());
                        //get the announce of the current user on the screen
                        if (currentUserId.equals(userID)) {
                        count++;
                        }

                    }


                    count_announce.setText(""+count);
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
