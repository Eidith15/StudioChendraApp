package com.eidith.studiochendraapp.api;

import com.eidith.studiochendraapp.model.WorkshopModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {

    //Get data Workshop
    @GET("get_workshop.php")
    Call<WorkshopModel> RetrieveData();

    //Post Data Workshop
    @Multipart
    @POST("post_workshop.php")
    Call<WorkshopModel> CreateData(@Part("judul_workshop") RequestBody judul_workshop,
                                   @Part("deskripsi_workshop") RequestBody deskripsi_workshop,
                                   @Part("tanggal_workshop") RequestBody tanggal_workshop,
                                   @Part MultipartBody.Part gambar_workshop,
                                   @Part MultipartBody.Part video_workshop);


}
