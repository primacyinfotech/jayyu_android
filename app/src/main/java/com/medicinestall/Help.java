package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.HelpAdapter;
import Model.HelpModel;

public class Help extends AppCompatActivity {
private RecyclerView help_list;
    private ImageView back_button;
    HelpAdapter helpAdapter;

    ArrayList<HelpModel> helpModels;
    private String help_topic_url= BaseUrl.BaseUrlNew+"jaayu_tropic";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helpModels=new ArrayList<>();
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        help_list=(RecyclerView)findViewById(R.id.help_list);
        getHelp();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(Help.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    private void getHelp(){
        RequestQueue requestQueue = Volley.newRequestQueue(Help.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,help_topic_url,
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

                                helpModels=new ArrayList<>();
                                JSONArray jsonArray=person.getJSONArray("tropic");
                                for(int i=0;i<jsonArray.length();i++){
                                    HelpModel helpModel=new HelpModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    helpModel.setHelp_id(object.getInt("id"));
                                    helpModel.setHelpitem(object.getString("tropic"));

                                    helpModels.add(helpModel);

                                }
                                helpAdapter=new HelpAdapter(helpModels,Help.this);
                                help_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                help_list.setLayoutManager(layoutManager);

                                help_list.setAdapter(helpAdapter);
                                helpAdapter.notifyDataSetChanged();

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

               /* params.put("user_id" ,u_id);*/
                /* params.put("user_id" ,"35");*/

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent GotOaccount=new Intent(Help.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
