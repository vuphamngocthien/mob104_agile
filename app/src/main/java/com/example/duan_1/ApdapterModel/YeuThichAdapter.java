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
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.Model.YeuThich;
import com.example.duan_1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.YeuThichViewHolder>{
    Context context;
    ArrayList<YeuThich> mListYT;
    ClickListener clickListener;
    DecimalFormat format = new DecimalFormat("###,###,###");
    public YeuThichAdapter(Context context, ArrayList<YeuThich> mListYT,ClickListener clickListener) {
        this.context = context;
        this.mListYT = mListYT;
        this.clickListener =clickListener;
    }

    @NonNull
    @Override
    public YeuThichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_yeuthich,parent,false);
        return new YeuThichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YeuThichViewHolder holder, int position) {
        YeuThich yeuThich = mListYT.get(position);
        holder.tv_tespYT.setText(yeuThich.getTenYT());
        holder.tv_giatienYT.setText(String.valueOf(format.format(yeuThich.getGiaYT())));
        holder.tv_soluongYT.setText(yeuThich.getSoluonYT()+" Sản phẩm");
        Picasso.with(context).load(yeuThich.getHinh()).into(holder.im_avtYT);
        holder.im_dlYt.setOnClickListener(view -> {
            clickListener.onDeleteItemYeuThich(yeuThich);
        });
    }

    @Override
    public int getItemCount() {
        return mListYT.size();
    }

    public void setDataYT(ArrayList<YeuThich> listYT){
        mListYT.clear();
        mListYT.addAll(listYT);
        notifyDataSetChanged();
    }
    public static class YeuThichViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tespYT,tv_giatienYT,tv_soluongYT;
        ImageView im_avtYT,im_dlYt;
        public YeuThichViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tespYT = itemView.findViewById(R.id.tv_tenYT);
            tv_giatienYT = itemView.findViewById(R.id.tv_giatYT);
            tv_soluongYT = itemView.findViewById(R.id.tv_soluongYT);
            im_avtYT = itemView.findViewById(R.id.im_avtYT);
            im_dlYt = itemView.findViewById(R.id.im_dlYT);
        }
    }
    public interface ClickListener{
        void onDeleteItemYeuThich(YeuThich yeuThich);
    }
}
