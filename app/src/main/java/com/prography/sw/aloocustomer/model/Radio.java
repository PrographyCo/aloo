package com.prography.sw.aloocustomer.model;

public class Radio {
    private int id;
    private String title;
    private Boolean isCheck;

    public Radio(String title, Boolean isCheck) {
        this.title = title;
        this.isCheck = isCheck;
    }

    public Radio(int id, String title, Boolean isCheck) {
        this.id = id;
        this.title = title;
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}
