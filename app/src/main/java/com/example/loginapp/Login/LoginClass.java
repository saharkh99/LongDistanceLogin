package com.example.loginapp.Login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.example.loginapp.Verification.SMSVerification;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClass {
    static String code = "wrong";

    // Context mContext;
    public static String requestLogin(String username, String passwords, Context context) {
        BaseApiService mApiService;

        mApiService = UtilsApi.getAPIService();
        mApiService.registerRequest(username, passwords)
                .enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dialog);
                        dialog.show();

                        Log.d("response", response.toString());
                        if (response.isSuccessful()) {
                            Log.d("suceess", "success");
                            try {
                                if (response.code() == 400) {
                                    Log.v("Error code 400", response.errorBody().string());
                                }
                                String responseBody = response.body();
                                if (responseBody.equals("true")) {
                                    dialog.dismiss();
                                    code = responseBody;
                                    Log.d("correct", "correct");
                                    Intent intent = new Intent(context, SMSVerification.class);
                                    intent.putExtra("username", username);
                                    context.startActivity(intent);
                                } else {
                                    dialog.dismiss();
                                    Dialog dialog1 = new Dialog(context);
                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog1.setContentView(R.layout.dialog_information);
                                    TextView ok = dialog1.findViewById(R.id.btn_ok);
                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog1.dismiss();
                                        }
                                    });
                                    dialog1.show();
                                    code = responseBody;
                                    Log.d("incorrect password", "correct");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        t.printStackTrace();

                    }
                });
        return code;

    }
}













