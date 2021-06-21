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
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class AkunActivity extends AppCompatActivity {

    private TextView tvAkunUsername, tvAkunPasword, tvAkunNama, tvAkunEmail, tvAkunNoHp;
    private DrawerLayout drawerLayoutAkun;
    private Button btnAkunLogout;
    private NavigationView navViewAkun;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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
        drawerLayoutAkun = findViewById(R.id.drawableLayoutAkun);
        navViewAkun = findViewById(R.id.navViewAkun);


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

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutAkun, R.string.open, R.string.close);

        if (accessCodeAkunUser == 1){
            navigationViewAdmin();
        } else {
            navigationViewUser();
        }

        //Set menu
        drawerLayoutAkun.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAkunLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(AkunActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        });
    }

    //On item selected Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigationViewUser(){
        navViewAkun.getMenu().clear();
        navViewAkun.inflateMenu(R.menu.navigation_menu_user);
        navViewAkun.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeUser:
                        onBackPressed();
                        return true;

                    case R.id.akunUser:
                        drawerLayoutAkun.closeDrawers();
                        return true;

                    case R.id.workshop:
                        Intent intentWorkshop = new Intent(AkunActivity.this, WorkshopActivity.class);
                        intentWorkshop.putExtra("Nama User", namaAkunUser);
                        intentWorkshop.putExtra("Email User", emailAkunUser);
                        intentWorkshop.putExtra("NoHp User", noHpAkunUser);
                        intentWorkshop.putExtra("Username User", usernameAkunUser);
                        intentWorkshop.putExtra("Password User", passwordAkunUser);
                        startActivity(intentWorkshop);
                        finish();
                        return true;

                    case R.id.artikelFotografi:
                        Intent intentArtikel = new Intent(AkunActivity.this, ArtikelActivity.class);
                        intentArtikel.putExtra("Nama User", namaAkunUser);
                        intentArtikel.putExtra("Email User", emailAkunUser);
                        intentArtikel.putExtra("NoHp User", noHpAkunUser);
                        intentArtikel.putExtra("Username User", usernameAkunUser);
                        intentArtikel.putExtra("Password User", passwordAkunUser);
                        startActivity(intentArtikel);
                        finish();
                        return true;

                    case R.id.layananJasaFotografi:
                        Intent intentLayanan = new Intent(AkunActivity.this, LayananActivity.class);
                        intentLayanan.putExtra("Nama User", namaAkunUser);
                        intentLayanan.putExtra("Email User", emailAkunUser);
                        intentLayanan.putExtra("NoHp User", noHpAkunUser);
                        intentLayanan.putExtra("Username User", usernameAkunUser);
                        intentLayanan.putExtra("Password User", passwordAkunUser);
                        startActivity(intentLayanan);
                        finish();
                        return true;

                    case R.id.fotoPortofolio:
                        Intent intentPortofolio = new Intent(AkunActivity.this, PortofolioActivity.class);
                        intentPortofolio.putExtra("Nama User", namaAkunUser);
                        intentPortofolio.putExtra("Email User", emailAkunUser);
                        intentPortofolio.putExtra("NoHp User", noHpAkunUser);
                        intentPortofolio.putExtra("Username User", usernameAkunUser);
                        intentPortofolio.putExtra("Password User", passwordAkunUser);
                        startActivity(intentPortofolio);
                        finish();
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(AkunActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(AkunActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutUser:
                        Intent intentLogin = new Intent(AkunActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void navigationViewAdmin(){
        navViewAkun.getMenu().clear();
        navViewAkun.inflateMenu(R.menu.navigation_menu_admin);
        navViewAkun.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeAdmin:
                        onBackPressed();
                        return true;

                    case R.id.akunAdmin:
                        drawerLayoutAkun.closeDrawers();
                        return true;

                    case R.id.tambahWorkshop:
                        Intent intentWorkshop = new Intent(AkunActivity.this, TambahWorkshopActivity.class);
                        startActivity(intentWorkshop);
                        finish();
                        return true;

                    case R.id.tambahArtikelFotografi:
                        Intent intentArtikel = new Intent(AkunActivity.this, TambahArtikelActivity.class);
                        startActivity(intentArtikel);
                        finish();
                        return true;

                    case R.id.tambahLayananJasaFotografi:
                        Intent intentLayanan = new Intent(AkunActivity.this, TambahLayananActivity.class);
                        startActivity(intentLayanan);
                        finish();
                        return true;

                    case R.id.tambahFotoPortofolio:
                        Intent intentPortofolio = new Intent(AkunActivity.this, TambahPortofolioActivity.class);
                        startActivity(intentPortofolio);
                        finish();
                        return true;

                    case R.id.registrasiOrderAdmin:
                        Intent intentRegistrasiOrder = new Intent(AkunActivity.this, ListRegistrasiOrderActivity.class);
                        intentRegistrasiOrder.putExtra("Nama User", namaAkunUser);
                        intentRegistrasiOrder.putExtra("Email User", emailAkunUser);
                        intentRegistrasiOrder.putExtra("NoHp User", noHpAkunUser);
                        intentRegistrasiOrder.putExtra("Username User", usernameAkunUser);
                        intentRegistrasiOrder.putExtra("Password User", passwordAkunUser);
                        intentRegistrasiOrder.putExtra("Access Code", accessCodeAkunUser);
                        startActivity(intentRegistrasiOrder);
                        finish();
                        return true;

                    case R.id.respondCustomer:
                        Toast.makeText(AkunActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutAdmin:
                        Intent intentLogin = new Intent(AkunActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }

}