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


    public static final String EXTRA_ID="fr.c7regne.seekandsharedrawer";

    private Button search;
    private Button publication;
    DatabaseReference reff;
    String currentUserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v2 = inflater.inflate(R.layout.fragment_home, container, false);

        reff = FirebaseDatabase.getInstance().getReference().child("Tanguy");
        DatabaseReference[] tabReff = Function.Parcours();

        for (DatabaseReference data : tabReff) {
        data.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey().toString();

                    LinearLayout layout = (LinearLayout) v2.findViewById(R.id.home_announce_list);

                    //sending to put on screen
                    LinearLayout Aview = Function.takePost(dataSnapshot, key, getActivity(), layout);
                    final String finalI = dataSnapshot.getRef().getParent().getKey() + " " + dataSnapshot.getKey() + " " +String.valueOf(key);
                    Aview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //switch to Announce Fragment to show the announce published
                            Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                            act.putExtra(EXTRA_ID, finalI);
                            startActivity(act);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });}

        return v2;
    }
}