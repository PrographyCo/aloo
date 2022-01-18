
package com.prography.sw.alooproduct.data.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.prography.sw.alooproduct.data.response.data.AboutData;
import com.prography.sw.alooproduct.data.response.data.CancelData;
import com.prography.sw.alooproduct.data.response.data.CitiesData;
import com.prography.sw.alooproduct.data.response.data.ConfirmData;
import com.prography.sw.alooproduct.data.response.data.GetStatusData;
import com.prography.sw.alooproduct.data.response.data.MyData;
import com.prography.sw.alooproduct.data.response.data.MyOrderData;
import com.prography.sw.alooproduct.data.response.data.LoginData;
import com.prography.sw.alooproduct.data.response.data.NotificationData;
import com.prography.sw.alooproduct.data.response.data.QuestionsData;
import com.prography.sw.alooproduct.data.response.data.ReadyData;
import com.prography.sw.alooproduct.data.response.data.StatusData;
import com.prography.sw.alooproduct.data.response.data.PrivacyData;
import com.prography.sw.alooproduct.data.response.data.ProfileData;
import com.prography.sw.alooproduct.data.response.data.OrderData;
import com.prography.sw.alooproduct.data.response.data.OrdersListData;
import com.prography.sw.alooproduct.data.response.data.StoreProductData;
import com.prography.sw.alooproduct.data.response.data.WalletData;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class GeneralResponse implements Serializable {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Object data = null;
    @SerializedName("content-language")
    @Expose
    private String contentLanguage;
    @SerializedName("status")
    @Expose
    private boolean status;
    private final static long serialVersionUID = -3803546303514320732L;

    public GeneralResponse() {
    }

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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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

    // ------------------- status--------------------
    public StatusData getStatusData() {
        Gson gson = new Gson();
        Type type = new TypeToken<StatusData>() {
        }.getType();
        StatusData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- OrdersListData--------------------
    public OrdersListData getOrdersListData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OrdersListData>() {
        }.getType();
        OrdersListData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- OrderData--------------------
    public OrderData getOrderData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OrderData>() {
        }.getType();
        OrderData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- ConfirmData--------------------
    public ConfirmData getConfirmData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ConfirmData>() {
        }.getType();
        ConfirmData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- CancelData--------------------
    public CancelData getCancelData() {
        Gson gson = new Gson();
        Type type = new TypeToken<CancelData>() {
        }.getType();
        CancelData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- ReadyData--------------------
    public ReadyData getReadyData() {
        Gson gson = new Gson();
        Type type = new TypeToken<ReadyData>() {
        }.getType();
        ReadyData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- my order--------------------
    public MyOrderData getMyOrderData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyOrderData>() {
        }.getType();
        MyOrderData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- MyData--------------------
    public MyData getMyData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyData>() {
        }.getType();
        MyData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- GetStatusData--------------------
    public GetStatusData getGetStatusData() {
        Gson gson = new Gson();
        Type type = new TypeToken<GetStatusData>() {
        }.getType();
        GetStatusData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }   // ------------------- StoreProductData--------------------

    public StoreProductData getStoreProductData() {
        Gson gson = new Gson();
        Type type = new TypeToken<StoreProductData>() {
        }.getType();
        StoreProductData myData = gson.fromJson(gson.toJson(getData()), type);
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
