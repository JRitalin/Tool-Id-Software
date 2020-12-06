package com.example.tool_identification;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tool_identification.UIFragments.CamFrag2;
import com.example.tool_identification.UIFragments.HomeFrag;
import com.example.tool_identification.UIFragments.CamFrag;
import com.example.tool_identification.UIFragments.HelpFrag1;
import com.example.tool_identification.UIFragments.SettingsFrag1;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActionBar navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navBar = getSupportActionBar();
        navBar.setTitle("Tool ID");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_holder,
                    new HomeFrag()).commit();
        }




    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment frag;

        if (item.getItemId()==R.id.bottomNavHomeId){
            navBar.setTitle("Home");
            frag = new HomeFrag();
            loadFrag(frag);
            return true;
        }else if (item.getItemId()==R.id.bottomNavCameraId){
            navBar.setTitle("Camera");
            frag = new CamFrag2();
            loadFrag(frag);
            return true;
        }else if (item.getItemId()==R.id.bottomNavHelpId){
            navBar.setTitle("Help");
            frag = new HelpFrag1();
            loadFrag(frag);
            return true;
        }else if(item.getItemId()==R.id.bottomNavSettingsId){
            navBar.setTitle("Settings");
            frag = new SettingsFrag1();
            loadFrag(frag);
            return true;
        }
            return false;

/*        switch (item.getItemId()) {
            case R.id.bottomNavHomeId:
                frag = new HomeFrag();
                loadFrag(frag);
                return true;
            case R.id.bottomNavCameraId:
                frag = new CamFrag();
                loadFrag(frag);
                return true;
            case R.id.bottomNavHelpId:
                frag = new HelpFrag();
                loadFrag(frag);
                return true;
        }
        return false;*/
    };

    private void loadFrag(Fragment frag) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.frag_holder, frag);
        trans.addToBackStack(null);
        trans.commit();
    }


}
