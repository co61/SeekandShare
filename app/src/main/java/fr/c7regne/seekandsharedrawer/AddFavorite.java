package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddFavorite extends AppCompatActivity {
    DatabaseReference reff;
    String currentUserId;

    @SuppressLint("ResourceAsColor")
    public void addToFavorite(final Context activity, final String key, final ImageView btn) {



        reff = FirebaseDatabase.getInstance().getReference().child("Favorite");






    }

}

