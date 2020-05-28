package com.example.loginapp.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface GetDataService {
    @Headers("Api-Key:service.RWmI2uXg3gousNT1hSxw62xoAhSjsKdkHA3J5009")
    @GET
    Call<NeshanAddress> getNeshanSearch(@Url String url);
}
