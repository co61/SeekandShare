package fr.c7regne.seekandsharedrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.LENGTH_SHORT;

public class PubAnnounceFragment extends Fragment implements View.OnClickListener {
    //ellement
    EditText content_announce;
    EditText title_announce;
    Button postButton;
    //Radiogroups
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioButton radioButton1;
    RadioButton radioButton2;
    //database
    PostSaveStruct postsave;
    DatabaseReference reff;
    int maxid;
    TextView caractnb;
    String name, email, id;


    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_post_announce, container, false);
        //find element
        title_announce = (EditText) v.findViewById(R.id.post_title_txt);
        content_announce = (EditText) v.findViewById(R.id.post_content_txt);
        postButton = (Button) v.findViewById(R.id.validate_post_button);
        radioGroup1 = (RadioGroup) v.findViewById(R.id.post_SP_radioGroup);
        radioGroup2 = (RadioGroup) v.findViewById(R.id.post_DP_radioGroup);
        caractnb = (TextView) v.findViewById(R.id.caractnb);

        //get information on user
        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(getContext());
        if(signInAccount != null){
            name=signInAccount.getDisplayName();
            email=signInAccount.getEmail();
            id=signInAccount.getId();
        }
        reff = FirebaseDatabase.getInstance().getReference().child("Posts").child(id);
        title_announce.requestFocus();

        //increment carct_count of the announce content
        content_announce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputContent = content_announce.getText().toString();
                if (inputContent.length() <= 50) {
                    caractnb.setText("Caractères : " + String.valueOf(inputContent.length()) + "/50");
                }else {
                    caractnb.setText("Caractères : 50/50");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //count number of post already in database to increment by one the id of the post
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    maxid = (int)dataSnapshot.getChildrenCount();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //validation to push data in FirebaseRealtimedatabase
        postButton.setOnClickListener(this);

        return v;
    }

    String inputTitle;
    String inputContent;

    @Override
    public void onClick(View view) {
        //Auto increment in database


        //pass to string text enter by user
        inputTitle = title_announce.getText().toString();
        inputContent = content_announce.getText().toString();
        //radiobutton selected
        int radioID1 = radioGroup1.getCheckedRadioButtonId();
        radioButton1 = v.findViewById(radioID1);
        int radioID2 = radioGroup2.getCheckedRadioButtonId();
        radioButton2 = v.findViewById(radioID2);

        //Save in database if something in EditText
        if (inputTitle.equals("") || inputContent.equals("") || inputContent.length() <= 50) {


            if (inputTitle.equals("")) {
                title_announce.setError("Erreur");
                title_announce.requestFocus();
            }
            if (inputContent.equals("") || inputContent.length() <= 50) {
                content_announce.setError("Erreur");
                content_announce.requestFocus();
            }
            Toast.makeText(getActivity(), getString(R.string.error_post_msg), LENGTH_SHORT).show();

        } else {
            //construction struct to send into database with auto increment depending on number of member in this branch
            postsave= new PostSaveStruct(inputTitle,inputContent,radioButton2.getText().toString(),radioButton1.getText().toString());
            reff.child(String.valueOf(maxid+1)).setValue(postsave);


            //confirm to the user that the announce is published
            Toast.makeText(getActivity(), getString(R.string.post_published), LENGTH_SHORT).show();

            //switch to Announce Fragment to show the announce published
            Intent act = new Intent(getActivity(), AnnounceActivity.class);
            startActivity(act);
        }
    }


}
