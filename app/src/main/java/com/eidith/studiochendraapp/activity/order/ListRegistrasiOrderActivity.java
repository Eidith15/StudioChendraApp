package com.eidith.studiochendraapp.activity.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.activity.MainActivity;
import com.eidith.studiochendraapp.activity.akun.AkunActivity;
import com.eidith.studiochendraapp.activity.artikel.ArtikelActivity;
import com.eidith.studiochendraapp.activity.artikel.TambahArtikelActivity;
import com.eidith.studiochendraapp.activity.layanan.LayananActivity;
import com.eidith.studiochendraapp.activity.layanan.TambahLayananActivity;
import com.eidith.studiochendraapp.activity.login.LoginActivity;
import com.eidith.studiochendraapp.activity.portofolio.PortofolioActivity;
import com.eidith.studiochendraapp.activity.portofolio.TambahPortofolioActivity;
import com.eidith.studiochendraapp.activity.workshop.DetailWorkshopActivity;
import com.eidith.studiochendraapp.activity.workshop.TambahWorkshopActivity;
import com.eidith.studiochendraapp.activity.workshop.WorkshopActivity;
import com.eidith.studiochendraapp.adapter.RegistrasiOrderAdapter;
import com.eidith.studiochendraapp.adapter.WorkshopAdapter;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.RegistrasiOrderModel;
import com.eidith.studiochendraapp.model.WorkshopModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRegistrasiOrderActivity extends AppCompatActivity implements RegistrasiOrderAdapter.OnItemClickListener{

    private DrawerLayout drawableLayoutRegistrasi;
    private SwipeRefreshLayout refreshRegistrasiOrder;
    private RecyclerView rvListRegistrasi;
    private ProgressBar pbarRegistrasi;
    private NavigationView navViewRegistrasi;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private List<RegistrasiOrderModel> listRegistrasiOrder = new ArrayList<>();

    private String namaUser;
    private String emailUser;
    private String noHpUser;
    private String usernameUser;
    private String passwordUser;
    private int accessCodeAkunUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registrasi_order);

        setTitle("List Order");

        drawableLayoutRegistrasi = findViewById(R.id.drawableLayoutRegistrasi);
        refreshRegistrasiOrder = findViewById(R.id.refreshRegistrasiOrder);
        rvListRegistrasi = findViewById(R.id.rvListRegistrasi);
        pbarRegistrasi = findViewById(R.id.pbarRegistrasi);
        navViewRegistrasi = findViewById(R.id.navViewRegistrasi);


        namaUser = getIntent().getExtras().getString("Nama User");
        emailUser = getIntent().getExtras().getString("Email User");
        noHpUser = getIntent().getExtras().getString("NoHp User");
        usernameUser = getIntent().getExtras().getString("Username User");
        passwordUser = getIntent().getExtras().getString("Password User");
        accessCodeAkunUser = getIntent().getExtras().getInt("Access Code");

        //Set Recycler view Layout
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListRegistrasi.setLayoutManager(layoutManager);

        //Set Menu on Action bar
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawableLayoutRegistrasi, R.string.open, R.string.close);

        //Navigation view menu listener
        navViewRegistrasi.getMenu().clear();
        navViewRegistrasi.inflateMenu(R.menu.navigation_menu_admin);
        navViewRegistrasi.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeAdmin:
                        onBackPressed();
                        return true;

                    case R.id.akunAdmin:
                        Intent intentAkun = new Intent(ListRegistrasiOrderActivity.this, AkunActivity.class);
                        intentAkun.putExtra("Nama User", namaUser);
                        intentAkun.putExtra("Email User", emailUser);
                        intentAkun.putExtra("NoHp User", noHpUser);
                        intentAkun.putExtra("Username User", usernameUser);
                        intentAkun.putExtra("Password User", passwordUser);
                        intentAkun.putExtra("Access Code", accessCodeAkunUser);
                        startActivity(intentAkun);
                        finish();
                        return true;

                    case R.id.tambahWorkshop:
                        Intent intentWorkshop = new Intent(ListRegistrasiOrderActivity.this, TambahWorkshopActivity.class);
                        startActivity(intentWorkshop);
                        return true;

                    case R.id.tambahArtikelFotografi:
                        Intent intentArtikel = new Intent(ListRegistrasiOrderActivity.this, TambahArtikelActivity.class);
                        startActivity(intentArtikel);
                        return true;

                    case R.id.tambahLayananJasaFotografi:
                        Intent intentLayanan = new Intent(ListRegistrasiOrderActivity.this, TambahLayananActivity.class);
                        startActivity(intentLayanan);
                        return true;

                    case R.id.tambahFotoPortofolio:
                        Intent intentPortofolio = new Intent(ListRegistrasiOrderActivity.this, TambahPortofolioActivity.class);
                        startActivity(intentPortofolio);
                        return true;

                    case R.id.registrasiOrderAdmin:
                        drawableLayoutRegistrasi.closeDrawers();
                        return true;

                    case R.id.respondCustomer:
                        Toast.makeText(ListRegistrasiOrderActivity.this, "Ini Ke Respond Customer", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.logoutAdmin:
                        Intent intentLogin = new Intent(ListRegistrasiOrderActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        finish();
                        return true;

                }
                return false;
            }
        });

        //Set menu
        drawableLayoutRegistrasi.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set refresh swipe to get data
        refreshRegistrasiOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRegistrasiOrder.setRefreshing(true);
                retrieveData();
                refreshRegistrasiOrder.setRefreshing(false);
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
        drawableLayoutRegistrasi.closeDrawers();
    }

    public void retrieveData(){
        pbarRegistrasi.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data
        APIRequestData ardData = APIClient.connectRetrofit().create(APIRequestData.class);
        Call<RegistrasiOrderModel> tampilData = ardData.RetrieveDataRegistrasiOrder();

        tampilData.enqueue(new Callback<RegistrasiOrderModel>() {
            @Override
            public void onResponse(Call<RegistrasiOrderModel> call, Response<RegistrasiOrderModel> response) {
                //Set data to Adapter
                listRegistrasiOrder = response.body().getData_registrasi();

                adapter = new RegistrasiOrderAdapter(ListRegistrasiOrderActivity.this, listRegistrasiOrder, ListRegistrasiOrderActivity.this::OnItemClickWorkshop);
                rvListRegistrasi.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarRegistrasi.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegistrasiOrderModel> call, Throwable t) {
                Toast.makeText(ListRegistrasiOrderActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarRegistrasi.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnItemClickWorkshop(int position) {
        //Send data to DetailWorkshop Activity
        Intent intent = new Intent(ListRegistrasiOrderActivity.this, DetailRegistrasiOrder.class);
        intent.putExtra("Id Registrasi", listRegistrasiOrder.get(position).getId_registrasi());
        intent.putExtra("Tanggal Registrasi", listRegistrasiOrder.get(position).getTanggal_registrasi());
        intent.putExtra("Id Layanan", listRegistrasiOrder.get(position).getId_layanan());
        intent.putExtra("Judul Layanan", listRegistrasiOrder.get(position).getJudul_layanan());
        intent.putExtra("Gambar Layanan", listRegistrasiOrder.get(position).getGambar_layanan());
        intent.putExtra("Id User", listRegistrasiOrder.get(position).getId_user());
        intent.putExtra("Nama User", listRegistrasiOrder.get(position).getNama_user());
        intent.putExtra("Email User", listRegistrasiOrder.get(position).getEmail_user());
        intent.putExtra("No Hp", listRegistrasiOrder.get(position).getNo_handphone_user());
        startActivity(intent);
    }

}