package com.example.duan_1.ApdapterModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_1.Model.DonHang;
import com.example.duan_1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder>{
    Context context;
    ArrayList<DonHang> mListDH;
    ClickListener clickListener;
    public DonHangAdapter(Context context, ArrayList<DonHang> mListDH, ClickListener clickListener) {
        this.context = context;
        this.mListDH = mListDH;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_donhang,parent,false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        DonHang donHang = mListDH.get(position);
        holder.tv_tesp.setText(donHang.getTenSP());
        holder.tv_giatien.setText(String.valueOf(donHang.getGiatien()));
        holder.tv_soluong.setText(donHang.getSoLuong()+" Sản phẩm");
        Picasso.with(context).load(donHang.getHinhDH()).into(holder.im_sanpham);
        holder.relativeLayout.setOnClickListener(view -> {
            clickListener.onItemClick(donHang);
        });
    }

    @Override
    public int getItemCount() {
        return mListDH.size();
    }

    public static class DonHangViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tesp,tv_giatien,tv_soluong;
        ImageView im_sanpham;
        RelativeLayout relativeLayout;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tesp = itemView.findViewById(R.id.tv_tenSPDH);
            tv_giatien = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            im_sanpham = itemView.findViewById(R.id.im_avtDH);
            relativeLayout = itemView.findViewById(R.id.layout_item_donhang);
        }
    }
    public interface ClickListener{
        void onItemClick(DonHang donHang);
    }
}
