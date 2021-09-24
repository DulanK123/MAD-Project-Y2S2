package com.example.razzappv2;

public class ElectronicModel {
    String des,eurl,name,qty;

    public ElectronicModel() {
    }

    public ElectronicModel(String des, String eurl, String name, String qty) {
        this.des = des;
        this.eurl = eurl;
        this.name = name;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
