package com.eidith.studiochendraapp.activity.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.RecyclerViewAdapterRegistrasiOrder;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.RegistrasiOrderModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRegistrasiOrderActivity extends AppCompatActivity implements RecyclerViewAdapterRegistrasiOrder.OnItemClickListener {

    private static final String LOG_TAG = "ListRegistrasiOrderActivity";

    private SwipeRefreshLayout refreshRegistrasiOrder;
    private RecyclerView rvListRegistrasi;
    private ProgressBar pbarRegistrasi;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<RegistrasiOrderModel> listRegistrasiOrder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registrasi_order);

        setTitle("List Registrasi Order");

        refreshRegistrasiOrder = findViewById(R.id.refreshRegistrasiOrder);
        rvListRegistrasi = findViewById(R.id.rvListRegistrasi);
        pbarRegistrasi = findViewById(R.id.pbarRegistrasi);

        //Set Recycler view Layout
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListRegistrasi.setLayoutManager(layoutManager);

        //Set refresh swipe to get data
        refreshRegistrasiOrder.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onRefresh() {
                refreshRegistrasiOrder.setRefreshing(true);
                Log.v(LOG_TAG, "Start Retrieve Data");
                retrieveData();
                Log.v(LOG_TAG, "Finish Retrieve Data");
                refreshRegistrasiOrder.setRefreshing(false);
            }
        });
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LOG_TAG, "Start Retrieve Data");
        retrieveData();
        Log.v(LOG_TAG, "Finish Retrieve Data");
    }

    public void retrieveData() {
        pbarRegistrasi.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data with Gson
        APIRequestData ardData = APIClient.connectRetrofitGson().create(APIRequestData.class);
        Call<RegistrasiOrderModel> tampilData = ardData.RetrieveDataRegistrasiOrder();

        //Conncet to server to parse Json and get data with Moshi
//        APIRequestData ardData = APIClient.connectRetrofitMoshi().create(APIRequestData.class);
//        Call<RegistrasiOrderModel> tampilData = ardData.RetrieveDataRegistrasiOrder();

        tampilData.enqueue(new Callback<RegistrasiOrderModel>() {
            @Override
            public void onResponse(Call<RegistrasiOrderModel> call, Response<RegistrasiOrderModel> response) {
                //Set data to Adapter
                listRegistrasiOrder = response.body().getData_registrasi();

                adapter = new RecyclerViewAdapterRegistrasiOrder(ListRegistrasiOrderActivity.this, listRegistrasiOrder, ListRegistrasiOrderActivity.this::OnitemClickRegistrasiOrder);
                rvListRegistrasi.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                pbarRegistrasi.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegistrasiOrderModel> call, Throwable t) {
                Toast.makeText(ListRegistrasiOrderActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();

                pbarRegistrasi.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void OnitemClickRegistrasiOrder(int position) {
        //Send data to DetailWorkshop Activity
        Intent intent = new Intent(ListRegistrasiOrderActivity.this, DetailRegistrasiOrder.class);
        intent.putExtra("Id Registrasi", listRegistrasiOrder.get(position).getId_registrasi());
        intent.putExtra("Tanggal Registrasi", listRegistrasiOrder.get(position).getTanggal_registrasi());
        intent.putExtra("Id Layanan", listRegistrasiOrder.get(position).getId_layanan());
        intent.putExtra("Judul Layanan", listRegistrasiOrder.get(position).getJudul_layanan());
        intent.putExtra("Gambar Layanan", listRegistrasiOrder.get(position).getGambar_layanan());
        intent.putExtra("Tanggal Layanan", listRegistrasiOrder.get(position).getTanggal_layanan());
        intent.putExtra("Id User", listRegistrasiOrder.get(position).getId_user());
        intent.putExtra("Nama User", listRegistrasiOrder.get(position).getNama_user());
        intent.putExtra("Email User", listRegistrasiOrder.get(position).getEmail_user());
        intent.putExtra("No Hp", listRegistrasiOrder.get(position).getNo_handphone_user());
        startActivity(intent);
    }

}