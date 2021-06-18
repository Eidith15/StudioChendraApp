package com.eidith.studiochendraapp.activity.portofolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.akun.AkunActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.adapter.PortofolioAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.PortofolioModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PortofolioActivity extends AppCompatActivity implements PortofolioAdapter.OnItemClickListener {

    private DrawerLayout drawableLayoutPortofolio;
    private SwipeRefreshLayout refreshPortofolio;
    private RecyclerView rvPortofolio;
    private ProgressBar pbarPotrofolio;
    private NavigationView navViewPortofolio;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<PortofolioModel> listPortofolio = new ArrayList<>();
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portofolio);

        setTitle("Portofolio Gallery");

        //Assign variable
        drawableLayoutPortofolio = findViewById(R.id.drawableLayoutPortofolio);
        refreshPortofolio = findViewById(R.id.refreshPortofolio);
        rvPortofolio = findViewById(R.id.rvPortofolio);
        pbarPotrofolio = findViewById(R.id.pbarPotrofolio);
        navViewPortofolio = findViewById(R.id.navViewPortofolio);

        layoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        rvPortofolio.setLayoutManager(layoutManager);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawableLayoutPortofolio, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewPortofolio.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeUser:
                        onBackPressed();
                        return true;

                    case R.id.akunUser:
                        Intent intentAkun = new Intent(PortofolioActivity.this, AkunActivity.class);
                        startActivity(intentAkun);
                        finish();
                        return true;

                    case R.id.workshop:
                        Intent intentWorkshop = new Intent(PortofolioActivity.this, WorkshopActivity.class);
                        startActivity(intentWorkshop);
                        finish();
                        return true;

                    case R.id.artikelFotografi:
                        Intent intentArtikel = new Intent(PortofolioActivity.this, ArtikelActivity.class);
                        startActivity(intentArtikel);
                        finish();
                        return true;

                    case R.id.layananJasaFotografi:
                        Intent intentLayanan = new Intent(PortofolioActivity.this, LayananActivity.class);
                        startActivity(intentLayanan);
                        finish();
                        return true;

                    case R.id.fotoPortofolio:
                        drawableLayoutPortofolio.closeDrawers();
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(PortofolioActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(PortofolioActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutUser:
                        Toast.makeText(PortofolioActivity.this, "Ini Ke Logout", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawableLayoutPortofolio.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set refresh swipe to get data
        refreshPortofolio.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPortofolio.setRefreshing(true);
                retrieveData();
                refreshPortofolio.setRefreshing(false);
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

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
        drawableLayoutPortofolio.closeDrawers();
    }

    public void retrieveData(){
        pbarPotrofolio.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<PortofolioModel> tampilData = ardData.RetrieveDataPortofolio();

        tampilData.enqueue(new Callback<PortofolioModel>() {
            @Override
            public void onResponse(Call<PortofolioModel> call, Response<PortofolioModel> response) {
                //Set data to Adapter
                listPortofolio = response.body().getData_portofolio();

                adapter = new PortofolioAdapter(PortofolioActivity.this, listPortofolio, PortofolioActivity.this::OnItemClickPortofolio);
                rvPortofolio.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarPotrofolio.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PortofolioModel> call, Throwable t) {
                Toast.makeText(PortofolioActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarPotrofolio.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickPortofolio(int position) {
        //Send data to DetailArtikel Activity
        Intent intent = new Intent(this, DetailPortofolioActivity.class);
        intent.putExtra("Id Foto", listPortofolio.get(position).getId_portofolio());
        intent.putExtra("Judul Foto", listPortofolio.get(position).getJudul_portofolio());
        intent.putExtra("Deskripsi Foto", listPortofolio.get(position).getDeskripsi_foto());
        intent.putExtra("Gambar Foto", listPortofolio.get(position).getGambar_foto());
        startActivity(intent);
    }
}