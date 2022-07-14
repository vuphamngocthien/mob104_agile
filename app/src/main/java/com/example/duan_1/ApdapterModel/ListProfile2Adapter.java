package com.example.duan_1.ApdapterModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_1.Model.ListProfile1;
import com.example.duan_1.Model.ListProfile2;
import com.example.duan_1.R;

import java.util.ArrayList;

public class ListProfile2Adapter extends BaseAdapter {
    Context context;
    ArrayList<ListProfile2> mList;

    public ListProfile2Adapter(Context context, ArrayList<ListProfile2> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = view;
        if (view==null){
            view1 = View.inflate(viewGroup.getContext(), R.layout.item_profile,null);
        }
        ListProfile2 list = (ListProfile2) getItem(i);
        ImageView im_hinh = view1.findViewById(R.id.im_hinh);
        TextView tv_name = view1.findViewById(R.id.tv_name);
        im_hinh.setImageResource(list.getHinh1());
        tv_name.setText(list.getName());
        return view1;
    }
}
