package com.example.razzappv2;

public class ClothModel {
    String des, eurl, name, mat, qty;

    public ClothModel() {
    }

    public ClothModel(String des, String eurl, String name, String mat, String qty) {
        this.des = des;
        this.eurl = eurl;
        this.name = name;
        this.mat = mat;
        this.qty = qty;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getEurl() {
        return eurl;
    }

    public void setEurl(String eurl) {
        this.eurl = eurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
