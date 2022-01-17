
package com.prography.sw.aloodelevery.data.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.prography.sw.aloodelevery.data.response.data.AboutData;
import com.prography.sw.aloodelevery.data.response.data.CitiesData;
import com.prography.sw.aloodelevery.data.response.data.ConfirmData;
import com.prography.sw.aloodelevery.data.response.data.LoginData;
import com.prography.sw.aloodelevery.data.response.data.MyData;
import com.prography.sw.aloodelevery.data.response.data.MyOrderDetailsData;
import com.prography.sw.aloodelevery.data.response.data.MyOrdersData;
import com.prography.sw.aloodelevery.data.response.data.NotificationData;
import com.prography.sw.aloodelevery.data.response.data.OrdersData;
import com.prography.sw.aloodelevery.data.response.data.OredrDetielsData;
import com.prography.sw.aloodelevery.data.response.data.PrivacyData;
import com.prography.sw.aloodelevery.data.response.data.ProfileData;
import com.prography.sw.aloodelevery.data.response.data.QuestionsData;
import com.prography.sw.aloodelevery.data.response.data.ToCustomerData;
import com.prography.sw.aloodelevery.data.response.data.WalletData;
import com.prography.sw.aloodelevery.data.response.data.WaitingData;

import java.io.Serializable;
import java.lang.reflect.Type;

public class GeneralResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Object data;
    @SerializedName("content-language")
    @Expose
    private String contentLanguage;
    @SerializedName("status")
    @Expose
    private boolean status;
    private final static long serialVersionUID = -5233539734221507174L;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    //************************* auth ******************************


    //     ------------------- login --------------------
    public LoginData getLoginData() {
        Gson gson = new Gson();
        Type type = new TypeToken<LoginData>() {
        }.getType();
        LoginData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }


    //************************* profile ******************************

    //------------ get profile --------------------------
    public ProfileData getProfileData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ProfileData>() {
        }.getType();
        ProfileData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //------------ Wallet --------------------------
    public WalletData getWalletData() {
        Gson gson = new Gson();
        Type type = new TypeToken<WalletData>() {
        }.getType();
        WalletData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //************************* general ******************************

    //------------------- Cities--------------------
    public CitiesData getCitiesData() {
        Gson gson = new Gson();
        Type type = new TypeToken<CitiesData>() {
        }.getType();
        CitiesData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- PrivacyData--------------------
    public PrivacyData getPrivacyData() {
        Gson gson = new Gson();
        Type type = new TypeToken<PrivacyData>() {
        }.getType();
        PrivacyData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- AboutData--------------------
    public AboutData getAboutData() {
        Gson gson = new Gson();
        Type type = new TypeToken<AboutData>() {
        }.getType();
        AboutData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- questions--------------------
    public QuestionsData getQuestionsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<QuestionsData>() {
        }.getType();
        QuestionsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //************************* orders ******************************

    // ------------------- orders--------------------
    public OrdersData getOrdersData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OrdersData>() {
        }.getType();
        OrdersData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
    // ------------------- ordersDetiels --------------------

    public OredrDetielsData getOredrDetielsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OredrDetielsData>() {
        }.getType();
        OredrDetielsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
// ------------------- confirm --------------------

    public ConfirmData getConfirmData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ConfirmData>() {
        }.getType();
        ConfirmData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- MyOrders --------------------
    public MyOrdersData getMyOrdersData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyOrdersData>() {
        }.getType();
        MyOrdersData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- Waiting --------------------
    public WaitingData getWaitingData() {
        Gson gson = new Gson();
        Type type = new TypeToken<WaitingData>() {
        }.getType();
        WaitingData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }// ------------------- ToCustomer --------------------

    public ToCustomerData getToCustomerData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ToCustomerData>() {
        }.getType();
        ToCustomerData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- MyOrderDetailsData --------------------
    public MyOrderDetailsData getMyOrderDetailsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyOrderDetailsData>() {
        }.getType();
        MyOrderDetailsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- MyData --------------------
    public MyData getMyData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyData>() {
        }.getType();
        MyData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    public NotificationData getNotificationData() {
        Gson gson = new Gson();
        Type type = new TypeToken<NotificationData>() {
        }.getType();
        NotificationData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

}
