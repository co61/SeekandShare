package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
    View v;
    String currentUserId;
    DatabaseReference reff;

    MessSaveStruct lastmsg;

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

        Log.i("test", "test");
        reff = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId);

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                View layoutremove = (View) v.findViewById(R.id.linearlayout_conversation_list);
                ((ViewGroup) layoutremove).removeAllViews();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (isVisible()) {
                        final String key = child.getKey().toString();
                        Log.i("test", key);
                        final LinearLayout layout = (LinearLayout) v.findViewById(R.id.linearlayout_conversation_list);

                        Query last = FirebaseDatabase.getInstance().getReference().child("Messages").child(currentUserId).child(key).orderByKey().limitToLast(1);
                        last.addListenerForSingleValueEvent(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    lastmsg =new MessSaveStruct((Boolean) child.child("side").getValue(),child.child("msg").getValue().toString(),child.child("date").getValue().toString(),Boolean.valueOf(child.child("read").getValue().toString()));

                                    //sending to put on screen
                                    LinearLayout Aview = new AddViewListConversation().addConversation(getActivity(), key.split("~")[1], lastmsg);
                                    final String finalI = key;
                                    layout.addView(Aview);
                                    Log.i("test", finalI);

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
