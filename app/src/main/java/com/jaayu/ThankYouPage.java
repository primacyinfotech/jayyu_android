package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThankYouPage extends AppCompatActivity {
    private TextView order_id,date_of_delivery,disclaimer,type_delivery;
    private LinearLayout order_track_btn,help_ord_btn;
  String ordID,u_id;
    SharedPreferences prefs_register;
    private  String delivery_address_Url= BaseUrl.BaseUrlNew+"delivery_date";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you_page);
        Intent getOrdId=getIntent();
        ordID=getOrdId.getStringExtra("OrderID");
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        order_id=(TextView)findViewById(R.id.order_id);
        date_of_delivery=(TextView)findViewById(R.id.date_of_delivery);
        order_track_btn=(LinearLayout)findViewById(R.id.order_track_btn);
        help_ord_btn=(LinearLayout)findViewById(R.id.help_ord_btn);
        type_delivery=(TextView)findViewById(R.id.type_delivery);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        delivery_address();
        order_id.setText("Order Id "+ordID+" has been successfully placed");
        order_track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoOrderPage=new Intent(ThankYouPage.this,OrderPage.class);
                startActivity(GotoOrderPage);
                overridePendingTransition(0,0);
                finish();
            }
        });
        help_ord_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GotoOrderPage=new Intent(ThankYouPage.this,Help.class);
                startActivity(GotoOrderPage);
                overridePendingTransition(0,0);
                finish();
            }
        });

        getDisclimer();
    }
    private void delivery_address(){
        RequestQueue requestQueue = Volley.newRequestQueue(ThankYouPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,delivery_address_Url,
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
                                String del_add=person.getString("Delivery date");
                                String subs_instant=person.getString("instant");
                                date_of_delivery.setText(del_add);
                                if(subs_instant.equals("0")){
                                    type_delivery.setText("Normal Delivery");
                                }
                                else {
                                    type_delivery.setText("Instant Delivery");
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
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(ThankYouPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,disclaimer_url,
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
                                JSONObject ins_con=person.getJSONObject("discm");
                                String content_ins=ins_con.getString("body");
                                Spanned htmlAsSpanned = Html.fromHtml(content_ins);
                                disclaimer.setText(String.valueOf(htmlAsSpanned));
                            }
                            /*else {
                                card_view_istant.setVisibility(View.GONE);
                            }
*/



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
                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThankYouPage.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
