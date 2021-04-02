package com.example.machinetestredapple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.machinetest2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText email;
    EditText email2;
    EditText email3;
    Button button;
    String  url = "http://test.redappletech.info/apiuser/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email=findViewById(R.id.email);
        email2=findViewById(R.id.email2);
        email3=findViewById(R.id.email3);
        button=findViewById(R.id.button);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {





              netcall();



//********************************************************************************
        /*      StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                      new Response.Listener<String>()
                      {
                          @Override
                          public void onResponse(String response)
                          {




                              Log.i("response",response);




                                 *//* try {
                                      JSONArray jsonArray = new JSONArray(response);
                                      for (int i = 0; i <jsonArray.length() ; i++) {
                                          JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                          Log.i("userId",jsonObject.getString("userId"));



                                      }
                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }*//*













                          }
                      },
                      new Response.ErrorListener()
                      {
                          @Override
                          public void onErrorResponse(VolleyError error)
                          {

                              if (error instanceof NetworkError)
                              {
                                  Toast.makeText(Registration.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                              }
                              else if (error instanceof ServerError)
                              {
                                  Toast.makeText(Registration.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                              }
                              else if (error instanceof AuthFailureError)
                              {
                                  Toast.makeText(Registration.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                              }
                              else if (error instanceof ParseError)
                              {
                                  Toast.makeText(Registration.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                              }
                              else if (error instanceof NoConnectionError)
                              {
                                  Toast.makeText(Registration.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                              }
                              else if (error instanceof TimeoutError)
                              {
                                  Toast.makeText(Registration.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                              }

                          }
                      })
              {
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError
                  {
                      Map<String,String> parameters  = new HashMap<String, String>();
                      parameters.put("email","samirmishra@gmail.com");
                      parameters.put("username","tom111");
                      parameters.put("password","5555555");



                      return parameters;
                  }
              };

              RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
              requestQueue.add(stringRequest);
*/
              //******************************************************************************









//-------------------------------------------------------------------------




/*


              if(email.getText().toString().equals(null) || email.getText().toString().equals("") ){
                  Toast.makeText(Registration.this, "email is null", Toast.LENGTH_SHORT).show();

              }
              if(email2.getText().toString().equals(null) || email2.getText().toString().equals("") ){
                  Toast.makeText(Registration.this, "username is null", Toast.LENGTH_SHORT).show();

              }
              if(email3.getText().toString().equals(null) || email3.getText().toString().equals("") ){
                  Toast.makeText(Registration.this, "password is null", Toast.LENGTH_SHORT).show();

              }
              if(isValidEmail(email.getText().toString())){

              }
              else{


























              }
*/


          }
      });
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public void netcall(){
        String requestUrl = "http://test.redappletech.info/apiuser/register";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VolleyResult", ""+response); //the response contains the result from the server, a json string or any other object returned by your server

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyResult", ""+error.toString()); //the response contains the result from the server, a json string or any other object returned by your server

                error.printStackTrace(); //log the error resulting from the request for diagnosis/debugging

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("email", "jack@gmai.com");
                postMap.put("username", "jack");
                postMap.put("username", "jack123");
                //..... Add as many key value pairs in the map as necessary for your request
                return postMap;
            }
        };
//make the request to your server as indicated in your request url
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }



}