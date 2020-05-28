package com.example.loginapp.Verification;

import com.google.gson.annotations.SerializedName;

public class Code {
    @SerializedName("code")
    String code;

    public String getCodes() {
        return code;
    }

    public void setCodes(String code) {
        this.code = code;
    }
}
