package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddFavorite extends AppCompatActivity {
    DatabaseReference reff;
    String currentUserId;
    FavoriteSaveStruct favoritesave;

    @SuppressLint("ResourceAsColor")
    public void addToFavorite(final Context activity, CharSequence key) {

        //get information on user
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(activity.getApplicationContext());
        if (signInAccount != null) {
            currentUserId = signInAccount.getId();
        }

        reff= FirebaseDatabase.getInstance().getReference().child("Favorite");

        //construction struct to send into database with auto increment depending on number of member in this branch
        favoritesave = new FavoriteSaveStruct(key);
        //reff.child(currentUserId).setValue(favoritesave);
        Toast.makeText(activity.getApplicationContext(),"ça marche",Toast.LENGTH_SHORT).show();

    }

}

