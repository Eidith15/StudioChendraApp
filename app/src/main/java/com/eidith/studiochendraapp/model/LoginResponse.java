package com.eidith.studiochendraapp.model;

import java.util.List;

public class LoginResponse {

    private boolean error;
    private String message;
    private List<UserModel> data_user;


    public LoginResponse(boolean error, String message, List<UserModel> data_user) {
        this.error = error;
        this.message = message;
        this.data_user = data_user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<UserModel> getData_user() {
        return data_user;
    }
}
