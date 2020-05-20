package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.muddzdev.styleabletoast.StyleableToast;

import static android.widget.Toast.LENGTH_SHORT;

public class EditAnnounceActivity extends AppCompatActivity {

    DatabaseReference reff;
    String currentUserId, userName, fulldate;
    String ID;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioButton radioButton1;
    RadioButton radioButton2;
    TextView titleView, placeView, caractnb;
    EditText contentView;
    Button editbutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_announce);
        //change title action Bar
        getSupportActionBar().setTitle("Editer mon annonce");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        ID = extras.getString("ID");

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
            userName = signInAccount.getDisplayName();
        }

        titleView = (TextView) findViewById(R.id.edit_title_txt);
        placeView = (TextView) findViewById(R.id.edit_place_txt);
        radioGroup1 = (RadioGroup) findViewById(R.id.edit_SP_radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.edit_DP_radioGroup);
        contentView = (EditText) findViewById(R.id.edit_content_txt);
        caractnb = (TextView) findViewById(R.id.edit_count_txt);
        editbutton = (Button) findViewById(R.id.validate_edit_button);
        final String[] way = ID.split("~");

        reff = FirebaseDatabase.getInstance().getReference().child("Posts").child(way[0]).child(way[1]);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot keyContext = dataSnapshot.child(way[2]);
                String title = String.valueOf(keyContext.child("title").getValue());
                String content = String.valueOf(keyContext.child("content").getValue());
                String dpchoice = String.valueOf(keyContext.child("dpchoice").getValue());
                String spchoice = String.valueOf(keyContext.child("spchoice").getValue());
                String place = String.valueOf(keyContext.child("place").getValue());
                fulldate = String.valueOf(keyContext.child("publicationDate").getValue());

                titleView.setText(title);
                placeView.setText(place);
                if (dpchoice.equals("Demande")) {
                    RadioButton r1 = findViewById(R.id.edit_demande_radio);
                    r1.setChecked(true);
                    RadioButton r2 = findViewById(R.id.edit_propostion_radio);
                    r2.setChecked(false);
                } else {
                    RadioButton r1 = findViewById(R.id.edit_demande_radio);
                    r1.setChecked(false);
                    RadioButton r2 = findViewById(R.id.edit_propostion_radio);
                    r2.setChecked(true);
                }

                if (spchoice.equals("Service")) {
                    RadioButton r1 = findViewById(R.id.edit_service_radio);
                    r1.setChecked(true);
                    RadioButton r2 = findViewById(R.id.edit_pret_radio);
                    r2.setChecked(false);
                } else {
                    RadioButton r1 = findViewById(R.id.edit_service_radio);
                    r1.setChecked(false);
                    RadioButton r2 = findViewById(R.id.edit_pret_radio);
                    r2.setChecked(true);
                }
                contentView.setText(content);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //increment carct_count of the announce content
        contentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputContent = contentView.getText().toString();
                if (inputContent.length() <= 50) {
                    caractnb.setText("Caractères : " + String.valueOf(inputContent.length()) + "/50");
                } else {
                    caractnb.setText("Caractères : 50/50");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        int radioID1 = radioGroup1.getCheckedRadioButtonId();
        radioButton1 = findViewById(radioID1);
        int radioID2 = radioGroup2.getCheckedRadioButtonId();
        radioButton2 = findViewById(radioID2);
        Log.i("text", radioButton1.getText().toString());
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.child(way[2]).setValue(null);
                //pass to string text enter by user
                String inputTitle = titleView.getText().toString();
                String inputContent = contentView.getText().toString();
                String inputPlace = placeView.getText().toString();
                //radiobutton selected
                int radioID1 = radioGroup1.getCheckedRadioButtonId();
                radioButton1 = findViewById(radioID1);
                int radioID2 = radioGroup2.getCheckedRadioButtonId();
                radioButton2 = findViewById(radioID2);
                Log.i("text", radioButton1.getText().toString());

                //Save in database if something in EditText
                if (inputTitle.equals("") || inputContent.equals("") || inputContent.length() <= 50 || inputPlace.equals("")) {

                    if (inputContent.equals("") || inputContent.length() <= 50) {
                        contentView.setError("Il manque du contenu");
                        contentView.requestFocus();
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.error_post_msg), LENGTH_SHORT).show();

                } else {
                    reff = FirebaseDatabase.getInstance().getReference().child("Posts").child(radioButton1.getText().toString()).child(radioButton2.getText().toString());
                    PostSaveStruct postsave;
                    //construction struct to send into database with auto increment depending on number of member in this branch
                    postsave = new PostSaveStruct(currentUserId, userName, inputTitle, inputContent, inputPlace, radioButton2.getText().toString(), radioButton1.getText().toString(), fulldate);
                    reff.child(currentUserId + "-" + inputTitle).setValue(postsave);

                    String finalI = radioButton1.getText().toString() + "~" + radioButton2.getText().toString() + "~" + currentUserId + "-" + inputTitle;


                    //confirm to the user that the announce is published
                    StyleableToast.makeText(getApplicationContext(), getString(R.string.post_published), LENGTH_SHORT, R.style.publishedToast).show();
                    //switch to Announce Fragment to show the announce published
                    Bundle bundle = new Bundle();
                    bundle.putString("ParentActivity", "AnnounceActivity");
                    bundle.putString("ID", finalI);
                    Intent act = new Intent(getApplicationContext(), AffichagePostActivity.class);
                    act.putExtras(bundle);
                    startActivity(act);
                    finish();

                }
            }
        });

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
