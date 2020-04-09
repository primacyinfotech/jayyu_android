package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
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

import Adapter.CouponListAdapter;
import Adapter.PrescriptionCouponListAdapter;
import Model.CouponListModel;
import Model.SaveCoupon;
import Model.ViewDialog;

public class PrescriptionOrderSummery extends AppCompatActivity {
    SaveCoupon myDb;
    private ImageView back_button;
    private Animation animationUp;
    private Button submit_btn;
    private Animation animationDown;
    private CheckBox chk_instant;
    private CardView card_view_istant;
    private TextView open_item,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,upper_save_amt,
            customer_name,address_text,email_add,address_edit,items_view;
    private TextView place_apply_coupon,instan_content,disclaimer,type_add,coupon_off_on,sav_prescrtn,
            address_land,address_zipt,address_phone;
    private String order_summery_item_url="https://work.primacyinfotech.com/jaayu/api/addtocart/all";
    private String Order_tiem_total_dataUrl="https://work.primacyinfotech.com/jaayu/api/addtocart/sum_value";
    private  String orderLast_addressUrl= BaseUrl.BaseUrlNew+"order_address_single_last";
    private  String InstantOrderChkUrl=BaseUrl.BaseUrlNew+"instant_chk";
    private  String Change_instant_Url="https://work.primacyinfotech.com/jaayu/api/change_to_instant";
    private  String pass_val_url=BaseUrl.BaseUrlNew+"subscription_status";
    private  String press_Order_url=BaseUrl.BaseUrlNew+"prescription_order";
    private  String instan_content_url=BaseUrl.BaseUrlNew+"instant_content";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    private String coupon_list_url= BaseUrl.BaseUrlNew+"coupon_listing";
    String address,user_name,us_nm,us_add,sing_fullname,all_address,prescription_image,Common_Address,Common_Address2;
    String add_typ,formatted,ad_phone;
    int address_id,address_id_second,sing_add_id,prescription_requird,Addd_Second,Addd_first;
    SharedPreferences prefs_register;
    SharedPreferences prefs_Address;
    SharedPreferences prefs_Address_pin;
    SharedPreferences prefs_Address_second;
    SharedPreferences prefs_Pass_Value1,prefs_Pass_Value2,prefs_Pass_Value3,prefs_Pass_Value4,prefs_Pass_Value;
    private String u_id,check_pincode_Second,check_pincode_first,add_zip,sing_zip_code;
    private String Add_type,a_typ,lan_mark,lan_MArk,l_mark,sing_landmark,sing_ad_typ,
            sing_phone,a_zip,show_coupon;
    String pin_cod,pin_cod2,instant,val,val1,val2,val3,val4,pass_val,pass_val_pref,pref;
    ProgressDialog progressDialog;
    private LinearLayout apply_coupon_btn;
    PrescriptionCouponListAdapter couponListAdapter;
    private ArrayList<CouponListModel> couponListModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_order_summery);
        myDb = new SaveCoupon(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent fetchAddress=getIntent();
        address_id=fetchAddress.getIntExtra("ADDRESS_ID",0);
        address=fetchAddress.getStringExtra("ADDRESS");
        user_name=fetchAddress.getStringExtra("NAME");
        prescription_image=fetchAddress.getStringExtra("Prescription");
        add_zip=fetchAddress.getStringExtra("ADDRESS_zip");
        Add_type=fetchAddress.getStringExtra("ADDRESS_PREF");
        lan_mark=fetchAddress.getStringExtra("ADDRESS_LAND");
        String add_phone=fetchAddress.getStringExtra("ADDRESS_PHONE");

        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        prefs_Address = getSharedPreferences(
                "Address Details", Context.MODE_PRIVATE);
        prefs_Address_pin = getSharedPreferences(
                "Address pin Details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs_Address.edit();
        editor.putString("USER_NAME",user_name);
        editor.putString("USER_ADDRESS",address);
        editor.putString("TYPE_ADDRESS",Add_type);
        editor.putString("LAND_ADDRESS",lan_mark);
        editor.putString("PHONE_ADDRESS",add_phone);
        editor.putInt("FIRST_ADD", address_id);
        editor.putString("Zip_ADD", add_zip);
        editor.commit();
        us_nm=prefs_Address.getString("USER_NAME","");
        us_add=prefs_Address.getString("USER_ADDRESS","");
        Addd_first=prefs_Address.getInt("FIRST_ADD",0);
        a_typ=prefs_Address.getString("TYPE_ADDRESS","");
        lan_MArk=prefs_Address.getString("LAND_ADDRESS","");
        ad_phone=prefs_Address.getString("PHONE_ADDRESS","");
        u_id=prefs_register.getString("USER_ID","");
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        address_edit=(TextView) findViewById(R.id.address_edit);
        submit_btn=(Button)findViewById(R.id.submit_btn);
        mrp_total=(TextView)findViewById(R.id.mrp_total);
        total_save_price=(TextView)findViewById(R.id.total_save_price);
        shipping_charge=(TextView)findViewById(R.id.shipping_charge);
        payable_amount=(TextView)findViewById(R.id.payable_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        discount_limit_amt=(TextView)findViewById(R.id.discount_limit_amt);
        main_pay=(TextView)findViewById(R.id.main_pay);
        customer_name=(TextView)findViewById(R.id.customer_name);
        address_text=(TextView)findViewById(R.id.address_text);
        address_land=(TextView)findViewById(R.id.address_land);
        address_zipt=(TextView)findViewById(R.id.address_zipt);
        address_phone=(TextView)findViewById(R.id.address_phone);
        email_add=(TextView)findViewById(R.id.email_add);
        instan_content=(TextView)findViewById(R.id.instan_content);
        type_add=(TextView)findViewById(R.id.type_add);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        coupon_off_on=(TextView) findViewById(R.id.coupon_off_on);
        chk_instant=(CheckBox)findViewById(R.id.chk_instant);
        card_view_istant=(CardView)findViewById(R.id.card_view_istant);
        apply_coupon_btn=(LinearLayout)findViewById(R.id.apply_coupon_btn);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        card_view_istant.setVisibility(View.GONE);
        Cursor res=myDb.getAllData();
        while (res.moveToNext()){
            show_coupon=res.getString(2);
        }
        if(show_coupon!=null){
            place_apply_coupon.setText(show_coupon);
            coupon_off_on.setVisibility(View.VISIBLE);
        }
        else {
            place_apply_coupon.setText("Apply Coupon");
            coupon_off_on.setVisibility(View.GONE);
        }
        coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                place_apply_coupon.setText("Apply Coupon");
                coupon_off_on.setVisibility(View.GONE);
            }
        });
        apply_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponListModelArrayList=new ArrayList<>();
                final Dialog dialog = new Dialog(PrescriptionOrderSummery.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.coupon_dialog_layout);
                ImageView close_btn=(ImageView)dialog.findViewById(R.id.close_btn);
                final RecyclerView coupon_list=(RecyclerView)dialog.findViewById(R.id.coupon_list);
             /* couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"GOFERCE","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                      "Expire In 2 Days"));
              couponListAdapter=new CouponListAdapter(couponListModelArrayList,CartActivity.this);
              coupon_list.setHasFixedSize(true);
              coupon_list.setLayoutManager(new LinearLayoutManager(CartActivity.this));
              coupon_list.setAdapter(couponListAdapter);
              couponListAdapter.notifyDataSetChanged();*/
                RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,coupon_list_url,
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


                                        JSONArray jsonArray=person.getJSONArray("list");
                                        for(int i=0;i<jsonArray.length();i++){
                                            CouponListModel couponListModel=new CouponListModel();
                                            JSONObject object=jsonArray.getJSONObject(i);
                                            couponListModel.setCoupon_id(object.getInt("id"));
                                            couponListModel.setCoupon_code(object.getString("coupon_code"));
                                            couponListModel.setCoupon_img(object.getString("image"));
                                            couponListModel.setCoupn_code_details(object.getString("sdescr"));
                                            couponListModel.setCoupon_code_des(object.getString("descr"));

                                            couponListModelArrayList.add(couponListModel);

                                        }
                                        couponListAdapter=new PrescriptionCouponListAdapter(couponListModelArrayList,PrescriptionOrderSummery.this);
                                        coupon_list.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
                                        //address_list.setLayoutManager(new LinearLayoutManager(LocationAddress.this));
                                        coupon_list.setLayoutManager(layoutManager);

                                        coupon_list.setAdapter(couponListAdapter);
                                        couponListAdapter.notifyDataSetChanged();

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
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*    place_apply_coupon.setText("Apply Coupon");
            coupon_off_on.setImageResource(R.drawable.rigth_arrow);
        }*/
                myDb.deleteData();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                place_apply_coupon.setText("Apply Coupon");
                coupon_off_on.setVisibility(View.GONE);


            }
        });
        getInstantContent();
        getDisclimer();



        chk_instant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_instant.isChecked()){
                   instant="1";
                }
              /*  else {
                  instant="0";
                }*/
            }
        });
        address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLocationAddress=new Intent(PrescriptionOrderSummery.this,PrescriptionLocationAddress.class);
                startActivity(goToLocationAddress);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescriptionOrderSummery.this, OrderPrescriptionInfo.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(Addd_Second).equals("0")){
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(PrescriptionOrderSummery.this, "Address Should be Required......");
                }
                else {
                    if(String.valueOf(Addd_first).equals(String.valueOf(address_id))){
                        Common_Address=String.valueOf(Addd_first);
                        if(!Common_Address.equals("0")){
                           /* Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                            intentGotoDelivery.putExtra("Address", String.valueOf(Addd_first));
                            intentGotoDelivery.putExtra("userID", u_id);
                            //intentGotoDelivery.putExtra("prescription_img", prescription_image);
                            startActivity(intentGotoDelivery);
                            finish();*/
                            RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
                            StringRequest postRequest = new StringRequest(Request.Method.POST,press_Order_url,
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
                                                String OrdID=person.getString("orderid");
                                               /* String message=person.getString("message");
                                                Toast.makeText(getApplicationContext(),OrdID+" "+message,Toast.LENGTH_LONG).show();*/
                                                    Intent goToIthankU=new Intent(PrescriptionOrderSummery.this,PrescriptionThankYouPage.class);
                                                    goToIthankU.putExtra("OrderID",OrdID);
                                                    startActivity(goToIthankU);
                                                    overridePendingTransition(0,0);
                                                    finish();

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




                                    params.put("user_id" ,u_id);
                                    params.put("aId1" ,String.valueOf(Addd_first));
                                    if(instant!=null){
                                        params.put("instant" ,instant);
                                    }
                                    else {
                                        params.put("instant" ,"0");
                                    }
                                    //params.put("instant" ,instant);
                                    params.put("source" ,"1");


                                    return params;
                                }

                            };
                            requestQueue.add(postRequest);

                        }
                        else {
                         /*   Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                            intentGotoDelivery.putExtra("Address", String.valueOf(Addd_Second));
                            intentGotoDelivery.putExtra("userID", u_id);
                            // intentGotoDelivery.putExtra("prescription_img", prescription_image);
                            startActivity(intentGotoDelivery);
                            finish();*/
                            RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
                            StringRequest postRequest = new StringRequest(Request.Method.POST,press_Order_url,
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
                                                    String OrdID=person.getString("orderid");
                                                    /*String message=person.getString("message");
                                                    Toast.makeText(getApplicationContext(),OrdID+" "+message,Toast.LENGTH_LONG).show();*/
                                                    Intent goToIthankU=new Intent(PrescriptionOrderSummery.this,PrescriptionThankYouPage.class);
                                                    goToIthankU.putExtra("OrderID",OrdID);
                                                    startActivity(goToIthankU);
                                                    overridePendingTransition(0,0);
                                                    finish();




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




                                    params.put("user_id" ,u_id);
                                    params.put("aId1" , String.valueOf(Addd_Second));
                                    params.put("instant" ,"0");
                                    params.put("source" ,"1");


                                    return params;
                                }

                            };
                            requestQueue.add(postRequest);
                        }

                    }
                }
            }
        });
        Order_address();
        last_address();
        VisibleOrNotVisibleCheckBox();
        ViewItems();
    }
    private void  Order_address() {
        prefs_Address_second = getSharedPreferences(
                "SECOND_ADDRESS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs_Address_second.edit();
        editor.remove("SECOND_ADD");
        editor.commit();
        if (us_nm.equals("") && us_add.equals("")) {
            String unknown = sing_fullname;
            String unknown_add = all_address;
            String unkown_add_type = add_typ;
            String unkown_land = l_mark;
            String unkown_zip = sing_zip_code;
            String unkown_ph = sing_phone;
            customer_name.setText(unknown);
            address_text.setText(unknown_add);
            type_add.setText(unkown_add_type);
            address_zipt.setText(unkown_zip);
            address_land.setText(unkown_land);
            address_phone.setText(unkown_ph);
        } else {
            customer_name.setText(us_nm);
            address_text.setText(us_add);
            type_add.setText(a_typ);
            address_zipt.setText(a_zip);
            address_land.setText(lan_MArk);
            address_phone.setText(ad_phone);
        }
    }
        private void last_address(){
        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,orderLast_addressUrl,
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
                                JSONObject object=person.getJSONObject("address");
                                sing_add_id=object.getInt("id");
                                address_id_second=sing_add_id;

                                String sing_first_nm=object.getString("first_name");
                                // String sing_last_nm=object.getString("last_name");
                                 sing_phone=object.getString("phone");
                                // String sing_email=object.getString("email");
                                String sing_address=object.getString("address");
                                //String sing_country=object.getString("country");
                                // String sing_state=object.getString("state");
                                // String sing_city=object.getString("city");
                                sing_landmark=object.getString("landmark");
                                String sing_ad_typ=object.getString("atype");
                                add_typ=sing_ad_typ;
                                sing_zip_code=object.getString("zip_code");
                                l_mark=sing_landmark;
                                sing_fullname=sing_first_nm;
                                all_address=sing_address;
                                prefs_Address_second = getSharedPreferences(
                                        "SECOND_ADDRESS", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs_Address_second.edit();
                                editor.putInt("SECOND_ADD", address_id_second);
                                editor.putString("PIN_CODE",sing_zip_code);
                                editor.commit();
                                Addd_Second=prefs_Address_second.getInt("SECOND_ADD",0);


                                System.out.println("id"+address_id_second+Addd_first);
                                prefs_Address = getSharedPreferences(
                                        "Address Details", Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = prefs_Address.edit();
                                e.remove("FIRST_ADD");
                                e.commit();

                                if (us_nm.equals("")&&us_add.equals("")) {
                                    String unknown=sing_fullname;
                                    String unknown_add=all_address;
                                    String unkown_add_type=add_typ;
                                    String unkown_land=l_mark;
                                    String unkown_zip=sing_zip_code;
                                    String unkown_ph=sing_phone;
                                    customer_name.setText(unknown);
                                    address_text.setText(unknown_add);
                                    type_add.setText(unkown_add_type);
                                    address_zipt.setText(unkown_zip);
                                    address_land.setText(unkown_land);
                                    address_phone.setText(unkown_ph);
                                }
                                else {
                                    customer_name.setText(us_nm);
                                    address_text.setText(us_add);
                                    type_add.setText(a_typ);

                                }

                                // email_add.setText(email);



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
    private void  VisibleOrNotVisibleCheckBox(){
        check_pincode_first=prefs_Address.getString("Zip_ADD","");
        check_pincode_Second=prefs_Address_second.getString("PIN_CODE","");
        pin_cod=check_pincode_first;
        pin_cod2=check_pincode_Second;
        if(pin_cod.matches(check_pincode_first)){
            RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST,InstantOrderChkUrl,
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

                                    card_view_istant.setVisibility(View.VISIBLE);
                                 /* prefs_Address_second = getSharedPreferences(
                                          "SECOND_ADDRESS", Context.MODE_PRIVATE);
                                  SharedPreferences.Editor editor2 = prefs_Address_second.edit();
                                  editor2.remove("PIN_CODE");
                                  editor2.apply();*/
                                }
                           /* else {
                                card_view_istant.setVisibility(View.GONE);
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



                    params.put("pincode" ,pin_cod);
                    params.put("user_id" ,u_id);


                    return params;
                }

            };
            requestQueue.add(postRequest);
        }
        if(pin_cod2.matches(check_pincode_Second)){
            RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST,InstantOrderChkUrl,
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

                                    card_view_istant.setVisibility(View.VISIBLE);
                                /*  prefs_Address = getSharedPreferences(
                                          "Address Details", Context.MODE_PRIVATE);
                                  SharedPreferences.Editor edito = prefs_Address.edit();
                                  edito.remove("Zip_ADD");
                                  edito.apply();*/
                                    prefs_Address_second = getSharedPreferences(
                                            "SECOND_ADDRESS", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = prefs_Address_second.edit();
                                    editor2.remove("PIN_CODE");
                                    editor2.apply();
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



                    params.put("pincode" ,pin_cod2);
                    params.put("user_id" ,u_id);


                    return params;
                }

            };
            requestQueue.add(postRequest);
        }

    }
    private void ViewItems(){
        items_view=(TextView)findViewById(R.id.items_view);
        Intent fetchPassVal =getIntent();
        val=fetchPassVal.getStringExtra("Value");
        pref=fetchPassVal.getStringExtra("Pref");
       // Toast.makeText(getApplicationContext(),val+pref,Toast.LENGTH_LONG).show();
        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,pass_val_url,
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
                                String result=person.getString("result");
                                String msg=person.getString("message");

                                prefs_Pass_Value = getSharedPreferences(
                                        "PASS_DATA", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs_Pass_Value.edit();
                                editor.putString("Pass_Value",result);
                                editor.putString("Pass_Value_pref",msg);
                                editor.commit();
                                pass_val=prefs_Pass_Value.getString("Pass_Value","");
                                pass_val_pref=prefs_Pass_Value.getString("Pass_Value_pref","");
                                items_view.setText(pass_val_pref+"\n"+pass_val);

                            }
                         /* else {
                                prefs_Pass_Value = getSharedPreferences(
                                        "PASS_DATA", Context.MODE_PRIVATE);
                                pass_val=prefs_Pass_Value.getString("Pass_Value","");
                                items_view.setText(pass_val);
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



                params.put("dur" ,val);
                params.put("subs" ,pref);



                return params;
            }

        };
        requestQueue.add(postRequest);
     /*   prefs_Pass_Value = getSharedPreferences(
                "PASS_DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs_Pass_Value.edit();
        editor.putString("Pass_Value",val);
        editor.commit();
        pass_val=prefs_Pass_Value.getString("Pass_Value","");
        prefs_Pass_Value1 = getSharedPreferences(
                "PASS_DATA_1", Context.MODE_PRIVATE);
        prefs_Pass_Value2 = getSharedPreferences(
                "PASS_DATA_2", Context.MODE_PRIVATE);
        prefs_Pass_Value3 = getSharedPreferences(
                "PASS_DATA_3", Context.MODE_PRIVATE);
        prefs_Pass_Value4 = getSharedPreferences(
                "PASS_DATA_4", Context.MODE_PRIVATE);

        val1=prefs_Pass_Value1.getString("VALUE_ONE","");
        val2=prefs_Pass_Value2.getString("VALUE_TWO","");
        val3=prefs_Pass_Value3.getString("VALUE_THREE","");
        val4=prefs_Pass_Value4.getString("VALUE_FOUR","");
        if(pass_val.equals(val1)){
            items_view.setText(val1);
        }
        if (pass_val.equals(val2)){
            items_view.setText(val2);
        }
         if(pass_val.equals(val3)){
            items_view.setText(val3);
        }
         if(pass_val.equals(val4)){
            items_view.setText(val4);
        }*/
        prefs_Pass_Value = getSharedPreferences(
                "PASS_DATA", Context.MODE_PRIVATE);
        pass_val=prefs_Pass_Value.getString("Pass_Value","");
        pass_val_pref=prefs_Pass_Value.getString("Pass_Value_pref","");
        items_view.setText(pass_val_pref+"\n"+pass_val);

    }
    private  void  getInstantContent(){
        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,instan_content_url,
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
                                JSONObject ins_con=person.getJSONObject("instt");
                                String content_ins=ins_con.getString("body");
                                instan_content.setText(content_ins);
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
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(PrescriptionOrderSummery.this);
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
                                disclaimer.setText(content_ins);
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
        Intent intent = new Intent(PrescriptionOrderSummery.this, OrderPrescriptionInfo.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

}
