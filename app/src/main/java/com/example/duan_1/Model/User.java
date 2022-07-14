package com.example.duan_1.Model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String hoTen;
    private String as;

    public User(String username, String password, String hoTen, String as) {
        this.username = username;
        this.password = password;
        this.hoTen = hoTen;
        this.as = as;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public Map<String, Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("username",username);
        result.put("password",password);
        result.put("as",as);
        result.put("hoten",hoTen);
        return result;
    }
}
