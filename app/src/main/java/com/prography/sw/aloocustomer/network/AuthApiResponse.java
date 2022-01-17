package com.prography.sw.aloocustomer.network;

import android.util.Log;

import com.google.gson.Gson;
import com.prography.sw.aloocustomer.R;
import com.prography.sw.aloocustomer.data.response.GeneralResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import retrofit2.Response;

public class AuthApiResponse<T> {
    private static final String TAG = "AuthApiResponse";


    public AuthApiResponse<R> create(String t) {
        if (t.contains("Internet")) return new AuthApiErrorResponse(null, t, 0);
        else return new AuthApiErrorResponse(null, t, -1);
    }

    public AuthApiResponse<T> create(Response<T> response) {
        Log.d(TAG, "call Message: " + response.message());
        Log.d(TAG, "call Code: " + response.code());

        if (response.isSuccessful()) {
            T body = response.body();
            return new AuthApiSuccessResponse<T>(body);
        } else {

            //Printing error messages

            InputStream i = response.errorBody().byteStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(i));
            StringBuilder errorResult = new StringBuilder();
            String line;
            try {
                while ((line = r.readLine()) != null) {
                    errorResult.append(line).append('\n');
                }
                Log.d(TAG, "create: errorBody " + errorResult.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }


            String errorMessage = "";
            try {
                GeneralResponse generalErrorResponse = new Gson().fromJson(errorResult.toString(), GeneralResponse.class);
                errorMessage = generalErrorResponse.getMessage();
                Log.d(TAG, "response code given :" + response.code());
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = response.message();
            }
            return new AuthApiErrorResponse<>(response.body(), errorMessage, response.code());
        }
    }

    public class AuthApiSuccessResponse<T> extends AuthApiResponse<T> {

        private T body;

        AuthApiSuccessResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }

    }

    public class AuthApiErrorResponse<T> extends AuthApiResponse<T> {
        private T body;
        private String errorMessage;
        private int errorCode;

        public AuthApiErrorResponse(T body, String errorMessage, int errorCode) {
            this.body = body;
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        public T getBody() {
            return body;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }
}
