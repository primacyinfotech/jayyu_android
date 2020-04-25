package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.jaayu.Model.PrescriptionReqDatabase;

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

public class OrderReview extends AppCompatActivity {
    SaveCoupon myDb;
    PrescriptionReqDatabase prescriptionReqDatabase;
    private ArrayList<OrderSummeryModel> modelList;
    RecyclerView pres_list;
    OrderSummeryAdapter orderSummeryAdapter;
    RecyclerView recyclerView;
    private ImageView back_button;
    private Button submit_btn;
    private CardView card_view_istant,card_view_date;
    private TextView open_item,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,upper_save_amt,sav_prescrtn,coupon_off_on,
            customer_name,address_text,email_add,address_edit,estimated_date,place_apply_coupon, disclaimer, address_land,address_zipt,address_phone,type_add,
            type_delivery,interval_delivery,free_charge;
    private String order_summery_item_url= BaseUrl.BaseUrlNew+"addtocart/all";
    private String Order_tiem_total_dataUrl=BaseUrl.BaseUrlNew+"addtocart/sum_value";
    private  String orderLast_addressUrl=BaseUrl.BaseUrlNew+"order_address_single_last";
    private  String orderSingle_addressUrl=BaseUrl.BaseUrlNew+"order_address_single";
    private  String InstantOrderChkUrl="https://work.primacyinfotech.com/jaayu/api/instant_chk";
    private  String Change_instant_Url="https://work.primacyinfotech.com/jaayu/api/change_to_instant";
    private  String delivery_address_Url=BaseUrl.BaseUrlNew+"delivery_date";
    private  String Order_confirm_Url=BaseUrl.BaseUrlNew+"order_conform";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    private  String free_delivery_url=BaseUrl.BaseUrlNew+"delivery_charge";
    String user_id,user_add,day_time,duration,cod,net_bank,presc_img,show_coupon,coupon_id;
    String address,user_name,us_nm,us_add,sing_fullname,all_address,prescription_image,Common_Address,Common_Address2;
    int address_id,address_id_second,sing_add_id,prescription_requird,Addd_Second,Addd_first;
    SharedPreferences prefs_register;
    SharedPreferences prefs_Address;
    SharedPreferences prefs_Address_pin;
    SharedPreferences prefs_Address_second;
    private String u_id,check_pincode_Second,check_pincode_first,add_zip,aId,note,sing_zip_code,sing_phone,sing_lanmark,
            a_typ,lan_mark,lan_MArk,l_mark,sing_landmark,sing_ad_typ,ad_phone,
            a_zip;
    ProgressDialog progressDialog;
    private EditText additional_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prescriptionReqDatabase=new PrescriptionReqDatabase(this);
        progressDialog = new ProgressDialog(OrderReview.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        myDb = new SaveCoupon(this);
        Intent passDataFromDeliveryPage=getIntent();
        user_id=passDataFromDeliveryPage.getStringExtra("User_ID");
        user_add=passDataFromDeliveryPage.getStringExtra("User_add");
        day_time=passDataFromDeliveryPage.getStringExtra("DAY");
        duration=passDataFromDeliveryPage.getStringExtra("Duration");
        //presc_img=passDataFromDeliveryPage.getStringExtra("presc_img");
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        prefs_Address = getSharedPreferences(
                "Address Details", Context.MODE_PRIVATE);
        prefs_Address_pin = getSharedPreferences(
                "Address pin Details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs_Address.edit();
        editor.putString("USER_NAME",user_name);
        editor.putString("USER_ADDRESS",address);
        editor.putInt("FIRST_ADD", address_id);
        editor.putString("Zip_ADD", add_zip);
        editor.putString("Single Add", user_add);
        editor.commit();
        us_nm=prefs_Address.getString("USER_NAME","");
        us_add=prefs_Address.getString("USER_ADDRESS","");
        Addd_first=prefs_Address.getInt("FIRST_ADD",0);
        aId=prefs_Address.getString("Single Add","");
        a_typ=prefs_Address.getString("TYPE_ADDRESS","");
        lan_MArk=prefs_Address.getString("LAND_ADDRESS","");
        Addd_first=prefs_Address.getInt("FIRST_ADD",0);
        ad_phone=prefs_Address.getString("PHONE_ADDRESS","");
        a_zip=prefs_Address.getString("Zip_ADD","");

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
        email_add=(TextView)findViewById(R.id.email_add);
        card_view_date=(CardView)findViewById(R.id.card_view_date);
        estimated_date=(TextView)findViewById(R.id.estimated_date);
        type_delivery=(TextView)findViewById(R.id.type_delivery);
        interval_delivery=(TextView)findViewById(R.id.interval_delivery);
        upper_save_amt=(TextView)findViewById(R.id.upper_save_amt);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        additional_note=(EditText)findViewById(R.id.additional_note);
        coupon_off_on=(TextView)findViewById(R.id.coupon_off_on);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        address_land=(TextView)findViewById(R.id.address_land);
        address_zipt=(TextView)findViewById(R.id.address_zipt);
        address_phone=(TextView)findViewById(R.id.address_phone);
        type_add=(TextView)findViewById(R.id.type_add);
        sav_prescrtn=(TextView)findViewById(R.id.sav_prescrtn);
        free_charge=(TextView) findViewById(R.id.free_charge);
        pres_list=(RecyclerView)findViewById(R.id.pres_list);
        Cursor res=myDb.getAllData();
        getDisclimer();
        getFerrCharge();

        while (res.moveToNext()){
            coupon_id=res.getString(1);
            show_coupon=res.getString(2);
        }
        //Toast.makeText(getApplicationContext(),coupon_id,Toast.LENGTH_LONG).show();
        if(show_coupon!=null){
            place_apply_coupon.setText(show_coupon);
           // coupon_off_on.setVisibility(View.VISIBLE);
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
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSubscription=new Intent(OrderReview.this,SubscriptionDelivery.class);
                startActivity(goToSubscription);
                overridePendingTransition(0,0);
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note=additional_note.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,Order_confirm_Url,
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

                                        String Ord_id=person.getString("orderId");
                                        Intent goToIthankU=new Intent(OrderReview.this,ThankYouPage.class);
                                        goToIthankU.putExtra("OrderID",Ord_id);
                                        startActivity(goToIthankU);
                                        overridePendingTransition(0,0);
                                        finish();
                                        myDb.deleteData();
                                        prescriptionReqDatabase.deleteDataPres();

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
                        params.put("conf", "1");
                        params.put("note", note);
                        if(coupon_id!=null){
                            params.put("cpid", coupon_id);
                        }
                      /*  params.put("cpid", coupon_id);*/


                        return params;
                    }
                };

                requestQueue.add(postRequest);
            }
        });
        getCartOrder();
        calcutate_section();
       // last_address();
        SingleAddress();
        delivery_address();

    }
    private void getFerrCharge(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
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
    private void getCartOrder(){
        modelList=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
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
                                progressDialog.dismiss();
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
                                    /*String formatted_normal = format_per.format(serch.getDouble("normal_disc"));
                                    String formatted_instant = format_per.format(serch.getDouble("instant_disc"));*/
                                    String formatted_normal = serch.getString("normal_disc");
                                    String formatted_instant = serch.getString("instant_disc");
                                    String check_ins=serch.getString("instant");
                                    String save_amt=format_per.format(serch.getDouble("save_amount"));
                                    String mrp_val=format_per.format(serch.getDouble("mrp"));
                                    String price_amt=format_per.format(serch.getDouble("price"));
                                    if(check_ins.equals("1")){
                                        cartModel.setSaveings_percentage(formatted_instant);
                                    }
                                    else {
                                        cartModel.setSaveings_percentage(formatted_normal);
                                    }

                                    cartModel.setSave_amount(save_amt);
                                    cartModel.setTotal(mrp_val);
                                    cartModel.setPrice_amt(price_amt);
                                /*cartModel.setSaveings_percentage(serch.getDouble("save_percent"));
                                cartModel.setSave_amount(serch.getDouble("save_amount"));
                                cartModel.setTotal(serch.getDouble("mrp"));*/
                                    cartModel.setQuantity(serch.getString("quantity"));
                                    modelList.add(cartModel);
                                }
                                orderSummeryAdapter=new OrderSummeryAdapter(modelList,OrderReview.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(OrderReview.this));
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
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Order_tiem_total_dataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressDialog.dismiss();
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                            Double total_mrp_value = person.getDouble("MRP");
                            DecimalFormat format = new DecimalFormat("##.##");
                            Double saving_parcent=person.getDouble("saving_parcent");
                            String sav_persnt=format.format(saving_parcent);
                            String tot_mrp = format.format(total_mrp_value);
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
    private void last_address(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
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
                                progressDialog.dismiss();
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
                                 sing_zip_code=object.getString("zip_code");
                                sing_lanmark=object.getString("landmark");
                                 sing_ad_typ=object.getString("atype");

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
                               /* prefs_Address = getSharedPreferences(
                                        "Address Details", Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = prefs_Address.edit();
                                e.remove("FIRST_ADD");
                                e.commit();*/

                                if (us_nm.equals("")&&us_add.equals("")&&a_typ.equals("")&&lan_MArk.equals("")&& ad_phone.equals("")&&a_zip.equals("")) {
                                    String unknown=sing_fullname;
                                    String unknown_add=all_address;
                                    String unkown_add_type=sing_ad_typ;
                                    String unkown_land=sing_lanmark;
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
    private void SingleAddress(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,orderSingle_addressUrl,
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
                                sing_zip_code=object.getString("zip_code");
                                sing_lanmark=object.getString("landmark");
                                sing_ad_typ=object.getString("atype");

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
                               /* prefs_Address = getSharedPreferences(
                                        "Address Details", Context.MODE_PRIVATE);
                                SharedPreferences.Editor e = prefs_Address.edit();
                                e.remove("FIRST_ADD");
                                e.commit();*/

                                if (us_nm.equals("")&&us_add.equals("")) {
                                    String unknown=sing_fullname;
                                    String unknown_add=all_address;
                                    String unkown_add_type=sing_ad_typ;
                                    String unkown_land=sing_lanmark;
                                    String unkown_zip=sing_zip_code;
                                    String unkown_ph=sing_phone;
                                    customer_name.setText(unknown);
                                    address_text.setText(unknown_add);
                                    type_add.setText(unkown_add_type);
                                    address_zipt.setText(unkown_zip);
                                    address_land.setText(unkown_land);
                                    address_phone.setText(unkown_ph);
                                }
                               /* else {
                                    customer_name.setText(us_nm);
                                    address_text.setText(us_add);
                                    type_add.setText(a_typ);
                                    address_zipt.setText(a_zip);
                                    address_land.setText(lan_MArk);
                                    address_phone.setText(ad_phone);
                                }*/

                                // email_add.setText(email);





                                /*else {
                                    customer_name.setText(us_nm);
                                    address_text.setText(us_add);
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

                params.put("aId", aId);


                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void delivery_address(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
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
                                progressDialog.dismiss();
                         String del_add=person.getString("Delivery date");
                                estimated_date.setText(del_add);
                         String subs_days=person.getString("day");
                         String subs_interval=person.getString("interval");
                         String subs_instant=person.getString("instant");
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
        RequestQueue requestQueue = Volley.newRequestQueue(OrderReview.this);
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
        Intent intent = new Intent(OrderReview.this, SubscriptionDelivery.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
