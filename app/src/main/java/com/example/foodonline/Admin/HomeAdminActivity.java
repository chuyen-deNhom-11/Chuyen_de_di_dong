package com.example.foodonline.Admin;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodonline.R;
import com.example.foodonline.base.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends BaseActivity {

    private BottomNavigationView bottomNav;
    private NavController navController;

    @Override
    protected void initViews() {
        bottomNav = findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    protected void initListenner() {
        hideBottomNavigationWith(
                R.id.scheduleFragment,
                R.id.staffDetailFragment,
                R.id.addFoodFragment
        );
    }

    public void hideBottomNavigationWith(int... ids) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            for (int id :
                    ids) {
                if (id == destination.getId()) {
                    bottomNav.setVisibility(View.GONE);
                    return;
                }
            }
            bottomNav.setVisibility(View.VISIBLE);
        });
    }


    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_admin;
    }
}