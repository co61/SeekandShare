package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    private Button search;
    private Button publication;
    DatabaseReference reff;
    String currentUserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        reff = FirebaseDatabase.getInstance().getReference().child("Tanguy");

        reff.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey().toString();
                    String userID = String.valueOf(dataSnapshot.child(key).child("userId").getValue());

                    LinearLayout layout = (LinearLayout) v.findViewById(R.id.home_announce_list);
                    String title = String.valueOf(dataSnapshot.child(key).child("title").getValue());
                    String content = String.valueOf(dataSnapshot.child(key).child("content").getValue());
                    String dpchoice = String.valueOf(dataSnapshot.child(key).child("dpchoice").getValue());
                    String spchoice = String.valueOf(dataSnapshot.child(key).child("spchoice").getValue());
                    String publicationDate = String.valueOf(dataSnapshot.child(key).child("publicationDate").getValue());
                    String userName = String.valueOf(dataSnapshot.child(key).child("userName").getValue());
                    //sending to put on screen
                    LinearLayout Aview = new AddViewListAnnounce().addAnnounceUser(getActivity(), title, publicationDate, dpchoice, spchoice, content, userName, userID);
                    layout.addView(Aview);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }
}