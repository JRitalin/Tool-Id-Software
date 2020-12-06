package com.example.tool_identification.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tool_identification.R;


public class SettingsFrag1 extends Fragment {
    //Variables
    TextView textView;

    public SettingsFrag1(){
        // Required Empty Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){
        //This inflates the layout
        View view = inflater.inflate(R.layout.frag_settings,container,false);
        textView = view.findViewById(R.id.text);
        textView.setText("Settings");

        return view;
    }
}
