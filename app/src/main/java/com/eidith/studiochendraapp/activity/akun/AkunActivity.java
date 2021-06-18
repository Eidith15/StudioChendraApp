package com.eidith.studiochendraapp.activity.akun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.eidith.studiochendraapp.R;

public class AkunActivity extends AppCompatActivity {

    private TextView tvAkunUsername, tvAkunPasword, tvAkunNama, tvAkunEmail, tvAkunNoHp;
    private Button btnAkunLogout;

    private String namaAkunUser;
    private String emailAkunUser;
    private String noHpAkunUser;
    private String usernameAkunUser;
    private String passwordAkunUser;
    private int accessCodeAkunUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        setTitle("Akun");

        tvAkunUsername = findViewById(R.id.tvAkunUsername);
        tvAkunPasword = findViewById(R.id.tvAkunPasword);
        tvAkunNama = findViewById(R.id.tvAkunNama);
        tvAkunEmail = findViewById(R.id.tvAkunEmail);
        tvAkunNoHp = findViewById(R.id.tvAkunNoHp);
        btnAkunLogout = findViewById(R.id.btnAkunLogout);

        namaAkunUser = getIntent().getExtras().getString("Nama User");
        emailAkunUser = getIntent().getExtras().getString("Email User");
        noHpAkunUser = getIntent().getExtras().getString("NoHp User");
        usernameAkunUser = getIntent().getExtras().getString("Username User");
        passwordAkunUser = getIntent().getExtras().getString("Password User");
        accessCodeAkunUser = getIntent().getExtras().getInt("Access Code");

        tvAkunUsername.setText(usernameAkunUser);
        tvAkunPasword.setText(passwordAkunUser);
        tvAkunNama.setText(namaAkunUser);
        tvAkunEmail.setText(emailAkunUser);
        tvAkunNoHp.setText(noHpAkunUser);




    }
}