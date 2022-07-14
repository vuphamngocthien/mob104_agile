package com.example.duan_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan_1.Model.DonHang;
import com.example.duan_1.Model.YeuThich;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChiTietDonHangActivity extends AppCompatActivity {
    ImageView im_avtDH,im_backCTDH;
    TextView tv_tenSPDH,tv_giatien,tv_soluong,tv_tien,tv_tb,tv_madon,tv_tgdh,tv_mtv,tv_msp,tv_addYT;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat format = new DecimalFormat("###,###,###");
    public static ArrayList<YeuThich> mListYT = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);
        initt();
        DonHang donHang = (DonHang) getIntent().getSerializableExtra("thongtindonhang");
        int madonhang = donHang.getMaDH();
        String maThanhVien = donHang.getMaTV();
        int maSanPham = donHang.getMaSP();
        String tenSanPham = donHang.getTenSP();
        String ngay = donHang.getNgay();
        int giaTien = donHang.getGiatien();
        int soLuong = donHang.getSoLuong();
        String hinh = donHang.getHinhDH();

        tv_tenSPDH.setText(tenSanPham);
        tv_giatien.setText(String.valueOf(format.format(giaTien))+ " VND");
        tv_soluong.setText(soLuong+" sản phẩm");
        tv_tien.setText(String.valueOf(format.format(giaTien))+"VND");
        tv_tb.setText(format.format(giaTien) + "VND");
        tv_madon.setText(String.valueOf(madonhang));
        tv_mtv.setText(String.valueOf(maThanhVien));
        tv_msp.setText(String.valueOf(maSanPham));
        tv_tgdh.setText(ngay);
        Picasso.with(getApplicationContext()).load(hinh).into(im_avtDH);

        tv_addYT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListYT.add(new YeuThich(tenSanPham,giaTien,soLuong,hinh,maSanPham));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("List_YeuThich");
                myRef.setValue(mListYT);
                Toast.makeText(ChiTietDonHangActivity.this, "Đã thêm vào sản phẩm yêu thích", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


    }

    private void initt() {
        im_avtDH = findViewById(R.id.im_avtDHCT);
        tv_tenSPDH = findViewById(R.id.tv_tenSPDHCT);
        tv_giatien = findViewById(R.id.tv_giatien);
        tv_soluong = findViewById(R.id.tv_slDHCT);
        tv_tien = findViewById(R.id.tv_tien);
        tv_tb = findViewById(R.id.tv_tb);
        tv_madon = findViewById(R.id.tv_madon);
        tv_mtv = findViewById(R.id.tv_mtv);
        tv_msp = findViewById(R.id.tv_msp);
        tv_tgdh = findViewById(R.id.tv_tgdh);
        tv_addYT = findViewById(R.id.tv_addYT);
    }
}