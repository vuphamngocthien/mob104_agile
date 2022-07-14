package com.example.duan_1.ApdapterModel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.R;
import com.example.duan_1.ui.MuaSanPhamFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import static android.content.Intent.getIntent;

public class MuaSanPhamAdapter extends RecyclerView.Adapter<MuaSanPhamAdapter.MyViewHoldere>{
    Context context;
    ArrayList<SanPham> mListSanPham;
    MuaSanPhamAdapter.ClickListener clickListener;

    public MuaSanPhamAdapter(Context context, ArrayList<SanPham> mListSanPham, MuaSanPhamAdapter.ClickListener clickListener) {
        this.context = context;
        this.mListSanPham = mListSanPham;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MuaSanPhamAdapter.MyViewHoldere onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_muasanpham,parent,false);
        return new MuaSanPhamAdapter.MyViewHoldere(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuaSanPhamAdapter.MyViewHoldere holder, int position) {
        SanPham sanPham = mListSanPham.get(position);
//        holder.tv_masp.setText("Mã sản phẩm: "+sanPham.getMaSP());
//        holder.tv_matv.setText("Mã Thành viên: "+sanPham.getMaTV());
        holder.tv_tensp.setText(sanPham.getTenSP());
        holder.tv_giasp.setText("Giá: "+sanPham.getGia());
//        holder.tv_loai.setText("Loại: "+sanPham.getLoai());
        Picasso.with(context).load(sanPham.getImage()).into(holder.im_sanpham);
        holder.cardView.setOnClickListener(view -> {
            clickListener.onItemClick(sanPham);
        });
    }

    @Override
    public int getItemCount() {
        return mListSanPham.size();
    }

    public static class MyViewHoldere extends RecyclerView.ViewHolder{
        TextView tv_masp,tv_matv,tv_tensp,tv_giasp,tv_loai;
        public static ImageView im_sanpham,im_update,im_delete;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Login");
        CardView cardView;
        public MyViewHoldere(@NonNull View itemView) {
            super(itemView);

//            tv_masp = itemView.findViewById(R.id.tv_MaSP);
            tv_tensp = itemView.findViewById(R.id.tv_TenSP);
            tv_giasp = itemView.findViewById(R.id.tv_GiaSP);
            im_sanpham = itemView.findViewById(R.id.im_avtSP);
//            tv_matv = itemView.findViewById(R.id.tv_MaTV);
//            tv_loai = itemView.findViewById(R.id.tv_Loai);
            cardView = itemView.findViewById(R.id.layout_item_sanpham);
        }

        public static void hide() {
            im_update.setVisibility(View.INVISIBLE);
            im_delete.setVisibility(View.INVISIBLE);
        }
    }

    public interface ClickListener{
        void onUpdateItem(SanPham sanPham);
        void onDeleteItem(SanPham sanPham);
        void onItemClick(SanPham sanPham);
    }
}
