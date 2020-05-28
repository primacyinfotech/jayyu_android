package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
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

import Adapter.HelpAdapter;
import Model.HelpModel;

public class AboutUS extends AppCompatActivity {
    private ImageView back_button;
    private String about_topic_url= BaseUrl.BaseUrlNew+"about_content";
    private TextView abt_title,abt_body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        abt_body=(TextView)findViewById(R.id.abt_body);
        abt_title=(TextView)findViewById(R.id.abt_title);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(AboutUS.this,AccountPage.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getAboutUs();
    }
    private  void getAboutUs(){
        RequestQueue requestQueue = Volley.newRequestQueue(AboutUS.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,about_topic_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            String status=person.getString("status");


                            if(status.equals("1")) {
                           JSONObject  object=person.getJSONObject("abtu");
                           String abt_titl=object.getString("title");
                           String abt_content=object.getString("body");
                                Spanned htmlAsSpanned = null;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    htmlAsSpanned = Html.fromHtml(abt_content,Html.FROM_HTML_MODE_COMPACT);
                                    abt_body.setText(htmlAsSpanned);
                                }
                                else {
                                    htmlAsSpanned = Html.fromHtml(abt_content);
                                    abt_body.setText( htmlAsSpanned);
                                }

                                //  abt_title.setText(abt_titl);
                                //abt_body.setText(abt_content);
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
        Intent GotOaccount=new Intent(AboutUS.this,AccountPage.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
