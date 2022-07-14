package com.example.duan_1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan_1.ApdapterModel.ListProfile2Adapter;
import com.example.duan_1.ApdapterModel.ListProfileAdapter;
import com.example.duan_1.DangKyActivity;
import com.example.duan_1.LoginActivity;
import com.example.duan_1.Model.ListProfile1;
import com.example.duan_1.Model.ListProfile2;
import com.example.duan_1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    ListView lv_Pf1,lv_pf2;
    ListProfileAdapter adapter1;
    ListProfile2Adapter adapter2;
    ArrayList<ListProfile1> mListPF1;
    ArrayList<ListProfile2> mListPF2;
    ImageView im_tailai;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_Pf1 = view.findViewById(R.id.lv_profile1);
        lv_pf2 = view.findViewById(R.id.lv_profile2);
        im_tailai = view.findViewById(R.id.im_tailai);

        im_tailai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DangKyActivity.class);
                startActivity(i);
            }
        });
        //danh sách các mục trong tài khoản
        mListPF1 = new ArrayList<>();
        mListPF1.add(new ListProfile1(R.drawable.pin,"Địa chỉ"));
        mListPF1.add(new ListProfile1(R.drawable.creditcard,"Thẻ ngân hàng"));
        mListPF1.add(new ListProfile1(R.drawable.shoppingcart,"Giỏ hàng"));
        mListPF1.add(new ListProfile1(R.drawable.user,"Thông tin cá nhân"));
        adapter1 = new ListProfileAdapter(getContext(),mListPF1);
        lv_Pf1.setAdapter(adapter1);
        //danh sách các mục trong mục cài đặt
        mListPF2 = new ArrayList<>();
        mListPF2.add(new ListProfile2(R.drawable.globe,"Quốc gia"));
        mListPF2.add(new ListProfile2(R.drawable.checkbox,"Đơn hàng đã bán"));
        mListPF2.add(new ListProfile2(R.drawable.bell,"Thông báo"));
        adapter2 = new ListProfile2Adapter(getContext(),mListPF2);
        lv_pf2.setAdapter(adapter2);
    }

//    public void dangXuat(View view){
//        Intent i = new Intent(getContext(), LoginActivity.class);
//        startActivity(i);
//    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    public static Fragment newInstance() {
        return ProfileFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}