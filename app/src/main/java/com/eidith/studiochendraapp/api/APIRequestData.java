package com.eidith.studiochendraapp.api;

import com.eidith.studiochendraapp.model.ArtikelModel;
import com.eidith.studiochendraapp.model.LayananModel;
import com.eidith.studiochendraapp.model.PortofolioModel;
import com.eidith.studiochendraapp.model.RegistrasiOrderModel;
import com.eidith.studiochendraapp.model.UserModel;
import com.eidith.studiochendraapp.model.WorkshopModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {

    @FormUrlEncoded
    @POST("login_user.php")
    Call<UserModel> AuthUser(
            @Field("username_user") String usernameInput,
            @Field("password_user") String passwordInput
    );

    @FormUrlEncoded
    @POST("register_user.php")
    Call<UserModel> RegUser(
            @Field("nama_user") String nama_user,
            @Field("email_user") String email_user,
            @Field("no_handphone_user") String no_handphone_user,
            @Field("username_user") String username_user,
            @Field("password_user") String password_user
    );

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
                                           @Part MultipartBody.Part video_workshop
    );

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
                                           @Part MultipartBody.Part video_artikel
    );

    //Get data layanan
    @GET("get_layanan.php")
    Call<LayananModel> RetrieveDataLayanan();

    //Post data layanan
    @Multipart
    @POST("post_layanan.php")
    Call<LayananModel> CreateDataLayanan(@Part("judul_layanan") RequestBody judul_layanan,
                                         @Part("deskripsi_layanan") RequestBody deskripsi_layanan,
                                         @Part("tanggal_layanan") RequestBody tanggal_layanan,
                                         @Part MultipartBody.Part gambar_layanan,
                                         @Part MultipartBody.Part video_layanan
    );

    //Get data layanan
    @GET("get_portofolio.php")
    Call<PortofolioModel> RetrieveDataPortofolio();

    //Post data layanan
    @Multipart
    @POST("post_portofolio.php")
    Call<PortofolioModel> CreateDataPortofolio(@Part("judul_portofolio") RequestBody judu_portofolio,
                                         @Part("deskripsi_foto") RequestBody deskripsi_foto,
                                         @Part MultipartBody.Part gambar_foto
    );

    //Get data registrasi
    @GET("get_registrasi.php")
    Call<RegistrasiOrderModel> RetrieveDataRegistrasiOrder();


}
