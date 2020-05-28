package com.example.loginapp.Login;


import com.example.loginapp.Verification.Code;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("Home/Login")
    Call<String> registerRequest(@Field("username") String username,
                                 @Field("passwords") String passwords);

    // @FormUrlEncoded
    @GET("Home/setCode")
    Call<String> getCode();

    @FormUrlEncoded
    @POST("Home/getCode")
    Call<String> setCode(@Field("code") String code);

}
