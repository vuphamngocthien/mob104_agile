
package com.example.duan_1.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.duan_1.ApdapterModel.LoaiSanPhamAdapter;
import com.example.duan_1.ApdapterModel.SanPhamLoaiAdapter;
import com.example.duan_1.Model.LoaiSanPham;
import com.example.duan_1.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoaiSanPhamFragment extends Fragment {
    GridView lv;
    LoaiSanPhamAdapter adapter;
    public static  ArrayList<LoaiSanPham> mListLoaiSP = new ArrayList<>();
    public LoaiSanPhamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        lv = view.findViewById(R.id.lv_loaiSP);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_LoaiSP");
        if (mListLoaiSP.size() != 0) {
            mListLoaiSP = new ArrayList<>();
            mListLoaiSP.add(new LoaiSanPham("2", "Quần áo nam", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fquanaonam.png?alt=media&token=de900077-750c-48cc-ae1c-8678fc2d4ced"));
            mListLoaiSP.add(new LoaiSanPham("1", "Quần áo nữ", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fquanaonu.png?alt=media&token=d66ae9f5-e1f3-4866-add2-67ca04838653"));
            mListLoaiSP.add(new LoaiSanPham("3", "Điện thoại", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdienthoai.png?alt=media&token=baaf6bac-c340-4001-ac4b-e9d914e0a763"));
            mListLoaiSP.add(new LoaiSanPham("4", "Đồng hồ", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdongho.png?alt=media&token=f58dfedf-3b99-4aba-a0d6-de156b919dc5"));
            mListLoaiSP.add(new LoaiSanPham("5", "Dụng cụ học tập", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdungcu.png?alt=media&token=88aa927a-4313-4bf7-9704-a7ca52d1ab3f"));
            mListLoaiSP.add(new LoaiSanPham("6", "Laptop", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Flaptop.png?alt=media&token=3b5e5c7e-e96b-4c96-b245-e3542be9377a"));
            mListLoaiSP.add(new LoaiSanPham("7", "Mẹ và bé", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fmevabe.png?alt=media&token=5fa69c3c-f257-43a2-ad70-4f5d1314b31a"));
            mListLoaiSP.add(new LoaiSanPham("8", "Ô tô", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Foto.png?alt=media&token=8cd46e19-7874-43b6-bfb4-32deb29bbc48"));
            mListLoaiSP.add(new LoaiSanPham("9", "Phòng bếp", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fphongbep.png?alt=media&token=7412d41c-d94d-4210-a1c6-836743a37385"));
            mListLoaiSP.add(new LoaiSanPham("10", "Phụ kiện", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fphukien.png?alt=media&token=aedc80b6-881f-49f2-8b88-17d1b5908d0c"));
            mListLoaiSP.add(new LoaiSanPham("11", "Sách", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fsach.png?alt=media&token=6bf0ae35-ef03-4060-9ee2-0859ca24f922"));
            mListLoaiSP.add(new LoaiSanPham("12", "Thiết bị điện tử", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fthietbidientu.png?alt=media&token=47d3a8be-1d5e-40d9-96a4-8feea9662fc6"));
            mListLoaiSP.add(new LoaiSanPham("13", "Túi và ví", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Ftuivavi.png?alt=media&token=6181337a-c0c5-448a-bd46-7fbf70601225"));
            adapter = new LoaiSanPhamAdapter(getContext(), mListLoaiSP);
            lv.setAdapter(adapter);
        }else {
            mListLoaiSP.add(new LoaiSanPham("2", "Quần áo nam", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fquanaonam.png?alt=media&token=de900077-750c-48cc-ae1c-8678fc2d4ced"));
            mListLoaiSP.add(new LoaiSanPham("1", "Quần áo nữ", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fquanaonu.png?alt=media&token=d66ae9f5-e1f3-4866-add2-67ca04838653"));
            mListLoaiSP.add(new LoaiSanPham("3", "Điện thoại", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdienthoai.png?alt=media&token=baaf6bac-c340-4001-ac4b-e9d914e0a763"));
            mListLoaiSP.add(new LoaiSanPham("4", "Đồng hồ", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdongho.png?alt=media&token=f58dfedf-3b99-4aba-a0d6-de156b919dc5"));
            mListLoaiSP.add(new LoaiSanPham("5", "Dụng cụ học tập", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fdungcu.png?alt=media&token=88aa927a-4313-4bf7-9704-a7ca52d1ab3f"));
            mListLoaiSP.add(new LoaiSanPham("6", "Laptop", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Flaptop.png?alt=media&token=3b5e5c7e-e96b-4c96-b245-e3542be9377a"));
            mListLoaiSP.add(new LoaiSanPham("7", "Mẹ và bé", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fmevabe.png?alt=media&token=5fa69c3c-f257-43a2-ad70-4f5d1314b31a"));
            mListLoaiSP.add(new LoaiSanPham("8", "Ô tô", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Foto.png?alt=media&token=8cd46e19-7874-43b6-bfb4-32deb29bbc48"));
            mListLoaiSP.add(new LoaiSanPham("9", "Phòng bếp", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fphongbep.png?alt=media&token=7412d41c-d94d-4210-a1c6-836743a37385"));
            mListLoaiSP.add(new LoaiSanPham("10", "Phụ kiện", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fphukien.png?alt=media&token=aedc80b6-881f-49f2-8b88-17d1b5908d0c"));
            mListLoaiSP.add(new LoaiSanPham("11", "Sách", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fsach.png?alt=media&token=6bf0ae35-ef03-4060-9ee2-0859ca24f922"));
            mListLoaiSP.add(new LoaiSanPham("12", "Thiết bị điện tử", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Fthietbidientu.png?alt=media&token=47d3a8be-1d5e-40d9-96a4-8feea9662fc6"));
            mListLoaiSP.add(new LoaiSanPham("13", "Túi và ví", "https://firebasestorage.googleapis.com/v0/b/duan1-3cc8b.appspot.com/o/IM_LOAISANPHAM%2Ftuivavi.png?alt=media&token=6181337a-c0c5-448a-bd46-7fbf70601225"));
            adapter = new LoaiSanPhamAdapter(getContext(), mListLoaiSP);
            lv.setAdapter(adapter);
        }
        myRef.setValue(mListLoaiSP);

    }
//    private void getListLoaiSP(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("List_LoaiSP");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    LoaiSanPham loaiSanPham = dataSnapshot.getValue(LoaiSanPham.class);
//                    LoaiSanPhamFragment.mListLoaiSP.add(loaiSanPham);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
    public static LoaiSanPhamFragment newInstance(String param1, String param2) {
        LoaiSanPhamFragment fragment = new LoaiSanPhamFragment();
        return fragment;
    }

    public static Fragment newInstance() {
        return LoaiSanPhamFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loai_san_pham, container, false);
    }
}

