package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.OrderSummeryAdapter;
import Model.OrderSummeryModel;
import Model.SaveCoupon;
import Model.ViewDialog;

public class OrderSummery extends AppCompatActivity {
    SaveCoupon myDb;
    private ArrayList<OrderSummeryModel> modelList;
    OrderSummeryAdapter orderSummeryAdapter;
    RecyclerView recyclerView;
    private ImageView back_button;
    private Animation animationUp;
    private Button submit_btn;
    private Animation animationDown;
    private CheckBox chk_instant;
    private CardView card_view_istant;
    private TextView open_item,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,upper_save_amt,
            customer_name,address_text,email_add,address_edit,place_apply_coupon,instan_content,disclaimer,type_add,coupon_off_on,sav_prescrtn,
            address_land,address_zipt,address_phone,free_charge;
    private String order_summery_item_url= BaseUrl.BaseUrlNew+"addtocart/all";
    private String Order_tiem_total_dataUrl=BaseUrl.BaseUrlNew+"addtocart/sum_value";
    private  String orderLast_addressUrl=BaseUrl.BaseUrlNew+"order_address_single_last";
    private  String InstantOrderChkUrl=BaseUrl.BaseUrlNew+"instant_chk";
    private  String Change_instant_Url=BaseUrl.BaseUrlNew+"change_to_instant";
    private  String instan_content_url=BaseUrl.BaseUrlNew+"instant_content";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    private  String free_delivery_url=BaseUrl.BaseUrlNew+"delivery_charge";
    private  String order_addres_chang=BaseUrl.BaseUrlNew+"order_addres_change";
    String address,user_name,us_nm,us_add,sing_fullname,all_address,prescription_image,Common_Address,Common_Address2,add_typ,formatted,ad_phone;
    int address_id,address_id_second,sing_add_id,prescription_requird,Addd_Second,Addd_first;
    SharedPreferences prefs_register;
    SharedPreferences prefs_Address;
    SharedPreferences prefs_Address_pin;
    SharedPreferences prefs_Address_second;
    private String u_id,check_pincode_Second,check_pincode_first,add_zip,sing_zip_code,Add_type,a_typ,lan_mark,lan_MArk,l_mark,sing_landmark,sing_ad_typ,
            sing_phone,a_zip;
    String pin_cod,pin_cod2,show_coupon,ins;
    ProgressDialog progressDialog;
    ProgressDialog progressDialogLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialogLoader = new ProgressDialog(OrderSummery.this);
        progressDialogLoader.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialogLoader.show();
        progressDialogLoader.setMessage("Downloading...");
        progressDialogLoader.setCancelable(false);
        myDb = new SaveCoupon(this);
        Intent fetchAddress=getIntent();
        prescription_requird=fetchAddress.getIntExtra("PRES_REQ",0);
        address_id=fetchAddress.getIntExtra("ADDRESS_ID",0);
       /* address=fetchAddress.getStringExtra("ADDRESS");
        user_name=fetchAddress.getStringExtra("NAME");
        prescription_image=fetchAddress.getStringExtra("Prescription");
        add_zip=fetchAddress.getStringExtra("ADDRESS_zip");
        Add_type=fetchAddress.getStringExtra("ADDRESS_PREF");
        lan_mark=fetchAddress.getStringExtra("ADDRESS_LAND");
        String add_phone=fetchAddress.getStringExtra("ADDRESS_PHONE");*/
        upper_save_amt=(TextView)findViewById(R.id.upper_save_amt);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_instant"));
      /*  SharedPreferences preferences = getSharedPreferences("PRESCRIPTION REQUIRED", Context.MODE_PRIVATE);
        prescription_requird=preferences.getInt("Prescrip_required",0);*/

        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        prefs_Address = getSharedPreferences(
                "Address Details", Context.MODE_PRIVATE);
        prefs_Address_pin = getSharedPreferences(
                "Address pin Details", Context.MODE_PRIVATE);
       /* SharedPreferences.Editor editor = prefs_Address.edit();
        editor.putString("USER_NAME",user_name);
        editor.putString("USER_ADDRESS",address);
        editor.putString("TYPE_ADDRESS",Add_type);
        editor.putString("LAND_ADDRESS",lan_mark);
        editor.putString("PHONE_ADDRESS",add_phone);
        editor.putInt("FIRST_ADD", address_id);
        editor.putString("Zip_ADD", add_zip);
        editor.commit();*/
       /* us_nm=prefs_Address.getString("USER_NAME","");
        us_add=prefs_Address.getString("USER_ADDRESS","");
        a_typ=prefs_Address.getString("TYPE_ADDRESS","");
        lan_MArk=prefs_Address.getString("LAND_ADDRESS","");
        Addd_first=prefs_Address.getInt("FIRST_ADD",0);
        ad_phone=prefs_Address.getString("PHONE_ADDRESS","");
        a_zip=prefs_Address.getString("Zip_ADD","");*/

        u_id=prefs_register.getString("USER_ID","");
        animationUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        recyclerView=(RecyclerView)findViewById(R.id.rv_items);
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
        chk_instant=(CheckBox)findViewById(R.id.chk_instant);
        card_view_istant=(CardView)findViewById(R.id.card_view_istant);
        card_view_istant.setVisibility(View.GONE);
        coupon_off_on=(TextView) findViewById(R.id.coupon_off_on);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        sav_prescrtn=(TextView)findViewById(R.id.sav_prescrtn);
        free_charge=(TextView) findViewById(R.id.free_charge);
        Cursor res=myDb.getAllData();
        getInstantContent();
        getDisclimer();
        getFerrCharge();
        while (res.moveToNext()){
          show_coupon=res.getString(2);
        }
        if(show_coupon!=null){
            place_apply_coupon.setText(show_coupon);
            coupon_off_on.setVisibility(View.GONE);
        }
        else {
            place_apply_coupon.setText("Coupon Not Applied");
            coupon_off_on.setVisibility(View.GONE);
        }
        coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb.deleteData();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_LONG).show();
                place_apply_coupon.setText("Coupon Not Applied");
                coupon_off_on.setVisibility(View.GONE);
            }
        });

        NormalCheckboxtest();
        chk_instant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_instant.isChecked()){
                    ins="1";
                    RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
                    StringRequest postRequest = new StringRequest(Request.Method.POST,Change_instant_Url,
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

                                            progressDialog = new ProgressDialog(OrderSummery.this);
                                            progressDialog.setMessage("Loading..."); // Setting Message
                                           // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);
                                            new Thread(new Runnable() {
                                                public void run() {
                                                    try {

                                                        Intent goToRefresh=new Intent("message_instant");
                                                        LocalBroadcastManager.getInstance(OrderSummery.this).sendBroadcast(goToRefresh);
                                                        overridePendingTransition(0,0);

                                                        Thread.sleep(1000);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    progressDialog.dismiss();



                                                }
                                            }).start();
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
                            params.put("instant", "1");


                            return params;
                        }
                    };

                    requestQueue.add(postRequest);
                }
                else {
                    ins="0";
                    RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
                    StringRequest postRequest = new StringRequest(Request.Method.POST,Change_instant_Url,
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

                                            progressDialog = new ProgressDialog(OrderSummery.this);
                                            progressDialog.setMessage("Loading..."); // Setting Message
                                            // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                            progressDialog.show(); // Display Progress Dialog
                                            progressDialog.setCancelable(false);
                                            new Thread(new Runnable() {
                                                public void run() {
                                                    try {

                                                        Intent goToRefresh=new Intent("message_instant");
                                                        LocalBroadcastManager.getInstance(OrderSummery.this).sendBroadcast(goToRefresh);
                                                        overridePendingTransition(0,0);

                                                        Thread.sleep(1000);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    progressDialog.dismiss();



                                                }
                                            }).start();
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
                            params.put("instant", "0");


                            return params;
                        }
                    };

                    requestQueue.add(postRequest);
                }
            }
        });
        getCartOrder();
        calcutate_section();
        Order_address();
       // last_address();
        //VisibleOrNotVisibleCheckBox();
       // open_item=(TextView)findViewById(R.id.open_item);
       // recyclerView.setVisibility(View.GONE);
        /*open_item.setOnClickListener(new View.OnClickListener() {
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
        address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLocationAddress=new Intent(OrderSummery.this,LocationAddress.class);
                startActivity(goToLocationAddress);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummery.this, CartActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                   /* else if(String.valueOf(Addd_Second).equals("1"))   {

                        Intent intentGotoDelivery=new Intent(OrderSummery.this,SubscriptionDelivery.class);
                        intentGotoDelivery.putExtra("Address",String.valueOf(Addd_Second));
                        intentGotoDelivery.putExtra("userID",u_id);
                        startActivity(intentGotoDelivery);
                        finish();

                    }*/
                  /* if(String.valueOf(Addd_first).equals(String.valueOf(address_id))){
                        Common_Address=String.valueOf(Addd_first);
                        if(!Common_Address.equals("0")){
                            Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                            intentGotoDelivery.putExtra("Address", String.valueOf(Addd_first));
                            intentGotoDelivery.putExtra("userID", u_id);
                            intentGotoDelivery.putExtra("prescription_img", prescription_image);
                            startActivity(intentGotoDelivery);
                            finish();
                        }
                        else {
                            Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                            intentGotoDelivery.putExtra("Address", String.valueOf(Addd_Second));
                            intentGotoDelivery.putExtra("userID", u_id);
                            intentGotoDelivery.putExtra("prescription_img", prescription_image);
                            startActivity(intentGotoDelivery);
                            finish();
                        }

                   }*/

                 /*  else if(String.valueOf(Addd_Second).equals(String.valueOf(address_id_second))){
                         Common_Address2=String.valueOf(Addd_Second);
                         if(!Common_Address2.equals("0")){

                         }


                   }*/
                   /* if(String.valueOf(Addd_Second).equals("0")){
                       ViewDialog alert = new ViewDialog();
                       alert.showDialog(OrderSummery.this, "Address Should be Required......");
                   }
                    else {*/
                        /*if(String.valueOf(Addd_first).equals(String.valueOf(address_id))) {*/
                Addd_first=prefs_Address.getInt("FIRST_ADD",0);
                            Common_Address = String.valueOf(Addd_first);
                            if (!Common_Address.equals("0")) {
                                Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                                intentGotoDelivery.putExtra("Address", String.valueOf(Addd_first));
                                intentGotoDelivery.putExtra("userID", u_id);
                                intentGotoDelivery.putExtra("INSTANT", ins);
                                intentGotoDelivery.putExtra("address_id_table", address_id);
                                startActivity(intentGotoDelivery);
                                finish();
                            } else {
                                Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                                intentGotoDelivery.putExtra("Address", String.valueOf(Addd_Second));
                                intentGotoDelivery.putExtra("userID", u_id);
                                intentGotoDelivery.putExtra("INSTANT", ins);
                                // intentGotoDelivery.putExtra("prescription_img", prescription_image);
                                startActivity(intentGotoDelivery);
                                finish();
                            }




              /*  if (String.valueOf(Addd_Second).equals("0")) {
                    // Toast.makeText(getApplicationContext(),"ADD REQUIRED",Toast.LENGTH_LONG).show();
                    ViewDialog alert = new ViewDialog();
                    alert.showDialog(OrderSummery.this, "Address Should be Required......");

                }
                else {
                    Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);

                    intentGotoDelivery.putExtra("Address", String.valueOf(Addd_first));

                    intentGotoDelivery.putExtra("userID", u_id);
                    intentGotoDelivery.putExtra("prescription_img", prescription_image);
                    startActivity(intentGotoDelivery);
                    finish();
                }*/
                 /*if(String.valueOf(Addd_Second).equals(String.valueOf(address_id_second)))  {
                    Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);

                    intentGotoDelivery.putExtra("userID", u_id);
                    intentGotoDelivery.putExtra("prescription_img", prescription_image);
                    startActivity(intentGotoDelivery);
                    finish();
                }
                if(String.valueOf(Addd_first).equals(String.valueOf(address_id))){
                    Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                    intentGotoDelivery.putExtra("Address", String.valueOf(Addd_first));
                    intentGotoDelivery.putExtra("userID", u_id);
                    intentGotoDelivery.putExtra("prescription_img", prescription_image);
                    startActivity(intentGotoDelivery);
                    finish();
                }*/


              /*  Intent intentGotoDelivery = new Intent(OrderSummery.this, SubscriptionDelivery.class);
                intentGotoDelivery.putExtra("Address", String.valueOf(Addd_Second));
                intentGotoDelivery.putExtra("userID", u_id);
                intentGotoDelivery.putExtra("prescription_img", prescription_image);
                startActivity(intentGotoDelivery);
                finish();*/

            }





        });

     /*   modelList.add(new OrderSummeryModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
        modelList.add(new OrderSummeryModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
        modelList.add(new OrderSummeryModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
*/
       /* orderSummeryAdapter=new OrderSummeryAdapter(modelList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderSummeryAdapter);
        orderSummeryAdapter.notifyDataSetChanged();*/

    }
    private void getFerrCharge(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,free_delivery_url,
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
                                String ferr_charg=person.getString("free_delivery_charge");

                                free_charge.setText("Free Delivery above Rs."+ferr_charg+" | Save more!");
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
    private void  getCartOrder(){
        modelList=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,order_summery_item_url,
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
                                progressDialogLoader.dismiss();
                                JSONArray jsonArray=person.getJSONArray("cart");

                                for(int i=0;i<jsonArray.length();i++){
                                    OrderSummeryModel cartModel=new OrderSummeryModel();
                                    JSONObject serch=jsonArray.getJSONObject(i);
                                   cartModel.setItem_id(serch.getInt("id"));
                                    cartModel.setOder_id(serch.getInt("product_id"));
                                    cartModel.setCart_item(serch.getString("title"));
                                    cartModel.setUnit(serch.getString("box"));
                                    cartModel.setCompany_name(serch.getString("com_name"));
                                    DecimalFormat format_per = new DecimalFormat("##.##");
                                     formatted = format_per.format(serch.getDouble("save_percent"));
                                    String save_amt=format_per.format(serch.getDouble("save_amount"));
                                    String mrp_val=format_per.format(serch.getDouble("mrp"));
                                    String price_amt=format_per.format(serch.getDouble("price"));

                                    cartModel.setSaveings_percentage(formatted+" %");
                                    cartModel.setSave_amount(save_amt);
                                    cartModel.setTotal(mrp_val);
                                    cartModel.setPrice_amt(price_amt);
                                /*cartModel.setSaveings_percentage(serch.getDouble("save_percent"));
                                cartModel.setSave_amount(serch.getDouble("save_amount"));
                                cartModel.setTotal(serch.getDouble("mrp"));*/
                                    cartModel.setQuantity(serch.getString("quantity"));
                                    modelList.add(cartModel);
                                }
                                orderSummeryAdapter=new OrderSummeryAdapter(modelList,OrderSummery.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderSummery.this));
                                recyclerView.setAdapter(orderSummeryAdapter);
                                orderSummeryAdapter.notifyDataSetChanged();
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
    private void  calcutate_section() {
        RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Order_tiem_total_dataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressDialogLoader.dismiss();
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            Double total_mrp_value = person.getDouble("MRP");
                            DecimalFormat format = new DecimalFormat("##.##");
                            Double saving_parcent=person.getDouble("saving_parcent");
                            String tot_mrp = format.format(total_mrp_value);
                            String sav_persnt=format.format(saving_parcent);
                            mrp_total.setText(tot_mrp);

                            Double tot_save_amt = person.getDouble("saving");
                            String sav_amt_tot = format.format(tot_save_amt);
                            save_amount.setText(sav_amt_tot);
                            total_save_price.setText(sav_amt_tot);
                            Double ship_charge = person.getDouble("shipping");
                            String ship_crhg = format.format(ship_charge);
                            shipping_charge.setText(ship_crhg);
                            Double pay_amount = person.getDouble("Total");
                            String p_amt = format.format(pay_amount);
                            String main_pay_amt=format.format(Math.round(pay_amount));
                            payable_amount.setText(p_amt);
                            main_pay.setText("\u20B9"+main_pay_amt);
                            upper_save_amt.setText("You are Saving "+"\u20B9"+" "+sav_amt_tot+" "+"on this order.");
                            //sav_prescrtn.setText("Saving @ "+sav_persnt +" %");
                            sav_prescrtn.setText("Saving");


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
                }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }




    };
        requestQueue.add(postRequest);
    }
    private void  Order_address(){
        prefs_Address_second = getSharedPreferences(
                "SECOND_ADDRESS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs_Address_second.edit();
        editor.remove("SECOND_ADD");
        editor.commit();
      /*  if (us_nm.equals("")&&us_add.equals("")&&a_typ.equals("")&&lan_MArk.equals("")&& ad_phone.equals("")&&a_zip.equals("")) {
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
            address_zipt.setText(a_zip);
            address_land.setText(lan_MArk);
            address_phone.setText(ad_phone);
            RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                                    progressDialogLoader.dismiss();
                                    JSONObject object=person.getJSONObject("address");






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
        }*/
        if (address_id==0) {
           /* String unknown=sing_fullname;
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
            address_phone.setText(unkown_ph);*/
            last_address();
        }
        else {
           /* customer_name.setText(us_nm);
            address_text.setText(us_add);
            type_add.setText(a_typ);
            address_zipt.setText(a_zip);
            address_land.setText(lan_MArk);
            address_phone.setText(ad_phone);*/
            RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST,order_addres_chang,
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
                                    progressDialogLoader.dismiss();
                                  //  JSONObject object=person.getJSONObject("address");
                                   int a_id=person.getInt("address_id");
                                    us_nm=person.getString("name");
                                    us_add=person.getString("address");
                                    a_typ=person.getString("address type");
                                 String   aa_zip=person.getString("pincode");
                                    lan_MArk=person.getString("landmark");
                                    ad_phone=person.getString("phone");
                                    SharedPreferences.Editor editor = prefs_Address.edit();
                                    editor.putString("USER_NAME",us_nm);
                                    editor.putString("USER_ADDRESS",us_add);
                                    editor.putString("TYPE_ADDRESS",a_typ);
                                    editor.putString("LAND_ADDRESS",lan_mark);
                                    editor.putString("PHONE_ADDRESS",ad_phone);
                                    editor.putInt("FIRST_ADD", a_id);
                                    editor.putString("Zip_ADD", aa_zip);
                                 /*   Addd_first=prefs_Address.getInt("FIRST_ADD",0);*/
                                   // a_zip=prefs_Address.getString("Zip_ADD","");
                                    VisibleOrNotVisibleCheckBox();
                                    editor.commit();
                                    customer_name.setText(us_nm);
                                    address_text.setText(us_add);
                                    type_add.setText(a_typ);
                                    address_zipt.setText(aa_zip);
                                    address_land.setText(lan_MArk);
                                    address_phone.setText(ad_phone);

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
                    params.put("aId", String.valueOf(address_id));


                    return params;
                }
            };

            requestQueue.add(postRequest);
        }


    }
    private void last_address(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                                progressDialogLoader.dismiss();
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
                                sing_landmark=object.getString("landmark");
                                String sing_ad_typ=object.getString("atype");
                                add_typ=sing_ad_typ;
                                sing_zip_code=object.getString("zip_code");
                                l_mark=sing_landmark;
                                sing_fullname=sing_first_nm;
                               // all_address=sing_address+","+"\n"+"Pin Code-"+sing_zip_code+","+"\n"+"phone:"+sing_phone;
                                all_address=sing_address;
                                //all_address=sing_address+"\n"+sing_zip_code+","+"\n"+sing_phone;
                                prefs_Address_second = getSharedPreferences(
                                        "SECOND_ADDRESS", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs_Address_second.edit();
                                editor.putInt("SECOND_ADD", address_id_second);
                                 editor.putString("PIN_CODE",sing_zip_code);
                                editor.commit();
                                Addd_Second=prefs_Address_second.getInt("SECOND_ADD",0);
                                //check_pincode_Second=prefs_Address_second.getString("PIN_CODE","");
                               /* if(a_zip.equals("")){
                                    SecondVisibilityCheck(sing_zip_code);
                                }*/
                                if(check_pincode_first==null){
                                    SecondVisibilityCheck(sing_zip_code);
                                }
                              /*  SecondVisibilityCheck(sing_zip_code);*/
                                System.out.println("id"+address_id_second+Addd_first);
                                prefs_Address = getSharedPreferences(
                                        "Address Details", Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = prefs_Address.edit();
                                e.remove("FIRST_ADD");
                                e.commit();
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

                               /* if (String.valueOf(address_id).equals("")) {
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
                                   *//* customer_name.setText(us_nm);
                                    address_text.setText(us_add);
                                    type_add.setText(a_typ);*//*
                                }*/

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
       // check_pincode_Second=prefs_Address_second.getString("PIN_CODE","");
        pin_cod=check_pincode_first;
     //   pin_cod2=check_pincode_Second;
      if(pin_cod.matches(check_pincode_first)){
          RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                                  progressDialogLoader.dismiss();
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
     /* if(pin_cod2.matches(check_pincode_Second)){
          RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                                  progressDialogLoader.dismiss();
                                  card_view_istant.setVisibility(View.VISIBLE);
                                *//*  prefs_Address = getSharedPreferences(
                                          "Address Details", Context.MODE_PRIVATE);
                                  SharedPreferences.Editor edito = prefs_Address.edit();
                                  edito.remove("Zip_ADD");
                                  edito.apply();*//*
                                  prefs_Address_second = getSharedPreferences(
                                          "SECOND_ADDRESS", Context.MODE_PRIVATE);
                                  SharedPreferences.Editor editor2 = prefs_Address_second.edit();
                                  editor2.remove("PIN_CODE");
                                  editor2.apply();
                              }
                            *//*else {
                                card_view_istant.setVisibility(View.GONE);
                            }
*//*



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
      }*/

    }
    private void  SecondVisibilityCheck(final String second_pin){
       /* check_pincode_Second=prefs_Address_second.getString("PIN_CODE","");
        pin_cod2=check_pincode_Second;*/
        if(second_pin!=null){
            RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                                    progressDialogLoader.dismiss();
                                    card_view_istant.setVisibility(View.VISIBLE);
                               /*   prefs_Address = getSharedPreferences(
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



                    params.put("pincode" ,second_pin);
                    params.put("user_id" ,u_id);


                    return params;
                }

            };
            requestQueue.add(postRequest);
        }
    }



private  void  getInstantContent(){
    RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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
                            Spanned htmlAsSpanned = Html.fromHtml(content_ins);
                            instan_content.setText(String.valueOf(htmlAsSpanned));
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
    RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
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

private void NormalCheckboxtest(){
    RequestQueue requestQueue = Volley.newRequestQueue(OrderSummery.this);
    StringRequest postRequest = new StringRequest(Request.Method.POST,Change_instant_Url,
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

                            progressDialog = new ProgressDialog(OrderSummery.this);
                            progressDialog.setMessage("Loading..."); // Setting Message
                            // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            progressDialog.show(); // Display Progress Dialog
                            progressDialog.setCancelable(false);
                            new Thread(new Runnable() {
                                public void run() {
                                    try {

                                        Intent goToRefresh=new Intent("message_instant");
                                        LocalBroadcastManager.getInstance(OrderSummery.this).sendBroadcast(goToRefresh);
                                        overridePendingTransition(0,0);

                                        Thread.sleep(1000);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    progressDialog.dismiss();



                                }
                            }).start();
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
            params.put("instant", "0");


            return params;
        }
    };

    requestQueue.add(postRequest);
}


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            calcutate_section();
        }
    };
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderSummery.this, CartActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
