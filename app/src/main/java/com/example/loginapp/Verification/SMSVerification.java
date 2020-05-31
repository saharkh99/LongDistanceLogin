package com.example.loginapp.Verification;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Location.LocationFinder;
import com.example.loginapp.Login.BaseApiService;
import com.example.loginapp.Login.UtilsApi;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SMSVerification extends AppCompatActivity {

    MaterialButton verify;
    TextInputEditText userPhone, userCode;
    ProgressBar mProgressView;
    TextView tvLoad;
    BaseApiService mApiService;
    String code = "";
    // Code code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsverification2);
        findView();
        showAlertDialogButtonClicked();
        mApiService = UtilsApi.getAPIService();
        sendingCode();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify.setEnabled(true);
                code = userCode.getText().toString().trim();
                Log.d("codesent", code);
                if (checkEmpty(code)) {
                    mApiService.setCode(code).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("codesent", code);
                            Log.d("result", response.body());
                            String result = response.body();
                            if (result != null) {
                                if (result.equals("true")) {
                                    Intent intent = new Intent(SMSVerification.this, LocationFinder.class);
                                    startActivity(intent);

                                } else {

                                    Dialog dialog = new Dialog(SMSVerification.this);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.dialog_resend);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    TextView cancel = dialog.findViewById(R.id.btn_cancel);
                                    TextView resend = dialog.findViewById(R.id.btn_resend);
                                    resend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            sendingCode();
                                        }
                                    });
                                    cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e("debug", "onFailure: ERROR > " + t.toString());
                            t.printStackTrace();
                        }
                    });
                }

            }
        });

    }

    private void sendingCode() {
        mApiService.getCode().enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("code", response.body());
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                //code = ((response.body()));
                userCode.setText(response.body());
                userPhone.setText(username);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                t.printStackTrace();
            }
        });
    }

    private boolean checkEmpty(String code) {
        if (code != null) {

            return true;
        } else {
            verify.setEnabled(false);
            userCode.setError("کد دریافتی را وارد کنید");
            return false;
        }
    }

    private void findView() {
        verify = findViewById(R.id.verify);
        userPhone = findViewById(R.id.et_userphone);
        userCode = findViewById(R.id.et_code);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
    }

    public void showAlertDialogButtonClicked() {

        Dialog dialog = new Dialog(SMSVerification.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sending_code);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView ok = dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
