package com.example.presenter;

import android.content.Context;
import android.util.Log;

import com.example.model.MachineTest2Pojo;
import com.example.network.ApiInterface;
import com.example.network.Apiclient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;

public class MachineTest2Presenter {
    MachineTest2PresenterView MachineTest2PresenterView;
    private Context context;




    public MachineTest2Presenter(MachineTest2PresenterView loginPresenterView){
        this.MachineTest2PresenterView = loginPresenterView;

    }



    public void retrofitLogin()
    {

        Map<String,String> map = new HashMap<>();
        map.put("Content-Type","application/json");

        MediaType mediaType = MediaType.parse("application/json");

        JSONObject obj = new JSONObject();
        try {
            obj.put("action","aa");

            Log.i("SignUpPresenterBody",obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



      //  Log.i("SignUpPresenterBody",""+obj.toString());
       // RequestBody body = RequestBody.create(mediaType);

        ApiInterface apiService =
                Apiclient.getClient().create(ApiInterface.class);

        Call<MachineTest2Pojo> call = (Call<MachineTest2Pojo>) apiService.getSignInResult2();
        call.enqueue(new Callback<MachineTest2Pojo>() {
            @Override
            public void onResponse(Call<MachineTest2Pojo> call, retrofit2.Response<MachineTest2Pojo> response) {


                if (response.code() == 200){




                  //  JSONArray array = new JSONArray(response.body().ge);
                  //  for (int i = 0; i < array.length(); i++) {


                    try{

                        /*for(int i=0;i<response.body().getBody().si)*/
                        Log.i("20000",response.body().getBody());
                        Log.i("20000", String.valueOf(response.body().getId()));
                        Log.i("2000", String.valueOf(response.body().getUserId()));
                        Log.i("20000",response.body().getTitle());


                       // MachineTest2PresenterView.responceSignup(String.valueOf(response.code()),response.body().getResult().getProfileDetails().getAccessToken(),response.body().getResult().getProfileDetails().getRtmToken(),response.body().getResult().getProfileDetails().getFullName());

                    }
                    catch (Exception e){
                       // MachineTest2PresenterView.getErrorSignUpPresenter("Some thing went wrong, please try again with proper login credentials");

                    }






                }else{

                    Log.i("20000",response.body().getBody());
                    Log.i("20000", String.valueOf(response.body().getId()));
                    Log.i("2000", String.valueOf(response.body().getUserId()));
                    Log.i("20000",response.body().getTitle());
                  //  Log.i("SignUpPresenteResponse",""+response.body().);

                  /*  Log.i("20000",response.body().getBody());
                    Log.i("20000", String.valueOf(response.body().getId()));
                    Log.i("2000", String.valueOf(response.body().getUserId()));
                    Log.i("20000",response.body().getTitle());*/
                    Gson gson = new GsonBuilder().create();
                 //   ErrorData mError = new ErrorData();

/*
                    try {
                        //Log.i("SignUpPresetexception",""+response.body().toString());
                       *//* mError = gson.fromJson(response.errorBody().string(), ErrorData.class);
                        MachineTest2PresenterView.getErrorSignUpPresenter(mError.getServerResponse().getMessage());*//*



                    }
                    catch (IOException e) {
                        Log.i("SignUpPresetexception",""+e.toString());

                    }*/

                }


            }

            @Override
            public void onFailure(Call<MachineTest2Pojo> call, Throwable t) {

                Log.i("SignUpFailureNET",""+call.toString().trim());



            }
        });
    }




    public interface MachineTest2PresenterView{

        void getErrorSignUpPresenter(String error);
        void responceSignup(String resonse, String accessToken, String rtmToken,String fullName);


    }


}
