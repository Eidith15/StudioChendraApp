package com.eidith.studiochendraapp.activity.akun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.artikel.TambahArtikelActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.layanan.TambahLayananActivity;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.activity.order.ListRegistrasiOrderActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.activity.portofolio.TambahPortofolioActivity;
import com.eidith.studiochendraapp.activity.workshop.TambahWorkshopActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.model.UserModel;
import com.eidith.studiochendraapp.storage.SharedPrefManager;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class AkunActivity extends AppCompatActivity {

    private TextView tvAkunUsername, tvAkunPasword, tvAkunNama, tvAkunEmail, tvAkunNoHp;
    private Button btnAkunLogout;

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

        UserModel userModel = SharedPrefManager.getInstance(this).getUserData();

        tvAkunUsername.setText(userModel.getUsername_user());
        tvAkunPasword.setText(userModel.getUsername_user());
        tvAkunNama.setText(userModel.getNama_user());
        tvAkunEmail.setText(userModel.getEmail_user());
        tvAkunNoHp.setText(userModel.getNo_handphone_user());

        //Set Menu on Action bar

        btnAkunLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(AkunActivity.this).clear();
                Intent intent = new Intent(AkunActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}