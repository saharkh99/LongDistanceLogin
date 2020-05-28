package com.example.loginapp.Location;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LocationCall {
    private List<Item> items;
    static String region = "";

    protected static String doSearch( double lat, double lng) {
        final String requestURL = "https://api.neshan.org/v1/reverse?lat=" + lat + "&lng=" + lng;
        GetDataService api = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<NeshanAddress> call = api.getNeshanSearch(requestURL);

        call.enqueue(new Callback<NeshanAddress>() {
            @Override
            public void onResponse(Call<NeshanAddress> call, Response<NeshanAddress> response) {
                if (response.isSuccessful()) {
                    Log.d("suceess", "ok");
                    NeshanAddress neshanSearch = response.body();
                    Log.d("neshan", response.body().toString());
                    region = neshanSearch.getCity();
                    Log.d("region", region);
                   // region = items.get(0).getRegion();
                } else {
                    Log.i(TAG, "onResponse: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NeshanAddress> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });

        return region;
    }


}