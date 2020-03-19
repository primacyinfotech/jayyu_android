package com.medicinestall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.OldPrescriptionAdapter;
import Model.OldPrescriptionModel;

public class OldPrescription extends AppCompatActivity {
RecyclerView old_prescription_list;
OldPrescriptionAdapter oldPrescriptionAdapter;
ArrayList<OldPrescriptionModel> oldPrescriptionModels;
    SharedPreferences prefs_register;
    private String u_id,old_pres_id;
    private Button upload_prescription;
    ArrayList<String> old_Plist;
    List<String>[] old_Plist_two;
    List<String> All_Plist_two=new ArrayList<>();;
    List<String>l;
    int data;
    StringBuffer stringBuffer=null;


private String Old_prescription_url= BaseUrl.BaseUrlNew+"prescription_req_display_old";
    private String Old_prescription_url_add=BaseUrl.BaseUrlNew+"prescription_old_add";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_prescription);
        oldPrescriptionModels=new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);

        u_id=prefs_register.getString("USER_ID","");
        old_prescription_list=(RecyclerView)findViewById(R.id.old_prescription_list);
        upload_prescription=(Button)findViewById(R.id.upload_prescription);
        oldPrescriptionModels=fetchOldPrescription(false);
       // fetchOldPrescription();
        upload_prescription.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                for(int i=0;i<OldPrescriptionAdapter.oldPrescriptionModels.size();i++){
         if(OldPrescriptionAdapter.oldPrescriptionModels.get(i).getSelected()){
         old_Plist=new ArrayList<>();
         old_Plist.add(String.valueOf(OldPrescriptionAdapter.oldPrescriptionModels.get(i).getOld_Pres_id()));
         All_Plist_two.addAll(old_Plist);

         }

     }
                old_Plist_two=new List[1];
                old_Plist_two[0]=All_Plist_two;

                for(int j=0;j<old_Plist_two.length;j++){
                    l=old_Plist_two[j];
                   // System.out.println("OlD"+l);
         Gson gson = new Gson();
         old_pres_id = gson.toJson(l);
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













        });
    }
    private  ArrayList<OldPrescriptionModel> fetchOldPrescription(final boolean isSelect){
      //  oldPrescriptionModels=new ArrayList<>();
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
        return oldPrescriptionModels;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OldPrescription.this,UploadToPrescription.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
