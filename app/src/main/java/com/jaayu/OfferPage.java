package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.CouponListAdapter;
import Adapter.OfferPageAdapter;
import Model.CouponListModel;
import Model.OfferPageModel;

public class OfferPage extends AppCompatActivity {
    private ImageView back_button;
    RecyclerView offer_list;
    OfferPageAdapter offerPageAdapter;
    ArrayList<OfferPageModel> offerPageModels;
    private  String offer_url= BaseUrl.BaseUrlNew+"offer_jaayu";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(OfferPage.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading....");
        progressDialog.setCancelable(false);
        offer_list=(RecyclerView)findViewById(R.id.offer_list);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotOaccount=new Intent(OfferPage.this,MainActivity.class);
                startActivity(GotOaccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getOffers();
    }
    private void getOffers(){
        offerPageModels=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(OfferPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,offer_url,
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
                                progressDialog.dismiss();

                                JSONArray jsonArray=person.getJSONArray("msg");
                                for(int i=0;i<jsonArray.length();i++){
                                    OfferPageModel offerPageModel=new OfferPageModel();
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    offerPageModel.setOffer_heading(object.getString("heading"));
                                    offerPageModel.setOffer_des(object.getString("short_description"));
                                    offerPageModel.setOffer_code(object.getString("code"));
                                    String longdes=object.getString("long_description");
                                    Spanned htmlAsSpanned = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                        htmlAsSpanned = Html.fromHtml(longdes,Html.FROM_HTML_MODE_COMPACT);
                                        offerPageModel.setOffer_long_des(String.valueOf(htmlAsSpanned));
                                    }
                                    else {
                                        offerPageModel.setOffer_long_des(String.valueOf(htmlAsSpanned));
                                    }
                                   // offerPageModel.setOffer_des(String.valueOf(htmlAsSpanned));
                                    offerPageModel.setOffer_img(object.getString("image"));
                                    String exp=object.getString("expiry_date");
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                                    Date testDate=sdf.parse(exp);
                                    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                    String newFormat = formatter.format(testDate);
                                    offerPageModel.setOffer_exp_date("Exp Date :"+newFormat);

                                    offerPageModels.add(offerPageModel);
                                }
                                offerPageAdapter=new OfferPageAdapter( offerPageModels,OfferPage.this);
                                offer_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                offer_list.setLayoutManager(layoutManager);

                                offer_list.setAdapter(offerPageAdapter);
                                offerPageAdapter.notifyDataSetChanged();

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
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
        Intent GotOaccount=new Intent(OfferPage.this,MainActivity.class);
        startActivity(GotOaccount);
        overridePendingTransition(0,0);
        finish();
    }
}
