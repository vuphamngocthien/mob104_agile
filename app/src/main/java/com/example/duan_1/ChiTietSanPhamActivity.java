package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan_1.ApdapterModel.GioHangAdapter;
import com.example.duan_1.ApdapterModel.YeuThichAdapter;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.Model.YeuThich;
import com.example.duan_1.ui.ThemSanPhamFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    ImageView im_getavt, im_cart,im_backsp,im_addGh,im_addYT;
    TextView tv_getMaSP, tv_getTenSP, tv_getGia, tv_getMota;
    int maSP = 0;
    int soLuong = 1;
    int gia = 0;
    String maTV = "";
    String tenSP = "";
    String moTa = "";
    String hinhanh = "";
    GioHang gioHang;
    YeuThich yeuThich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        initt();
        getInformation();

        im_addGh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themVaoGioHang();
            }
        });
        im_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
            }
        });

        //Thêm sản phẩm yêu thích
        im_addYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yeuThich = new YeuThich(tenSP, gia, soLuong, hinhanh,maSP);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("List_YeuThich");
                String path = String.valueOf(maSP);
                myRef.child(path).setValue(yeuThich);
                Toast.makeText(ChiTietSanPhamActivity.this, "Đã thêm vào sản phẩm yêu thích", Toast.LENGTH_SHORT).show();
                im_addYT.setImageResource(R.drawable.pinkheart);
            }
        });
    }
    //Thêm sản phẩm vào giỏ hàng
    private void themVaoGioHang() {
        boolean exists = false;
        if (GioHangActivity.mListGioHang.size() > 0) {
            for (int i = 0; i < GioHangActivity.mListGioHang.size(); i++) {
                if (GioHangActivity.mListGioHang.get(i).getMaSP() == maSP) {
                    GioHangActivity.mListGioHang.get(i).setSoluong(GioHangActivity.mListGioHang.get(i).getSoluong() + 1);
                    startActivity(new Intent(getApplicationContext(),GioHangActivity.class));
                    exists = true;
                }
            }
            if (exists == false) {
                gioHang = new GioHang(maSP,tenSP,hinhanh,gia,soLuong,0);
                Toast.makeText(this, "Đã thêm vào giở hàng", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("List_GioHang");
                String path = String.valueOf(maSP);
                myRef.child(path).setValue(gioHang);
            }
        }else {
            gioHang = new GioHang(maSP,tenSP,hinhanh,gia,soLuong,0);
            Toast.makeText(this, "Đã thêm vào giở hàng", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), GioHangActivity.class));
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("List_GioHang");
            String path = String.valueOf(maSP);
            myRef.child(path).setValue(gioHang);
        }
    }
    // Ánh xạ view
    private void initt() {
        im_getavt = findViewById(R.id.im_getavt);
        im_cart = findViewById(R.id.im_cart);
        tv_getMaSP = findViewById(R.id.tv_getMaSP);
        tv_getTenSP = findViewById(R.id.tv_getTenSP);
        tv_getGia = findViewById(R.id.tv_getGia);
        tv_getMota = findViewById(R.id.tv_getMota);
        im_addGh = findViewById(R.id.imthemvaogio);
        im_addYT = findViewById(R.id.im_addYT);
    }
    // lấy thông tin sản phẩm
    private void getInformation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        maSP = sanPham.getMaSP();
        maTV = sanPham.getMaTV();
        tenSP = sanPham.getTenSP();
        gia = sanPham.getGia();
        hinhanh = sanPham.getImage();
        moTa = sanPham.getMoTa();
        tv_getMaSP.setText("Mã: " + maSP);
        tv_getTenSP.setText(tenSP);
        DecimalFormat format = new DecimalFormat("###,###,###");
        tv_getGia.setText("Giá: " + format.format(gia) + "VND");
        tv_getMota.setText(moTa);
        Picasso.with(getApplicationContext()).load(hinhanh).placeholder(R.drawable.common_full_open_on_phone).into(im_getavt);
    }
}

