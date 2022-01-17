package com.prography.sw.aloodelevery.network;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloodelevery.data.response.GeneralResponse;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlooApi {

    //************************* auth ******************************

    //------------login--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("car/auth/login")
    LiveData<AuthApiResponse<GeneralResponse>> login(@Field("type") String type,
                                                     @Field("login_number") String phone,
                                                     @Field("password") String password,
                                                     @Header("Accept-Language") String acceptLanguage);

    //------------logout--------------
    @Headers("Accept: application/json")
    @GET("car/auth/logout")
    LiveData<AuthApiResponse<GeneralResponse>> logout(@Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);


    //************************* profile ******************************

    //------------ get profile --------------------------
    @Headers("Accept: application/json")
    @GET("car/profile")
    LiveData<AuthApiResponse<GeneralResponse>> getProfile(@Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);

    //------------ get wallet --------------------------
    @Headers("Accept: application/json")
    @GET("car/wallet")
    LiveData<AuthApiResponse<GeneralResponse>> getWallet(@Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------ get My Data --------------------------
    @Headers("Accept: application/json")
    @GET("car/profile/data")
    LiveData<AuthApiResponse<GeneralResponse>> getMyData(@Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("car/token")
    LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(@Field("token") String fcmToken,
                                                                @Header("Authorization") String token,
                                                                @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @GET("car/profile/notification")
    LiveData<AuthApiResponse<GeneralResponse>> getNotification(@Query("page") int page,
                                                               @Header("Authorization") String token,
                                                               @Header("Accept-Language") String acceptLanguage);
    //************************* General Data ******************************

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
    @POST("car/profile/notification")
    LiveData<AuthApiResponse<GeneralResponse>> clientService(@Field("type") String type, @Field("question") String question,
                                                             @Header("Accept-Language") String acceptLanguage);


    //************************* orders ******************************

    @Headers("Accept: application/json")
    @GET("car/orders/get")
    LiveData<AuthApiResponse<GeneralResponse>> orders(@Query("lon") double lon,
                                                      @Query("lat") double lat,
                                                      @Query("page") int page,
                                                      @Query("order_by") String order_by,
                                                      @Query("type") int type,
                                                      @Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @GET("car/orders/view/{id}")
    LiveData<AuthApiResponse<GeneralResponse>> orderData(@Path("id") int id,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("car/orders/status/{id}/confirm")
    LiveData<AuthApiResponse<GeneralResponse>> confirm(@Path("id") int id,
                                                       @Field("distance") int distance,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("car/orders/status/{id}/cancel")
    LiveData<AuthApiResponse<GeneralResponse>> cancel(@Path("id") int id,
                                                      @Field("message") String message,
                                                      @Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @POST("car/orders/status/{id}/waiting")
    LiveData<AuthApiResponse<GeneralResponse>> waiting(@Path("id") int id,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @POST("car/orders/status/{id}/toCustomer")
    LiveData<AuthApiResponse<GeneralResponse>> toCustomer(@Path("id") int id,
                                                          @Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @POST("car/orders/status/{id}/arrived")
    LiveData<AuthApiResponse<GeneralResponse>> arrived(@Path("id") int id,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @POST("car/orders/status/{id}/delivered")
    LiveData<AuthApiResponse<GeneralResponse>> delivered(@Path("id") int id,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @GET("car/orders/{status}")
    LiveData<AuthApiResponse<GeneralResponse>> getOrdersCurrentCancelled(@Path("status") String status,
                                                                         @Header("Authorization") String token,
                                                                         @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @GET("car/orders/view/current/{id}")
    LiveData<AuthApiResponse<GeneralResponse>> getOrdersCurrentDetails(@Path("id") int id,
                                                                       @Header("Authorization") String token,
                                                                       @Header("Accept-Language") String acceptLanguage);


    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("car/orders/rate/{order_id}")
    LiveData<AuthApiResponse<GeneralResponse>> rate(@Path("order_id") int orderId,
                                                    @Field("rate") String rate,
                                                    @Field("message") String message,
                                                    @Header("Authorization") String token,
                                                    @Header("Accept-Language") String acceptLanguage);

}
