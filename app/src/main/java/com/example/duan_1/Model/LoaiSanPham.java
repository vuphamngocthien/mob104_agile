package com.example.duan_1.Model;

public class LoaiSanPham {

    String maLoai, tenLoai, image;

    public LoaiSanPham() {
    }

    public LoaiSanPham(String maLoai, String tenLoai, String image) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
