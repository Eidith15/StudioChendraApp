package com.eidith.studiochendraapp.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eidith.studiochendraapp.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView ivRegisterLogo;
    private EditText etRegisterNama, etRegisterEmail, etRegisterNohp, etRegisterUsername, etRegisterPassword, etRegisterConfirmPassword;
    private Button btnRegister;
    private TextView tvRegisterBack;

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



    }
}
