package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapter.CartAdapter;
import Adapter.CouponListAdapter;
import Model.CartModel;
import Model.CouponListModel;
import Model.SaveCoupon;

public class CartActivity extends AppCompatActivity {
    SaveCoupon myDb;
    private ArrayList<CartModel> modelList;
    CartAdapter cartAdapter;
    private ArrayList<CouponListModel> couponListModelArrayList;
    RecyclerView recyclerView;
   private ImageView back_button,search_page;
   private Button submit_btn;
   private LinearLayout apply_coupon_btn,new_item_add;
   CouponListAdapter couponListAdapter;
   private TextView place_apply_coupon,mrp_total,total_save_price,shipping_charge,payable_amount,save_amount,discount_limit_amt,main_pay,upper_save_amt,
           disclaimer,coupon_off_on,sav_prescrtn,free_charge;
    String fetchCpn,show_coupon,formatted;

    int fetchCpnId;
    private String cart_items_url=BaseUrl.BaseUrlNew+"addtocart/all";
    private String cart_tiem_total_dataUrl=BaseUrl.BaseUrlNew+"addtocart/sum_value";
    private String Chk_data_hasCart_url=BaseUrl.BaseUrlNew+"addtocart_chk";
    private String quantity_update_url=BaseUrl.BaseUrlNew+"addtocart/quantity";
    private String coupon_list_url= BaseUrl.BaseUrlNew+"coupon_listing";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    private  String free_delivery_url=BaseUrl.BaseUrlNew+"delivery_charge";
    SharedPreferences prefs_register;
    SharedPreferences prefs_Quantity;
    private String u_id,p_id,qty,qty_u_id;
    SharedPreferences Cart_item_number_counter;
    SharedPreferences Coupon_store;
    int count_one=0;
    ProgressDialog progressDialog;
    int prescription_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDb = new SaveCoupon(this);
        progressDialog = new ProgressDialog(CartActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading....");
        progressDialog.setCancelable(false);
        /* Intent pres_req=getIntent();
         prescription_req=pres_req.getIntExtra("P_Required",0);*/
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));

        SharedPreferences preferences = getSharedPreferences("PRESCRIPTION REQUIRED", Context.MODE_PRIVATE);
        prescription_req=preferences.getInt("Prescrip_requir",0);
        Cart_item_number_counter = getSharedPreferences(
                "CARTITEM_COUNTER", Context.MODE_PRIVATE);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        prefs_Quantity = getSharedPreferences(
                "Quantity Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        final Intent fetchCoupon=getIntent();
       fetchCpn=fetchCoupon.getStringExtra("Coupon Code");
       fetchCpnId=fetchCoupon.getIntExtra("Coupon_id",0);
      /*  Coupon_store = getSharedPreferences(
                "COUPON_STORE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Coupon_store.edit();
        editor.putString("COUPON_CODE",fetchCpn);
        editor.putString("COUPON_CODE_id", String.valueOf(fetchCpnId));
        editor.commit();
       show_coupon=Coupon_store.getString("COUPON_CODE","");*/
       /* boolean isUpdate=myDb.insertData(String.valueOf(fetchCpnId),fetchCpn);

        if(isUpdate){
            Toast.makeText(CartActivity.this,"Data Update",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(CartActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
        }*/


     /*   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        search_page=(ImageView)toolbar.findViewById(R.id.search_page);
        coupon_off_on=(TextView) findViewById(R.id.coupon_off_on);
        new_item_add=(LinearLayout) findViewById(R.id.new_item_add);
        place_apply_coupon=(TextView)findViewById(R.id.place_apply_coupon);
        mrp_total=(TextView)findViewById(R.id.mrp_total);
        total_save_price=(TextView)findViewById(R.id.total_save_price);
        shipping_charge=(TextView)findViewById(R.id.shipping_charge);
        payable_amount=(TextView)findViewById(R.id.payable_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        discount_limit_amt=(TextView)findViewById(R.id.discount_limit_amt);
        main_pay=(TextView)findViewById(R.id.main_pay);
        upper_save_amt=(TextView)findViewById(R.id.upper_save_amt);
        sav_prescrtn=(TextView)findViewById(R.id.sav_prescrtn);
        free_charge=(TextView) findViewById(R.id.free_charge);
       Cursor res=myDb.getAllData();
        getDisclimer();
        getFerrCharge();
       while (res.moveToNext()){
         show_coupon=res.getString(2);
       }
      /* res.moveToFirst();
       show_coupon=res.getString(2);*/
      if(show_coupon!=null){
          place_apply_coupon.setText(show_coupon);
          coupon_off_on.setVisibility(View.VISIBLE);
      }
      else {
          place_apply_coupon.setText("Apply Coupon");
          coupon_off_on.setVisibility(View.GONE);
      }

        submit_btn=(Button)findViewById(R.id.submit_btn);
        apply_coupon_btn=(LinearLayout)findViewById(R.id.apply_coupon_btn);
        recyclerView=(RecyclerView)findViewById(R.id.cart_items);
        fetch_Cart();
       calcutate_section();
        IfCartDataCheck();
       // getCartAdapterData();

       
      /*  coupon_off_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place_apply_coupon.setText("Apply Coupon");
                coupon_off_on.setImageResource(R.drawable.rigth_arrow);


            }
        });*/
        new_item_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSeatch=new Intent(CartActivity.this,SearchActivity.class);
                startActivity(goToSeatch);
            }
        });
        apply_coupon_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              couponListModelArrayList=new ArrayList<>();
              final Dialog dialog = new Dialog(CartActivity.this);
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
              RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
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
                                      progressDialog.dismiss();

                                      JSONArray jsonArray=person.getJSONArray("list");
                                      for(int i=0;i<jsonArray.length();i++){
                                         CouponListModel couponListModel=new CouponListModel();
                                          JSONObject object=jsonArray.getJSONObject(i);
                                          couponListModel.setCoupon_id(object.getInt("id"));
                                          couponListModel.setCoupon_code(object.getString("coupon_code"));
                                          couponListModel.setCoupon_img(object.getString("image"));
                                          couponListModel.setCoupn_code_details(object.getString("sdescr"));
                                          couponListModel.setCoupon_code_des(object.getString("heading"));

                                          String date_view=object.getString("validtill");
                                          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                          Date testDate=sdf.parse(date_view);
                                          SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM,yyyy");
                                          String newFormat = formatter.format(testDate);
                                          couponListModel.setCoupon_time(newFormat);

                                      /*    String cDes=object.getString("descr");
                                          Spanned htmlAsSpanned = Html.fromHtml(cDes);
                                          couponListModel.setCoupon_code_des(String.valueOf(htmlAsSpanned));*/

                                          couponListModelArrayList.add(couponListModel);

                                      }
                                      couponListAdapter=new CouponListAdapter(couponListModelArrayList,CartActivity.this);
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

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prescription_req==1){
                    Intent intentGotoOrderSummery=new Intent(CartActivity.this,UploadToPrescription.class);
                    intentGotoOrderSummery.putExtra("PRES_REQ",prescription_req);
                    startActivity(intentGotoOrderSummery);
                }
                else {
                    Intent intentGotoOrderSummery=new Intent(CartActivity.this,OrderSummery.class);
                    startActivity(intentGotoOrderSummery);
                }

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        search_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        *//*.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)*//*
                        .commit();*/
                Intent intent = new Intent(CartActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
      /*  apply_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponListModelArrayList=new ArrayList<>();
                 final Dialog dialog = new Dialog(CartActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.coupon_dialog_layout);
                ImageView close_btn=(ImageView)dialog.findViewById(R.id.close_btn);
                RecyclerView coupon_list=(RecyclerView)dialog.findViewById(R.id.coupon_list);
                couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                        "Expire In 2 Days"));
                couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"GOFERCE","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                        "Expire In 2 Days"));
                couponListModelArrayList.add(new CouponListModel(R.drawable.myntra,"MYNTRA","Flat Rs.250 off in Myntra","Get Rs.250 Voucher with in 7 days",
                        "Expire In 2 Days"));
                couponListAdapter=new CouponListAdapter(couponListModelArrayList,CartActivity.this);
                coupon_list.setHasFixedSize(true);
                coupon_list.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                coupon_list.setAdapter(couponListAdapter);
                couponListAdapter.notifyDataSetChanged();
                close_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });*/
       /* modelList=new ArrayList<>();
        modelList.add(new CartModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
        modelList.add(new CartModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
        modelList.add(new CartModel("Calpol 500mg tablet","15 tablet 1 strip","25%","12.52","14.25"));
*/

       /* cartAdapter=new CartAdapter(modelList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        cartAdapter.notifyDataSetChanged();*/
        if(count_one==0){

            SharedPreferences.Editor editor2 = Cart_item_number_counter.edit();
            editor2.putInt("Counter_Item", count_one);
            editor2.commit();
          //  Toast.makeText(getApplicationContext(), String.valueOf(count_one), Toast.LENGTH_LONG).show();
        }

    }
    private void getFerrCharge(){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
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
    private void  fetch_Cart() {
        modelList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
      /*  JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                cart_items_url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                           CartModel cartModel=new CartModel();
                            try {
                                JSONObject serch=response.getJSONObject(i);
                                cartModel.setCart_item_id(serch.getInt("id"));
                                cartModel.setCart_item(serch.getString("title"));
                                cartModel.setUnit(serch.getString("box"));
                                cartModel.setSaveings_percentage(serch.getString("save_percent"));
                                cartModel.setSave_amount(serch.getString("save_amount"));
                                cartModel.setTotal(serch.getString("mrp"));
                                modelList.add(cartModel);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        cartAdapter=new CartAdapter(modelList,CartActivity.this);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                        recyclerView.setAdapter(cartAdapter);
                        recyclerView.setNestedScrollingEnabled(false);
                        cartAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred


                    }
                }
        );*/
        StringRequest postRequest = new StringRequest(Request.Method.POST,cart_items_url,
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
                                //findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                                JSONArray jsonArray = person.getJSONArray("cart");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    CartModel cartModel = new CartModel();
                                    JSONObject serch = jsonArray.getJSONObject(i);
                                    cartModel.setCart_id(serch.getInt("id"));
                                    cartModel.setCart_item_id(serch.getInt("product_id"));
                                    cartModel.setCart_item(serch.getString("title"));
                                    cartModel.setUnit(serch.getString("box"));
                                    cartModel.setCompany_name(serch.getString("com_name"));

                                    DecimalFormat format_per = new DecimalFormat("##.##");
                                     formatted = format_per.format(serch.getDouble("save_percent"));
                                    String save_amt = format_per.format(serch.getDouble("save_amount"));
                                    String mrp_val = format_per.format(serch.getDouble("mrp"));
                                    String price_amt = format_per.format(serch.getDouble("price"));


                                    cartModel.setSaveings_percentage(formatted);
                                    cartModel.setSave_amount(save_amt);
                                    cartModel.setTotal(mrp_val);
                                    cartModel.setPrice_amt(price_amt);
                                /*cartModel.setSaveings_percentage(serch.getDouble("save_percent"));
                                cartModel.setSave_amount(serch.getDouble("save_amount"));
                                cartModel.setTotal(serch.getDouble("mrp"));*/
                                    cartModel.setQty(serch.getInt("quantity"));
                                    modelList.add(cartModel);
                                }
                               /* cartAdapter = new CartAdapter(modelList, CartActivity.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                                recyclerView.setItemViewCacheSize(20);
                                recyclerView.setAdapter(cartAdapter);
                                cartAdapter.notifyDataSetChanged();*/
                                cartAdapter = new CartAdapter(modelList, CartActivity.this);
                                recyclerView.setHasFixedSize(true);

                                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
                                recyclerView.setAdapter(cartAdapter);

                                cartAdapter.notifyDataSetChanged();
                                /*cartAdapter.notifyDataSetChanged();
                                cartAdapter.notifyItemInserted(modelList.size());*/
                                recyclerView.setItemViewCacheSize(100);
                                recyclerView.setDrawingCacheEnabled(true);
                                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                               /* recyclerView.setAdapter(cartAdapter);
                                cartAdapter.notifyDataSetChanged();*/
                               // progress = new ProgressBar(CartActivity.this);

                             /*   progressDialog = new ProgressDialog(CartActivity.this);
                                progressDialog.setMessage("Loading..."); // Setting Message
                                progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                progressDialog.show(); // Display Progress Dialog
                                progressDialog.setCancelable(false);
                                new Thread(new Runnable() {
                                    public void run() {
                                        try {
                                            if(cartAdapter!=null){


                                            }

                                           // cartAdapter.notifyDataSetChanged();
                                            Thread.sleep(3000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        progressDialog.dismiss();



                                    }
                                }).start();*/


//                                if (cartAdapter.getItemCount() == 0) {
//                                    int cartCount2 =0;
//                                    SharedPreferences.Editor editor2 = Cart_item_number_counter.edit();
//                                    editor2.putInt("Counter_Item", cartCount2);
//                                    editor2.commit();
//                                    Toast.makeText(getApplicationContext(), String.valueOf(cartCount2), Toast.LENGTH_LONG).show();
//
//                                } else {
                                   /* int cartCount = cartAdapter.getItemCount();

                                    SharedPreferences.Editor editor2 = Cart_item_number_counter.edit();
                                    editor2.putInt("Counter_Item", cartCount);
                                    editor2.commit();
                                    Toast.makeText(getApplicationContext(), String.valueOf(cartCount), Toast.LENGTH_LONG).show();*/
//                                }
                                int cartCount = cartAdapter.getItemCount();

                                SharedPreferences.Editor editor2 = Cart_item_number_counter.edit();
                                editor2.putInt("Counter_Item", cartCount);
                                editor2.commit();
                               // Toast.makeText(getApplicationContext(), String.valueOf(cartCount), Toast.LENGTH_LONG).show();

                            }
                            else if(status.equals("0")){
                                //findViewById(R.id.progressbar).setVisibility(View.VISIBLE);
                               /* Intent goToemptyCart=new Intent(getApplicationContext(),EmptyCart.class);
                                startActivity(goToemptyCart);
                                finish();*/
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
        )
        {
            @Override
            protected Map<String, String> getParams ()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", u_id);


                return params;
            }

        };
        requestQueue.add(postRequest);
        // Add JsonArrayRequest to the RequestQueue

    }
    private void  calcutate_section(){
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST,cart_tiem_total_dataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            final JSONObject person = new JSONObject(response);
                            String status=person.getString("status");
                            if(status.equals("success")){
                                progressDialog.dismiss();
                               final Double total_mrp_value = person.getDouble("MRP");
                                final Double saving_parcent=person.getDouble("saving_parcent");

                                final Double tot_save_amt=person.getDouble("saving");

                                final Double ship_charge=person.getDouble("shipping");

                                final Double pay_amount=person.getDouble("Total");
                                DecimalFormat format = new DecimalFormat("##.##");
                                final String tot_mrp=format.format(total_mrp_value);
                                String sav_persnt=format.format(saving_parcent);
                                mrp_total.setText(tot_mrp);
                                String sav_amt_tot=format.format(tot_save_amt);
                                save_amount.setText(sav_amt_tot);
                                total_save_price.setText(sav_amt_tot);
                                String ship_crhg=format.format(ship_charge);
                                shipping_charge.setText(ship_crhg);
                                String p_amt=format.format(pay_amount);
                                String main_pay_amt=format.format(Math.round(pay_amount));
                                payable_amount.setText(p_amt);
                                main_pay.setText("\u20B9"+main_pay_amt);
                                upper_save_amt.setText("You are Saving  "+"\u20B9"+" "+sav_amt_tot+" "+"on this order.");
                               // sav_prescrtn.setText("Saving @ "+sav_persnt +" %");
                                sav_prescrtn.setText("Saving");
                              /*  Thread t = new Thread() {

                                    @Override
                                    public void run() {
                                        try {
                                            while (!isInterrupted()) {
                                                Thread.sleep(1000);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        DecimalFormat format = new DecimalFormat("##.##");
                                                        final String tot_mrp=format.format(total_mrp_value);
                                                        mrp_total.setText(tot_mrp);
                                                        String sav_amt_tot=format.format(tot_save_amt);
                                                        save_amount.setText(sav_amt_tot);
                                                        total_save_price.setText(sav_amt_tot);
                                                        String ship_crhg=format.format(ship_charge);
                                                        shipping_charge.setText(ship_crhg);
                                                        String p_amt=format.format(pay_amount);
                                                        String main_pay_amt=format.format(Math.round(pay_amount));
                                                        payable_amount.setText(p_amt);
                                                        main_pay.setText("\u20B9"+main_pay_amt);
                                                    }
                                                });
                                            }
                                        } catch (InterruptedException e) {
                                        }
                                    }
                                };

                                t.start();*/
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CartActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
 private  void  IfCartDataCheck(){
     RequestQueue queue2 = Volley.newRequestQueue(CartActivity.this);
     StringRequest postRequest2 = new StringRequest(Request.Method.POST, Chk_data_hasCart_url,
             new Response.Listener<String>()
             {
                 @Override
                 public void onResponse(String response) {
                     // response
                     Log.d("Response", response);
                     try {
                         //Do it with this it will work
                         JSONObject person = new JSONObject(response);
                         String status=person.getString("status");
                        int count_cart=person.getInt("count");
                         if(status.equals("0")){
                             progressDialog.dismiss();
                             Intent intentCart=new Intent(CartActivity.this,EmptyCart.class);
                             startActivity(intentCart);
                             overridePendingTransition(0,0);
                             finish();

                             //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                         }
                       /*  else if(status.equals("1")) {


                             Intent intentCart=new Intent(CartActivity.this,CartActivity.class);
                             startActivity(intentCart);
                             overridePendingTransition(0,0);
                             finish();

                         }*/





                     } catch (JSONException e) {
                         e.printStackTrace();
                         Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                     }


                 }
             },
             new Response.ErrorListener()
             {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     // error

                 }
             }
     ) {
         @Override
         protected Map<String, String> getParams()
         {
             Map<String, String>  params = new HashMap<String, String>();

             params.put("user_id", u_id);


             return params;
         }
     };
     queue2.add(postRequest2);
 }
 /*private void getCartAdapterData(){

        p_id=prefs_Quantity.getString("product_id","");
        qty=prefs_Quantity.getString("quantity","");
     qty_u_id=prefs_Quantity.getString("user_id","");

     RequestQueue queue = Volley.newRequestQueue(CartActivity.this);
     StringRequest postRequest = new StringRequest(Request.Method.POST, quantity_update_url,
             new Response.Listener<String>()
             {
                 @Override
                 public void onResponse(String response) {
                     // response
                     Log.d("Response", response);
                     try {
                         //Do it with this it will work
                         JSONObject person = new JSONObject(response);
                         String status=person.getString("status");
                         if(status.equals("success")){
                          *//*   Intent intent= new Intent(getApplicationContext(),CartActivity.class);
                             intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                             startActivity(intent);
                             overridePendingTransition(0,0);*//*

                            SharedPreferences.Editor editor=prefs_Quantity.edit();
                            editor.clear();
                            editor.commit();

                             // Toast.makeText(context,status+" "+"Removed",Toast.LENGTH_LONG).show();
                         }
                         else {
                             //  Toast.makeText(context,status+" "+"Not Removed",Toast.LENGTH_LONG).show();
                         }





                     } catch (JSONException e) {
                         e.printStackTrace();
                         Toast.makeText(CartActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                     }


                 }
             },
             new Response.ErrorListener()
             {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                     // error

                 }
             }
     ) {
         @Override
         protected Map<String, String> getParams()
         {
             Map<String, String>  params = new HashMap<String, String>();
             params.put("product_id", p_id);
             params.put("quantity", qty);
             params.put("user_id", qty_u_id);
             return params;
         }
     };
     queue.add(postRequest);
 }*/
 private void getDisclimer(){
     RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
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
 public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
     @Override
     public void onReceive(Context context, Intent intent) {
         calcutate_section();
     }
 };
}
