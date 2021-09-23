package com.example.razzappv2;

public class FoodModel {
    String des, eurl, name, date, qty;

    public FoodModel() {
    }

    public FoodModel(String des, String eurl, String name, String date, String qty) {
        this.des = des;
        this.eurl = eurl;
        this.name = name;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}