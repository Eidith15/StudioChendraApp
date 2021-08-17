package com.eidith.studiochendraapp.activity.order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.adapter.SpinnerAdapterLayanan;
import com.eidith.studiochendraapp.api.APIClient;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.model.LayananModel;
import com.eidith.studiochendraapp.model.RegistrasiOrderModel;
import com.eidith.studiochendraapp.model.UserModel;
import com.eidith.studiochendraapp.storage.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahRegistrasiOrderActivity extends AppCompatActivity {

    private static final String LOG_TAG = "TambahRegistrasiOrderActivity";


    List<LayananModel> listLayanan = new ArrayList<>();
    int idLayanan;
    int idUser;
    String tanggalRegistrasi;
    private Spinner sDropdownListLayanan;
    private TextView tvTambahRegistrasiIdUser,
            tvTambahNamaRegistrasi, tvTambahNoTelpRegistrasi, tvTambahTanggalRegistrasi, tvPilihLayanan;
    private Button btnTambahRegistrasiOrder;
    private ProgressBar pbarTambahRegistrasiOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_registrasi_order);

        setTitle("Registrasi Order");

        sDropdownListLayanan = findViewById(R.id.sDropdownListLayanan);
        tvTambahRegistrasiIdUser = findViewById(R.id.tvTambahRegistrasiIdUser);
        tvTambahTanggalRegistrasi = findViewById(R.id.tvTambahTanggalRegistrasi);
        tvTambahNoTelpRegistrasi = findViewById(R.id.tvTambahNoTelpRegistrasi);
        tvTambahNamaRegistrasi = findViewById(R.id.tvTambahNamaRegistrasi);
        tvPilihLayanan = findViewById(R.id.tvPilihLayanan);
        btnTambahRegistrasiOrder = findViewById(R.id.btnTambahRegistrasiOrder);
        pbarTambahRegistrasiOrder = findViewById(R.id.pbarTambahRegistrasiOrder);

        retrieveDataLayanan();

        UserModel userModel = SharedPrefManager.getInstance(this).getUserData();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tanggalRegistrasi = simpleDateFormat.format(calendar.getTime());

        tvTambahRegistrasiIdUser.setText(String.valueOf(userModel.getId_user()));
        tvTambahNamaRegistrasi.setText(userModel.getNama_user());
        tvTambahNoTelpRegistrasi.setText(userModel.getNo_handphone_user());
        tvTambahTanggalRegistrasi.setText(tanggalRegistrasi);

        idUser = userModel.getId_user();

        btnTambahRegistrasiOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahRegistrasiOrderActivity.this);
                builder.setTitle("Registrasi Order");
                builder.setMessage("Yakin ingin melakukan registrasi?");
                builder.setIcon(R.drawable.ic_alert);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Log.v(LOG_TAG, "Start Upload Data");
                        UploadData();
                        Log.v(LOG_TAG, "Finish Upload Data");
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void retrieveDataLayanan() {
        pbarTambahRegistrasiOrder.setVisibility(View.VISIBLE);

        //Conncet to server to parse Json and get data with gson
        APIRequestData ardData = APIClient.connectRetrofitGson().create(APIRequestData.class);
        Call<LayananModel> tampilData = ardData.RetrieveDataLayanan();

        //Conncet to server to parse Json and get data with moshi
//        APIRequestData ardData = APIClient.connectRetrofitMoshi().create(APIRequestData.class);
//        Call<LayananModel> tampilData = ardData.RetrieveDataLayanan();

        tampilData.enqueue(new Callback<LayananModel>() {
            @Override
            public void onResponse(Call<LayananModel> call, Response<LayananModel> response) {
                //Set data to Adapter
                listLayanan = response.body().getData_layanan();

                SpinnerAdapterLayanan adapter = new SpinnerAdapterLayanan(TambahRegistrasiOrderActivity.this, R.layout.item_layanan, listLayanan);
                sDropdownListLayanan.setAdapter(adapter);

                sDropdownListLayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idLayanan = listLayanan.get(position).getId_layanan();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(TambahRegistrasiOrderActivity.this, "Harap Pilih Layanan", Toast.LENGTH_SHORT).show();
                    }
                });

                pbarTambahRegistrasiOrder.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LayananModel> call, Throwable t) {
                Toast.makeText(TambahRegistrasiOrderActivity.this, "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                pbarTambahRegistrasiOrder.setVisibility(View.GONE);
            }
        });
    }

    public void UploadData() {
        pbarTambahRegistrasiOrder.setVisibility(View.VISIBLE);

        //Upload data with gson
//        APIRequestData ardData = APIClient.connectRetrofitGson().create(APIRequestData.class);
//        Call<RegistrasiOrderModel> createData = ardData.CreateDataRegistrasiOrder(idUser, idLayanan, tanggalRegistrasi);

        //Upload data with moshi
        APIRequestData ardData = APIClient.connectRetrofitMoshi().create(APIRequestData.class);
        Call<RegistrasiOrderModel> createData = ardData.CreateDataRegistrasiOrder(idUser, idLayanan, tanggalRegistrasi);

        createData.enqueue(new Callback<RegistrasiOrderModel>() {
            @Override
            public void onResponse(Call<RegistrasiOrderModel> call, Response<RegistrasiOrderModel> response) {
                String pesan = response.body().getMessage();
                Toast.makeText(TambahRegistrasiOrderActivity.this, pesan, Toast.LENGTH_SHORT).show();
                pbarTambahRegistrasiOrder.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RegistrasiOrderModel> call, Throwable t) {
                Toast.makeText(TambahRegistrasiOrderActivity.this, "Pesan : " + t, Toast.LENGTH_SHORT).show();
                pbarTambahRegistrasiOrder.setVisibility(View.GONE);
            }
        });

    }
}
