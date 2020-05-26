package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.BoringLayout;
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
    /***
     * Show the conversation between two user
     * by getting each messages in FireBase and add them in the layout
     *
     */

    private ScrollView scroll;
    private String currentUserId;
    private String userId;
    private EditText message;
    private Button send;
    private DatabaseReference reff;
    private String userName;
    private String currentUserName;
    private Query qReff;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //get the userName and UserId of the other user in userId
        userId = getIntent().getExtras().getString("ID");
        scroll = (ScrollView) findViewById(R.id.scroll);
        message = (EditText) findViewById(R.id.message_field);
        send = (Button) findViewById(R.id.send_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //separate name and Id
        userName = userId.split("~")[1];
        getSupportActionBar().setTitle(userName);
        userId = userId.split("~")[0];

        //get information of the currentUser
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {

            currentUserName = signInAccount.getDisplayName();
            currentUserId = signInAccount.getId();
        }

        //get and show the new messages
        refreshMessage();

        //scroll to the bottom of the view to show the last message
        scroll.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                scroll.scrollTo(0, scroll.getChildAt(0).getHeight());
            }
        });

        //Send the message to the FireBase DataBase
        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                Calendar calendar = Calendar.getInstance();

                //We get two date: One for order, the other for show
                String fullDate = new SimpleDateFormat("yyyy MM dd kk mm ss").format(calendar.getTime());
                String Date = new SimpleDateFormat("dd MMMM yyyy - kk:mm").format(calendar.getTime());

                //Send the message in the currentUser database Message
                reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(userId + "~" + userName);
                MessSaveStruct Mess = new MessSaveStruct(true, msg, Date, false);
                reff.child(fullDate).setValue(Mess);

                //Send the message to the other user database message
                reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(userId).child(currentUserId + "~" + currentUserName);
                Mess.setSide(false);
                reff.child(fullDate).setValue(Mess);

                //close keyboard and scroll down
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                message.setText(null);

                scroll.scrollTo(0, scroll.getChildAt(0).getHeight());
            }
        });



    }

    ValueEventListener listener;
    DatabaseReference setreff,reffSender;
    protected void refreshMessage() {
        View layoutremove = (View) findViewById(R.id.linearlayout_message_list);
        ((ViewGroup) layoutremove).removeAllViews();

        //get the conversation between the two users in FireBase
        qReff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(userId + "~" + userName).orderByKey();
        setreff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(userId + "~" + userName);
        reffSender= FirebaseDatabase.getInstance().getReference().child("Messages").child(userId).child(currentUserId+"~"+currentUserName);

        listener=setreff.orderByKey().limitToLast(20).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //reset the view
                View layoutremove = (View) findViewById(R.id.linearlayout_message_list);
                ((ViewGroup) layoutremove).removeAllViews();

                //for each message in the conversation, we add it in the layout
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    LinearLayout layout = (LinearLayout) findViewById(R.id.linearlayout_message_list);
                    final boolean read=Boolean.valueOf(child.child("read").getValue().toString());

                    //Add the message to the layout
                    LinearLayout Aview = new AddViewListMessage()
                            .addMessageUser(MessageActivity.this, child.child("msg").getValue().toString(), (Boolean) child.child("side").getValue(), child.child("date").getValue().toString(), read);
                    layout.addView(Aview);
                    setreff.child(child.getKey()).child("read").setValue(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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

    //if click on return android button then go back to home
    @Override
    public void onBackPressed() {

        if (qReff != null && listener != null) {
            setreff.removeEventListener(listener);
        }
        finish();
    }
}
