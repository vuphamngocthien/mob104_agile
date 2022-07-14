package com.example.duan_1.ApdapterModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_1.Model.LoaiSanPham;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamLoaiAdapter extends RecyclerView.Adapter<SanPhamLoaiAdapter.MyViewHoldere> {

    Context context;
    ArrayList<LoaiSanPham> mListLoaiSP;

    public SanPhamLoaiAdapter(Context context, ArrayList<LoaiSanPham> mListLoaiSP) {
        this.context = context;
        this.mListLoaiSP = mListLoaiSP;
    }

    @NonNull
    @Override
    public SanPhamLoaiAdapter.MyViewHoldere onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_loaisanpham,parent,false);
        return new SanPhamLoaiAdapter.MyViewHoldere(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamLoaiAdapter.MyViewHoldere holder, int position) {
        LoaiSanPham sanPham = mListLoaiSP.get(position);
        holder.tv_loai.setText(sanPham.getTenLoai());
//        holder.tv_loai.setText("Loại: "+sanPham.getLoai());
        Picasso.with(context).load(sanPham.getImage()).into(holder.img_loai);
    }

    @Override
    public int getItemCount() {
        return mListLoaiSP.size();
    }

    public static class MyViewHoldere extends RecyclerView.ViewHolder {
        TextView tv_loai;
        ImageView img_loai;
        CardView cardView;

        public MyViewHoldere(@NonNull View itemView) {
            super(itemView);
            ImageView img_loai = itemView.findViewById(R.id.img_loaisanpham);
            TextView tv_loai = itemView.findViewById(R.id.tv_lsp);
            cardView = itemView.findViewById(R.id.layout_item_sanpham);
        }
    }
}
