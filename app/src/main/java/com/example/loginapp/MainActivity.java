package com.example.loginapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.Location.LocationFinder;
import com.example.loginapp.Login.LoginClass;
import com.example.loginapp.Verification.SMSVerification;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity {

    MaterialButton login;

    TextInputEditText username;
    TextInputEditText password;
    View mProgressView;
    TextView tvLoad;

    String resultCode = "wrong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setEnabled(true);
                String uname = username.getText().toString().trim();
                //String uname = username.getText().toString().trim();
                String upass = password.getText().toString().trim();

                if (checkNotEmpty(uname, upass)) {
                    resultCode = LoginClass.requestLogin(uname, upass, MainActivity.this);
                }
            }
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    private boolean checkNotEmpty(String uname, String upass) {
        if (uname.isEmpty() || upass.isEmpty()) {
            login.setEnabled(false);
            // showAlertDialogButtonClicked();
            if (uname.isEmpty())
                username.setError("نام کاربری خود را وارد کنید");
            if (upass.isEmpty())
                password.setError(" رمز عبور خود را وارد کنید");
            return false;
        } else {
            login.setTextColor(getResources().getColor(R.color.whiteBlueDark));
            return true;

        }

    }

    private void findView() {
        login = findViewById(R.id.login);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/farroya.ttf");
        login.setTypeface(typeface);
    }

//    public void showAlertDialogButtonClicked() {
//
//        Dialog dialog=new Dialog(MainActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_information);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        TextView ok=dialog.findViewById(R.id.btn_ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }


}
