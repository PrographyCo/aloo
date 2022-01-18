package com.prography.sw.alooproduct.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sizes implements Serializable
{

@SerializedName("Small")
@Expose
private int small;
@SerializedName("Medium")
@Expose
private int medium;
@SerializedName("Big")
@Expose
private int big;
private final static long serialVersionUID = 5319248344663085118L;

public int getSmall() {
return small;
}

public void setSmall(int small) {
this.small = small;
}

public int getMedium() {
return medium;
}

public void setMedium(int medium) {
this.medium = medium;
}

public int getBig() {
return big;
}

public void setBig(int big) {
this.big = big;
}

}