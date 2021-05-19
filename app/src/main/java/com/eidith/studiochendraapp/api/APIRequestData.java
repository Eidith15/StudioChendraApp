package com.eidith.studiochendraapp.api;

import com.eidith.studiochendraapp.model.WorkshopModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestData {

    @GET("get_workshop.php")
    Call<WorkshopModel> ardRetrieveData();

}
