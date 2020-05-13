package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {


    public static final String EXTRA_ID="fr.c7regne.seekandsharedrawer";


    private Button search;
    private Button publication;
    DatabaseReference reff;
    String currentUserId;
    private View v;

    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v2 = inflater.inflate(R.layout.fragment_home, container, false);
        reff = FirebaseDatabase.getInstance().getReference().child("Tanguy");
        DatabaseReference[] tabReff = Function.Parcours();
/*
        //circle
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular);
        final ProgressBar mProgress=v.findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);

      /*  ObjectAnimator animation = ObjectAnimator.ofInt(mProgress, "progress", 0, 100);
        animation.setDuration(50000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (pStatus < 100) {
                    pStatus += 1;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mProgress.setProgress(pStatus);
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        // Just to display the progress slowly
                        Thread.sleep(8); //thread will take approx 1.5 seconds to finish
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/




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