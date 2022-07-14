//package com.example.duan_1.ui;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.duan_1.ApdapterModel.LoaiSanPhamAdapter;
//import com.example.duan_1.ApdapterModel.SanPhamLoaiAdapter;
//import com.example.duan_1.Model.LoaiSanPham;
//import com.example.duan_1.R;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//
//public class SanPhamLoaiFragment extends Fragment {
//
//    RecyclerView recview;
//    SanPhamLoaiAdapter adapter;
//    public static ArrayList<LoaiSanPham> mListLoaiSP = new ArrayList<>();
//    public SanPhamLoaiFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//        recview=(RecyclerView)view.findViewById(R.id.test);
//        recview.setLayoutManager(new LinearLayoutManager(getContext()));
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("List_LoaiSP");
//
////        FirebaseRecyclerOptions<LoaiSanPham> options =
////                new FirebaseRecyclerOptions.Builder<LoaiSanPham>()
////                        .setQuery(FirebaseDatabase.getInstance().getReference().child("List_LoaiSP"), LoaiSanPham.class)
////                        .build();
////
////        adapter=new SanPhamLoaiAdapter(options);
////        recview.setAdapter(adapter);
//        if (mListLoaiSP.size() != 0) {
//            mListLoaiSP = new ArrayList<>();
//            adapter = new SanPhamLoaiAdapter(requireContext(), mListLoaiSP);
//            recview.setAdapter(adapter);
//        }else {
//            adapter = new SanPhamLoaiAdapter(getContext(), mListLoaiSP);
//            recview.setAdapter(adapter);
//        }
//        myRef.setValue(mListLoaiSP);
//        getListLoaiSP();
//    }
//
//    private void getListLoaiSP() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("List_LoaiSP");
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    LoaiSanPham loaiSanPham = dataSnapshot.getValue(LoaiSanPham.class);
//                    LoaiSanPhamFragment.mListLoaiSP.add(loaiSanPham);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
//    public static SanPhamLoaiFragment newInstance(String param1, String param2) {
//        SanPhamLoaiFragment fragment = new SanPhamLoaiFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_san_pham_loai, container, false);
//    }
//}