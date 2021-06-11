package com.eidith.studiochendraapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawableLayoutMain;
    private SwipeRefreshLayout refreshMain;
    private RecyclerView rvMain;
    private ProgressBar pbarMain;
    private NavigationView navViewMain;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawableLayoutMain = findViewById(R.id.drawableLayoutMain);
//        refreshMain = findViewById(R.id.refreshMain);
//        rvMain = findViewById(R.id.rvMain);
//        pbarMain = findViewById(R.id.pbarMain);
        navViewMain = findViewById(R.id.navViewMain);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawableLayoutMain, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewMain.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.akunUser:
                        Toast.makeText(MainActivity.this, "Ini ke Akun", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.workshop:
                        Intent intentWorkshop = new Intent(MainActivity.this, TambahWorkshopActivity.class);
                        startActivity(intentWorkshop);
                        return true;

                    case R.id.artikelFotografi:
                        Intent intentArtikel = new Intent(MainActivity.this, TambahArtikelActivity.class);
                        startActivity(intentArtikel);
                        return true;

                    case R.id.layananJasaFotografi:
                        Toast.makeText(MainActivity.this, "Ini Ke Tambah Layanan", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.fotoPortofolio:
                        Toast.makeText(MainActivity.this, "Ini Ke Tambah Foto", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(MainActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(MainActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logout:
                        Toast.makeText(MainActivity.this, "Ini Ke Logout", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawableLayoutMain.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //On item selected Menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}