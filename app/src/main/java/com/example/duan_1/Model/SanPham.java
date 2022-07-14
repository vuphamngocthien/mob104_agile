package com.example.duan_1.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SanPham implements Serializable {
    private int MaSP;
    private String MaTV;
    private String TenSP;
    private int gia;
    private String Loai;
    private String Image;
    private String moTa;


    public SanPham() {
    }

    public SanPham(int maSP, String maTV, String tenSP, int gia, String loai, String image,String moTa) {
        MaSP = maSP;
        MaTV = maTV;
        TenSP = tenSP;
        this.gia = gia;
        Loai = loai;
        Image = image;
        this.moTa = moTa;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getMaTV() {
        return MaTV;
    }

    public void setMaTV(String maTV) {
        MaTV = maTV;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getLoai() {
        return Loai;
    }

    public void setLoai(String loai) {
        Loai = loai;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("tenSP",TenSP);
        result.put("gia",gia);
        result.put("image",Image);
        result.put("moTa",moTa);
        return result;
    }
}
