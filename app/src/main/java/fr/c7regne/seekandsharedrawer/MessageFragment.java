package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MessageFragment extends Fragment {
    /***
     * Show the conversation of the currentUser with other user in the panel message in main activity
     *
     */

    private View v;
    private String currentUserId;
    private DatabaseReference reff;

    private MessSaveStruct lastmsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_message, container, false);
        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (signInAccount != null) {
            ///userEmail = signInAccount.getEmail();
            currentUserId = signInAccount.getId();
        }

        return v;
    }


    @Override
    public void onStart() {

        super.onStart();

        //get conversation of currentUser
        reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //refresh the layout
                    View layoutremove = (View) v.findViewById(R.id.linearlayout_conversation_list);
                    ((ViewGroup) layoutremove).removeAllViews();
                    if (isVisible()) {
                        final String key = child.getKey().toString();
                        final LinearLayout layout = (LinearLayout) v.findViewById(R.id.linearlayout_conversation_list);
                        //get number of non-read message
                        int nbrLMsg=0;
                        for(DataSnapshot nbrchild : child.getChildren()){
                            if(nbrchild.child("read").getValue().toString().equals("false")){
                                nbrLMsg++;
                            }
                        }

                        //get the last msg and on change show it
                        Query last = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(key).orderByKey().limitToLast(1);
                        final int finalNbrLMsg = nbrLMsg;

                        last.addListenerForSingleValueEvent(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (isVisible()) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        lastmsg = new MessSaveStruct((Boolean) child.child("side").getValue(), child.child("msg").getValue().toString(),
                                                child.child("date").getValue().toString(), Boolean.valueOf(child.child("read").getValue().toString()));
                                        //add the conversation to the layout
                                        LinearLayout Aview = new AddViewListConversation().addConversation(getActivity(), key.split("~")[1], finalNbrLMsg, lastmsg);
                                        final String finalI = key;
                                        layout.addView(Aview);

                                        //open the conversation on click and give the ID to the MessageActivity
                                        Aview.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent act = new Intent(v.getContext(), MessageActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("ID", finalI);
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


}
