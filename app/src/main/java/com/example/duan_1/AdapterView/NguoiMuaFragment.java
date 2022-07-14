package com.example.duan_1.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan_1.ui.LoaiSanPhamFragment;
import com.example.duan_1.ui.MuaSanPhamFragment;
import com.example.duan_1.ui.ProfileFragment;
import com.example.duan_1.ui.ThemSanPhamFragment;
import com.example.duan_1.ui.ThongKyFragment;
import com.example.duan_1.ui.YeuThichFragment;

import org.jetbrains.annotations.NotNull;

public class NguoiMuaFragment extends FragmentStateAdapter {

    public NguoiMuaFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoaiSanPhamFragment();
            case 1:
                return new MuaSanPhamFragment();
            case 2:
                return new YeuThichFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
