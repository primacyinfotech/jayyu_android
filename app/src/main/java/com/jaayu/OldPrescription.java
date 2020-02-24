package com.jaayu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Adapter.CartAdapter;
import Adapter.OldPrescriptionAdapter;
import Model.CartModel;
import Model.OldPrescriptionModel;

public class OldPrescription extends AppCompatActivity {
RecyclerView old_prescription_list;
OldPrescriptionAdapter oldPrescriptionAdapter;
ArrayList<OldPrescriptionModel> oldPrescriptionModels;
    SharedPreferences prefs_register;
    private String u_id,old_pres_id;
    private Button upload_prescription;
    ArrayList<String> old_Plist;
    ArrayList<String> old_Plist_two;
    ArrayList<String> All_Plist_two;

private String Old_prescription_url="https://work.primacyinfotech.com/jaayu/api/prescription_req_display_old";
    private String Old_prescription_url_add="https://work.primacyinfotech.com/jaayu/api/prescription_old_add";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_prescription);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        old_prescription_list=(RecyclerView)findViewById(R.id.old_prescription_list);
        upload_prescription=(Button)findViewById(R.id.upload_prescription);

        fetchOldPrescription();
        upload_prescription.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<OldPrescriptionModel> oldList=((OldPrescriptionAdapter) oldPrescriptionAdapter).getOldPrescription();
                for (int i=0;i<oldList.size();i++){
                    OldPrescriptionModel singleStudent = oldList.get(i);
                    if(singleStudent.isSelected()){
                       int  data=singleStudent.getOld_Pres_id();
                        //System.out.println("OlD_v"+data);
                        old_Plist=new ArrayList<>();
                        old_Plist.add(String.valueOf(singleStudent.getOld_Pres_id()));
                        old_Plist_two=new ArrayList<>();
                        old_Plist_two.addAll(old_Plist);
                        for(String str:old_Plist){
                            All_Plist_two=new ArrayList<>();
                            All_Plist_two.add(str);

                            Gson gson=new Gson();
                            old_pres_id=gson.toJson(All_Plist_two);
                            System.out.println("OlD"+old_pres_id);
                            RequestQueue requestQueue = Volley.newRequestQueue(OldPrescription.this);
                            StringRequest postRequest = new StringRequest(Request.Method.POST,Old_prescription_url_add,
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
                                                    Intent goToUpload=new Intent(OldPrescription.this,UploadToPrescription.class);
                                                    startActivity(goToUpload);
                                                    overridePendingTransition(0,0);
                                                    finish();
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
                            )
                            {
                                @Override
                                protected Map<String, String> getParams ()
                                {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("user_id", u_id);
                                    params.put("opid", old_pres_id);


                                    return params;
                                }

                            };
                            requestQueue.add(postRequest);

                    }




                    }


                }




                }





        });
    }
    private void fetchOldPrescription(){
        oldPrescriptionModels=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(OldPrescription.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Old_prescription_url,
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
                               JSONArray jsonArray=person.getJSONArray("prescription_img");
                               for(int i=0;i<jsonArray.length();i++){
                                   OldPrescriptionModel oldPrescriptionModel=new OldPrescriptionModel();
                                   JSONObject object=jsonArray.getJSONObject(i);
                                   oldPrescriptionModel.setOld_Pres_id(object.getInt("id"));
                                   oldPrescriptionModel.setOld_prescription_date(object.getString("created_at"));
                                   oldPrescriptionModel.setOldprescription_url(object.getString("prescription"));
                                   oldPrescriptionModels.add(oldPrescriptionModel);
                                   oldPrescriptionAdapter=new OldPrescriptionAdapter(oldPrescriptionModels,OldPrescription.this);
                                   old_prescription_list.setHasFixedSize(true);
                                   old_prescription_list.setLayoutManager(new LinearLayoutManager(OldPrescription.this));
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
        )
        {
            @Override
            protected Map<String, String> getParams ()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OldPrescription.this,UploadToPrescription.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
