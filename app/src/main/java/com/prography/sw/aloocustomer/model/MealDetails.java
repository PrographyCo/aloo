package com.prography.sw.aloocustomer.model;

public class MealDetails {
    private int id;
    private String title;
    private Boolean isChecked;

    public MealDetails(String title, Boolean isChecked) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public MealDetails(int id, String title, Boolean isChecked) {
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
