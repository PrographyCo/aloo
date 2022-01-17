package com.prography.sw.aloodelevery.network;

import androidx.lifecycle.LiveData;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    private String type;

    /**
     * This method preforms a number of checks and then returns the response type for retrofit requests.
     *
     * @bodyType is the response type (AreaCategoriesResponse)?
     * CHECK #1 returnType returns LiveData
     * CHECK #2 LiveData<T> is of DataApiResponse.class
     * CHECK #3 Make sure that DataApiResponse is parameterized. AKA: DataApiResponse exists
     */
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        // Check #1
        // Make sure the CallAdapter is returning a type of LiveData
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }

        // Check #2
        // Type that LiveData is wrapping
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);

        // Check if it's of Type DataApiResponse
        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != AuthApiResponse.class) {
            throw new IllegalArgumentException("Type must be a defined resource");
        }


        // Check #3
        // Check if DataApiResponse is parameterized. AKA: Does DataApiResponse<T> exists? (must wrap around T)
        // FYI: T is AreaCategoriesResponse
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
            return new LiveDataAuthCallAdapter<Type>(bodyType);

    }

}