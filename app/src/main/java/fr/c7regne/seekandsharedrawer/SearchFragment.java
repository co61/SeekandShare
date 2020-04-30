package fr.c7regne.seekandsharedrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SearchFragment extends Fragment implements View.OnClickListener {
    //element
    private EditText search_txt;
    private Button searchButton;
    private TextView txtview;
    //RadioGroups and radioButtons
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;

    private Intent launchIntent;

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_search, container, false);
        //find element
        txtview = (TextView) v.findViewById(R.id.search_results);
        search_txt = (EditText) v.findViewById(R.id.search_txt);
        searchButton = (Button) v.findViewById(R.id.validate_search_button);
        radioGroup1 = (RadioGroup) v.findViewById(R.id.search_DP_radioGroup);
        radioGroup2 = (RadioGroup) v.findViewById(R.id.search_SP_radioGroup);
        //validation to search data
        searchButton.setOnClickListener(this);

        return v;
    }

    public String inputSearchContent;
    @Override
    public void onClick(View view) {
        //pass to string text enter by user
        inputSearchContent = search_txt.getText().toString();
        //radiobutton selected
        int radioID1=radioGroup1.getCheckedRadioButtonId();
        radioButton1=v.findViewById(radioID1);
        int radioID2=radioGroup2.getCheckedRadioButtonId();
        radioButton2=v.findViewById(radioID2);

        txtview.setText("Votre recherche est la suivante :\n"+inputSearchContent+"\nAvec les option suivante : \n  -"
                +radioButton1.getText().toString()+"\n  -"+radioButton2.getText().toString());

        launchIntent = new Intent(getContext(),SearchActivity.class);
        startActivity(launchIntent);



    }
}
