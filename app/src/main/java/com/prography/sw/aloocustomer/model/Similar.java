package com.prography.sw.aloocustomer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Similar implements Serializable
{

@SerializedName("id")
@Expose
private int id;
@SerializedName("img")
@Expose
private String img;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
@SerializedName("price")
@Expose
private String price;
private final static long serialVersionUID = -665004106954841366L;

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getImg() {
return img;
}

public void setImg(String img) {
this.img = img;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getPrice() {
return price;
}

public void setPrice(String price) {
this.price = price;
}

}