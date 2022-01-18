package com.prography.sw.alooproduct.network;

import androidx.lifecycle.LiveData;

import com.prography.sw.alooproduct.data.response.GeneralResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Path;

public interface AlooApi {

    //*************************auth***************************

    //--------------login-----------------------

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("branch/auth/login")
    LiveData<AuthApiResponse<GeneralResponse>> login(@Field("type") String type,
                                                     @Field("login_number") String logn_number,
                                                     @Field("password") String password,
                                                     @Header("Accept-Language") String acceptLanguage);

    //------------logout--------------
    @Headers("Accept: application/json")
    @GET("branch/auth/logout")
    LiveData<AuthApiResponse<GeneralResponse>> logout(@Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);


    //*************************orders***************************
//------------order--------------
    @Headers("Accept: application/json")
    @GET("branch/order/list")
    LiveData<AuthApiResponse<GeneralResponse>> order(@Query("page") int page,
                                                     @Query("order_by") String order_by,
                                                     @Query("search") String search,
                                                     @Header("Authorization") String token,
                                                     @Header("Accept-Language") String acceptLanguage);


    //------------ordersData--------------
    @Headers("Accept: application/json")
    @GET("branch/order/{id}")
    LiveData<AuthApiResponse<GeneralResponse>> ordersData(@Path("id") String id,
                                                          @Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);


    //------------confirm--------------
    @Headers("Accept: application/json")
    @POST("branch/order/status/{id}/confirm")
    LiveData<AuthApiResponse<GeneralResponse>> confirm(@Path("id") int id,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);

    //------------cancel--------------
    @Headers("Accept: application/json")
    @POST("branch/order/status/{id}/cancel")
    LiveData<AuthApiResponse<GeneralResponse>> cancel(@Path("id") int id,
                                                      @Query("message") String message,
                                                      @Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);


    //------------ready--------------
    @Headers("Accept: application/json")
    @POST("branch/order/status/{id}/ready")
    LiveData<AuthApiResponse<GeneralResponse>> ready(@Path("id") int id,
                                                     @Header("Authorization") String token,
                                                     @Header("Accept-Language") String acceptLanguage);


    //------------cancelled--------------
    @Headers("Accept: application/json")
    @GET("branch/order/list/cancelled")
    LiveData<AuthApiResponse<GeneralResponse>> cancelled(@Query("page") int page,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------current--------------
    @Headers("Accept: application/json")
    @GET("branch/order/list/current")
    LiveData<AuthApiResponse<GeneralResponse>> current(@Query("page") int page,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);

    //************************* profile ***************************

    //------------change Available Status--------------
    @Headers("Accept: application/json")
    @POST("branch/changeAvailableStatus")
    LiveData<AuthApiResponse<GeneralResponse>> changeAvailableStatus(@Header("Authorization") String token,
                                                                     @Header("Accept-Language") String acceptLanguage);


    //------------get Available Status--------------
    @Headers("Accept: application/json")
    @GET("branch/getStatus")
    LiveData<AuthApiResponse<GeneralResponse>> getAvailableStatus(@Header("Authorization") String token,
                                                                  @Header("Accept-Language") String acceptLanguage);

    //------------get categories--------------
    @Headers("Accept: application/json")
    @GET("branch/categories")
    LiveData<AuthApiResponse<GeneralResponse>> getCategories(@Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);

    //------------get items--------------
    @Headers("Accept: application/json")
    @GET("branch/items")
    LiveData<AuthApiResponse<GeneralResponse>> getItems(@Query("page") int page,
                                                        @Header("Authorization") String token,
                                                        @Header("Accept-Language") String acceptLanguage);

    //------------get profile--------------
    @Headers("Accept: application/json")
    @GET("branch/profile")
    LiveData<AuthApiResponse<GeneralResponse>> getProfile(@Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);

    //------------my data--------------
    @Headers("Accept: application/json")
    @GET("branch/data")
    LiveData<AuthApiResponse<GeneralResponse>> getMyData(@Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------ get wallet --------------------------
    @Headers("Accept: application/json")
    @GET("branch/wallet")
    LiveData<AuthApiResponse<GeneralResponse>> getWallet(@Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("branch/token")
    LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(@Field("token") String fcmToken,
                                                                @Header("Authorization") String token,
                                                                @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @GET("branch/profile/notification")
    LiveData<AuthApiResponse<GeneralResponse>> getNotification(@Query("page") int page,
                                                               @Header("Authorization") String token,
                                                               @Header("Accept-Language") String acceptLanguage);

    //------------aboutData--------------
    @Headers("Accept: application/json")
    @GET("data/about")
    LiveData<AuthApiResponse<GeneralResponse>> aboutData(@Header("Accept-Language") String acceptLanguage);

    //------------privacy--------------
    @Headers("Accept: application/json")
    @GET("data/privacy/driver")
    LiveData<AuthApiResponse<GeneralResponse>> privacy(@Header("Accept-Language") String acceptLanguage);

    //------------questions--------------
    @Headers("Accept: application/json")
    @GET("data/faq/driver")
    LiveData<AuthApiResponse<GeneralResponse>> questions(@Query("page") int page,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------clientService--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("data/clientService/vendor")
    LiveData<AuthApiResponse<GeneralResponse>> clientService(@Field("type") String type, @Field("question") String question,
                                                             @Header("Accept-Language") String acceptLanguage);


}
