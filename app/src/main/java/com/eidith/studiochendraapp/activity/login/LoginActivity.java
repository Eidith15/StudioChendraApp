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
import com.eidith.studiochendraapp.model.PortofolioModel;
import com.eidith.studiochendraapp.model.UserModel;

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

    private String usernameInput;
    private String passwordInput;

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

    private void login(String usernameInput, String passwordInput) {
        progressDialog.show();

        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<UserModel> authLogin = ardData.AuthUser(usernameInput, passwordInput);

        authLogin.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                String nama = response.body().getNama_user();
                String email = response.body().getEmail_user();
                String noHP = response.body().getNo_handphone_user();
                String username = response.body().getUsername_user();
                String password = response.body().getUsername_user();
                int accessCode = response.body().getAccess_code();

                if (kode == 1){
                    Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    Intent intentSuccess = new Intent(LoginActivity.this, MainActivity.class);
                    intentSuccess.putExtra("Nama User", nama);
                    intentSuccess.putExtra("Email User", email);
                    intentSuccess.putExtra("NoHp User", noHP);
                    intentSuccess.putExtra("Username User", username);
                    intentSuccess.putExtra("Password User", password);
                    intentSuccess.putExtra("Access Code", accessCode);
                    startActivity(intentSuccess);
                    progressDialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
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