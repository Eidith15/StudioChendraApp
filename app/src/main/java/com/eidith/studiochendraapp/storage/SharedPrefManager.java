package com.eidith.studiochendraapp.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.eidith.studiochendraapp.model.UserModel;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "shared_pref";
    private static SharedPrefManager instance;
    private Context context;

    private SharedPrefManager(Context context){
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(UserModel userModel){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id_user", userModel.getId_user());
        editor.putString("nama_user", userModel.getNama_user());
        editor.putString("email_user", userModel.getEmail_user());
        editor.putString("noHp_user", userModel.getNo_handphone_user());
        editor.putString("username_user", userModel.getUsername_user());
        editor.putString("password_user", userModel.getPassword_user());
        editor.putInt("accessCode_user", userModel.getAccess_code());

        editor.apply();

    }

    public boolean isLoggedin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("id_user", -1) != -1){
            return true;
        } else {
            return false;
        }
    }

    public UserModel getUserData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        UserModel userModel = new UserModel(
                sharedPreferences.getInt("id_user", -1),
                sharedPreferences.getString("nama_user", null),
                sharedPreferences.getString("email_user", null),
                sharedPreferences.getString("noHp_user", null),
                sharedPreferences.getString("username_user", null),
                sharedPreferences.getString("password_user", null),
                sharedPreferences.getInt("accessCode_user", -1)
        );
        return userModel;
    }

    public void clear(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
