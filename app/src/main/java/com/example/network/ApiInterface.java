package com.example.network;


import com.example.model.MachineTest2Pojo;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("posts")
    Call<MachineTest2Pojo> getSignInResult2(

           );

}