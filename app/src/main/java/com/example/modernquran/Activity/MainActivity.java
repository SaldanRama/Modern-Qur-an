package com.example.modernquran.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.modernquran.Fragment.ProfileFragment;
import com.example.modernquran.R;
import com.example.modernquran.Fragment.SurahListFragment;

public class MainActivity extends AppCompatActivity {
    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check for saved user data and redirect to login if not found
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        // Set the layout for the activity
        setContentView(R.layout.activity_main);

        // Initialize MeowBottomNavigation
        meowBottomNavigation = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            loadFragment(new SurahListFragment());
        }

        // Add navigation items
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.user));

        // Set listener for navigation item click
        meowBottomNavigation.setOnClickMenuListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new SurahListFragment();
                    break;
                case 2:
                    fragment = new ProfileFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set listener for default selected item
        meowBottomNavigation.setOnShowListener(model -> {
            Fragment fragment = null;
            switch (model.getId()) {
                case 1:
                    fragment = new SurahListFragment();
                    break;
                case 2:
                    fragment = new ProfileFragment();
                    break;
            }
            loadFragment(fragment);
            return null;
        });

        // Set the first selected item
        meowBottomNavigation.show(1, true);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}
