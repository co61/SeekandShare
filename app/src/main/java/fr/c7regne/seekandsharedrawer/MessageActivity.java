package fr.c7regne.seekandsharedrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {


    ScrollView scroll;
    String currentUserId;
    String userId;
    EditText message;
    Button send;
    DatabaseReference reff;
    String userName;
    String currentUserName;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        userId = getIntent().getExtras().getString("ID");
        scroll = (ScrollView) findViewById(R.id.scroll);
        message = (EditText) findViewById(R.id.message_field);
        send = (Button) findViewById(R.id.send_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userName = userId.split("~")[1];
        getSupportActionBar().setTitle(userName);
        userId = userId.split("~")[0];

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {

            currentUserName = signInAccount.getDisplayName();
            currentUserId = signInAccount.getId();
        }

        refreshMessage();

        scroll.scrollTo(0, scroll.getBottom());

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                String msg = message.getText().toString();
                Calendar calendar = Calendar.getInstance();
                String fullDate = new SimpleDateFormat("yyyy MM dd kk mm ss").format(calendar.getTime());
                String Date = new SimpleDateFormat("dd MMMM yyyy - kk:mm").format(calendar.getTime());

                reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(userId+"~"+userName);
                MessSaveStruct Mess = new MessSaveStruct(true, msg, Date);
                reff.child(fullDate).setValue(Mess);
                reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(userId).child(currentUserId+"~"+currentUserName);
                Mess.setSide(false);
                reff.child(fullDate).setValue(Mess);



                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                message.setText(null);
                scroll = (ScrollView) findViewById(R.id.scroll);
                scroll.scrollTo(0, scroll.getBottom());
            }
        });


    }


    protected void refreshMessage(){
        View layoutremove=(View) findViewById(R.id.linearlayout_message_list);
    ((ViewGroup) layoutremove).removeAllViews();
    //reed children posts count


        Query reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(userId+"~"+userName).orderByKey();



        reff.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                View layoutremove=(View) findViewById(R.id.linearlayout_message_list);
                ((ViewGroup) layoutremove).removeAllViews();
                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    //get the announce of the current user on the screen
                    LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_message_list);
                    LinearLayout Aview = new AddViewListMessage().addMessageUser(MessageActivity.this, child.child("msg").getValue().toString(), (Boolean) child.child("side").getValue(), child.child("date").getValue().toString());
                    Log.i("test",child.child("side").getValue().toString());
                    layout.addView(Aview);
                }


                scroll = (ScrollView) findViewById(R.id.scroll);
                scroll.scrollTo(0, scroll.getBottom());
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
}
