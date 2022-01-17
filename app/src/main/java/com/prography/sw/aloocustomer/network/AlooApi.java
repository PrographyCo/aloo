package com.prography.sw.aloocustomer.network;

import androidx.lifecycle.LiveData;

import com.prography.sw.aloocustomer.data.response.GeneralResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    @POST("customer/auth/login")
    LiveData<AuthApiResponse<GeneralResponse>> login(@Field("type")String type,
                                                     @Field("phone") String phone,
                                                     @Field("password") String password,
                                                     @Header("Accept-Language") String acceptLanguage);

    //------------logout--------------
    @Headers("Accept: application/json")
    @GET("customer/auth/logout")
    LiveData<AuthApiResponse<GeneralResponse>> logout(@Header("Authorization") String token,
                                                      @Header("Accept-Language") String acceptLanguage);

    //------------signup--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/auth/signup")
    LiveData<AuthApiResponse<GeneralResponse>> signup(@Field("name") String name,
                                                      @Field("phone") String phone,
                                                      @Field("gender") String gender,
                                                      @Field("city") String city,
                                                      @Field("password") String password,
                                                      @Field("password_confirmation") String password_confirmation,
                                                      @Header("Accept-Language") String acceptLanguage);


    //------------verify--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/auth/verify/phone")
    LiveData<AuthApiResponse<GeneralResponse>> verify(@Field("phone") String phone,
                                                      @Field("verify_code") String verify_code,
                                                      @Header("Accept-Language") String acceptLanguage);


    //------------resendCode--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/auth/send_verify/phone")
    LiveData<AuthApiResponse<GeneralResponse>> resendCode(@Field("phone") String phone,
                                                          @Header("Accept-Language") String acceptLanguage);


    //************************* profile ******************************

    //------------ get profile --------------------------
    @Headers("Accept: application/json")
    @GET("customer/profile/show")
    LiveData<AuthApiResponse<GeneralResponse>> getProfile(@Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);

    //------------update profile --------------------------
    @Headers("Accept: application/json")
    @Multipart
    @POST("customer/profile/edit")
    LiveData<AuthApiResponse<GeneralResponse>> updateProfile(@Part("name") RequestBody name,
                                                             @Part("password") RequestBody password,
                                                             @Part("password_confirmation") RequestBody passwordConfirmation,
                                                             @Part("city") RequestBody city,
                                                             @Part("gender") RequestBody gender,
                                                             @Part("phone") RequestBody phone,
                                                             @Part("email") RequestBody email,
                                                             @Part MultipartBody.Part img,
                                                             @Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);

    //------------ get wallet --------------------------
    @Headers("Accept: application/json")
    @GET("customer/profile/wallet")
    LiveData<AuthApiResponse<GeneralResponse>> getWallet(@Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage); //------------ get wallet --------------------------


    //------------ FCM TOKEN --------------------------

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/profile/token")
    LiveData<AuthApiResponse<GeneralResponse>> setFirebaseToken(@Field("token") String fcmToken,
                                                                @Header("Authorization") String token,
                                                                @Header("Accept-Language") String acceptLanguage);

    @Headers("Accept: application/json")
    @GET("customer/profile/notification")
    LiveData<AuthApiResponse<GeneralResponse>> getNotification(@Query("page") int page,
                                                               @Header("Authorization") String token,
                                                               @Header("Accept-Language") String acceptLanguage);


    //************************* addresses ******************************

    //------------ get addresses --------------------------
    @Headers("Accept: application/json")
    @GET("customer/profile/places")
    LiveData<AuthApiResponse<GeneralResponse>> getAddresses(@Header("Authorization") String token,
                                                            @Header("Accept-Language") String acceptLanguage);

    //------------add addresses --------------------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/profile/places/new")
    LiveData<AuthApiResponse<GeneralResponse>> addAddress(@Field("name") String name,
                                                          @Field("location_name") String locationName,
                                                          @Field("lon") String lon,
                                                          @Field("lat") String lat,
                                                          @Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);

    //------------ delete address --------------------------
    @Headers("Accept: application/json")
    @DELETE("customer/profile/places/delete/{id}")
    LiveData<AuthApiResponse<GeneralResponse>> deleteAddress(@Path("id") int id,
                                                             @Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);


    //************************* favourite ******************************

    //------------ get favorite vendor --------------------------
    @Headers("Accept: application/json")
    @GET("customer/favorite/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getFavoriteVendors(@Path("supported_vendor_id") int id,
                                                                  @Header("Authorization") String token,
                                                                  @Header("Accept-Language") String acceptLanguage);

    //------------ get favorite vendor --------------------------
    @Headers("Accept: application/json")
    @GET("customer/favorite/vendor/{vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getFavoriteItems(@Path("vendor_id") int id,
                                                                @Query("page") int page,
                                                                @Header("Authorization") String token,
                                                                @Header("Accept-Language") String acceptLanguage);

    //------------add to favorite --------------------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/favorite/add")
    LiveData<AuthApiResponse<GeneralResponse>> addToFavorite(@Field("item_id") String id,
                                                             @Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);


    //------------ delete from favorite --------------------------
    @Headers("Accept: application/json")
    @DELETE("customer/favorite/delete/{item_id}")
    LiveData<AuthApiResponse<GeneralResponse>> deleteFavorite(@Path("item_id") int id,
                                                              @Header("Authorization") String token,
                                                              @Header("Accept-Language") String acceptLanguage);


    //************************* Main ******************************


    //------------ Main --------------------------
    @Headers("Accept: application/json")
    @GET("customer/main/list/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getMain(@Path("supported_vendor_id") int id,
                                                       @Query("page") int page,
                                                       @Query("lon") double lon,
                                                       @Query("lat") double lat,
                                                       @Query("place_id") int place_id,
                                                       @Query("kitchen_type") int kitchen_type,
                                                       @Query("restaurant_type") int restaurant_type,
                                                       @Query("order_by") String order_by,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);


    //------------ Main --------------------------

    @Headers("Accept: application/json")
    @GET("customer/main/list/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getMain(@Path("supported_vendor_id") int id,
                                                       @Query("page") int page,
                                                       @Query("lon") double lon,
                                                       @Query("lat") double lat,
                                                       @Query("place_id") int place_id,
                                                       @Header("Authorization") String token,
                                                       @Header("Accept-Language") String acceptLanguage);

    //------------ getVendor --------------------------
    @Headers("Accept: application/json")
    @GET("customer/main/list/items/{vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getVendor(@Path("vendor_id") int id,
                                                         @Query("page") int page,
                                                         @Query("lon") double lon,
                                                         @Query("lat") double lat,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------ getVendor --------------------------
    @Headers("Accept: application/json")
    @GET("customer/main/list/items/{vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getVendor(@Path("vendor_id") int id,
                                                         @Query("page") int page,
                                                         @Query("order_by") String order_by,
                                                         @Query("category") int category,
                                                         @Query("lon") double lon,
                                                         @Query("lat") double lat,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------ getVendorOneItem --------------------------

    @Headers("Accept: application/json")
    @GET("customer/main/view/item/{item_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getVendorItem(@Path("item_id") int id,
                                                             @Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);


    //------------ getCategories --------------------------
    @Headers("Accept: application/json")
    @GET("data/vendor/{vendor_id}/categories")
    LiveData<AuthApiResponse<GeneralResponse>> getCategories(@Path("vendor_id") int id,
                                                             @Header("Authorization") String token,
                                                             @Header("Accept-Language") String acceptLanguage);


    // ************************* cart ******************************


    //------------ addToCard --------------------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/main/cart/add")
    LiveData<AuthApiResponse<GeneralResponse>> addToCard(@Field("item_id") int item_id,
                                                         @Field("amount") int amount,
                                                         @Field("with[]") List<String> with,
                                                         @Field("without[]") List<String> without,
                                                         @Field("size") String size,
                                                         @Field("drinks[]") List<Integer> drinks,
                                                         @Field("extras[]") List<Integer> extras,
                                                         @Header("Authorization") String token,
                                                         @Header("Accept-Language") String acceptLanguage);


    //------------ addToCardSuperPhrmacy --------------------------

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/main/cart/add")
    LiveData<AuthApiResponse<GeneralResponse>> addToCardSuperPhrmacy(@Field("item_id") int item_id,
                                                                     @Field("amount") int amount,
                                                                     @Header("Authorization") String token,
                                                                     @Header("Accept-Language") String acceptLanguage);


    //------------ getAllCardItem --------------------------
    //customer/main/cart/add
    @Headers("Accept: application/json")
    @GET("customer/main/cart/list/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getAllCardItem(@Path("supported_vendor_id") int supported_vendor_id,
                                                              @Header("Authorization") String token,
                                                              @Header("Accept-Language") String acceptLanguage);

    //------------ deleteCartItem --------------------------

    @Headers("Accept: application/json")
    @DELETE("customer/main/cart/{id}/delete")
    LiveData<AuthApiResponse<GeneralResponse>> deleteCartItem(@Path("id") int id,
                                                              @Header("Authorization") String token,
                                                              @Header("Accept-Language") String acceptLanguage);


    //------------ getOneItem --------------------------
    @Headers("Accept: application/json")
    @GET("customer/main/cart/{id}/view")
    LiveData<AuthApiResponse<GeneralResponse>> getOneItem(@Path("id") int id,
                                                          @Header("Authorization") String token,
                                                          @Header("Accept-Language") String acceptLanguage);


    //------------ SaveEdit --------------------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/main/cart/{id}/edit")
    LiveData<AuthApiResponse<GeneralResponse>> SaveEdit(@Path("id") int id,
                                                        @Field("amount") int amount,
                                                        @Field("with[]") List<String> with,
                                                        @Field("without[]") List<String> without,
                                                        @Field("size") String size,
                                                        @Field("drinks[]") List<Integer> drinks,
                                                        @Field("extras[]") List<Integer> extras,
                                                        @Header("Authorization") String token,
                                                        @Header("Accept-Language") String acceptLanguage);


    //------------ SaveEdit --------------------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/main/cart/{id}/edit")
    LiveData<AuthApiResponse<GeneralResponse>> SaveEdit(@Path("id") int id,
                                                        @Field("amount") int amount,
                                                        @Header("Authorization") String token,
                                                        @Header("Accept-Language") String acceptLanguage);

    //------------ submitCartOrder --------------------------

    @Headers("Accept: application/json")
    @GET("customer/main/cart/toOrder/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> submitCartOrder(@Path("supported_vendor_id") int supported_vendor_id,
                                                               @Header("Authorization") String token,
                                                               @Header("Accept-Language") String acceptLanguage);


    // ************************* my orders ******************************

    //------------my orders--------------
    @Headers("Accept: application/json")
    @GET("customer/orders/list/{supported_vendor_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getMYOrders(@Path("supported_vendor_id") int id,
                                                           @Header("Authorization") String token,
                                                           @Header("Accept-Language") String acceptLanguage);

    //------------ order details--------------
    @Headers("Accept: application/json")
    @GET("customer/orders/show/{order_id}")
    LiveData<AuthApiResponse<GeneralResponse>> getOrderDetails(@Path("order_id") int id,
                                                               @Header("Authorization") String token,
                                                               @Header("Accept-Language") String acceptLanguage);

    //------------ rate --------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("customer/rate/branch/{order_id}")
    LiveData<AuthApiResponse<GeneralResponse>> rate(@Path("order_id") int orderId,
                                                    @Field("rate") String rate,
                                                    @Field("message") String message,
                                                    @Header("Authorization") String token,
                                                    @Header("Accept-Language") String acceptLanguage);


    // ************************* General Data ******************************

    //------------cities--------------
    @Headers("Accept: application/json")
    @GET("data/cities")
    LiveData<AuthApiResponse<GeneralResponse>> cities(@Header("Accept-Language") String acceptLanguage);

    //------------banks--------------
    @Headers("Accept: application/json")
    @GET("data/banks")
    LiveData<AuthApiResponse<GeneralResponse>> banks(@Header("Accept-Language") String acceptLanguage);

    //------------aboutData--------------
    @Headers("Accept: application/json")
    @GET("data/about")
    LiveData<AuthApiResponse<GeneralResponse>> aboutData(@Header("Accept-Language") String acceptLanguage);

    //------------privacy--------------
    @Headers("Accept: application/json")
    @GET("data/privacy/customer")
    LiveData<AuthApiResponse<GeneralResponse>> privacy(@Header("Accept-Language") String acceptLanguage);

    //------------questions--------------
    @Headers("Accept: application/json")
    @GET("data/faq/customer")
    LiveData<AuthApiResponse<GeneralResponse>> questions(@Query("page") int page,
                                                         @Header("Accept-Language") String acceptLanguage);

    //------------services--------------
    @Headers("Accept: application/json")
    @GET("data/services")
    LiveData<AuthApiResponse<GeneralResponse>> services(@Header("Accept-Language") String acceptLanguage);


    //------------restaurant--------------
    @Headers("Accept: application/json")
    @GET("data/restaurant")
    LiveData<AuthApiResponse<GeneralResponse>> getRestaurantTypes(@Header("Accept-Language") String acceptLanguage);


    //------------kitchen--------------
    @Headers("Accept: application/json")
    @GET("data/kitchen")
    LiveData<AuthApiResponse<GeneralResponse>> getKitchenTypes(@Header("Accept-Language") String acceptLanguage);

    //------------clientService--------------
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("data/clientService/customer")
    LiveData<AuthApiResponse<GeneralResponse>> clientService(@Field("type") String type, @Field("question") String question,
                                                             @Header("Accept-Language") String acceptLanguage);


}
