package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.AssignPatientAdapter;
import Model.AssignPatientModel;

public class AssignPatient extends AppCompatActivity {
    SharedPreferences prefs_register;
    AssignPatientAdapter assignPatientAdapter;
    String u_id;
    ArrayList<AssignPatientModel> assignPatientModels;
    RecyclerView assign_patient_list;
    Button assign_patient_btn;
    private String patient_list_url= BaseUrl.BaseUrlNew+"patient_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_patient);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        assign_patient_list=(RecyclerView)findViewById(R.id.assign_patient_list);
        assign_patient_btn=(Button)findViewById(R.id.assign_patient_btn);
        fetchpatientList();
        assign_patient_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignPatientAdapter.getSelectedItem();



            }
        });

    }
    private void fetchpatientList(){
        RequestQueue requestQueue = Volley.newRequestQueue(AssignPatient.this);
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

                                assignPatientModels=new ArrayList<>();
                                JSONArray jsonArray=person.getJSONArray("patient_list");
                                for(int i=0;i<jsonArray.length();i++){
                                    AssignPatientModel patientListModel=new AssignPatientModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    patientListModel.setAssign_patient_id(object.getInt("id"));
                                    patientListModel.setUser_id(object.getString("user_id"));
                                    patientListModel.setAssign_patient_name(object.getString("name"));


                                    assignPatientModels.add(patientListModel);

                                }
                                assignPatientAdapter=new AssignPatientAdapter(assignPatientModels,AssignPatient.this);
                                assign_patient_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                assign_patient_list.setLayoutManager(layoutManager);

                                assign_patient_list.setAdapter(assignPatientAdapter);
                                assignPatientAdapter.notifyDataSetChanged();

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
        Intent GotOaccount=new Intent(AssignPatient.this,AccounPrescriptionView.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
