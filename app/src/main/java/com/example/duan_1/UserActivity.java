package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.duan_1.AdapterView.NguoiMuaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class UserActivity extends AppCompatActivity {

    BottomNavigationView mNavigationView;
    ViewPager2 mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mViewPager = findViewById(R.id.viewpager2);
        mNavigationView = findViewById(R.id.bottomnavigation);

        NguoiMuaFragment viewpager =  new NguoiMuaFragment(this);
        mViewPager.setAdapter(viewpager);

        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.botton_loaisp){
                    mViewPager.setCurrentItem(0);
                }else if(id == R.id.bottom_home){
                    mViewPager.setCurrentItem(1);
                }else if(id == R.id.bottom_yeuthich) {
                    mViewPager.setCurrentItem(2);
                }else if(id == R.id.botton_cn) {
                    mViewPager.setCurrentItem(3);
                }
                return true;
            }
        });

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.botton_loaisp).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.bottom_yeuthich).setChecked(true);
                        break;
                    case 3:
                        mNavigationView.getMenu().findItem(R.id.botton_cn).setChecked(true);
                        break;
                }
            }
        });
    }
}