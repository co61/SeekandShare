package fr.c7regne.seekandsharedrawer;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class HomeFragment extends Fragment {


    private static final String EXTRA_ID = "fr.c7regne.seekandsharedrawer";

    private Button search;
    private Button publication;
    private DatabaseReference reff;
    String currentUserId;
    private View v2;


    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        v2 = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = new ProgressDialog(v2.getContext());
        progressBar.setCancelable(true);
        progressBar.setMessage("Chargement des annonces");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < 100) {
                    progressBarStatus++;
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }

                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }
        }).start();
        return v2;
    }


    @Override
    public void onStart() {

        DatabaseReference[] tabReff = Function.Parcours();
        super.onStart();
        for (DatabaseReference data : tabReff) {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String key = child.getKey().toString();

                        LinearLayout layout = (LinearLayout) v2.findViewById(R.id.home_announce_list);

                        //sending to put on screen
                        LinearLayout Aview = Function.takePost(dataSnapshot, key, getActivity(), layout);
                        final String finalI = dataSnapshot.getRef().getParent().getKey() + " " + dataSnapshot.getKey() + " " + String.valueOf(key);
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
            });
        }
    }

}

