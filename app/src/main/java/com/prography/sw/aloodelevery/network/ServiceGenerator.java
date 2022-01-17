package com.prography.sw.aloodelevery.network;


import static com.prography.sw.aloodelevery.util.Constants.BASE_URL;
import static com.prography.sw.aloodelevery.util.Constants.CONNECTION_TIMEOUT;
import static com.prography.sw.aloodelevery.util.Constants.READ_TIMEOUT;
import static com.prography.sw.aloodelevery.util.Constants.WRITE_TIMEOUT;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static final Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = retrofitBuilder.build();

    private static final AlooApi M_API = retrofit.create(AlooApi.class);

    public static AlooApi getMApi() {
        return M_API;
    }
}
