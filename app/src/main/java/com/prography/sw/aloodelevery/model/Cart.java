package com.prography.sw.aloodelevery.model;

public class Cart {
    private int imageMeal;
    private String nameMeal;
    private String peiceMeal;
    private String descMeal;
    private String number;

    public Cart(int imageMeal, String nameMeal, String peiceMeal, String descMeal, String number) {
        this.imageMeal = imageMeal;
        this.nameMeal = nameMeal;
        this.peiceMeal = peiceMeal;
        this.descMeal = descMeal;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImageMeal() {
        return imageMeal;
    }

    public void setImageMeal(int imageMeal) {
        this.imageMeal = imageMeal;
    }


    public String getNameMeal() {
        return nameMeal;
    }

    public void setNameMeal(String nameMeal) {
        this.nameMeal = nameMeal;
    }

    public String getPeiceMeal() {
        return peiceMeal;
    }

    public void setPeiceMeal(String peiceMeal) {
        this.peiceMeal = peiceMeal;
    }

    public String getDescMeal() {
        return descMeal;
    }

    public void setDescMeal(String descMeal) {
        this.descMeal = descMeal;
    }
}
