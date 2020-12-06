package com.example.tool_identification.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tool_identification.R;


public class HelpFrag1 extends Fragment {

    TextView textView;

    public HelpFrag1() {
        // Required Empty Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        //This inflates the layout
        View view = inflater.inflate(R.layout.frag_help, container, false);
        textView = view.findViewById(R.id.text);
        textView.setText("Help");

        return view;
    }



}
