package com.eidith.studiochendraapp.activity.workshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.akun.AkunActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.adapter.WorkshopAdapter;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.model.WorkshopModel;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkshopActivity extends AppCompatActivity implements WorkshopAdapter.OnItemClickListener {

    private RecyclerView rvWorkshop;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<WorkshopModel> listWorkshop = new ArrayList<>();
    private SwipeRefreshLayout refreshWorkshop;
    private ProgressBar pbarWorkshop;
    private DrawerLayout drawerLayoutWorkshop;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navViewWorkshop;

    private String namaUser;
    private String emailUser;
    private String noHpUser;
    private String usernameUser;
    private String passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop);

        setTitle("Workshop");

        //Assign Variable
        rvWorkshop = findViewById(R.id.rvWorkshop);
        refreshWorkshop = findViewById(R.id.refreshWorkshop);
        pbarWorkshop = findViewById(R.id.pbarWorkshop);
        navViewWorkshop = findViewById(R.id.navViewWorkshop);
        drawerLayoutWorkshop = findViewById(R.id.drawableLayoutWorkshop);

        namaUser = getIntent().getExtras().getString("Nama User");
        emailUser = getIntent().getExtras().getString("Email User");
        noHpUser = getIntent().getExtras().getString("NoHp User");
        usernameUser = getIntent().getExtras().getString("Username User");
        passwordUser = getIntent().getExtras().getString("Password User");

        //Set Recycler view Layout
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWorkshop.setLayoutManager(layoutManager);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutWorkshop, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewWorkshop.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeUser:
                        onBackPressed();
                        return true;

                    case R.id.akunUser:
                        Intent intentAkun = new Intent(WorkshopActivity.this, AkunActivity.class);
                        intentAkun.putExtra("Nama User", namaUser);
                        intentAkun.putExtra("Email User", emailUser);
                        intentAkun.putExtra("NoHp User", noHpUser);
                        intentAkun.putExtra("Username User", usernameUser);
                        intentAkun.putExtra("Password User", passwordUser);
                        startActivity(intentAkun);
                        finish();
                        return true;

                    case R.id.workshop:
                        drawerLayoutWorkshop.closeDrawers();
                        return true;

                    case R.id.artikelFotografi:
                        Intent intentArtikel = new Intent(WorkshopActivity.this, ArtikelActivity.class);
                        intentArtikel.putExtra("Nama User", namaUser);
                        intentArtikel.putExtra("Email User", emailUser);
                        intentArtikel.putExtra("NoHp User", noHpUser);
                        intentArtikel.putExtra("Username User", usernameUser);
                        intentArtikel.putExtra("Password User", passwordUser);
                        startActivity(intentArtikel);
                        finish();
                        return true;

                    case R.id.layananJasaFotografi:
                        Intent intentLayanan = new Intent(WorkshopActivity.this, LayananActivity.class);
                        intentLayanan.putExtra("Nama User", namaUser);
                        intentLayanan.putExtra("Email User", emailUser);
                        intentLayanan.putExtra("NoHp User", noHpUser);
                        intentLayanan.putExtra("Username User", usernameUser);
                        intentLayanan.putExtra("Password User", passwordUser);
                        startActivity(intentLayanan);
                        finish();
                        return true;

                    case R.id.fotoPortofolio:
                        Intent intentPortofolio = new Intent(WorkshopActivity.this, PortofolioActivity.class);
                        intentPortofolio.putExtra("Nama User", namaUser);
                        intentPortofolio.putExtra("Email User", emailUser);
                        intentPortofolio.putExtra("NoHp User", noHpUser);
                        intentPortofolio.putExtra("Username User", usernameUser);
                        intentPortofolio.putExtra("Password User", passwordUser);
                        startActivity(intentPortofolio);
                        finish();
                        return true;

                    case R.id.registrasiOrder:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Registrasi Order", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.customerService:
                        Toast.makeText(WorkshopActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutUser:
                        Intent intentLogin = new Intent(WorkshopActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        finish();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawerLayoutWorkshop.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set refresh swipe to get data
        refreshWorkshop.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshWorkshop.setRefreshing(true);
                retrieveData();
                refreshWorkshop.setRefreshing(false);
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
        drawerLayoutWorkshop.closeDrawers();
    }

    public void retrieveData(){
        pbarWorkshop.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<WorkshopModel> tampilData = ardData.RetrieveDataWorkshop();

        tampilData.enqueue(new Callback<WorkshopModel>() {
            @Override
            public void onResponse(Call<WorkshopModel> call, Response<WorkshopModel> response) {
                //Set data to Adapter
                listWorkshop = response.body().getData_workshop();

                adapter = new WorkshopAdapter(WorkshopActivity.this, listWorkshop, WorkshopActivity.this::OnItemClickWorkshop);
                rvWorkshop.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarWorkshop.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WorkshopModel> call, Throwable t) {
                Toast.makeText(WorkshopActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarWorkshop.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickWorkshop(int position) {
        //Send data to DetailWorkshop Activity
        Intent intent = new Intent(WorkshopActivity.this, DetailWorkshopActivity.class);
        intent.putExtra("Id Workshop", listWorkshop.get(position).getId_workshop());
        intent.putExtra("Judul Workshop", listWorkshop.get(position).getJudul_workshop());
        intent.putExtra("Deskripsi Workshop", listWorkshop.get(position).getDeskripsi_workshop());
        intent.putExtra("Gambar Workshop", listWorkshop.get(position).getGambar_workshop());
        intent.putExtra("Video Workshop", listWorkshop.get(position).getVideo_workshop());
        intent.putExtra("Tanggal Workshop", listWorkshop.get(position).getTanggal_workshop());
        startActivity(intent);
    }

}
