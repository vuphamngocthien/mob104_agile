package com.example.duan_1.Model;

public class GioHang {
    public int maSP;
    public String tenSP;
    public String hinhSP;
    public int giaSP;
    public int soluong;
    private int check;
    public GioHang() {
    }

    public GioHang(int maSP, String tenSP, String hinhSP, int giaSP, int soluong,int check) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.hinhSP = hinhSP;
        this.giaSP = giaSP;
        this.soluong = soluong;
        this.check = check;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
