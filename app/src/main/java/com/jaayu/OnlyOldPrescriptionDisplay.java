package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.OnlyOldPresciptionAdapter;
import Model.OnlyOldPrescriptionModel;

public class OnlyOldPrescriptionDisplay extends AppCompatActivity {
    RecyclerView old_prescription_list;
    OnlyOldPresciptionAdapter oldPrescriptionAdapter;
    ArrayList<OnlyOldPrescriptionModel> oldPrescriptionModels;
    SharedPreferences prefs_register;
    private String u_id, old_pres_id;
    private Button upload_prescription;
    ArrayList<String> old_Plist;
    List<String>[] old_Plist_two;
    List<String> All_Plist_two = new ArrayList<>();
    ProgressDialog progressDialog;
    List<String> l;
    private String Old_prescription_url = BaseUrl.BaseUrlNew+"prescription_req_display_old_press";
    private String Old_prescription_url_add = BaseUrl.BaseUrlNew+"prescription_old_add_press";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_only_old_prescription_display);
        oldPrescriptionModels = new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        old_prescription_list = (RecyclerView) findViewById(R.id.old_prescription_list);
        upload_prescription = (Button) findViewById(R.id.upload_prescription);

        oldPrescriptionModels = fetchOldPrescription(false);
        upload_prescription.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(OnlyOldPrescriptionDisplay.this);
                progressDialog.setMessage("Uploading..."); // Setting Message
                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                //progressDialog.setCancelable(false);

                for (int i = 0; i < OnlyOldPresciptionAdapter.oldPrescriptionModels.size(); i++) {
                    if (OnlyOldPresciptionAdapter.oldPrescriptionModels.get(i).getSelected()) {
                        old_Plist = new ArrayList<>();
                        old_Plist.add(String.valueOf(OnlyOldPresciptionAdapter.oldPrescriptionModels.get(i).getOld_Pres_id()));
                        All_Plist_two.addAll(old_Plist);
                    }
                }
                old_Plist_two = new List[1];
                old_Plist_two[0] = All_Plist_two;
                for (int j = 0; j < old_Plist_two.length; j++) {
                    l = old_Plist_two[j];
                    Gson gson = new Gson();
                    old_pres_id = gson.toJson(l);
                    RequestQueue requestQueue = Volley.newRequestQueue(OnlyOldPrescriptionDisplay.this);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, Old_prescription_url_add,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                    try {
                                        //Do it with this it will work
                                        JSONObject person = new JSONObject(response);
                                        String status = person.getString("status");
                                        if (status.equals("1")) {
                                            Intent goToUpload = new Intent(OnlyOldPrescriptionDisplay.this, OnlyUploadPrescription.class);
                                            startActivity(goToUpload);
                                            overridePendingTransition(0, 0);
                                            finish();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    progressDialog.dismiss();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    progressDialog.dismiss();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("user_id", u_id);
                            params.put("opid", old_pres_id);


                            return params;
                        }

                    };
                    requestQueue.add(postRequest);
                }


            }


        });
    }

    private ArrayList<OnlyOldPrescriptionModel> fetchOldPrescription(final boolean isSelect) {
        oldPrescriptionModels = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(OnlyOldPrescriptionDisplay.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Old_prescription_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status = person.getString("status");
                            if (status.equals("1")) {
                                JSONArray jsonArray = person.getJSONArray("prescription_img");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    OnlyOldPrescriptionModel oldPrescriptionModel = new OnlyOldPrescriptionModel();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    oldPrescriptionModel.setOld_Pres_id(object.getInt("id"));
                                    oldPrescriptionModel.setOld_prescription_date(object.getString("created_at"));
                                    oldPrescriptionModel.setOldprescription_url(object.getString("prescription"));
                                    oldPrescriptionModels.add(oldPrescriptionModel);
                                    oldPrescriptionAdapter = new OnlyOldPresciptionAdapter(oldPrescriptionModels, OnlyOldPrescriptionDisplay.this);
                                    old_prescription_list.setHasFixedSize(true);
                                    old_prescription_list.setLayoutManager(new LinearLayoutManager(OnlyOldPrescriptionDisplay.this));
                                    old_prescription_list.setAdapter(oldPrescriptionAdapter);
                                    old_prescription_list.setNestedScrollingEnabled(true);
                                    oldPrescriptionAdapter.notifyDataSetChanged();

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }

        };
        requestQueue.add(postRequest);
        return oldPrescriptionModels;
    }
}
