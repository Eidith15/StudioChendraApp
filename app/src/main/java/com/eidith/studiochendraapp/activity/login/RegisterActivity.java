package com.eidith.studiochendraapp.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ImageView ivRegisterLogo;
    private EditText etRegisterNama, etRegisterEmail, etRegisterNohp, etRegisterUsername, etRegisterPassword, etRegisterConfirmPassword;
    private Button btnRegister;
    private TextView tvRegisterBack;

    private ProgressDialog progressDialog;

    private String nama_user, email_user, no_handphone_user, username_user, password_user, confirm_password_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ivRegisterLogo = findViewById(R.id.ivRegisterLogo);
        etRegisterNama = findViewById(R.id.etRegisterNama);
        etRegisterEmail = findViewById(R.id.etRegisterEmail);
        etRegisterNohp = findViewById(R.id.etRegisterNohp);
        etRegisterUsername = findViewById(R.id.etRegisterUsername);
        etRegisterPassword = findViewById(R.id.etRegisterPassword);
        etRegisterConfirmPassword = findViewById(R.id.etRegisterConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvRegisterBack = findViewById(R.id.tvRegisterLogin);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mendaftarkan Akun");
        progressDialog.setCancelable(false);

        tvRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama_user = etRegisterNama.getText().toString();
                email_user = etRegisterEmail.getText().toString();
                no_handphone_user = etRegisterNohp.getText().toString();
                username_user = etRegisterUsername.getText().toString();
                password_user = etRegisterPassword.getText().toString();
                confirm_password_user = etRegisterConfirmPassword.getText().toString();

                if (nama_user.trim().equals("")){
                    etRegisterNama.setError("Harap Masukan Nama");
                } else if (email_user.trim().equals("") || email_user.contains(" ")){
                    etRegisterEmail.setError("Harap Masukan Email/Ada spasi");
                } else if (no_handphone_user.trim().equals("") || no_handphone_user.contains(" ")){
                    etRegisterNohp.setError("Harap Masukan No HP");
                } else if (username_user.trim().equals("") || username_user.contains(" ")){
                    etRegisterUsername.setError("Harap Masukan Username/Ada spasi");
                } else if (password_user.trim().equals("") || password_user.contains(" ")){
                    etRegisterPassword.setError("Harap Masukan Password/Ada spasi");
                } else if (confirm_password_user.trim().equals("") || confirm_password_user.contains(" ")){
                    etRegisterConfirmPassword.setError("Harap Masukan Konfirmasi Password");
                } else if (!confirm_password_user.trim().equals(password_user.trim())){
                    etRegisterConfirmPassword.setError("Konfirmasi Password tidak sama");
                } else {
                    registrasiAkun();
                }
            }
        });

    }

    public void registrasiAkun(){
        progressDialog.show();

        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<UserModel> regUser = ardData.RegUser(nama_user, email_user, no_handphone_user, username_user, password_user);

        regUser.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();

                Toast.makeText(RegisterActivity.this, "Kode : "+kode+"| Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Pesan : "+t, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
