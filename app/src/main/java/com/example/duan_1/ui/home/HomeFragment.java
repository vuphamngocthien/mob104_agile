package com.example.duan_1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan_1.AdapterView.ViewPagerAdapter;
import com.example.duan_1.R;
import com.example.duan_1.databinding.FragmentHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    BottomNavigationView mNavigationView;
    ViewPager mViewPager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewPager = root.findViewById(R.id.viewpager);
        mNavigationView = root.findViewById(R.id.nav_bottom);
        setUpviewpager();
        return root;
    }

    private void setUpviewpager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_LoaiSP:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_ThemSP:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_basket:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.tab_Profile:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.tab_LoaiSP).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.tab_ThemSP).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.tab_basket).setChecked(true);
                        break;
                    case 3:
                        mNavigationView.getMenu().findItem(R.id.tab_Profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}