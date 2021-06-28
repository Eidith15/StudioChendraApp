package com.eidith.studiochendraapp.activity.akun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.model.UserModel;
import com.eidith.studiochendraapp.storage.SharedPrefManager;

public class AkunActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshAkun;
    private TextView tvAkunUsername, tvAkunPasword, tvAkunNama, tvAkunEmail, tvAkunNoHp;
    private ProgressBar pbarAkun;

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
        Button btnAkunLogout = findViewById(R.id.btnAkunLogout);
        refreshAkun = findViewById(R.id.refreshAkun);
        pbarAkun = findViewById(R.id.pbarAkun);

        UserModel userModel = SharedPrefManager.getInstance(this).getUserData();

        tvAkunUsername.setText(userModel.getUsername_user());
        tvAkunPasword.setText(userModel.getUsername_user());
        tvAkunNama.setText(userModel.getNama_user());
        tvAkunEmail.setText(userModel.getEmail_user());
        tvAkunNoHp.setText(userModel.getNo_handphone_user());

        btnAkunLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AkunActivity.this);
            builder.setTitle("Akun");
            builder.setMessage("Yakin ingin logout?");
            builder.setIcon(R.drawable.ic_alert);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                dialog.dismiss();
                SharedPrefManager.getInstance(AkunActivity.this).clear();
                Intent intent = new Intent(AkunActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        refreshAkun.setOnRefreshListener(() -> {

            refreshAkun.setRefreshing(true);
            pbarAkun.setVisibility(View.VISIBLE);

            UserModel userModel1 = SharedPrefManager.getInstance(AkunActivity.this).getUserData();

            tvAkunUsername.setText(userModel1.getUsername_user());
            tvAkunPasword.setText(userModel1.getUsername_user());
            tvAkunNama.setText(userModel1.getNama_user());
            tvAkunEmail.setText(userModel1.getEmail_user());
            tvAkunNoHp.setText(userModel1.getNo_handphone_user());

            pbarAkun.setVisibility(View.GONE);
            refreshAkun.setRefreshing(false);

        });

    }

}