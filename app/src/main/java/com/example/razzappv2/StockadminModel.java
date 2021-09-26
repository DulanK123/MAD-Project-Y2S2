package com.example.razzappv2;

public class StockadminModel {
    String id,Name,email;

    public StockadminModel() {
    }

    public StockadminModel(String id, String name, String email) {
        this.id = id;
        Name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
