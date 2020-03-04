package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.HelpAdapter;
import Adapter.PatientAdapter;
import Model.HelpModel;
import Model.PatientListModel;

public class PatientListView extends AppCompatActivity {
    private ImageView back_button;
    private Button add_btn;
    RecyclerView patient_list;
    PatientAdapter patientAdapter;
    ArrayList<PatientListModel> patientListModels;
    String u_id;
    SharedPreferences prefs_register;
    private String patient_list_url="https://work.primacyinfotech.com/jaayu/api/patient_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        patient_list=(RecyclerView)findViewById(R.id.patient_list);
        add_btn=(Button)findViewById(R.id.add_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(PatientListView.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPatientAdd=new Intent(PatientListView.this,PatientAddPage.class);
                startActivity(goToPatientAdd);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getPatient();
    }
    private void getPatient(){
        RequestQueue requestQueue = Volley.newRequestQueue(PatientListView.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,patient_list_url,
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

                                patientListModels=new ArrayList<>();
                                JSONArray jsonArray=person.getJSONArray("patient_list");
                                for(int i=0;i<jsonArray.length();i++){
                                   PatientListModel patientListModel=new PatientListModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    patientListModel.setPatient_id(object.getInt("id"));
                                    patientListModel.setPatient_name(object.getString("name"));
                                    patientListModel.setRelation_patient(object.getString("rel"));

                                    patientListModels.add(patientListModel);

                                }
                                patientAdapter=new PatientAdapter(patientListModels,PatientListView.this);
                                patient_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                patient_list.setLayoutManager(layoutManager);

                                patient_list.setAdapter(patientAdapter);
                                patientAdapter.notifyDataSetChanged();

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
                /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(PatientListView.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
