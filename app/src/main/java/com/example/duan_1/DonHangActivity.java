package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.duan_1.ApdapterModel.DonHangAdapter;
import com.example.duan_1.ApdapterModel.GioHangAdapter;
import com.example.duan_1.Model.DonHang;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DonHangActivity extends AppCompatActivity implements DonHangAdapter.ClickListener{
    RecyclerView recyclerView;
    DonHangAdapter adapter;
    ArrayList<DonHang> mListDH = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        recyclerView = findViewById(R.id.rcl_donhang);
        if (mListDH != null){
            adapter = new DonHangAdapter(this,mListDH,this::onItemClick);
            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
        }else {
            mListDH = new ArrayList<>();
        }
        getListDonHang();
    }

    public void back(View v){
        Intent i = new Intent(DonHangActivity.this, GioHangActivity.class);
        startActivity(i);
    }

    private void getListDonHang(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_DonHang");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            //phương thức onChildAdded(cập nhật lại list sau khi thêm sản phẩm mới )
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DonHang donHang = snapshot.getValue(DonHang.class);
                if (donHang!=null){
                    mListDH.add(donHang);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override

            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override

            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onItemClick(DonHang donHang) {
        Intent intent = new Intent(getApplicationContext(),ChiTietDonHangActivity.class);
        intent.putExtra("thongtindonhang",donHang);
        startActivity(intent);
    }
}