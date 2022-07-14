package com.example.duan_1.ApdapterModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_1.Model.LoaiSanPham;
import com.example.duan_1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiSanPham> mListLoaiSP;

    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> mListLoaiSP) {
        this.context = context;
        this.mListLoaiSP = mListLoaiSP;
    }

    @Override
    public int getCount() {
        return mListLoaiSP.size();
    }

    @Override
    public Object getItem(int i) {
        return mListLoaiSP.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if (view==null){
            view1 = View.inflate(viewGroup.getContext(), R.layout.layout_item_loaisanpham,null);
        }
        LoaiSanPham loaiSanPham = (LoaiSanPham) getItem(i);
        TextView tv_loai = view1.findViewById(R.id.tv_lsp);
        ImageView tv_loai_image = view1.findViewById(R.id.img_loaisanpham);
        tv_loai.setText(loaiSanPham.getTenLoai());
        Picasso.with(context).load(loaiSanPham.getImage()).into(tv_loai_image);
        return view1;
    }
}
