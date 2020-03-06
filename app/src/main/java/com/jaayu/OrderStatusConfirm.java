package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import Adapter.OrderItemAdapter;
import Adapter.OrderStatusItemAdapter;
import Model.OderItemModel;
import Model.OrderStatusItemModel;

public class OrderStatusConfirm extends AppCompatActivity {
    RecyclerView cart_items;
    ImageView back_button,expend_btn,order_details_icon;
    ArrayList<OrderStatusItemModel> orderStatusItemModels;
    OrderStatusItemAdapter orderStatusItemAdapter;
    SharedPreferences prefs_register;
    private String Orderdetails_url="https://work.primacyinfotech.com/jaayu/api/order_details_profile";
    String u_id,instant_id,ship_status,delivery_date,ord_id,ord_date,mrp_amt,save_amt,shipping_charge,tot_pay,ship_add_name,ship_add_phone,
            ship_add_address,ship_add_land,ship_add_pin,payment_status;
    int odr_id;
    private TextView mrp_amount,save_amount,ship_charge,total_pay,ship_address,date_of_delivery,main_pay;
    private LinearLayout cancel_btn,paynow_btn;
    private TextView active_order_two,active_order_three,active_order,active_order_four,active_order_five,order_id,order_date,text_cancel,text_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_order_intent"));
        orderStatusItemModels=new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        Intent gettheOrderData=getIntent();
        odr_id=gettheOrderData.getIntExtra("Order_id",0);
        instant_id=gettheOrderData.getStringExtra("Instant");
        cart_items=(RecyclerView)findViewById(R.id.cart_items);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        /*order_details_icon=(ImageView) findViewById(R.id.order_details_icon);*/
        mrp_amount=(TextView)findViewById(R.id.mrp_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        ship_charge=(TextView)findViewById(R.id.ship_charge);
        total_pay=(TextView)findViewById(R.id.total_pay);
        main_pay=(TextView)findViewById(R.id.main_pay);
        //ship_address=(TextView)findViewById(R.id.ship_address);
       // date_of_delivery=(TextView)findViewById(R.id.date_of_delivery);
       /* text_pay=(TextView)findViewById(R.id.text_pay);
        text_cancel=(TextView)findViewById(R.id.text_cancel);*/
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderStatusConfirm.this, OrderPage.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getOrderDetails();
        getListOFOrder();
    }
    private void getListOFOrder(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderStatusConfirm.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Orderdetails_url,
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
                                JSONArray jsonArray=person.getJSONArray("Items");
                                for (int i=0;i<jsonArray.length();i++){
                                    OrderStatusItemModel itemModel=new OrderStatusItemModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    itemModel.setItem_id(jsonObject.getInt("id"));
                                    itemModel.setTitle(jsonObject.getString("title"));
                                    itemModel.setStrip(jsonObject.getString("strip"));
                                    itemModel.setPrice_normal(jsonObject.getString("price_normal"));
                                    itemModel.setMrp_price(jsonObject.getString("mrp_price"));
                                    itemModel.setQuantity(jsonObject.getInt("quantity"));
                                    orderStatusItemModels.add(itemModel);

                                }


                                orderStatusItemAdapter=new OrderStatusItemAdapter(orderStatusItemModels,OrderStatusConfirm.this);
                                cart_items.setLayoutManager(new LinearLayoutManager(OrderStatusConfirm.this,RecyclerView.VERTICAL,false));
                                cart_items.setHasFixedSize(true);
                                cart_items.setAdapter(orderStatusItemAdapter);
                                orderStatusItemAdapter.notifyDataSetChanged();


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
                params.put("oid", String.valueOf(odr_id));
                params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void getOrderDetails(){
        /*order_id=(TextView)findViewById(R.id.ordr_id);
        order_date=(TextView)findViewById(R.id.ordr_date);*/
        RequestQueue requestQueue = Volley.newRequestQueue(OrderStatusConfirm.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,Orderdetails_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            //String status = person.getString("status");
                            ship_status=person.getString("shipping_status");
                            payment_status=person.getString("payment_status");
                           /* ord_id=person.getString("OrderId");
                            order_id.setText(ord_id);
                            ord_date=person.getString("Order_date");
                            order_date.setText(ord_date);*/
                            mrp_amt=person.getString("MRP");
                            save_amt=person.getString("Saving");
                            shipping_charge=person.getString("Shipping charge");
                            tot_pay=person.getString("Total Pay");
                            mrp_amount.setText(mrp_amt);
                            save_amount.setText(save_amt);
                            ship_charge.setText(shipping_charge);
                            total_pay.setText(tot_pay);
                            main_pay.setText(tot_pay);
                            ship_add_name=person.getString("Name");
                            ship_add_phone=person.getString("phone");
                            ship_add_address=person.getString("Address");
                            ship_add_land=person.getString("Landmark");
                            ship_add_pin=person.getString("Pincode");
                            //ship_address.setText(ship_add_name+"\n"+ship_add_address+"\n"+ship_add_land+","+"Pin:"+ship_add_pin+"\n"+"Mobile:"+ship_add_phone);
                            /*if(ship_status.equals("0")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.VISIBLE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Help");
                                }

                            }
                            if (ship_status.equals("1")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.VISIBLE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }

                            }
                            if(ship_status.equals("2")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.VISIBLE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                text_cancel.setText("Cancel Order");
                                text_pay.setText("Pay Now");

                            }
                            if(ship_status.equals("3")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.VISIBLE);
                                active_order_five.setVisibility(View.GONE);

                            }
                            if(ship_status.equals("4")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.VISIBLE);
                            }*/
                          /*  delivery_date=person.getString("Delivery date");
                            date_of_delivery.setText(delivery_date);*/


                           /* if (status.equals("1")) {
                                JSONArray jsonArray=person.getJSONArray("Items");
                                for (int i=0;i<jsonArray.length();i++){
                                    OrderStatusItemModel itemModel=new OrderStatusItemModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    itemModel.setItem_id(jsonObject.getInt("id"));
                                    itemModel.setTitle(jsonObject.getString("title"));
                                    itemModel.setStrip(jsonObject.getString("strip"));
                                    itemModel.setPrice_normal(jsonObject.getString("price_normal"));
                                    itemModel.setMrp_price(jsonObject.getString("mrp_price"));
                                    itemModel.setQuantity(jsonObject.getInt("quantity"));
                                    orderStatusItemModels.add(itemModel);

                                }


                                orderStatusItemAdapter=new OrderStatusItemAdapter(orderStatusItemModels,OrderStatusConfirm.this);
                                cart_items.setLayoutManager(new LinearLayoutManager(OrderStatusConfirm.this,RecyclerView.VERTICAL,false));
                                cart_items.setHasFixedSize(true);
                                cart_items.setAdapter(orderStatusItemAdapter);
                                orderStatusItemAdapter.notifyDataSetChanged();


                            }*/


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
                params.put("oid", String.valueOf(odr_id));
                params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getOrderDetails();
        }
    };
}
