package com.example.foodonline.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodonline.Admin.fragment.BanFragment;
import com.example.foodonline.Admin.fragment.MonAnFragment;
import com.example.foodonline.Admin.fragment.NhanVienFragment;
import com.example.foodonline.Admin.fragment.TaiKhoanFragment;
import com.example.foodonline.R;
import com.example.foodonline.User.Fragment.BillFragment;
import com.example.foodonline.User.Fragment.HomUserFragment;
import com.example.foodonline.User.Fragment.InformationFragment;
import com.example.foodonline.User.Fragment.SetTableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeAdminActivity extends AppCompatActivity {
    BottomNavigationView navMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_admin);
        initialization();
        setMenuUser();

        swapContentFragment(new NhanVienFragment(), true, R.id.layout_user);
    }

    //TODO: set event menu user
    private void setMenuUser() {
        navMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home_user:
                        swapContentFragment(new NhanVienFragment(), true, R.id.layout_user);
                        return true;
                    case R.id.bill:
                        swapContentFragment(new BanFragment(), true, R.id.layout_user);
                        return true;
                    case R.id.booking:
                        swapContentFragment(new MonAnFragment(), true, R.id.layout_user);
                        return true;

                    case R.id.information:
                        swapContentFragment(new TaiKhoanFragment(), true, R.id.layout_user);
                        return true;
                }
                return false;
            }
        });
    }

    private void initialization() {

        navMenu = findViewById(R.id.bottom_hotelier_navagition_user
        );
    }


    //        TODO:  swap fragment
    private void swapContentFragment(final Fragment i_newFragment, final boolean i_addToStack, final int container) {
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, i_newFragment);
        if (!i_addToStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}