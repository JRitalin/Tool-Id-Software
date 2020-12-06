package com.example.tool_identification.UIFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tool_identification.R;


public class HomeFrag extends Fragment {


    private TextView textView;
    private Button testButton;

    public HomeFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_home, container, false);

        textView = view.findViewById(R.id.text);
        textView.setText("Home");
        testButton = view.findViewById(R.id.testButton);
        testButton.setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View view) {
        textView.setText("Success");
    }
}