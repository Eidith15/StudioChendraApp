package com.eidith.studiochendraapp.api;

import com.eidith.studiochendraapp.model.WorkshopModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestData {

    @GET("workshop_api.php")
    Call<WorkshopModel> ardRetrieveData();

}
