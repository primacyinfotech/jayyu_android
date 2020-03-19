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
import android.widget.ImageView;
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

import Adapter.AssignPrescriptionAdapter;
import Model.AssignPrescriptionModel;

public class AccounPrescriptionView extends AppCompatActivity {
    private ImageView back_button;
    RecyclerView assign_prescription;
    AssignPrescriptionAdapter assignPrescriptionAdapter;
    ArrayList<AssignPrescriptionModel> assignPrescriptionModels;
    private String Patient_fetch_prescription_url= BaseUrl.BaseUrlNew+"patient_prescription_list";
    SharedPreferences prefs_register;
    String u_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accoun_prescription_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        assign_prescription=(RecyclerView)findViewById(R.id.assign_prescription);
        fetchAssignPress();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(AccounPrescriptionView.this,AccountPrescription.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    private void fetchAssignPress(){
        assignPrescriptionModels=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(AccounPrescriptionView.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Patient_fetch_prescription_url,
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
                                JSONArray jsonArray=person.getJSONArray("prtescription");
                                for(int i=0;i<jsonArray.length();i++){
                                    AssignPrescriptionModel assignPrescriptionModel=new AssignPrescriptionModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    assignPrescriptionModel.setPress_id(object.getInt("id"));

                                    assignPrescriptionModel.setPress_img(object.getString("prescription"));
                                    assignPrescriptionModels.add(assignPrescriptionModel);

                                }

                                assignPrescriptionAdapter=new AssignPrescriptionAdapter(assignPrescriptionModels,AccounPrescriptionView.this);
                                assign_prescription.setHasFixedSize(true);
                                assign_prescription.setLayoutManager(new LinearLayoutManager(AccounPrescriptionView.this));
                                assign_prescription.setAdapter(assignPrescriptionAdapter);
                                assignPrescriptionAdapter.notifyDataSetChanged();

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
        Intent GotOaccount=new Intent(AccounPrescriptionView.this,AccountPrescription.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
