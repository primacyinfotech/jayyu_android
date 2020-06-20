package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class PrescriptionThankYouPage extends AppCompatActivity {
    String ordID, u_id;
    private TextView order_id, date_of_delivery, disclaimer;
    private LinearLayout order_track_btn, help_ord_btn;
    private String disclaimer_url = BaseUrl.BaseUrlNew + "disclaimer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_thank_you_page);
        Intent getOrdId = getIntent();
        ordID = getOrdId.getStringExtra("OrderID");
        order_id = (TextView) findViewById(R.id.order_id);
        order_track_btn = (LinearLayout) findViewById(R.id.order_track_btn);
        help_ord_btn = (LinearLayout) findViewById(R.id.help_ord_btn);
        order_id.setText("Order Id " + ordID + " has been successfully placed");
        disclaimer = (TextView) findViewById(R.id.disclaimer);
        order_track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoOrderPage = new Intent(PrescriptionThankYouPage.this, OrderPage.class);
                gotoOrderPage.putExtra("from", "thankYou");
                startActivity(gotoOrderPage);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        help_ord_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GotoOrderPage = new Intent(PrescriptionThankYouPage.this, Help.class);
                startActivity(GotoOrderPage);
                overridePendingTransition(0, 0);
            }
        });
        getDisclimer();
    }

    private void getDisclimer() {
        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionThankYouPage.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, disclaimer_url,
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
                                JSONObject ins_con = person.getJSONObject("discm");
                                String content_ins = ins_con.getString("body");
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
        Intent intent = new Intent(PrescriptionThankYouPage.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
