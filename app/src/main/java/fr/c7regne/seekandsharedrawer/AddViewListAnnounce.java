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


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;



public class AddViewListAnnounce extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("ResourceAsColor")
    public LinearLayout addAnnounceUser(final Context activity,  String Title, String Date, String DP, String SP, String Content, String UserName, String UserId) {



        //linearlayout à empiler
        LinearLayout newLL= new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin=dpToPx(15,activity.getApplicationContext());
        layoutParams.setMargins( margin,margin,margin,margin);
        newLL.setLayoutParams(layoutParams);
        newLL.setOrientation(LinearLayout.VERTICAL);
        int padding=dpToPx(10,activity.getApplicationContext());
        newLL.setPadding(padding,padding,padding,padding);


        //title
        TextView textViewTitle= new TextView(activity);
        textViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewTitle.setText(Title);
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(9,activity.getApplicationContext()));
        textViewTitle.setTextColor(Color.BLACK);
        //Date
        TextView textViewDate= new TextView(activity);
        textViewDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewDate.setText(Date);
        textViewDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, spToPx(4,activity.getApplicationContext()));
        textViewDate.setPadding( 0 ,0,0, dpToPx(2,activity.getApplicationContext()));
        textViewDate.setBackgroundResource(R.drawable.bottom_border_grey);
        textViewDate.setTextColor(Color.BLACK);

        //linearlayout orientation horizontal for textview DP SP
        LinearLayout LLHoriz= new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLHoriz.setLayoutParams(layoutParams2);
        LLHoriz.setOrientation(LinearLayout.VERTICAL);
        LLHoriz.setBackgroundResource(R.drawable.bottom_border_grey);
        LLHoriz.setOrientation(LinearLayout.HORIZONTAL);
        //DP
        TextView textViewDP= new TextView(activity);
        LinearLayout.LayoutParams textViewContentParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewContentParams2.width=0;
        textViewContentParams2.weight= (float) 0.5;
        textViewDP.setLayoutParams(textViewContentParams2);
        textViewDP.setText(DP);
        textViewDP.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(4,activity.getApplicationContext()));
        textViewDP.setTextColor(Color.BLACK);
        //SP
        TextView textViewSP= new TextView(activity);
        textViewSP.setLayoutParams(textViewContentParams2);
        textViewSP.setText(SP);
        textViewSP.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(4,activity.getApplicationContext()));
        textViewSP.setTextColor(Color.BLACK);
        //fin linearLayout
        LLHoriz.addView(textViewDP);
        LLHoriz.addView(textViewSP);

        //Content
        TextView textViewContent= new TextView(activity);
        LinearLayout.LayoutParams textViewContentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewContentParams.height=dpToPx(54,activity.getApplicationContext());
        textViewContent.setLayoutParams(textViewContentParams);
        textViewContent.setText(Content);
        textViewContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(6,activity.getApplicationContext()));
        textViewContent.setTextColor(Color.BLACK);
        //PublisherName
        TextView textViewPublisherName= new TextView(activity);
        LinearLayout.LayoutParams textViewPublisherNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewPublisherNameParams.gravity= Gravity.END;
        textViewPublisherName.setLayoutParams(textViewPublisherNameParams);
        textViewPublisherName.setText("PublisherName  "+UserName);
        textViewPublisherName.setTextSize(TypedValue.COMPLEX_UNIT_SP,spToPx(4,activity.getApplicationContext()));
        textViewPublisherName.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        textViewPublisherName.setTextColor(Color.BLACK);
        //fin LinearLayout
        newLL.addView(textViewTitle);
        newLL.addView(textViewDate);
        newLL.addView(LLHoriz);
        newLL.addView(textViewContent);
        newLL.addView(textViewPublisherName);


        return newLL;

    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}

