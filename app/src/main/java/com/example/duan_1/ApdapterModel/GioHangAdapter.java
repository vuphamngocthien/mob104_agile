package com.example.duan_1.ApdapterModel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_1.DonHangActivity;
import com.example.duan_1.GioHangActivity;
import com.example.duan_1.MainActivity;
import com.example.duan_1.Model.DonHang;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.R;
import com.example.duan_1.ui.ThemSanPhamFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import static java.time.LocalDate.now;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangCViewHolder>{
    Context context;
    ArrayList<GioHang> mListGioHang;
    GioHangActivity gioHangActivity;
    DecimalFormat format = new DecimalFormat("###,###,###");
    public GioHangAdapter(Context context, ArrayList<GioHang> mListGioHang,GioHangActivity gioHangActivity ) {
        this.context = context;
        this.mListGioHang = mListGioHang;
        this.gioHangActivity = gioHangActivity;
    }

    @NonNull
    @Override
    public GioHangCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_giohang,parent,false);
        return new GioHangCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangCViewHolder holder, int position) {
        GioHang gioHang = mListGioHang.get(position);
        holder.tv_tenSPGH.setText(gioHang.getTenSP());
        holder.tv_giaSPGH.setText(String.valueOf(format.format(gioHang.getGiaSP())));
        holder.tv_value.setText(String.valueOf(gioHang.getSoluong()));
        Picasso.with(context).load(gioHang.getHinhSP()).into(holder.im_spGH);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mListGioHang.get(position).setCheck(1);
                }else {
                    mListGioHang.get(position).setCheck(0);
                }
            }
        });
        //------
        int sl = Integer.parseInt(holder.tv_value.getText().toString());
        if (sl >= 10){
            holder.im_tang.setVisibility(View.INVISIBLE);
            holder.im_giam.setVisibility(View.VISIBLE);
        }else if(sl == 1){
            holder.im_tang.setVisibility(View.VISIBLE);
            holder.im_giam.setVisibility(View.INVISIBLE);
        }else {
            holder.im_tang.setVisibility(View.VISIBLE);
        }
        // sự kiện nút giảm số lượng trong giỏ hàng
        holder.im_giam.setOnClickListener(view -> {
            int sl_moinhat = Integer.parseInt(holder.tv_value.getText().toString()) - 1;
            int sl_hientai = mListGioHang.get(position).getSoluong();
            int gia_hientai = mListGioHang.get(position).getGiaSP();
           mListGioHang.get(position).setSoluong(sl_moinhat);
            int gia_moinhat = (gia_hientai * sl_moinhat) / sl_hientai;
            mListGioHang.get(position).setGiaSP(gia_moinhat);
            holder.tv_giaSPGH.setText(format.format(gia_moinhat)+" Đ");
            gioHangActivity.tinhTongTien();
            if (sl_moinhat  < 2){
                holder.im_giam.setVisibility(View.INVISIBLE);
                holder.im_tang.setVisibility(View.VISIBLE);
                holder.tv_value.setText(String.valueOf(sl_moinhat));
            }else {
                holder.im_giam.setVisibility(View.VISIBLE);
                holder.im_tang.setVisibility(View.VISIBLE);
                holder.tv_value.setText(String.valueOf(sl_moinhat));
            }
        });
        // sự kiện nút tăng số lượng trong giỏ hàng
        holder.im_tang.setOnClickListener(view -> {
            int sl_moinhat = Integer.parseInt(holder.tv_value.getText().toString()) + 1;
            int sl_hientai = mListGioHang.get(position).getSoluong();
            int gia_hientai = mListGioHang.get(position).getGiaSP();
            mListGioHang.get(position).setSoluong(sl_moinhat);
            int gia_moinhat = (gia_hientai * sl_moinhat) / sl_hientai;
            mListGioHang.get(position).setGiaSP(gia_moinhat);
            holder.tv_giaSPGH.setText(format.format(gia_moinhat)+" Đ");
            gioHangActivity.tinhTongTien();
            if (sl_moinhat  > 9){
                holder.im_giam.setVisibility(View.VISIBLE);
                holder.im_tang.setVisibility(View.INVISIBLE);
                holder.tv_value.setText(String.valueOf(sl_moinhat));
            }else {
                holder.im_giam.setVisibility(View.VISIBLE);
                holder.im_tang.setVisibility(View.VISIBLE);
                holder.tv_value.setText(String.valueOf(sl_moinhat));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListGioHang.size();
    }

    public GioHang getItem(int position) {
        if (mListGioHang == null || position>=mListGioHang.size()){
            return null;
        }else {
            return mListGioHang.get(position);
        }
    }
    public void setData(ArrayList<GioHang> listGh){
        mListGioHang.clear();
        mListGioHang.addAll(listGh);
        notifyDataSetChanged();
    }

    public static class GioHangCViewHolder extends RecyclerView.ViewHolder{
        ImageView im_spGH,im_tang,im_giam;
        TextView tv_tenSPGH,tv_giaSPGH,tv_value;
        CheckBox checkBox;
        public GioHangCViewHolder(@NonNull View itemView) {
            super(itemView);
            im_spGH = itemView.findViewById(R.id.im_spGiohang);
            tv_tenSPGH = itemView.findViewById(R.id.tv_tenSPGH);
            tv_giaSPGH = itemView.findViewById(R.id.tv_giaSPGH);
            im_giam = itemView.findViewById(R.id.im_giam);
            tv_value = itemView.findViewById(R.id.tv_value);
            im_tang = itemView.findViewById(R.id.im_tang);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
