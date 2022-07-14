package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.duan_1.ApdapterModel.GioHangAdapter;
import com.example.duan_1.Model.DonHang;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.ui.MuaSanPhamFragment;
import com.example.duan_1.ui.ThemSanPhamFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static java.time.ZonedDateTime.now;

public class GioHangActivity extends AppCompatActivity {
    ImageView im_backGH,im_muaHang,im_ttmua,im_donhang;
    RecyclerView rcl_giohang;
    TextView tv_thongbao;
    public static TextView tv_tongtien;
    public static ArrayList<GioHang> mListGioHang = new ArrayList<>();
    GioHangAdapter adapter;
    DonHang donHang;
    private static DatabaseReference mDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        initt();
        checkData();
        tinhTongTien();
        deleteItemGioHang();
        mListGioHang = new ArrayList<>();
        adapter = new GioHangAdapter(getApplicationContext(), mListGioHang,this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rcl_giohang.setLayoutManager(manager);
        rcl_giohang.setAdapter(adapter);
        im_ttmua.setOnClickListener(view -> {
            startActivity(new Intent(GioHangActivity.this, UserActivity.class));
        });
        //Thanh toán sản phẩm đc chọn trong giỏ hàng
        im_muaHang.setOnClickListener(view -> {
            for (int i = 0 ; i <mListGioHang.size() ;i++){
                if (mListGioHang.get(i).getCheck() == 1){
                    Intent intent = getIntent();
                    int madh = (int) System.currentTimeMillis();
                    int masp = mListGioHang.get(i).getMaSP();
                    String maTV = intent.getStringExtra("username");
                    String tesp =  mListGioHang.get(i).getTenSP();
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String formattedDate = myDateObj.format(myFormatObj);
                    String ngay = formattedDate;
                    int giatien = mListGioHang.get(i).getGiaSP();
                    int sl = mListGioHang.get(i).getSoluong();
                    String hinh = mListGioHang.get(i).getHinhSP();
                    donHang = new DonHang(madh,maTV,masp,tesp,ngay,giatien,sl,hinh);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("List_DonHang");
                    String path = String.valueOf(masp);
                    myRef.child(path).setValue(donHang);
                    Toast.makeText(getApplicationContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    clearData();

            }else {
                Toast.makeText(getApplicationContext(), "Chưa có dữ liệu", Toast.LENGTH_SHORT).show();
            }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("List_GioHang");
                myRef.child(String.valueOf(mListGioHang.get(i).getCheck() == 1)).setValue(null);
//                FirebaseDatabase.getInstance().getReference("List_GioHang").addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            for(DataSnapshot data : dataSnapshot.getChildren()) {
//                                data.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });
                }
//            mListGioHang = new ArrayList<>();
            startActivity(new Intent(getApplicationContext(),DonHangActivity.class));

        });
        im_donhang.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),DonHangActivity.class));
        });
        getListGioHang();
    }

    public static void clearData(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("List_GioHang").setValue(null);
    }

    private void getListGioHang(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_GioHang");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            //phương thức onChildAdded(cập nhật lại list sau khi thêm sản phẩm mới )
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang!=null){
                    mListGioHang.add(gioHang);
                }
            }
            @Override
            //phương thức onChildChanged(cập nhật lại list sau khi sửa sản phẩm đã tồn tại)
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            //phương thức onChildRemoved(cập nhật lại list sau khi xóa sản phẩm đã tồn tại)
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                GioHang gioHang = snapshot.getValue(GioHang.class);
                if (gioHang==null || mListGioHang==null || mListGioHang.isEmpty() ){
                    return;
                }
                for (int i = 0 ; i<mListGioHang.size() ; i++){
                    if (gioHang.getMaSP()==mListGioHang.get(i).getMaSP()){
                        mListGioHang.remove(mListGioHang.get(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Xóa item trong giỏ hàng
    private void deleteItemGioHang() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                GioHang gioHang = adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xóa");
                builder.setMessage("Xóa: "+gioHang.getTenSP());
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("List_GioHang");
                            myRef.child(String.valueOf(gioHang.getMaSP())).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(getApplicationContext(), "Đã xóa "+gioHang.getTenSP(), Toast.LENGTH_SHORT).show();
                                    adapter.setData(mListGioHang);
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Sản phẩm chưa được xóa", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(rcl_giohang);
    }
    //Tính tổng tiền của tất cả sản phẩm có trong giỏ hàng
    public static void tinhTongTien() {
        int tongTien = 0;
        for (int i = 0 ; i<mListGioHang.size() ; i++){
            tongTien+=mListGioHang.get(i).getGiaSP();
        }
        DecimalFormat format = new DecimalFormat("###,###,###");
        tv_tongtien.setText(format.format(tongTien)+"VND");
    }
    //kiểm tra giỏ hàng đã có sản phẩm nào chưa
    private void checkData() {
        if (mListGioHang.size() <= 0){
            tv_thongbao.setVisibility(View.VISIBLE);
            rcl_giohang.setVisibility(View.INVISIBLE);
        }else {
            tv_thongbao.setVisibility(View.INVISIBLE);
            rcl_giohang.setVisibility(View.VISIBLE);
        }
    }
    //ánh xạ view
    private void initt() {
        rcl_giohang = findViewById(R.id.rcl_giohang);
        tv_thongbao =findViewById(R.id.tv_thongbao);
        tv_tongtien = findViewById(R.id.tv_tongtien);
        im_donhang = findViewById(R.id.im_donhang);
        im_muaHang =findViewById(R.id.im_muaHang);
        im_ttmua =findViewById(R.id.im_ttmua);
    }
}