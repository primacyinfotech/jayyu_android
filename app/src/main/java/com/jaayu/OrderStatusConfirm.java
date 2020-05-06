package com.jaayu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.ReturnvalueDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.OnlyOldPresciptionAdapter;
import Adapter.OrderStatusItemAdapter;
import Adapter.OrderStatusPressAdapter;
import Model.OrderStatusItemModel;
import Model.OrderStatusPressModel;

public class OrderStatusConfirm extends AppCompatActivity {
    RecyclerView cart_items,pres_list;
    ImageView back_button,expend_btn,order_details_icon;
    ArrayList<OrderStatusItemModel> orderStatusItemModels;
    OrderStatusItemAdapter orderStatusItemAdapter;
    OrderStatusPressAdapter orderStatusPressAdapter;
    ArrayList<OrderStatusPressModel> orderStatusPressModels;
    SharedPreferences prefs_register;
    private  Button submit_btn;
    private String Orderdetails_url=BaseUrl.BaseUrlNew+"order_details_profile";
    private String Return_url=BaseUrl.BaseUrlNew+"return_medicine";
    private  String disclaimer_url=BaseUrl.BaseUrlNew+"disclaimer";
    String u_id,instant_id,ship_status,delivery_date,ord_id,ord_date,mrp_amt,save_amt,shipping_charge,tot_pay,ship_add_name,ship_add_phone,
            ship_add_address,ship_add_land,ship_add_pin,payment_status;
    int odr_id,oid;
    String Ord_vid;
    private TextView mrp_amount,save_amount,ship_charge,total_pay,ship_address,date_of_delivery,main_pay,sav_prescrtn,disclaimer;
    private LinearLayout cancel_btn,paynow_btn;
    ProgressDialog progressDialog;
    private TextView active_order_two,active_order_three,active_order,active_order_four,active_order_five,order_id,order_date,text_cancel,text_pay;
     ArrayList<String>itemid;
     ArrayList<String>itemtitle;
     ArrayList<String>itemPrice;
     ArrayList<String>itemMrp;
     ArrayList<String>itemQty;
    List<String>[] Order_Item_List;
    List<String> All_itemid = new ArrayList<>();
    List<String> All_itemtitle = new ArrayList<>();
    List<String> All_itemPrice = new ArrayList<>();
    List<String> All_itemMrp = new ArrayList<>();
    List<String> All_itemQty = new ArrayList<>();
    List<String> itmId;
    List<String> itmTitle;
    List<String> itmPrice;
    List<String> itmMrp;
    List<String> itmQty;
 public String itemIdtoJson,itemTitletojson,itempricetojson,itemmprijson,itemQtyjson,ojyid,ins;
    ReturnvalueDatabase returnvalueDatabase;
    LinearLayout return_ship_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status_confirm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_order_intent"));
        //LocalBroadcastManager.getInstance(this).registerReceiver(mdeleteReceiver, new IntentFilter("message_delete_intent"));
        returnvalueDatabase=new ReturnvalueDatabase(this);
        orderStatusItemModels=new ArrayList<>();
        orderStatusPressModels=new ArrayList<>();
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        Intent gettheOrderData=getIntent();
        oid=gettheOrderData.getIntExtra("Order_id",0);
        ojyid=gettheOrderData.getStringExtra("Order_Vid");
        ins=gettheOrderData.getStringExtra("Instant");

      if(ojyid!=null || ins!=null){

          boolean isUpdate=returnvalueDatabase.insertDataReturn(oid,ojyid,ins);
          if(isUpdate){
              Toast.makeText(OrderStatusConfirm.this,"Data Update",Toast.LENGTH_LONG).show();
          }
      }

       /* boolean isUpdate=returnvalueDatabase.insertDataReturn(oid,ojyid,ins);
        if(isUpdate){
            Toast.makeText(OrderStatusConfirm.this,"Data Update",Toast.LENGTH_LONG).show();
        }*/
        /*else {


          }*/

        Cursor return_req=returnvalueDatabase.getAllDatareturn();
        while (return_req.moveToNext()){
            odr_id=return_req.getInt(1);
            Ord_vid=return_req.getString(2);
            instant_id=return_req.getString(3);
        }
       /* odr_id=gettheOrderData.getIntExtra("Order_id",0);
        Ord_vid=gettheOrderData.getStringExtra("Order_Vid");
        instant_id=gettheOrderData.getStringExtra("Instant");*/
        cart_items=(RecyclerView)findViewById(R.id.cart_items);
        pres_list=(RecyclerView)findViewById(R.id.pres_list);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        /*order_details_icon=(ImageView) findViewById(R.id.order_details_icon);*/
        mrp_amount=(TextView)findViewById(R.id.mrp_amount);
        save_amount=(TextView)findViewById(R.id.save_amount);
        ship_charge=(TextView)findViewById(R.id.ship_charge);
        total_pay=(TextView)findViewById(R.id.total_pay);
        main_pay=(TextView)findViewById(R.id.main_pay);
        sav_prescrtn=(TextView)findViewById(R.id.sav_prescrtn);
        submit_btn=(Button)findViewById(R.id.submit_btn);
        disclaimer=(TextView)findViewById(R.id.disclaimer);
        return_ship_layout=(LinearLayout)findViewById(R.id.return_ship_layout);
        return_ship_layout.setVisibility(View.GONE);
        active_order=(TextView)findViewById(R.id.active_order);
        active_order_two=(TextView)findViewById(R.id.active_order_two);
        active_order_three=(TextView)findViewById(R.id.active_order_three);
        active_order_four=(TextView)findViewById(R.id.active_order_four);
        active_order_five=(TextView)findViewById(R.id.active_order_five);
        active_order.setVisibility(View.GONE);
        active_order_two.setVisibility(View.GONE);
        active_order_three.setVisibility(View.GONE);
        active_order_four.setVisibility(View.GONE);
        //ship_address=(TextView)findViewById(R.id.ship_address);
       // date_of_delivery=(TextView)findViewById(R.id.date_of_delivery);
       /* text_pay=(TextView)findViewById(R.id.text_pay);
        text_cancel=(TextView)findViewById(R.id.text_cancel);*/
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(OrderStatusConfirm.this, OrderPage.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();*/
                Intent goJumpPage=new Intent(OrderStatusConfirm.this,OrderDetails.class);
                goJumpPage.putExtra("Order_id",odr_id);
                goJumpPage.putExtra("Order_Vid",Ord_vid);
                goJumpPage.putExtra("Instant",instant_id);
                startActivity(goJumpPage);
                overridePendingTransition(0,0);
                finish();
            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent goToPaymentPage=new Intent(OrderStatusConfirm.this,Payment.class);
                goToPaymentPage.putExtra("ORDER_ID",String.valueOf(odr_id));
                goToPaymentPage.putExtra("ORDER_JY_ID",Ord_vid);
                goToPaymentPage.putExtra("INSTANT",instant_id);
                startActivity(goToPaymentPage);
                overridePendingTransition(0,0);
                finish();*/
                for (int i = 0; i < OrderStatusItemAdapter.modelList.size(); i++){
                    if( OrderStatusItemAdapter.modelList.get(i).getSelected()){
                      itemid=new ArrayList<>();
                       itemtitle=new ArrayList<>();
                       itemPrice=new ArrayList<>();
                        itemMrp=new ArrayList<>();
                        itemQty=new ArrayList<>();
                        itemid.add(String.valueOf(OrderStatusItemAdapter.modelList.get(i).getItem_id()));
                        itemQty.add(String.valueOf(OrderStatusItemAdapter.modelList.get(i).getQuantity()));
                        itemtitle.add(OrderStatusItemAdapter.modelList.get(i).getTitle());
                        itemPrice.add(OrderStatusItemAdapter.modelList.get(i).getPrice_normal());
                        itemMrp.add(OrderStatusItemAdapter.modelList.get(i).getMrp_price());
                        All_itemid.addAll(itemid);
                        All_itemtitle.addAll(itemtitle);
                        All_itemPrice.addAll(itemPrice);
                        All_itemMrp.addAll(itemMrp);
                        All_itemQty.addAll(itemQty);
                    }
                }
                Order_Item_List=new List[5];
                Order_Item_List[0]= All_itemid;
                Order_Item_List[1]=All_itemtitle;
                Order_Item_List[2]= All_itemPrice;
                Order_Item_List[3]=All_itemMrp;
                Order_Item_List[4]=All_itemQty;
               /* itmId = Order_Item_List[0];
                itmTitle = Order_Item_List[1];
                itmPrice = Order_Item_List[2];
                itmMrp = Order_Item_List[3];
                itmQty = Order_Item_List[4];
                Gson gson = new Gson();
                itemIdtoJson=gson.toJson(itmId);
                itemTitletojson=gson.toJson(itmTitle);
                itempricetojson=gson.toJson(itmPrice);
                itemmprijson=gson.toJson(itmMrp);
                itemQtyjson=gson.toJson(itmQty);*/

                for (int j = 0; j < Order_Item_List.length; j++){
                 itmId = Order_Item_List[0];
                itmTitle = Order_Item_List[1];
                itmPrice = Order_Item_List[2];
                itmMrp = Order_Item_List[3];
                itmQty = Order_Item_List[4];
                Gson gson = new Gson();
                itemIdtoJson=gson.toJson(itmId);
                itemTitletojson=gson.toJson(itmTitle);
                itempricetojson=gson.toJson(itmPrice);
                itemmprijson=gson.toJson(itmMrp);
                itemQtyjson=gson.toJson(itmQty);
                  //  System.out.println("Id"+itemIdtoJson+"Title"+itemTitletojson+"MRP"+itemmrpjson+"Price"+itemmrpjson+"QTY"+itemQtyjson);

                }
                System.out.println("Id"+itemIdtoJson+"Title"+itemTitletojson+"MRP"+itemmprijson+"Price"+itempricetojson+"QTY"+itemQtyjson);
                RequestQueue requestQueue = Volley.newRequestQueue(OrderStatusConfirm.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,Return_url,
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
                                        itemid.clear();
                                        itemQty.clear();
                                        itemtitle.clear();
                                        itemPrice.clear();
                                        itemMrp.clear();
                                        All_itemid.clear();
                                        All_itemtitle.clear();
                                        All_itemPrice.clear();
                                        All_itemMrp.clear();
                                        All_itemQty.clear();

                                    /*    Intent goJumpPage=new Intent(OrderStatusConfirm.this,OrderDetails.class);
                                        goJumpPage.putExtra("Order_id",odr_id);
                                        goJumpPage.putExtra("Order_Vid",Ord_vid);
                                        goJumpPage.putExtra("Instant",instant_id);
                                        startActivity(goJumpPage);
                                        overridePendingTransition(0,0);
                                        finish();*/
                                        String msg=person.getString("msg");
                                        ViewGroup viewGroup = findViewById(android.R.id.content);

                                        //then we will inflate the custom alert dialog xml that we created
                                        View dialogView = LayoutInflater.from(OrderStatusConfirm.this).inflate(R.layout.sucess_dialog, viewGroup, false);
                                        TextView masege=(TextView)dialogView.findViewById(R.id.return_msg) ;
                                        Button buttonOk=(Button)dialogView.findViewById(R.id.buttonOk);
                                        masege.setText(msg);


                                        //Now we need an AlertDialog.Builder object
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatusConfirm.this);

                                        //setting the view of the builder to our custom view that we already inflated
                                        builder.setView(dialogView);

                                        //finally creating the alert dialog and displaying it
                                        final AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                        buttonOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                alertDialog.dismiss();
                                                Intent goJumpPage=new Intent(OrderStatusConfirm.this,OrderDetails.class);
                                                goJumpPage.putExtra("Order_id",odr_id);
                                                goJumpPage.putExtra("Order_Vid",Ord_vid);
                                                goJumpPage.putExtra("Instant",instant_id);
                                                startActivity(goJumpPage);
                                                overridePendingTransition(0,0);
                                                finish();
                                            }
                                        });

                                   Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                        returnvalueDatabase.deleteDatareturn();

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
                        params.put("product_id", itemIdtoJson);
                        params.put("title", itemTitletojson);
                        params.put("price", itempricetojson);
                        params.put("mrp", itemmprijson);
                        params.put("qty", itemQtyjson);
                        return params;
                    }
                };

                requestQueue.add(postRequest);
            }
        });
        getOrderDetails();
        getListOFOrder();
        getPrescription();
        getDisclimer();
        progressDialog = new ProgressDialog(OrderStatusConfirm.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
    }
    private void getPrescription(){
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
                                progressDialog.dismiss();
                                JSONArray jsonArray=person.getJSONArray("prescription");
                                for (int i=0;i<jsonArray.length();i++){
                                    OrderStatusPressModel orderStatusPressModel=new OrderStatusPressModel();
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);


                                    orderStatusPressModel.setPress_img(jsonObject.getString("prescription"));
                                    orderStatusPressModels.add(orderStatusPressModel);

                                }


                                orderStatusPressAdapter=new OrderStatusPressAdapter(orderStatusPressModels,OrderStatusConfirm.this);
                                pres_list.setLayoutManager(new LinearLayoutManager(OrderStatusConfirm.this,RecyclerView.HORIZONTAL,false));
                                pres_list.setHasFixedSize(true);
                                pres_list.setAdapter(orderStatusPressAdapter);
                                orderStatusPressAdapter.notifyDataSetChanged();


                            }
                            else {
                                pres_list.setVisibility(View.GONE);
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
                                progressDialog.dismiss();
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
                params.put("order_id", Ord_vid);
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
                            progressDialog.dismiss();
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

                            sav_prescrtn.setText("Saving ");
                            ship_add_name=person.getString("Name");
                            ship_add_phone=person.getString("phone");
                            ship_add_address=person.getString("Address");
                            ship_add_land=person.getString("Landmark");
                            ship_add_pin=person.getString("Pincode");
                            //ship_address.setText(ship_add_name+"\n"+ship_add_address+"\n"+ship_add_land+","+"Pin:"+ship_add_pin+"\n"+"Mobile:"+ship_add_phone);
                            if(ship_status.equals("0")){
                               submit_btn.setEnabled(false);
                              /*  ViewDialog alert = new ViewDialog();
                                alert.showDialog(OrderStatusConfirm.this, "Order Not to be Confirm......");*/
                            }
                            if (ship_status.equals("1")){

                                submit_btn.setEnabled(true);
                            }
                            if(ship_status.equals("7")){
                                return_ship_layout.setVisibility(View.VISIBLE);
                                active_order.setVisibility(View.VISIBLE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);

                            }
                            if(ship_status.equals("8")){
                                return_ship_layout.setVisibility(View.VISIBLE);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.VISIBLE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.GONE);

                            }
                            if(ship_status.equals("9")){
                                return_ship_layout.setVisibility(View.VISIBLE);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.VISIBLE);
                                active_order_four.setVisibility(View.GONE);
                            }
                            if(ship_status.equals("10")){
                                return_ship_layout.setVisibility(View.VISIBLE);
                                active_order.setVisibility(View.GONE);
                                active_order_two.setVisibility(View.GONE);
                                active_order_three.setVisibility(View.GONE);
                                active_order_four.setVisibility(View.VISIBLE);
                            }
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
                params.put("order_id", Ord_vid);
                params.put("instant", instant_id);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }
    private void getDisclimer(){
        RequestQueue requestQueue = Volley.newRequestQueue(OrderStatusConfirm.this);
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
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getOrderDetails();

        }
    };
   /* public BroadcastReceiver mdeleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getListOFOrder();
        }
    };*/
    @Override
    public void onBackPressed() {
       /* Intent intent = new Intent(OrderStatusConfirm.this, OrderPage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();*/
        Intent goJumpPage=new Intent(OrderStatusConfirm.this,OrderDetails.class);
        goJumpPage.putExtra("Order_id",odr_id);
        goJumpPage.putExtra("Order_Vid",Ord_vid);
        goJumpPage.putExtra("Instant",instant_id);
        startActivity(goJumpPage);
        overridePendingTransition(0,0);
        finish();
    }
}
