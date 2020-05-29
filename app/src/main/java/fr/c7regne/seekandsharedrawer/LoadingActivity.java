/*** LoadingActivity launches a loading screen at the start of the application ***/

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


public class LoadingActivity extends AppCompatActivity {

    private Animation rotateAnimation;
    private ImageView imageView;
    private final int duration = 1900;
    private Animation bottomAnim;
    private TextView slogan,name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //launches the view in full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading_);

        //animate the screen
        //with a rotating logo
        imageView=(ImageView)findViewById(R.id.imageView);
        rotateAnimation();
        //and a text coming from the bottom of the screen
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        slogan = findViewById(R.id.loading_text_view1);
        name= findViewById(R.id.loading_text_view2);

        slogan.setAnimation(bottomAnim);
        name.setAnimation(bottomAnim);

        //When it's done (after the duration set by us, here 1900ms), launches the SignInActivity
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
    //used to rotate the logo
    private void rotateAnimation() {
        rotateAnimation= AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView.startAnimation(rotateAnimation);
    }
}
