package com.prography.sw.alooproduct.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetStatusData implements Serializable
{

@SerializedName("status")
@Expose
private boolean status;
private final static long serialVersionUID = -3659831731066841767L;

public boolean isStatus() {
return status;
}

public void setStatus(boolean status) {
this.status = status;
}

}