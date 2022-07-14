package com.example.duan_1.Model;

public class YeuThich {
    private String tenYT;
    private int giaYT;
    private int soluonYT;
    private String hinh;
    private int maYT;

    public YeuThich() {
    }

    public YeuThich(String tenYT, int giaYT, int soluonYT, String hinh,int maYT) {
        this.tenYT = tenYT;
        this.giaYT = giaYT;
        this.soluonYT = soluonYT;
        this.hinh = hinh;
        this.maYT = maYT;
    }

    public String getTenYT() {
        return tenYT;
    }

    public void setTenYT(String tenYT) {
        this.tenYT = tenYT;
    }

    public int getGiaYT() {
        return giaYT;
    }

    public void setGiaYT(int giaYT) {
        this.giaYT = giaYT;
    }

    public int getSoluonYT() {
        return soluonYT;
    }

    public void setSoluonYT(int soluonYT) {
        this.soluonYT = soluonYT;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getMaYT() {
        return maYT;
    }

    public void setMaYT(int maYT) {
        this.maYT = maYT;
    }
}
