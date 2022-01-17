package com.prography.sw.aloocustomer.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prography.sw.aloocustomer.R;


/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class AuthResource<T> extends AuthApiResponse<R> {
    @NonNull
    public final AuthStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public final int errorCode;

    public AuthResource(@NonNull AuthStatus status, @Nullable T data, @Nullable String message, int errorCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static <T> AuthResource<T> authenticated(@Nullable T data) {
        return new AuthResource<>(AuthStatus.AUTHENTICATED, data, null, 0);
    }

    public static <T> AuthResource<T> error(@NonNull String msg, @Nullable T data, int errorCode) {
        return new AuthResource<>(AuthStatus.ERROR, data, msg, errorCode);
    }

    public static <T> AuthResource<T> loading() {
        return new AuthResource<>(AuthStatus.LOADING, null, null, 0);
    }


    public enum AuthStatus {AUTHENTICATED, ERROR, LOADING}

}
