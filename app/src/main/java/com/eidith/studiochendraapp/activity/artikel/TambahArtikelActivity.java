package com.eidith.studiochendraapp.activity.artikel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.ArtikelModel;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahArtikelActivity extends AppCompatActivity {

    private static final int IMG_REQUEST_CODE = 0;
    private static final int VID_REQUEST_CODE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText etJudulArtikel, etDeskripsiArtikel;
    private Button btnTambahArtikel, btnDatePickerArtikel, btnSelectVideoArtikel, btnSelectImageArtikel;

    private ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String judul_artikel, deskripsi_artikel, gambar_artikel, video_artikel, tanggal_artikel;
    private String mediaPathImage;
    private String mediaPathVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_artikel);

        setTitle("Tambah Data Artikel");

        verifyStoragePermissions(TambahArtikelActivity.this);

        // Assign Variable
        etJudulArtikel = findViewById(R.id.etJudulArtikel);
        etDeskripsiArtikel = findViewById(R.id.etDeskripsiArtikel);
        btnDatePickerArtikel = findViewById(R.id.btnDatePickerArtikel);
        btnSelectImageArtikel = findViewById(R.id.btnSelectImageArtikel);
        btnSelectVideoArtikel = findViewById(R.id.btnSelectVideoArtikel);
        btnTambahArtikel = findViewById(R.id.btnTambahArtikel);

        //Set Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int selectedmonth = month+1;
                String date = year+"-"+selectedmonth+"-"+dayOfMonth;
                btnDatePickerArtikel.setText(date);
                btnDatePickerArtikel.setError(null);
            }
        };

        btnDatePickerArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });



        btnSelectImageArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
            }
        });

        btnSelectVideoArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Video
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, VID_REQUEST_CODE);
            }
        });

        btnTambahArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data input
                judul_artikel = etJudulArtikel.getText().toString();
                deskripsi_artikel = etDeskripsiArtikel.getText().toString();
                gambar_artikel = btnSelectImageArtikel.getText().toString();
                video_artikel = btnSelectVideoArtikel.getText().toString();
                tanggal_artikel = btnDatePickerArtikel.getText().toString();

                //Checking Null
                if (judul_artikel.trim().equals("")){
                    etJudulArtikel.setError("Harap Masukan Judul");
                } else if (deskripsi_artikel.trim().equals("")){
                    etDeskripsiArtikel.setError("Harap Masukan Deskripsi");
                } else if (tanggal_artikel.trim().equals("Pilih Tanggal")){
                    btnDatePickerArtikel.setError("Harap Masukan Tanggal");
                } else if (gambar_artikel.trim().equals("Tambah Gambar")){
                    btnSelectImageArtikel.setError("Harap Pilih Gambar");
                } else if (video_artikel.trim().equals("Tambah Video")){
                    btnSelectVideoArtikel.setError("Harap Pilih Video");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TambahArtikelActivity.this);
                    builder.setTitle("Tambah Data Artikel");
                    builder.setMessage("Yakin Menambah data?");
                    builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            UploadData();
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
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //Select Image from cursor and get path
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPathImage = cursor.getString(columnIndex);
                btnSelectImageArtikel.setText(mediaPathImage);
                btnSelectImageArtikel.setError(null);
                cursor.close();

                //Select Video from cursor and get path
            } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPathVideo = cursor.getString(columnIndex);
                btnSelectVideoArtikel.setText(mediaPathVideo);
                btnSelectVideoArtikel.setError(null);
                cursor.close();
            } else {
                Toast.makeText(this, "Harap pilih Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ada Kesalahan", Toast.LENGTH_LONG).show();
        }
    }

    private void UploadData(){
        progressDialog.show();

        //Set path image to file type
        File fileImage = new File(mediaPathImage);
        File fileVideo = new File(mediaPathVideo);

        //Set to Parser Json using Request Body and Multipart
        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), etJudulArtikel.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiArtikel.getText().toString());
        RequestBody tanggal = RequestBody.create(MediaType.parse("text/plain"), btnDatePickerArtikel.getText().toString());
        RequestBody gambar = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("gambar_artikel", fileImage.getName(), gambar);
        RequestBody video = RequestBody.create(MediaType.parse("video/*"), fileVideo);
        MultipartBody.Part videopart = MultipartBody.Part.createFormData("video_artikel", fileVideo.getName(), video);

        //Execute createData to json method
        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);
        Call<ArtikelModel> createData = ardData.CreateDataArtikel(judul, deskripsi, tanggal, imagepart, videopart);

        createData.enqueue(new Callback<ArtikelModel>() {
            @Override
            public void onResponse(Call<ArtikelModel> call, Response<ArtikelModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(TambahArtikelActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<ArtikelModel> call, Throwable t) {
                Toast.makeText(TambahArtikelActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Verify storage permission from user
    private void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TambahArtikelActivity.this,
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}