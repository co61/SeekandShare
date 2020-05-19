package fr.c7regne.seekandsharedrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {


    private static final String EXTRA_ID = "fr.c7regne.seekandsharedrawer";

    private Button search;
    private Button publication;
    private DatabaseReference reff;
    String currentUserId;
    private View v;


    int pStatus = 0;
    private Handler handler = new Handler();
    TextView tv;

    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    Thread thread;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container, false);

        return v2;
    }


    @Override
    public void onStart() {
        progressBar = new ProgressDialog(v2.getContext());

        progressBar.setCancelable(true);
        progressBar.setMessage("Chargement des annonces");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(1000);
        progressBar.show();
        progressBarStatus = 0;

        thread=new Thread(new Runnable() {
            public void run() {
                while (progressBarStatus < progressBar.getMax()) {
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

                if (progressBarStatus >= progressBar.getMax()) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.dismiss();
                }
            }

        });
        thread.start();
        View layoutremove = (View) v2.findViewById(R.id.home_announce_list);
        ((ViewGroup) layoutremove).removeAllViews();

        DatabaseReference[] tabReff = Function.Parcours();
        super.onStart();
        for (DatabaseReference data : tabReff) {
            data.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (isVisible()) {
                            String key = child.getKey().toString();
                            LinearLayout layout = (LinearLayout) v2.findViewById(R.id.home_announce_list);
                            //sending to put on screen
                            LinearLayout Aview = Function.takePost(dataSnapshot, key, getActivity(), layout);
                            final String finalI = dataSnapshot.getRef().getParent().getKey() + "~" + dataSnapshot.getKey() + "~" + String.valueOf(key);
                            Aview.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //switch to Announce Fragment to show the announce published
                                    Intent act = new Intent(v.getContext(), AffichagePostActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ParentActivity", "Homefragment");
                                    bundle.putString("ID", finalI);
                                    act.putExtras(bundle);
                                    startActivity(act);
                                }
                            });


                        }


                    }
                    progressBar.setMax(progressBarStatus);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

}

