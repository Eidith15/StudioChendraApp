package com.eidith.studiochendraapp.api;

import com.eidith.studiochendraapp.model.ArtikelModel;
import com.eidith.studiochendraapp.model.WorkshopModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {

    //Get data workshop
    @GET("get_workshop.php")
    Call<WorkshopModel> RetrieveDataWorkshop();

    //Post data workshop
    @Multipart
    @POST("post_workshop.php")
    Call<WorkshopModel> CreateDataWorkshop(@Part("judul_workshop") RequestBody judul_workshop,
                                           @Part("deskripsi_workshop") RequestBody deskripsi_workshop,
                                           @Part("tanggal_workshop") RequestBody tanggal_workshop,
                                           @Part MultipartBody.Part gambar_workshop,
                                           @Part MultipartBody.Part video_workshop);

    //Get data artikel
    @GET("get_artikel.php")
    Call<ArtikelModel> RetrieveDataArtikel();

    //Post data workshop
    @Multipart
    @POST("post_artikel.php")
    Call<ArtikelModel> CreateDataArtikel(@Part("judul_artikel") RequestBody judul_artikel,
                                           @Part("deskripsi_artikel") RequestBody deskripsi_artikel,
                                           @Part("tanggal_artikel") RequestBody tanggal_artikel,
                                           @Part MultipartBody.Part gambar_artikel,
                                           @Part MultipartBody.Part video_artikel);


}
