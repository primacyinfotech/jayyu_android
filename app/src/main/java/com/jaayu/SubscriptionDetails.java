package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;
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
import java.util.Locale;
import java.util.Map;

import Adapter.OrderStatusPressAdapter;
import Adapter.SubscriptionDeliveryDateAdapter;
import Adapter.SubscriptionOrderDetailsAdapter;
import Model.OrderStatusPressModel;
import Model.SubscriptionDeliveryDateModel;
import Model.SubscriptionOrderDetailsModel;

public class SubscriptionDetails extends AppCompatActivity {
    SharedPreferences prefs_register;
    String u_id,Ord_Subs_Tbl_Id,Invoice_Subs_Id,show_coupon,ship_add_name,ship_add_phone,
            ship_add_address,ship_add_land,ship_add_pin;
    ArrayList<SubscriptionDeliveryDateModel> subscriptionDeliveryDateModels;
    SubscriptionDeliveryDateAdapter subscriptionDeliveryDateAdapter;
    ArrayList<SubscriptionOrderDetailsModel> subscriptionOrderDetailsModels;
    SubscriptionOrderDetailsAdapter subscriptionOrderDetailsAdapter;
    OrderStatusPressAdapter orderStatusPressAdapter;
    ArrayList<OrderStatusPressModel> orderStatusPressModels;
    ImageView back_button;
    TextView active_order, customer_name,address_text,address_land,address_zipt,address_phone,place_apply_coupon,additional_note,disclaimer;
    RecyclerView show_delivery_list,rv_items,pres_list;
    private  String disclaimer_url= BaseUrl.BaseUrlNew+"disclaimer";
    private  String subscription_order_details_url= BaseUrl.BaseUrlNew+"subscription_order_details";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent subsordfetchj=getIntent();
        Ord_Subs_Tbl_Id=subsordfetchj.getStringExtra("Ord_subs_id");
        Invoice_Subs_Id=subsordfetchj.getStringExtra("Invoice_Id");
        subscriptionDeliveryDateModels=new ArrayList<>();
        subscriptionOrderDetailsModels=new ArrayList<>();
        orderStatusPressModels=new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        active_order=(TextView)findViewById(R.id.active_order);
        show_delivery_list=(RecyclerView)findViewById(R.id.show_delivery_list);
        customer_name=(TextView)findViewById(R.id.customer_name);
        address_text=(TextView)findViewById(R.id.address_text);
        address_land=(TextView)findViewById(R.id.address_land);
        address_zipt=(TextView)findViewById(R.id.address_zipt);
        address_phone=(TextView)findViewById(R.id.address_phone);
        rv_items=(RecyclerView)findViewById(R.id.rv_items);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        additional_note=(EditText)findViewById(R.id.additional_note);
        pres_list=(RecyclerView)findViewById(R.id.pres_list);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubscriptionDetails.this, SubscriptionOrder.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getDisclimer();
        getOrderDetails();
        getPrescription();

    }
    private void getOrderDetails(){
        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDetails.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, subscription_order_details_url,
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
                                JSONArray jsonArray=person.getJSONArray("delivery_date");
                                for (int i=0;i<jsonArray.length();i++){
                                    SubscriptionDeliveryDateModel subscriptionDeliveryDateModel=new SubscriptionDeliveryDateModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String date = jsonObject.getString("date");
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy");
                                        Date testDate = sdf.parse(date);
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yyyy");
                                        date = formatter.format(testDate);
                                    }catch (ParseException parseError){
                                    }
                                    subscriptionDeliveryDateModel.setDelivery_date(date);

                                    subscriptionDeliveryDateModel.setDelivery_status(jsonObject.getString("delivery_status"));
                                    subscriptionDeliveryDateModels.add(subscriptionDeliveryDateModel);
                                }
                                subscriptionDeliveryDateAdapter=new SubscriptionDeliveryDateAdapter(subscriptionDeliveryDateModels,SubscriptionDetails.this);
                                show_delivery_list.setHasFixedSize(true);
                                LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                show_delivery_list.setLayoutManager(layoutManager);
                                show_delivery_list.setAdapter(subscriptionDeliveryDateAdapter);
                                subscriptionDeliveryDateAdapter.notifyDataSetChanged();

                                JSONArray jsonArray2=person.getJSONArray("Items");
                                for (int j=0;j<jsonArray2.length();j++){
                                    SubscriptionOrderDetailsModel subscriptionOrderDetailsModel=new SubscriptionOrderDetailsModel();
                                    JSONObject jsonObject2=jsonArray2.getJSONObject(j);
                                    subscriptionOrderDetailsModel.setItem_id(jsonObject2.getInt("id"));
                                    subscriptionOrderDetailsModel.setItem_qty(jsonObject2.getString("quantity"));
                                    subscriptionOrderDetailsModel.setItem_name(jsonObject2.getString("title"));
                                    subscriptionOrderDetailsModel.setItem_unit(jsonObject2.getString("strip"));


                                    subscriptionOrderDetailsModel.setPrice_normal(jsonObject2.getString("price_normal"));
                                    subscriptionOrderDetailsModel.setMrp_price(jsonObject2.getString("mrp_price"));
                                    subscriptionOrderDetailsModel.setCom_name(jsonObject2.getString("com_name"));
                                    subscriptionOrderDetailsModel.setNormal_disc(jsonObject2.getString("normal_disc"));
                                    subscriptionOrderDetailsModel.setComposition(jsonObject2.getString("composition"));


                                    subscriptionOrderDetailsModels.add(subscriptionOrderDetailsModel);
                                }
                                subscriptionOrderDetailsAdapter=new SubscriptionOrderDetailsAdapter(subscriptionOrderDetailsModels,SubscriptionDetails.this);
                                rv_items.setHasFixedSize(true);
                                LinearLayoutManager layoutManager2=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                rv_items.setLayoutManager(layoutManager2);
                                rv_items.setAdapter(subscriptionOrderDetailsAdapter);
                                subscriptionOrderDetailsAdapter.notifyDataSetChanged();

                            }
                            show_coupon=person.getString("copon_code");
                            if(show_coupon!=null){
                                place_apply_coupon.setText(show_coupon);


                            }
                            else {
                                place_apply_coupon.setText("Coupon Not Applied");

                            }
                            String order_note=person.getString("order_note");
                            if(!order_note.equals("null")){
                                additional_note.setText(order_note);
                            }
                            else {
                                additional_note.setText("");
                            }
                            String Order_ID=person.getString("OrderId");
                            active_order.setText(Order_ID);
                            ship_add_name=person.getString("Name");
                            ship_add_phone=person.getString("phone");
                            ship_add_address=person.getString("Address");
                            ship_add_land=person.getString("Landmark");
                            ship_add_pin=person.getString("Pincode");
                            customer_name.setText(ship_add_name);
                            address_text.setText(ship_add_address);
                            address_land.setText(ship_add_land);
                            address_zipt.setText(ship_add_pin);
                            address_phone.setText(ship_add_phone);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",u_id);
                params.put("order_id",Ord_Subs_Tbl_Id);
                params.put("invoice_id",Invoice_Subs_Id);
                return params;
            }

        };
        requestQueue.add(postRequest);
    }
    private void getDisclimer() {
        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDetails.this);
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
    private void getPrescription(){
        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionDetails.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,subscription_order_details_url,
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


                                pres_list.setVisibility(View.VISIBLE);
                                JSONArray jsonArray=person.getJSONArray("prescription");
                                for (int i=0;i<jsonArray.length();i++){
                                    OrderStatusPressModel orderStatusPressModel=new OrderStatusPressModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    orderStatusPressModel.setPress_img(jsonObject.getString("prescription"));
                                    orderStatusPressModels.add(orderStatusPressModel);



                                }


                                orderStatusPressAdapter=new OrderStatusPressAdapter(orderStatusPressModels,SubscriptionDetails.this);
                                pres_list.setLayoutManager(new LinearLayoutManager(SubscriptionDetails.this,RecyclerView.HORIZONTAL,false));
                                pres_list.setHasFixedSize(true);
                                pres_list.setAdapter(orderStatusPressAdapter);
                                orderStatusPressAdapter.notifyDataSetChanged();

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

                params.put("user_id",u_id);
                params.put("order_id",Ord_Subs_Tbl_Id);
                params.put("invoice_id",Invoice_Subs_Id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SubscriptionDetails.this, SubscriptionOrder.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
