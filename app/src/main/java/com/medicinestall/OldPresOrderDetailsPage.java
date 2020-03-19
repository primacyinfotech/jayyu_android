package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
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
import com.medicinestall.Model.BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OldPresOrderDetailsPage extends AppCompatActivity {
    ImageView back_button,expend_btn,order_details_icon;
    private TextView active_order_two,active_order_three,active_order,active_order_four,active_order_five,order_id,order_date,text_cancel,text_pay;
    SharedPreferences prefs_register;
    private String Orderdetails_url= BaseUrl.BaseUrlNew+"profile_prescription_display_single";
    String u_id,instant_id,ship_status,delivery_date,ord_id,ord_date,mrp_amt,save_amt,shipping_charge,tot_pay,ship_add_name,ship_add_phone,
            ship_add_address,ship_add_land,ship_add_pin,payment_status,odr_id;
    int tbl_ord_id;
    private TextView mrp_amount,save_amount,ship_charge,total_pay,ship_address,date_of_delivery;
    private LinearLayout cancel_btn,paynow_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_pres_order_details_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        Intent gettheOrderData=getIntent();
       odr_id=gettheOrderData.getStringExtra("Order_id");
       tbl_ord_id=gettheOrderData.getIntExtra("table_id",0);
       // instant_id=gettheOrderData.getStringExtra("Instant");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        order_details_icon=(ImageView) findViewById(R.id.order_details_icon);
        active_order=(TextView)findViewById(R.id.active_order);
        active_order_two=(TextView)findViewById(R.id.active_order_two);
        active_order_three=(TextView)findViewById(R.id.active_order_three);
        active_order_four=(TextView)findViewById(R.id.active_order_four);
        active_order_five=(TextView)findViewById(R.id.active_order_five);
        total_pay=(TextView)findViewById(R.id.total_pay);
     /*   mrp_amount=(TextView)findViewById(R.id.mrp_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        ship_charge=(TextView)findViewById(R.id.ship_charge);
        total_pay=(TextView)findViewById(R.id.total_pay);*/
        ship_address=(TextView)findViewById(R.id.ship_address);
        // date_of_delivery=(TextView)findViewById(R.id.date_of_delivery);
        text_pay=(TextView)findViewById(R.id.text_pay);
        text_cancel=(TextView)findViewById(R.id.text_cancel);
        cancel_btn=(LinearLayout)findViewById(R.id.cancel_btn);
        paynow_btn=(LinearLayout)findViewById(R.id.paynow_btn);
        active_order.setVisibility(View.GONE);
        active_order_two.setVisibility(View.GONE);
        active_order_three.setVisibility(View.GONE);
        active_order_four.setVisibility(View.GONE);
        active_order_five.setVisibility(View.GONE);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OldPresOrderDetailsPage.this, OrderPage.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        getOrderDetails();
    }
    private void getOrderDetails(){
        order_id=(TextView)findViewById(R.id.ordr_id);
        order_date=(TextView)findViewById(R.id.ordr_date);
        RequestQueue requestQueue = Volley.newRequestQueue(OldPresOrderDetailsPage.this);
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
                            ship_status=person.getString("shipping");
                            payment_status=person.getString("payment");
                            ord_id=person.getString("orderid");
                            order_id.setText(ord_id);
                            ord_date=person.getString("order_date");
                            order_date.setText(ord_date);
                           /* mrp_amt=person.getString("MRP");
                            save_amt=person.getString("Saving");
                            shipping_charge=person.getString("Shipping charge");
                            tot_pay=person.getString("Total Pay");
                            mrp_amount.setText(mrp_amt);
                            save_amount.setText(save_amt);
                            ship_charge.setText(shipping_charge);
                            total_pay.setText(tot_pay);*/
                            ship_add_name=person.getString("Name");
                            ship_add_phone=person.getString("phone");
                            ship_add_address=person.getString("Address");
                            ship_add_land=person.getString("Landmark");
                            ship_add_pin=person.getString("Pincode");
                            ship_address.setText(ship_add_name+"\n"+ship_add_address+"\n"+ship_add_land+","+"Pin:"+ship_add_pin+"\n"+"Mobile:"+ship_add_phone);
                            if(ship_status.equals("0")){
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
                                if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }
                                if(payment_status.equals("1")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Help");
                                }


                            }
                            if(ship_status.equals("3")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.VISIBLE);
                                active_order_five.setVisibility(View.GONE);
                                if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }
                                if(payment_status.equals("1")){
                                    text_cancel.setText("Help");
                                    text_pay.setText("Reorder");
                                }

                            }
                            if(ship_status.equals("4")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.VISIBLE);
                                if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }
                                if(payment_status.equals("1")){
                                    text_cancel.setText("Help");
                                    text_pay.setText("Reorder");
                                }
                            }
                            /*delivery_date=person.getString("Delivery date");
                            date_of_delivery.setText(delivery_date);*/


                          /*  if (status.equals("1")) {
                                JSONArray jsonArray=person.getJSONArray("Items");
                                for (int i=0;i<jsonArray.length();i++){
                                    OderItemModel itemModel=new OderItemModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    itemModel.setItem_Name(jsonObject.getString("title"));
                                    itemModel.setItem_unit(jsonObject.getString("strip"));
                                    itemModel.setItem_amt(jsonObject.getString("price"));
                                    modelList.add(itemModel);

                                }


                                orderItemAdapter=new OrderItemAdapter(modelList,PrescriptionOrderDetails.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(PrescriptionOrderDetails.this, RecyclerView.VERTICAL,false));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(orderItemAdapter);
                                orderItemAdapter.notifyDataSetChanged();


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

              //  params.put("user_id", u_id);
                params.put("odr_id", odr_id);
                params.put("id", String.valueOf(tbl_ord_id));
               // params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OldPresOrderDetailsPage.this, OrderPage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
