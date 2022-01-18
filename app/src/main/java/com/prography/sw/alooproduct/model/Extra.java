package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Extra implements Serializable
{

@SerializedName("id")
@Expose
private int id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("price")
@Expose
private String price;
@SerializedName("calories")
@Expose
private String calories;
private final static long serialVersionUID = -7152205746853142421L;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

public String getCalories() {
return calories;
}

public void setCalories(String calories) {
this.calories = calories;
}

}