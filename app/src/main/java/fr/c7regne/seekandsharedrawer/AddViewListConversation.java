package fr.c7regne.seekandsharedrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class AddViewListConversation extends AppCompatActivity {

    private final int textColor = Color.BLACK;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public LinearLayout addConversation(final Activity activity, String message) {

        //linearlayout à empiler
        LinearLayout newLL = new LinearLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = dpToPx(15, activity.getApplicationContext());
        layoutParams.setMargins(margin, margin, margin, margin);
        newLL.setBackgroundResource(R.drawable.corner);
        newLL.setLayoutParams(layoutParams);
        newLL.setOrientation(LinearLayout.VERTICAL);
        int padding = dpToPx(10, activity.getApplicationContext());
        newLL.setPadding(padding, padding, padding, padding);








        //title
        TextView textViewTitle = new TextView(activity);
        LinearLayout.LayoutParams title_linear = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(title_linear);
        textViewTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textViewTitle.setText(message);
        textViewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, spToPx(6, activity.getApplicationContext()));
        textViewTitle.setTextColor(textColor);
        //fin linearLayout


        newLL.addView(textViewTitle);

        //fin LinearLayout

        return newLL;


    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }



}

