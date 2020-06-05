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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class SearchFragment extends Fragment implements View.OnClickListener {
    //element of the fragment
    private EditText search_txt;
    private Button searchButton;
    private TextView txtview;
    //RadioGroups and radioButtons for Pret/Service and Demande/Proposition
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
        //get elements of the Fragment
        search_txt = (EditText) v.findViewById(R.id.search_txt);
        searchButton = (Button) v.findViewById(R.id.validate_search_button);
        radioGroup1 = (RadioGroup) v.findViewById(R.id.search_DP_radioGroup);
        radioGroup2 = (RadioGroup) v.findViewById(R.id.search_SP_radioGroup);
        //validation to search data
        searchButton.setOnClickListener(this);

        return v;
    }
    public static String SearchInput="fr.c7regne.seekandsharedrawer";
    @Override
    public void onClick(View view) {
        //pass to string text enter by user
        SearchInput = search_txt.getText().toString();
        //get selected radiobutton
        int radioID1=radioGroup1.getCheckedRadioButtonId();
        radioButton1=v.findViewById(radioID1);
        int radioID2=radioGroup2.getCheckedRadioButtonId();
        radioButton2=v.findViewById(radioID2);


        //Launch the searchActivity with the arguments given by the user in SearchInput
        launchIntent = new Intent(getContext(),SearchActivity.class);
        SearchInput = radioButton2.getText().toString()+ " " + radioButton1.getText().toString() + " " + SearchInput;
        launchIntent.putExtra(SearchInput, SearchInput);
        startActivity(launchIntent);



    }


}
