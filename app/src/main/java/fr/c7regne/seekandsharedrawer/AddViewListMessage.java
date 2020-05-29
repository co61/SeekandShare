package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Type;


public class AddViewListMessage extends AppCompatActivity {
    /***
     * Add message in linearLayout in the activity given in argument
     * Show the message and the date on right if currentUser
     * on the left if its the other user
     */
    private final int textColor = Color.BLACK;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})

    public LinearLayout addMessageUser(final Activity activity, String message, Boolean side, String date, Boolean read) {


        //create the LinearLayout with chosen parameters
        LinearLayout newLL = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = dpToPx(15, activity.getApplicationContext());
        int marginSide1 = dpToPx(50, activity.getApplicationContext());
        int marginSide2 = dpToPx(10, activity.getApplicationContext());
        if(side){
            layoutParams.setMargins(marginSide1, margin, marginSide2, margin);
            newLL.setBackgroundResource(R.drawable.cornerright);
        }
        else{
            layoutParams.setMargins(marginSide2, margin, marginSide1, margin);
            newLL.setBackgroundResource(R.drawable.cornerleft);
        }
        newLL.setLayoutParams(layoutParams);
        newLL.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(10, activity.getApplicationContext());
        newLL.setPadding(padding, padding, padding, padding);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            newLL.setElevation(3);
            newLL.setTranslationZ(6);
        }

        //create the message format
        TextView textViewTitle = new TextView(activity);
        LinearLayout.LayoutParams title_linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(title_linear);
        textViewTitle.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        textViewTitle.setText(message);
        if(!read){
            textViewTitle.setTypeface(Typeface.DEFAULT_BOLD);
        }
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spToPx(6, activity.getApplicationContext()));
        textViewTitle.setTextColor(textColor);

        //Create the Date
        TextView textViewDate = new TextView(activity);
        textViewDate.setText(date);
        textViewDate.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        textViewDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, spToPx(4, activity.getApplicationContext()));
        textViewDate.setTextColor(textColor);

        //add the message and the date to the layout
        newLL.addView(textViewTitle);
        newLL.addView(textViewDate);


        //return the created Layout

        return newLL;


    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }



}

