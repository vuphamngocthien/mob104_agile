package com.example.duan_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class ThuAcitivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_acitivity);

        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setRotationEnabled(true);
        mChart.setDescription(new Description());
        mChart.setHoleRadius(35f);
        mChart.setTransparentCircleAlpha(0);
        mChart.setCenterText("PieChart");
        mChart.setCenterTextSize(10);
        mChart.setDrawEntryLabels(true);

        addDataSet(mChart);

        mChart.setOnChartValueSelectedListener(this);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onNothingSelected() {

    }

    private static void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 25, 40, 70 };
        String[] xData = { "January", "February", "January" };

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Employee Sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        // đặt ghi chú
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    /* Lọc thống kê:
     _ Theo tháng
     _ Theo loại sản phẩm
     _ Lọc nhân viên
     _ Lọc người mua (giới tính / tên / tuổi/ các mặt hàng chi nhiều nhất)
    * */

    // line chart
    private void generateDoanhThuTheoThang(){}

    // pie chart
    private void generateTongDoanhThu(){}

    // pie chart : Luong nv, luong thuong, VAT
    private void generateTongChi(){}

    // barchart
    private void generateDoanhThuChiTiet(){}

    // carousel + theo thu tu
    private void generateNguoiChiNhieu(){}

    // doanh so cua nhan vien + theo thu tu
    private void generateNhanVien(){}

    // phan qua user dc tang dua tren tong chi thang
    private void  generateGiftThresholdMonthly(){}

    private void generateLuongThuongTheoNangSuat(){}
}