package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;


public class ThuAcitivity extends AppCompatActivity implements OnChartValueSelectedListener {
    Spinner spinner;
    private PieChart chart_tongThu, chart_tongChi;
    CardView cv_thu, cv_chi;

    TextView tv_tongThuChi, tv_tongThu, tv_tongChi, tv_cardview_thu, tv_cardview_chi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);

        spinner = findViewById(R.id.spnThu);
        tv_tongThuChi = findViewById(R.id.tv_tongThuChi);
        tv_tongThu = findViewById(R.id.tv_tongThu);
        tv_tongChi = findViewById(R.id.tv_tongChi);
        cv_thu = findViewById(R.id.cardview_thu);
        cv_chi = findViewById(R.id.cardview_chi);
        tv_cardview_thu = findViewById(R.id.tv_cardview_thu);
        tv_cardview_chi = findViewById(R.id.tv_cardview_chi);

        chart_tongThu = (PieChart) findViewById(R.id.piechart_tongThu);
        chart_tongChi = findViewById(R.id.piechart_tongChi);

        // 1.000.000 500.000 265.000
        tv_cardview_thu.setText("Thu \n1.765.000đ");

        // 10%: 176.500
        tv_cardview_chi.setText("Chi \n176.500đ");

        generateTongThu();
        generateChiPhi();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Tất cả");
        arrayList.add("Tổng thu chi");
        arrayList.add("Tổng doanh thu");
        arrayList.add("Tổng chi phí");
        //arrayList.add("Người mua hàng");
        //arrayList.add("Quà tăng user theo mốc");
        //arrayList.add("Nhân viên");
        //arrayList.add("Lương thưởng năng lực");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String position = adapterView.getItemAtPosition(i).toString();
                switch (position) {
                    case "Tất cả":
                        tv_tongThuChi.setVisibility(View.VISIBLE);
                        tv_tongThu.setVisibility(View.VISIBLE);
                        tv_tongChi.setVisibility(View.VISIBLE);
                        cv_thu.setVisibility(View.VISIBLE);
                        cv_chi.setVisibility(View.VISIBLE);
                        chart_tongThu.setVisibility(View.VISIBLE);
                        chart_tongChi.setVisibility(View.VISIBLE);
                        break;
                    case "Tổng thu chi":
                        tv_tongThuChi.setVisibility(View.VISIBLE);
                        tv_tongThu.setVisibility(View.GONE);
                        tv_tongChi.setVisibility(View.GONE);
                        cv_thu.setVisibility(View.VISIBLE);
                        cv_chi.setVisibility(View.VISIBLE);
                        chart_tongThu.setVisibility(View.GONE);
                        chart_tongChi.setVisibility(View.GONE);
                        break;
                    case "Tổng doanh thu":
                        tv_tongThuChi.setVisibility(View.GONE);
                        tv_tongThu.setVisibility(View.VISIBLE);
                        tv_tongChi.setVisibility(View.GONE);
                        cv_thu.setVisibility(View.GONE);
                        cv_chi.setVisibility(View.GONE);
                        chart_tongThu.setVisibility(View.VISIBLE);
                        chart_tongChi.setVisibility(View.GONE);
                        break;
                    case "Tổng chi phí":
                        tv_tongThuChi.setVisibility(View.GONE);
                        tv_tongThu.setVisibility(View.GONE);
                        tv_tongChi.setVisibility(View.VISIBLE);
                        cv_thu.setVisibility(View.GONE);
                        cv_chi.setVisibility(View.GONE);
                        chart_tongThu.setVisibility(View.GONE);
                        chart_tongChi.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        // mChart.setVisibility(View.GONE);

    }




    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    /* Lọc thống kê:
     _ Theo tháng
     _ Theo loại sản phẩm
     _ Lọc nhân viên
     _ Lọc người mua (giới tính / tên / tuổi/ các mặt hàng chi nhiều nhất)
    * */



    // pie chart
    private void generateTongThu(){

        chart_tongThu.setRotationEnabled(false);
        chart_tongThu.setDescription(new Description());
        chart_tongThu.setHoleRadius(40f);
        chart_tongThu.setTransparentCircleAlpha(0);
        chart_tongThu.setCenterText("PieChart");
        chart_tongThu.setCenterTextSize(10);
        chart_tongThu.setDrawEntryLabels(true);

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 1000000, 500000, 265000 };
        String[] xData = { "Điện thoại", "Quần áo nam", "Mẹ và bé" };

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"Điện thoại, quần áo nam, mẹ và bé");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.LTGRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=chart_tongThu.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        // đặt ghi chú
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        PieData pieData=new PieData(pieDataSet);
        chart_tongThu.setData(pieData);
        chart_tongThu.invalidate();

        chart_tongThu.setOnChartValueSelectedListener(this);

    }

    // pie chart : Luong nv, luong thuong, VAT / tổng chi phí
    private void generateChiPhi(){

        chart_tongChi.setRotationEnabled(false);
        chart_tongChi.setDescription(new Description());
        chart_tongChi.setHoleRadius(40f);
        chart_tongChi.setTransparentCircleAlpha(0);
        chart_tongChi.setCenterText("Chi phí");
        chart_tongChi.setCenterTextSize(10);
        chart_tongChi.setDrawEntryLabels(true);

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 176500, 100000 };
        String[] xData = { "Điện thoại", "Quần áo nam" };

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"VAT, vận chuyển");
        pieDataSet.setSliceSpace(0);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.LTGRAY);
        colors.add(Color.BLUE);

        pieDataSet.setColors(colors);

        Legend legend=chart_tongThu.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        // đặt ghi chú
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        PieData pieData=new PieData(pieDataSet);
        chart_tongChi.setData(pieData);
        chart_tongChi.invalidate();

        chart_tongChi.setOnChartValueSelectedListener(this);

    }


    private void generateDoanhThuChiTiet(){}

    // carousel + theo thu tu
    private void generateNguoiChiNhieu(){}

    // doanh so cua nhan vien + theo thu tu
    private void generateNhanVien(){}

    // phan qua user dc tang dua tren tong chi thang
    private void  generateGiftThresholdMonthly(){}

    private void generateLuongThuongTheoNangSuat(){}
}