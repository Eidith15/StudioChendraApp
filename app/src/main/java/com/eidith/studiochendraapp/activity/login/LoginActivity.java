package com.eidith.studiochendraapp.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.portofolio.TambahPortofolioActivity;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.LoginResponse;
import com.eidith.studiochendraapp.model.PortofolioModel;
import com.eidith.studiochendraapp.model.UserModel;
import com.eidith.studiochendraapp.storage.SharedPrefManager;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private TextView tvLoginRegister;
    private ImageView ivLoginLogo;
    private Button btnLogin;

    private ProgressDialog progressDialog;

    public static String usernameInput;
    public static String passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivLoginLogo = findViewById(R.id.ivLoginLogo);
        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvLoginRegister = findViewById(R.id.tvLoginRegister);
        btnLogin = findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login...");
        progressDialog.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameInput = etLoginUsername.getText().toString();
                passwordInput = etLoginPassword.getText().toString();

                if(usernameInput.trim().equals("")){
                    etLoginUsername.setError("Harap Masukan Username / Email");
                }else if(passwordInput.trim().equals("")){
                    etLoginPassword.setError("Harap Masukan Password");
                }else{
                    try {
                        hashConvert(passwordInput);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    login(usernameInput, passwordInput);
                }
            }
        });

        tvLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedin()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void login(String usernameInput, String passwordInput) {
        progressDialog.show();

        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<LoginResponse> authLogin = ardData.AuthUser(usernameInput, passwordInput);

        authLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if ((!loginResponse.isError())){
                    SharedPrefManager.getInstance(LoginActivity.this).saveUser(loginResponse.getData_user().get(0));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    progressDialog.dismiss();

                }else{
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void hashConvert(String passwordInput) throws NoSuchAlgorithmException {
        this.passwordInput = passwordInput;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(passwordInput.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digest.length; i++) {
            sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }
    }
}