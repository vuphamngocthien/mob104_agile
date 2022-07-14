package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan_1.Model.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity {

    EditText edtUser, edtPass, edHoTen;
    AppCompatButton btnDangky;
    TextView tvDangNhap;
    AppCompatSpinner spinnerVT;
    User user;
    int maxid = 0;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edtUser = findViewById(R.id.editTextUserName);
        edHoTen = findViewById(R.id.editTextHoTen);
        edtPass = findViewById(R.id.editTextPass);
        btnDangky = findViewById(R.id.buttonDangky);
        tvDangNhap = findViewById(R.id.textviewDangNhap);

        //Chuyền dữ liệu vào spinner
        spinnerVT = findViewById(R.id.spinner);
        List<String> vaiTro = new ArrayList<>();
        vaiTro.add(0, "Chọn vai trò");
        vaiTro.add("Người mua");
        vaiTro.add("Người bán");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vaiTro);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerVT.setAdapter(dataAdapter);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bắt lỗi đăng ký
                if (edtUser.getText().toString().equals("") || edtPass.getText().toString().equals("") || edHoTen.getText().toString().equals("")) {
                    Toast.makeText(DangKyActivity.this, "Không để trống!", Toast.LENGTH_SHORT).show();
                }else if (edtUser.getText().toString().length() < 6 && edtUser.getText().toString().length() > 18) {
                    Toast.makeText(DangKyActivity.this, "Tên đăng nhập phải gồm 6 - 18 ký tự trợ lên!", Toast.LENGTH_SHORT).show();
                }else if (edtPass.getText().toString().length() < 8 && edtPass.getText().toString().length() > 20) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu phải gồm 8 - 20 ký tự trợ lên!", Toast.LENGTH_SHORT).show();
                }else if(spinnerVT.getSelectedItem().toString().equals("Chọn vai trò")){
                    Toast.makeText(DangKyActivity.this, "Bạn chưa chọn vai trò!", Toast.LENGTH_SHORT).show();
                }else{
                    onClickPushData();
                    edtUser.setText("");
                    edtPass.setText("");
                    edHoTen.setText("");
                }
            }
        });

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    private void onClickPushData() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Login");
        String username = edtUser.getText().toString().trim();
        String password = edtPass.getText().toString().trim();
        String hoTen = edHoTen.getText().toString().trim();
        String vaiTroas = spinnerVT.getSelectedItem().toString().trim();
        user = new User(username,password, hoTen,vaiTroas);
        String pathOject = String.valueOf(user.getUsername());
        myRef.child(pathOject).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                Toast.makeText(DangKyActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}