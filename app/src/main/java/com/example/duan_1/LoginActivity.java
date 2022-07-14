package com.example.duan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan_1.ApdapterModel.SanPhamAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText username,
            password;
    private AppCompatButton login;
    TextView tvDangKy;
    Switch active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        tvDangKy = findViewById(R.id.textviewDangKy);
        active = findViewById(R.id.active);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String input1 = username.getText().toString();
                        String input2 = password.getText().toString();

                        if((input1.equals("") || input2.equals(""))){
                            Toast.makeText(LoginActivity.this, "Không để trống!", Toast.LENGTH_SHORT).show();
                        }else if (dataSnapshot.child(input1).exists()) {
                            if (dataSnapshot.child(input1).child("password").getValue(String.class).equals(input2)) {
                                if (active.isChecked()) {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người bán")) {
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "Người bán");
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);
                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người mua")){
                                        SanPhamAdapter.MyViewHoldere.hide();
                                        preferences.setDataLogin(LoginActivity.this, true);
                                        preferences.setDataAs(LoginActivity.this, "Người mua");
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        intent.putExtra("as", input2);
                                        startActivity(intent);
                                    }
                                } else {
                                    if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người bán")) {
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);

                                    } else if (dataSnapshot.child(input1).child("as").getValue(String.class).equals("Người mua")){
                                        preferences.setDataLogin(LoginActivity.this, false);
                                        Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                        intent.putExtra("username",input1);
                                        startActivity(intent);
                                    }
                                }
                            } else {
                                username.setText("");
                                password.setText("");
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void anImage(){
        SanPhamAdapter.MyViewHoldere.im_delete.setVisibility(View.INVISIBLE);
        SanPhamAdapter.MyViewHoldere.im_update.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("Người bán")) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                SanPhamAdapter.MyViewHoldere.hide();
                startActivity(new Intent(this, UserActivity.class));
                finish();
            }
        }
    }
}