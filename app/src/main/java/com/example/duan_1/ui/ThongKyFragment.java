package com.example.duan_1.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan_1.Model.DonHang;
import com.example.duan_1.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThongKyFragment extends Fragment {

    BarChart barChart;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("List_DonHang");
    public ThongKyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.barChart);
        ArrayList<BarEntry> visitor = new ArrayList<>();
//        visitor.add(new BarEntry(2014, 420));
//        visitor.add(new BarEntry(2015, 475));
//        visitor.add(new BarEntry(2016, 500));
//        visitor.add(new BarEntry(2017, 550));
//        visitor.add(new BarEntry(2018, 600));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BarEntry> visitor = new ArrayList<>();

                if(snapshot.hasChildren()){
                    for(DataSnapshot child : snapshot.getChildren()){
                        DonHang dataPoint = child.getValue(DonHang.class);
                        String ngay = dataPoint.getNgay().substring(3,5);
                        Integer ngayInt = Integer.parseInt(ngay);
                        Log.e(">>>", ngayInt+"");

                        visitor.add(new BarEntry(ngayInt, dataPoint.getGiatien()));
                    }
                    BarDataSet barDataSet = new BarDataSet(visitor, "Giá tiền");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);

                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.getDescription().setText("Thống kê doanh thu");
                    barChart.animateY(2000);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        BarDataSet barDataSet = new BarDataSet(visitor, "Visitor");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar Chart Example");
        barChart.animateY(2000);
    }

    public static ThongKyFragment newInstance(String param1, String param2) {
        ThongKyFragment fragment = new ThongKyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ky, container, false);
    }

//    public void showChart(ArrayList<dataVal> dataVal){
//    }

}