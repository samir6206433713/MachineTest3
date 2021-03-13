package com.example.machinetest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.network.Apiclient;
import com.example.presenter.MachineTest2Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements MachineTest2Presenter.MachineTest2PresenterView {
    MachineTest2Presenter loginPresenter;
    RequestQueue requestQueue;
    String url = "https://jsonplaceholder.typicode.com/posts";
    private DBHelper mydb;
    Button button;
    ArrayList<NoteModel> arrayList;
    RecyclerView recyclerView;
    NotesAdapter adapter;
    protected boolean gps_enabled, network_enabled;
    private String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = ACCESS_FINE_LOCATION;

    // GPSTracker class
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    double longitude=22.593790;
    double latitude=88.387080;
    String format;
    Button button3;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mydb = new DBHelper(this);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);
        button2 = findViewById(R.id.button2);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

//---------------------Get date and time-----------------------------------------
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
         format = simpleDateFormat.format(new Date());
        Log.i("MainActivity", "Current Timestamp: " + format);

//-------------------------------------------------------------------------------------------
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        //----------------------------------------------Get gps location-----------------------------------------------------------------


        locationTrack = new LocationTrack(MainActivity.this);


        if (locationTrack.canGetLocation()) {


             longitude = locationTrack.getLongitude();
             latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {
             longitude = locationTrack.getLongitude();
             latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            //locationTrack.showSettingsAlert();
        }


//-----------------------------------------------------------------------------------







        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        } else {
            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            //locationTrack.showSettingsAlert();
        }
        //********************************************************************************
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {


                        //  Log.i("response",response);






                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                Log.i("userId",jsonObject.getString("userId"));
                                Log.i("id",jsonObject.getString("id"));
                                Log.i("title",jsonObject.getString("title"));
                                Log.i("body",jsonObject.getString("body"));
                                Log.i("body1", String.valueOf(longitude));
                                Log.i("body2", String.valueOf(latitude));
                                Log.i("body3",format);

                               // mydb.CheckIsDataAlreadyInDBorNot("contacts","id",jsonObject.getString("id"));
                                if(mydb.CheckIsDataAlreadyInDBorNot("contacts","id",jsonObject.getString("id")))
                                {
                                    Log.i("Duplicate","Yes");
                                }
                                else {
                                    if(mydb.insertContact(jsonObject.getString("userId"),jsonObject.getString("title"),jsonObject.getString("body"),String.valueOf(longitude),String.valueOf(latitude),format)){
                                        // Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                   /* Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);*/
                                        Log.i("updated","Updated");
                                    } else{
                                        Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                                    }
                                }



                                //textView.setText(jsonObject.getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }











                        // myPd_ring.dismiss();
                        if(response.trim().equals("Login Successful"))
                        {
                            //  Toast.makeText(SignInActivity.this,"Login Successful", Toast.LENGTH_LONG).show();

                           /* Config.EmailId=Email4.getText().toString();
                            Intent intent=new Intent(SignInActivity.this,Visiter.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("cemail", Email4.getText().toString());
                            intent.putExtras(bundle);

                            startActivity(intent);
                            finish();*/
                        }
                        else
                        {
                            //Toast.makeText(SignInActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                       /* myPd_ring.dismiss();
                        if (error instanceof NetworkError)
                        {
                            Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ServerError)
                        {
                            Toast.makeText(SignInActivity.this,"The server could not be found. Please try again after some time!!",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof AuthFailureError)
                        {
                            Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof ParseError)
                        {
                            Toast.makeText(SignInActivity.this,"Parsing error! Please try again after some time !!",Toast.LENGTH_LONG ).show();

                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }
                        else if (error instanceof TimeoutError)
                        {
                            Toast.makeText(SignInActivity.this,"Cannot connect to Internet...Please check your connection !",Toast.LENGTH_LONG ).show();
                        }*/

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> parameters  = new HashMap<String, String>();
             /*   parameters.put("email",Email4.getText().toString());
                parameters.put("password",Password4.getText().toString());*/


                return parameters;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        //******************************************************************************



















        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Update.class);
                startActivity(intent);

            }
        });

//-----------------------------------------------------------------------------------------------------

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotes();




            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Delete.class);
                startActivity(intent);




            }
        });





    }








    @Override
    public void getErrorSignUpPresenter(String error) {

    }

    @Override
    public void responceSignup(String resonse, String accessToken, String rtmToken, String fullName) {

    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        Toast.makeText(this, ParseJSON.KEY_ID, Toast.LENGTH_SHORT).show();
       /* CustomList cl = new CustomList(this, ParseJSON.ids,ParseJSON.names,ParseJSON.emails,ParseJSON.ratings);
        listView.setAdapter(cl)*/;


    }
    public void displayNotes() {
        //---------------------------------
        arrayList = new ArrayList<>(mydb.getNotes());
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter=new NotesAdapter(getApplicationContext(), MainActivity.this, arrayList);

        recyclerView.setAdapter(adapter);
//-----------------------------------------------
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Alert gps")
                        .setMessage("Enable gps")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }

    public static boolean isMockLocationOn(Location location, Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return location.isFromMockProvider();
        } else {
            String mockLocation = "0";
            try {
                mockLocation = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return !mockLocation.equals("0");
        }
    }
}