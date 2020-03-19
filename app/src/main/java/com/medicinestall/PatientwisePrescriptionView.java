package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.PatientwisePrescriptionAdapter;
import Model.PatientWisePrescriptionModel;

public class PatientwisePrescriptionView extends AppCompatActivity {
    RecyclerView list_of_patient_prescription;
    PatientwisePrescriptionAdapter patientwisePrescriptionAdapter;
    ArrayList<PatientWisePrescriptionModel> patientWisePrescriptionModels;
    private String Patient_img_Url= BaseUrl.BaseUrlNew+"patient_assign_list_pres";
    String u_id;
    int patient_id;
    SharedPreferences prefs_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientwise_prescription_view);
        Intent fetchpatientId=getIntent();
        patient_id=fetchpatientId.getIntExtra("Patient_id",0);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        list_of_patient_prescription=(RecyclerView)findViewById(R.id.list_of_patient_prescription);
        getPresciptionImage();
    }
    private void getPresciptionImage(){
        patientWisePrescriptionModels=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(PatientwisePrescriptionView.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Patient_img_Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");


                            if(status.equals("1")){


                                JSONArray jsonArray=person.getJSONArray("prescription_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    PatientWisePrescriptionModel patientListModel=new PatientWisePrescriptionModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    patientListModel.setPatient_img(object.getString("prescription"));


                                    patientWisePrescriptionModels.add(patientListModel);

                                }
                                patientwisePrescriptionAdapter=new PatientwisePrescriptionAdapter(patientWisePrescriptionModels,PatientwisePrescriptionView.this);
                                list_of_patient_prescription.setHasFixedSize(true);
                              //  LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),3);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                list_of_patient_prescription.setLayoutManager(layoutManager);

                                list_of_patient_prescription.setAdapter(patientwisePrescriptionAdapter);
                                patientwisePrescriptionAdapter.notifyDataSetChanged();

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

                params.put("user_id" ,u_id);
                 params.put("patient_id" , String.valueOf(patient_id));

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
}
