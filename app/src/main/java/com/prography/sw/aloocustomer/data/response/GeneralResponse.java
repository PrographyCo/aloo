
package com.prography.sw.aloocustomer.data.response;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.prography.sw.aloocustomer.data.response.data.AboutData;
import com.prography.sw.aloocustomer.data.response.data.AddAddressData;
import com.prography.sw.aloocustomer.data.response.data.AllCardItemsData;
import com.prography.sw.aloocustomer.data.response.data.CategoriesData;
import com.prography.sw.aloocustomer.data.response.data.EditItemData;
import com.prography.sw.aloocustomer.data.response.data.AddressesData;
import com.prography.sw.aloocustomer.data.response.data.FavoriteItemsData;
import com.prography.sw.aloocustomer.data.response.data.FavoriteVendorsData;
import com.prography.sw.aloocustomer.data.response.data.CitiesData;
import com.prography.sw.aloocustomer.data.response.data.KitchenTypesData;
import com.prography.sw.aloocustomer.data.response.data.MyOrdersData;
import com.prography.sw.aloocustomer.data.response.data.NotificationData;
import com.prography.sw.aloocustomer.data.response.data.OrderDetailsData;
import com.prography.sw.aloocustomer.data.response.data.MainData;
import com.prography.sw.aloocustomer.data.response.data.PrivacyData;
import com.prography.sw.aloocustomer.data.response.data.ProfileData;
import com.prography.sw.aloocustomer.data.response.data.QuestionsData;
import com.prography.sw.aloocustomer.data.response.data.RestaurantTypesData;
import com.prography.sw.aloocustomer.data.response.data.SignupData;
import com.prography.sw.aloocustomer.data.response.data.LoginData;
import com.prography.sw.aloocustomer.data.response.data.SubmitOrderData;
import com.prography.sw.aloocustomer.data.response.data.SupportedVendorsData;
import com.prography.sw.aloocustomer.data.response.data.UpdateProfileData;
import com.prography.sw.aloocustomer.data.response.data.VendorDetilseResturantData;
import com.prography.sw.aloocustomer.data.response.data.VendorDetilseSuperPhrmasData;
import com.prography.sw.aloocustomer.data.response.data.VerifyData;
import com.prography.sw.aloocustomer.data.response.data.VendorData;
import com.prography.sw.aloocustomer.data.response.data.VendorSuperPharmsData;
import com.prography.sw.aloocustomer.data.response.data.WalletData;

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

    // ------------------- SignupData --------------------
    public SignupData getSignupData() {
        Gson gson = new Gson();
        Type type = new TypeToken<SignupData>() {
        }.getType();
        SignupData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //     ------------------- login --------------------
    public LoginData getLoginData() {
        Gson gson = new Gson();
        Type type = new TypeToken<LoginData>() {
        }.getType();
        LoginData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //------------------- verify --------------------
    public VerifyData getVerificationData() {
        Gson gson = new Gson();
        Type type = new TypeToken<VerifyData>() {
        }.getType();
        VerifyData myData = gson.fromJson(gson.toJson(getData()), type);
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

    //------------update profile --------------------------
    public UpdateProfileData getUpdateProfileData() {
        Gson gson = new Gson();
        Type type = new TypeToken<UpdateProfileData>() {
        }.getType();
        UpdateProfileData myData = gson.fromJson(gson.toJson(getData()), type);
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


    //************************* addresses ******************************

    //------------ get addresses --------------------------
    public AddressesData getAddressesData() {
        Gson gson = new Gson();
        Type type = new TypeToken<AddressesData>() {
        }.getType();
        AddressesData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //------------add address --------------------------
    public AddAddressData getAddAddressData() {
        Gson gson = new Gson();
        Type type = new TypeToken<AddAddressData>() {
        }.getType();
        AddAddressData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }


    //************************* favorite ******************************

    //------------ get favorite vendors --------------------------
    public FavoriteVendorsData getFavoriteVendors() {
        Gson gson = new Gson();
        Type type = new TypeToken<FavoriteVendorsData>() {
        }.getType();
        FavoriteVendorsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    //------------ get favorite items --------------------------
    public FavoriteItemsData getFavoriteItems() {
        Gson gson = new Gson();
        Type type = new TypeToken<FavoriteItemsData>() {
        }.getType();
        FavoriteItemsData myData = gson.fromJson(gson.toJson(getData()), type);
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

    // ------------------- SupportedVendorsData--------------------
    public SupportedVendorsData getSupportedVendorsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<SupportedVendorsData>() {
        }.getType();
        SupportedVendorsData myData = gson.fromJson(gson.toJson(getData()), type);
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


    // ------------------- MainData--------------------
    public MainData getMainData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MainData>() {
        }.getType();
        MainData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- VendorData--------------------
    public VendorData getVendorData() {
        Gson gson = new Gson();
        Type type = new TypeToken<VendorData>() {
        }.getType();
        VendorData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- VendorData--------------------
    public VendorSuperPharmsData getVendorSuperPharmsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<VendorSuperPharmsData>() {
        }.getType();
        VendorSuperPharmsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- VendorItemData--------------------
    public VendorDetilseSuperPhrmasData getVendorDetilseSuperPhrmasData() {
        Gson gson = new Gson();
        Type type = new TypeToken<VendorDetilseSuperPhrmasData>() {
        }.getType();
        VendorDetilseSuperPhrmasData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- VendorItemData--------------------
    public VendorDetilseResturantData getVendorDetilseResturantData() {
        Gson gson = new Gson();
        Type type = new TypeToken<VendorDetilseResturantData>() {
        }.getType();
        VendorDetilseResturantData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- AllCardItemsData--------------------
    public AllCardItemsData getAllCardItemsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<AllCardItemsData>() {
        }.getType();
        AllCardItemsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- EditItemData--------------------
    public EditItemData getEditItemData() {
        Gson gson = new Gson();
        Type type = new TypeToken<EditItemData>() {
        }.getType();
        EditItemData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- my orders--------------------
    public MyOrdersData getMyOrdersData() {
        Gson gson = new Gson();
        Type type = new TypeToken<MyOrdersData>() {
        }.getType();
        MyOrdersData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- order details --------------------
    public OrderDetailsData getOrderDetailsData() {
        Gson gson = new Gson();
        Type type = new TypeToken<OrderDetailsData>() {
        }.getType();
        OrderDetailsData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
    // ------------------- SubmitOrderData --------------------

    public SubmitOrderData getSubmitOrderData() {
        Gson gson = new Gson();
        Type type = new TypeToken<SubmitOrderData>() {
        }.getType();
        SubmitOrderData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }

    // ------------------- RestaurantTypesData --------------------
    public RestaurantTypesData getRestaurantTypesData() {
        Gson gson = new Gson();
        Type type = new TypeToken<RestaurantTypesData>() {
        }.getType();
        RestaurantTypesData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
    // ------------------- KitchenTypesData --------------------

    public KitchenTypesData getKitchenTypesData() {
        Gson gson = new Gson();
        Type type = new TypeToken<KitchenTypesData>() {
        }.getType();
        KitchenTypesData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
    // ------------------- KitchenTypesData --------------------
    public CategoriesData getCategoriesData() {
        Gson gson = new Gson();
        Type type = new TypeToken<CategoriesData>() {
        }.getType();
        CategoriesData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
    // ------------------- KitchenTypesData --------------------

    public NotificationData getNotificationData() {
        Gson gson = new Gson();
        Type type = new TypeToken<NotificationData>() {
        }.getType();
        NotificationData myData = gson.fromJson(gson.toJson(getData()), type);
        return myData;
    }
}
