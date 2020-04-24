package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //change title action Bar
        getSupportActionBar().setTitle("Favoris");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    //if click onretrun android button then go back to home
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
