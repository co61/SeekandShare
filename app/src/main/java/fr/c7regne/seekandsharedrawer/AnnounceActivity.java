package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnounceActivity extends AppCompatActivity {

    TextView a;
    TextView b;
    TextView c;
    TextView d;
    TextView e;
    TextView f;
    TextView g;



    DatabaseReference reff;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announce_test_dynamic);

        //change title action Bar
        getSupportActionBar().setTitle("Mes annonces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i=0;i<10;i++){
            addAnnounce(i);
        }
/*
        a=(TextView)findViewById(R.id.a);
        b=(TextView)findViewById(R.id.b);
        c=(TextView)findViewById(R.id.c);
        d=(TextView)findViewById(R.id.d);
        e=(TextView)findViewById(R.id.e);
        f=(TextView)findViewById(R.id.f);
        g=(TextView)findViewById(R.id.g);

        reff= FirebaseDatabase.getInstance().getReference().child("Posts").child("1");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String title=(String)dataSnapshot.child("title").getValue();
                String content=dataSnapshot.child("content").getValue().toString();
                String dpchoice=dataSnapshot.child("dpchoice").getValue().toString();
                String spchoice=dataSnapshot.child("spchoice").getValue().toString();
                String publicationDate=dataSnapshot.child("publicationDate").getValue().toString();
                String userID=dataSnapshot.child("userId").getValue().toString();
                String userName=dataSnapshot.child("userName").getValue().toString();

                a.setText(title);
                b.setText(content);
                c.setText(dpchoice);
                d.setText(spchoice);
                e.setText(publicationDate);
                g.setText(userID);
                f.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    private void addAnnounce(int i) {
        LinearLayout layout=(LinearLayout) findViewById(R.id.linearlayout_announce_list);
        //linearlayout à empiler
        LinearLayout newLL= new LinearLayout(AnnounceActivity.this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin=dpToPx(15,getApplicationContext());
        layoutParams.setMargins( margin,margin,margin,margin);
        newLL.setLayoutParams(layoutParams);
        newLL.setOrientation(LinearLayout.VERTICAL);
        int padding=dpToPx(10,getApplicationContext());
        newLL.setPadding(padding,padding,padding,padding);




        //title
        TextView textViewTitle= new TextView(AnnounceActivity.this);
        textViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewTitle.setText("Title  "+i);
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(9,getApplicationContext()));
        textViewTitle.setTextColor(Color.BLACK);
        //Date
        TextView textViewDate= new TextView(AnnounceActivity.this);
        textViewDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewDate.setText("Date  "+i);
        textViewDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, spToPx(4,getApplicationContext()));
        textViewDate.setPadding( 0 ,0,0,dpToPx(2,getApplicationContext()));
        textViewDate.setBackgroundResource(R.drawable.bottom_border_grey);
        textViewDate.setTextColor(Color.BLACK);
        //Content
        TextView textViewContent= new TextView(AnnounceActivity.this);
        LinearLayout.LayoutParams textViewContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewContentParams.height=dpToPx(54,getApplicationContext());
        textViewContent.setLayoutParams(textViewContentParams);
        textViewContent.setText("Content  "+i);
        textViewContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(6,getApplicationContext()));
        textViewContent.setTextColor(Color.BLACK);
        //PublisherName
        TextView textViewPublisherName= new TextView(AnnounceActivity.this);
        LinearLayout.LayoutParams textViewPublisherNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewPublisherNameParams.gravity= Gravity.END;
        textViewPublisherName.setLayoutParams(textViewPublisherNameParams);
        textViewPublisherName.setText("PublisherName  "+i);
        textViewPublisherName.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(5,getApplicationContext()));
        textViewPublisherName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        textViewPublisherName.setTextColor(Color.BLACK);

        newLL.addView(textViewTitle);
        newLL.addView(textViewDate);
        newLL.addView(textViewContent);
        newLL.addView(textViewPublisherName);


        layout.addView(newLL);

    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    //if click onretrun android button then go back to home
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
