package fr.c7regne.seekandsharedrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Loading_Activity extends AppCompatActivity {

    private Animation rotateAnimation;
    private ImageView imageView;
    private final int duration = 1900;
    private Animation topAnim, bottomAnim;
    private TextView slogan, noms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_);

        //anime le logo
        imageView=(ImageView)findViewById(R.id.imageView);
        rotateAnimation();
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        slogan = findViewById(R.id.loading_text_view1);
        noms= findViewById(R.id.loading_text_view2);

        slogan.setAnimation(bottomAnim);
        noms.setAnimation(bottomAnim);

        //redirige vers la page principale MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
                finish();
            }
        }, duration );
    }

    private void rotateAnimation() {
        rotateAnimation= AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);
    }
}
