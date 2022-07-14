package com.example.duan_1.Model;

import java.io.Serializable;
import java.util.Date;

public class DonHang implements Serializable {
    public int maDH;
    public String maTV;
    public int maSP;
    public String tenSP;
    public String ngay;
    public int giatien;
    public int soLuong;
    public String hinhDH;
    public DonHang() {
    }

    public DonHang(int maDH, String maTV, int maSP, String tenSP, String ngay, int giatien, int soLuong,String hinhDH) {
        this.maDH = maDH;
        this.maTV = maTV;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.ngay = ngay;
        this.giatien = giatien;
        this.soLuong = soLuong;
        this.hinhDH = hinhDH;
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
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

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHinhDH() {
        return hinhDH;
    }

    public void setHinhDH(String hinhDH) {
        this.hinhDH = hinhDH;
    }
}
