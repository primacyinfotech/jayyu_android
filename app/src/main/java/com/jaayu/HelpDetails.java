package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import Adapter.HelpDetailsAdapter;
import Model.HelpDetailsModel;

public class HelpDetails extends AppCompatActivity {
 private ImageView back_button;
 private TextView header_help;
 RecyclerView help_details_list;
    HelpDetailsAdapter helpDetailsAdapter;
    ArrayList<HelpDetailsModel> helpDetailsModels;
    private String Help_Url= BaseUrl.BaseUrlNew+"jaayu_help";
    String help_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent getHelpData=getIntent();
        help_data=getHelpData.getStringExtra("Help_Id");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        header_help=(TextView)toolbar.findViewById(R.id.header_help);
        help_details_list=(RecyclerView)findViewById(R.id.help_details_list);
        GetHelp();
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHelp=new Intent(HelpDetails.this,Help.class);
                startActivity(goToHelp);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }
    private void GetHelp(){
        RequestQueue requestQueue = Volley.newRequestQueue(HelpDetails.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Help_Url,
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
                          String topic=person.getString("tropic");
                                header_help.setText(topic);
                                JSONArray jsonArray = person.getJSONArray("help");
                                helpDetailsModels = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    HelpDetailsModel helpModel = new HelpDetailsModel();
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    helpModel.setHelp_head(object.getString("question"));
                                    String ans=object.getString("answer");
                                    Spanned htmlAsSpanned = Html.fromHtml(ans);
                                    helpModel.setHelp_content(String.valueOf(htmlAsSpanned));
                                    helpDetailsModels.add(helpModel);

                                }
                                helpDetailsAdapter = new HelpDetailsAdapter(helpDetailsModels, HelpDetails.this);
                                help_details_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                help_details_list.setLayoutManager(layoutManager);

                                help_details_list.setAdapter(helpDetailsAdapter);
                                helpDetailsAdapter.notifyDataSetChanged();

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

                // params.put("user_id" ,u_id);
                 params.put("tropic" ,help_data);

                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent goToHelp=new Intent(HelpDetails.this,Help.class);
        startActivity(goToHelp);
        overridePendingTransition(0,0);
        finish();
    }
}
