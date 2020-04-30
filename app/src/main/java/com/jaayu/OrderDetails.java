package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
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
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.OrderItemAdapter;
import Adapter.OrderStatusPressAdapter;
import Model.OderItemModel;
import Model.OrderStatusPressModel;

public class OrderDetails extends AppCompatActivity {
    RecyclerView recyclerView,pres_list;
    private ArrayList<OderItemModel> modelList;
    ImageView back_button,expend_btn,order_details_icon;
    OrderItemAdapter orderItemAdapter;
    OrderStatusPressAdapter orderStatusPressAdapter;
    ArrayList<OrderStatusPressModel> orderStatusPressModels;
    private Animation animationUp;
    private Animation animationDown;
    private TextView active_order_two,active_order_three,active_order,active_order_four,active_order_five,active_order_six,active_order_seven,order_id,order_date,text_cancel,text_pay,
            wallet_pay,jaayu_pay,online_pay,cod_pay;
    SharedPreferences prefs_register;
    private String Orderdetails_url=BaseUrl.BaseUrlNew+"order_details_profile";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    String u_id,instant_id,ship_status,delivery_date,ord_id,ord_date,mrp_amt,save_amt,shipping_charge,tot_pay,ship_add_name,ship_add_phone,
    ship_add_address,ship_add_land,ship_add_pin,payment_status,w_cash,j_cash,onli_cash,c_cash,Ord_vid,show_coupon;
    int odr_id;
    private TextView mrp_amount,save_amount,ship_charge,total_pay,ship_address,date_of_delivery,disclaimer,place_apply_coupon, type_delivery,interval_delivery,
            customer_name,address_text,address_land,address_zipt,address_phone;
    private LinearLayout cancel_btn,paynow_btn,wraper_wallet,wraper_jaayu_cash,wraper_online_cash,wraper_cod_cash;
    private EditText additional_note;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(OrderDetails.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        orderStatusPressModels=new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        Intent gettheOrderData=getIntent();
        odr_id=gettheOrderData.getIntExtra("Order_id",0);
        Ord_vid=gettheOrderData.getStringExtra("Order_Vid");
        instant_id=gettheOrderData.getStringExtra("Instant");
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        modelList=new ArrayList<>();
      /*  modelList.add(new OderItemModel("Calpol 500mg Tablet"," ₹"+"12.36","1 strip 15 tablets"));
        modelList.add(new OderItemModel("Roxin 500mg Tablet"," ₹"+"12.46","1 strip 15 tablets"));
        modelList.add(new OderItemModel("Calpol 500mg Tablet"," ₹"+"12.36","1 strip 15 tablets"));
        modelList.add(new OderItemModel("Roxin 500mg Tablet"," ₹"+"12.46","1 strip 15 tablets"));*/
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        expend_btn=(ImageView)findViewById(R.id.expend_btn);
        order_details_icon=(ImageView) findViewById(R.id.order_details_icon);
        active_order=(TextView)findViewById(R.id.active_order);
        active_order_two=(TextView)findViewById(R.id.active_order_two);
        active_order_three=(TextView)findViewById(R.id.active_order_three);
        active_order_four=(TextView)findViewById(R.id.active_order_four);
        active_order_five=(TextView)findViewById(R.id.active_order_five);
        active_order_six=(TextView)findViewById(R.id.active_order_six);
        active_order_seven=(TextView)findViewById(R.id.active_order_seven);
        mrp_amount=(TextView)findViewById(R.id.mrp_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        ship_charge=(TextView)findViewById(R.id.ship_charge);
        total_pay=(TextView)findViewById(R.id.total_pay);
        ship_address=(TextView)findViewById(R.id.ship_address);
        date_of_delivery=(TextView)findViewById(R.id.date_of_delivery);
        type_delivery=(TextView)findViewById(R.id.type_of_delivery);
        interval_delivery=(TextView)findViewById(R.id.interval_of_delivery);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        customer_name=(TextView)findViewById(R.id.customer_name);
        address_text=(TextView)findViewById(R.id.address_text);
        address_land=(TextView)findViewById(R.id.address_land);
        address_zipt=(TextView)findViewById(R.id.address_zipt);
        address_phone=(TextView)findViewById(R.id.address_phone);
        text_pay=(TextView)findViewById(R.id.text_pay);
        text_cancel=(TextView)findViewById(R.id.text_cancel);
        wallet_pay=(TextView)findViewById(R.id.wallet_pay);
        jaayu_pay=(TextView)findViewById(R.id.jaayu_pay);
        online_pay=(TextView)findViewById(R.id.online_pay);
        cod_pay=(TextView)findViewById(R.id.cod_pay);
        additional_note=(EditText)findViewById(R.id.additional_note);
        cancel_btn=(LinearLayout)findViewById(R.id.cancel_btn);
        paynow_btn=(LinearLayout)findViewById(R.id.paynow_btn);
        wraper_wallet=(LinearLayout)findViewById(R.id.wraper_wallet);
        wraper_jaayu_cash=(LinearLayout)findViewById(R.id.wraper_jaayu_cash);
        wraper_online_cash=(LinearLayout)findViewById(R.id.wraper_online_cash);
        wraper_cod_cash=(LinearLayout)findViewById(R.id.wraper_cod_cash);
        wraper_wallet.setVisibility(View.GONE);
        wraper_jaayu_cash.setVisibility(View.GONE);
        wraper_online_cash.setVisibility(View.GONE);
        wraper_cod_cash.setVisibility(View.GONE);

        active_order.setVisibility(View.GONE);
        active_order_two.setVisibility(View.GONE);
        active_order_three.setVisibility(View.GONE);
        active_order_four.setVisibility(View.GONE);
        active_order_five.setVisibility(View.GONE);
        active_order_six.setVisibility(View.GONE);
        active_order_seven.setVisibility(View.GONE);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this, OrderPage.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.rv_items);
        pres_list=(RecyclerView)findViewById(R.id.pres_list);
   /*     recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderItemAdapter=new OrderItemAdapter(modelList,this);
        recyclerView.setAdapter(orderItemAdapter);
        orderItemAdapter.notifyDataSetChanged();*/
       /* recyclerView.setVisibility(View.GONE);
        expend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.isShown()){
                    recyclerView.setVisibility(View.GONE);
                    recyclerView.startAnimation(animationUp);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.startAnimation(animationDown);
                }
            }
        });*/

        getOrderDetails();
        getPrescription();
        getDisclimer();
    }
    private void getOrderDetails(){
        order_id=(TextView)findViewById(R.id.ordr_id);
        order_date=(TextView)findViewById(R.id.ordr_date);
        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetails.this);
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
                            ship_status=person.getString("shipping_status");
                            payment_status=person.getString("payment_status");
                            ord_id=person.getString("OrderId");
                            order_id.setText(ord_id);
                            ord_date=person.getString("Order_date");
                            order_date.setText(ord_date);
                            mrp_amt=person.getString("MRP");
                            save_amt=person.getString("Saving");
                            shipping_charge=person.getString("Shipping charge");
                            tot_pay=person.getString("Total Pay");
                            w_cash=person.getString("normal_wallet");
                            j_cash=person.getString("jaayu_wallet");

                            onli_cash=person.getString("online_pay");
                            c_cash=person.getString("cod_pay");

                         if(!w_cash.equals("0")){
                             wraper_wallet.setVisibility(View.VISIBLE);
                             wallet_pay.setText(w_cash);

                         }
                            if(!j_cash.equals("0")){
                                wraper_jaayu_cash.setVisibility(View.VISIBLE);
                                jaayu_pay.setText(j_cash);

                            }
                            if(!onli_cash.equals("0")){
                                wraper_online_cash.setVisibility(View.VISIBLE);
                                online_pay.setText(onli_cash);

                            }
                            if(!c_cash.equals("0")){
                                wraper_cod_cash.setVisibility(View.VISIBLE);
                                cod_pay.setText(c_cash);

                            }
                            mrp_amount.setText(mrp_amt);
                            save_amount.setText(save_amt);
                            ship_charge.setText(shipping_charge);
                            total_pay.setText(tot_pay);
                            ship_add_name=person.getString("Name");
                            ship_add_phone=person.getString("phone");
                            ship_add_address=person.getString("Address");
                            ship_add_land=person.getString("Landmark");
                            ship_add_pin=person.getString("Pincode");
                          ///  ship_address.setText(ship_add_name+"\n"+ship_add_address+"\n"+ship_add_land+"\n"+ship_add_pin+"\n"+ship_add_phone);
                            customer_name.setText(ship_add_name);
                            address_text.setText(ship_add_address);
                            address_land.setText(ship_add_land);
                            address_zipt.setText(ship_add_pin);
                            address_phone.setText(ship_add_phone);
                            if(ship_status.equals("0")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.VISIBLE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.GONE);
                                text_cancel.setText("Cancel Order");
                                text_pay.setText("Help");


                            }
                            if (ship_status.equals("1")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.VISIBLE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.GONE);
                                text_cancel.setText("Confirm & Pay");
                                text_pay.setText("Cancel Order");
                                /* if(payment_status.equals("0")){
                                     text_cancel.setText("Cancel Order");
                                     text_pay.setText("Help");
                                 }*/
                                text_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent goToPaymentPage=new Intent(OrderDetails.this,Payment.class);
                                        goToPaymentPage.putExtra("ORDER_ID",String.valueOf(odr_id));
                                        goToPaymentPage.putExtra("ORDER_JY_ID",Ord_vid);
                                        goToPaymentPage.putExtra("INSTANT",instant_id);
                                        startActivity(goToPaymentPage);
                                        overridePendingTransition(0,0);
                                        finish();
                                    }
                                });

                            }
                            if(ship_status.equals("2")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.VISIBLE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.GONE);
                                text_cancel.setText("Cancel Order");
                                text_pay.setText("Help");

                            }
                            if(ship_status.equals("3")){
                                order_details_icon.setImageResource(R.drawable.tick);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.VISIBLE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.GONE);
                                text_cancel.setText("Reorder");
                                text_pay.setText("Help");

                            }
                            if(ship_status.equals("4")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.VISIBLE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.GONE);
                                text_cancel.setText("Reorder");
                                text_pay.setText("Help");
                            }
                            if(ship_status.equals("5")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.VISIBLE);
                                active_order_seven.setVisibility(View.GONE);
                               /* if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }
                                if(payment_status.equals("1")){
                                    text_cancel.setText("Help");
                                    text_pay.setText("Reorder");
                                }*/
                                text_cancel.setText("Reorder");
                                text_pay.setText("Help");
                            }
                            if(ship_status.equals("6")){
                                order_details_icon.setImageResource(R.drawable.tickyellow);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);
                                active_order_five.setVisibility(View.GONE);
                                active_order_six.setVisibility(View.GONE);
                                active_order_seven.setVisibility(View.VISIBLE);
                               /* if(payment_status.equals("0")){
                                    text_cancel.setText("Cancel Order");
                                    text_pay.setText("Pay Now");
                                }
                                if(payment_status.equals("1")){
                                    text_cancel.setText("Help");
                                    text_pay.setText("Reorder");
                                }*/
                                text_cancel.setText("Reorder");
                                text_pay.setText("Help");
                            }
                            show_coupon=person.getString("copon_code");
                            if(show_coupon!=null){
                                place_apply_coupon.setText(show_coupon);


                            }
                            else {
                                place_apply_coupon.setText("Coupon Not Applied");

                            }
                            delivery_date=person.getString("Delivery date");
                            String subs_days=person.getString("day");
                            String subs_interval=person.getString("interval");
                            String subs_instant=person.getString("instant");
                            date_of_delivery.setText(delivery_date);
                            if(subs_instant.equals("0")){
                                type_delivery.setText("Normal Delivery");
                            }
                            else {
                                type_delivery.setText("Instant Delivery");
                            }
                            if(subs_days.equals("1")&&subs_interval.equals("1")){
                                interval_delivery.setText("One Time Order");
                            }
                            else {
                                /* interval_delivery.setText(subs_days+"Days"+" "+subs_interval+"Delivery");*/
                                interval_delivery.setText(subs_interval+" Delivery"+" "+"Every"+" "+subs_days+" Days");
                            }
                            String order_note=person.getString("order_note");
                            if(!order_note.equals("null")){
                                additional_note.setText(order_note);
                            }
                            else {
                                additional_note.setText("");
                            }

                            if (status.equals("1")) {
                                JSONArray jsonArray=person.getJSONArray("Items");
                                for (int i=0;i<jsonArray.length();i++){
                                    OderItemModel itemModel=new OderItemModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    itemModel.setItem_Name(jsonObject.getString("title"));
                                    itemModel.setItem_unit(jsonObject.getString("strip"));
                                    itemModel.setItem_amt(jsonObject.getString("price_normal"));
                                    itemModel.setItem_mrp(jsonObject.getString("mrp_price"));
                                    itemModel.setItem_qty(jsonObject.getString("quantity"));
                                    modelList.add(itemModel);

                                }


                                orderItemAdapter=new OrderItemAdapter(modelList,OrderDetails.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetails.this,RecyclerView.VERTICAL,false));
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(orderItemAdapter);
                                orderItemAdapter.notifyDataSetChanged();


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
                params.put("order_id", Ord_vid);
                params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void getPrescription(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetails.this);
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

                                progressDialog.dismiss();
                                pres_list.setVisibility(View.VISIBLE);
                                JSONArray jsonArray=person.getJSONArray("prescription");
                                for (int i=0;i<jsonArray.length();i++){
                                    OrderStatusPressModel orderStatusPressModel=new OrderStatusPressModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    orderStatusPressModel.setPress_img(jsonObject.getString("prescription"));
                                    orderStatusPressModels.add(orderStatusPressModel);



                                }


                                orderStatusPressAdapter=new OrderStatusPressAdapter(orderStatusPressModels,OrderDetails.this);
                                pres_list.setLayoutManager(new LinearLayoutManager(OrderDetails.this,RecyclerView.HORIZONTAL,false));
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

                params.put("user_id", u_id);
                params.put("oid", String.valueOf(odr_id));
                params.put("order_id", Ord_vid);
                params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderDetails.this);
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
        Intent intent = new Intent(OrderDetails.this, OrderPage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

}
