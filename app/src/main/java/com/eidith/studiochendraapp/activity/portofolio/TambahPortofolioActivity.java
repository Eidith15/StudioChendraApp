package com.eidith.studiochendraapp.activity.portofolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.eidith.studiochendraapp.R;
import com.eidith.studiochendraapp.api.APIRequestData;
import com.eidith.studiochendraapp.api.RetrofitServer;
import com.eidith.studiochendraapp.model.PortofolioModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPortofolioActivity extends AppCompatActivity {

    private static final int IMG_REQUEST_CODE = 0;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private EditText etJudulPortofolio, etDeskripsiFotoPortofolio;
    private Button btnSelectImagePortofolio, btnTambahFotoPortofolio;

    private ProgressDialog progressDialog;

    private String judul_portofolio, deskripsi_foto, gambar_foto;
    private String mediaPathImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_portofolio);

        setTitle("Tambah Foto Portofolio");

        verifyStoragePermissions(TambahPortofolioActivity.this);

        //Assign variable
        etJudulPortofolio = findViewById(R.id.etJudulPortofolio);
        etDeskripsiFotoPortofolio = findViewById(R.id.etDeskripsiFotoPortofolio);
        btnSelectImagePortofolio = findViewById(R.id.btnSelectImagePortofolio);
        btnTambahFotoPortofolio = findViewById(R.id.btnTambahFotoPortofolio);

        //Set Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);

        btnSelectImagePortofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Select Image
                Intent galleryIntent = new Intent (Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, IMG_REQUEST_CODE);
            }
        });

        btnTambahFotoPortofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Data input
                judul_portofolio = etJudulPortofolio.getText().toString();
                deskripsi_foto = etDeskripsiFotoPortofolio.getText().toString();
                gambar_foto = btnSelectImagePortofolio.getText().toString();

                //Checking Null
                if (judul_portofolio.trim().equals("")){
                    etJudulPortofolio.setError("Harap Masukan Judul");
                } else if (deskripsi_foto.trim().equals("")){
                    etDeskripsiFotoPortofolio.setError("Harap Masukan Deskripsi");
                } else if (gambar_foto.trim().equals("Tambah Gambar")){
                    btnSelectImagePortofolio.setError("Harap Pilih Gambar");
                }  else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TambahPortofolioActivity.this);
                    builder.setTitle("Tambah Data Foto Portofolio");
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
                btnSelectImagePortofolio.setText(mediaPathImage);
                btnSelectImagePortofolio.setError(null);
                cursor.close();

                //Select Video from cursor and get path
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

        //Set to Parser Json using Request Body and Multipart
        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), etJudulPortofolio.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), etDeskripsiFotoPortofolio.getText().toString());
        RequestBody gambar = RequestBody.create(MediaType.parse("image/*"), fileImage);
        MultipartBody.Part imagepart = MultipartBody.Part.createFormData("gambar_foto", fileImage.getName(), gambar);

        //Execute createData to json method
        APIRequestData ardData = RetrofitServer.connectRetrofit().create(APIRequestData.class);
        Call<PortofolioModel> createData = ardData.CreateDataPortofolio(judul, deskripsi, imagepart);

        createData.enqueue(new Callback<PortofolioModel>() {
            @Override
            public void onResponse(Call<PortofolioModel> call, Response<PortofolioModel> response) {
                int kode = response.body().getCode();
                String pesan = response.body().getMessage();
                Toast.makeText(TambahPortofolioActivity.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<PortofolioModel> call, Throwable t) {
                Toast.makeText(TambahPortofolioActivity.this, "Gagal Menghubungi Server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
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

}