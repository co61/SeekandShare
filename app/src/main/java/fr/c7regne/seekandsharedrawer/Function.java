package fr.c7regne.seekandsharedrawer;

import android.app.Activity;
import android.os.Build;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Function  {

    public static DatabaseReference[] Parcours() {
        /***
         * read the different category of posts database and return a table of DatabaseRefference
         */
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Posts");
        DatabaseReference[] tabReff = new DatabaseReference[4];
        tabReff[0] = reff.child("Prêt").child("Proposition");
        tabReff[1] = reff.child("Prêt").child("Demande");
        tabReff[2] = reff.child("Service").child("Proposition");
        tabReff[3] = reff.child("Service").child("Demande");
        return tabReff;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static LinearLayout takePost(DataSnapshot dataSnapshot, String key, Activity context, LinearLayout layout) {
        /**
         * Reed the element of the announce in database datasnapshot give in parameter with the key
         * Call addAnnounceUser to display the announce on the view give in parameter
         * Return the view for action needed on it
         */
        DataSnapshot keyContent = dataSnapshot.child(key);
        String title = String.valueOf(keyContent.child("title").getValue());
        String content = String.valueOf(keyContent.child("content").getValue());
        String dpchoice = String.valueOf(keyContent.child("dpchoice").getValue());
        String spchoice = String.valueOf(keyContent.child("spchoice").getValue());
        String publicationDate = String.valueOf(keyContent.child("publicationDate").getValue());
        String userName = String.valueOf(keyContent.child("userName").getValue());
        String userID = String.valueOf(keyContent.child("userId").getValue());
        //sending to put on screen
        LinearLayout Aview = new AddViewListAnnounce().addAnnounceUser(context, title, publicationDate, dpchoice, spchoice, content, userName, userID);
        layout.addView(Aview);
        return Aview;
    }


}
