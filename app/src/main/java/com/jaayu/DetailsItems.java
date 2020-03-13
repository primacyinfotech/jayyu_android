package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.juanlabrador.badgecounter.BadgeCounter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.Item_view;
import Model.Item_model;
import Model.ViewDialog;

public class DetailsItems extends AppCompatActivity {
    TextView pin_set_txt,pin_set_button,comny_mame,comny_compo,comny_compo3,mrp,save_percernt,save_amt,qty_per;
    RecyclerView recyclerView;
    private ArrayList<Item_model> modelList;
    Item_view item_view;
    ImageView back_button,search_page,cart_view,required_pres_img;
    ArrayList<String> Categories2;;
    private Spinner country_select;
    private  int pro_id,spinner_no;
    private String product_details_url="https://work.primacyinfotech.com/jaayu/api/product/single";
    private String product_add_cart="https://work.primacyinfotech.com/jaayu/api/addtocart";
   /* private String Chk_data_hasCart_url="https://work.primacyinfotech.com/jaayu/api/addtocart/all";*/
   private String Chk_data_hasCart_url="https://work.primacyinfotech.com/jaayu/api/addtocart_chk";
    private Button add_cart;
    private ScrollView tcuch_scroll;
    LinearLayout linearLayout_sheet;
    BottomSheetBehavior bottomSheetBehavior;
    SharedPreferences prefs_register;
    SharedPreferences product_id_Store;
    private String u_id,status;
    SharedPreferences Cart_item_number_counter;
    int notifiCart,prescription_requird,stote_pro_id;
    int notti=0;
    int clickcount=0;
    int count_cart;
    ProgressDialog progressDialog;
    int prescription_required;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_items);
        Cart_item_number_counter = getSharedPreferences(
                "CARTITEM_COUNTER", Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        Intent prodid=getIntent();
        pro_id=prodid.getIntExtra("Product Id",0) ;
        product_id_Store = getSharedPreferences(
                "Store_product", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=product_id_Store.edit();
        editor.putInt("Product_Store",pro_id);
        editor.commit();
        stote_pro_id=product_id_Store.getInt("Product_Store",0);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        search_page=(ImageView)toolbar.findViewById(R.id.search_page);
        cart_view=(ImageView)toolbar.findViewById(R.id.cart_view);
        required_pres_img=(ImageView)findViewById(R.id.required_pres_img);
        country_select=(Spinner)findViewById(R.id.country_select);
        tcuch_scroll=(ScrollView)findViewById(R.id.tcuch_scroll);
        linearLayout_sheet=(LinearLayout)findViewById(R.id.bottom_sheet);

       //bottomSheetBehavior=BottomSheetBehavior.from(linearLayout_sheet);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tcuch_scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if(scrollY<=oldScrollY){
//                        //linearLayout_sheet.setVisibility(View.GONE);
//                        linearLayout_sheet.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        linearLayout_sheet.setVisibility(View.GONE);
//                    }
                    if(oldScrollY<=scrollY){
                        linearLayout_sheet.setVisibility(View.VISIBLE);
                    }
                    else {
                        linearLayout_sheet.setVisibility(View.GONE);
                    }


                }
            });
        }*/
        add_cart=(Button)findViewById(R.id.add_cart);
        Categories2=new ArrayList<>();
        Categories2.add("1");
        Categories2.add("2");
        Categories2.add("3");
        Categories2.add("4");
        Categories2.add("5");
        Categories2.add("6");
        Categories2.add("7");
        Categories2.add("8");
        Categories2.add("9");
        Categories2.add("10");
        Categories2.add("11");
        Categories2.add("12");
        Categories2.add("13");
        Categories2.add("14");
        Categories2.add("15");
        Categories2.add("16");
        Categories2.add("17");
        Categories2.add("18");
        Categories2.add("19");
        Categories2.add("20");
        Categories2.add("21");
        Categories2.add("22");
        Categories2.add("23");
        Categories2.add("24");
        Categories2.add("25");
        Categories2.add("26");
        Categories2.add("27");
        Categories2.add("28");
        Categories2.add("29");
        Categories2.add("30");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Categories2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       country_select.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
        country_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_no=Integer.parseInt(country_select.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(DetailsItems.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, product_add_cart,
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
                                        Intent goToCart=new Intent(DetailsItems.this,CartActivity.class);
                                        startActivity(goToCart);
                                        finish();
                                        overridePendingTransition(0,0);
                                       /* progressDialog = new ProgressDialog(DetailsItems.this);
                                        progressDialog.setMessage("Loading..."); // Setting Message
                                        progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                        progressDialog.show(); // Display Progress Dialog
                                        progressDialog.setCancelable(false);
                                        new Thread(new Runnable() {
                                            public void run() {
                                                try {
                                                    Thread.sleep(2000);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                progressDialog.dismiss();



                                            }
                                        }).start();*/
                                        //Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                       /* Intent gotoRefresh=new Intent(DetailsItems.this,DetailsItems.class);
                                        startActivity(gotoRefresh);
                                        overridePendingTransition(0,0);*/
                                    }
                                    if(status.equals("2")){
                                        String msg=person.getString("message");
                                        ViewDialog alert = new ViewDialog();
                                        alert.showDialog(DetailsItems.this, msg);
                                    }





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
                        params.put("product_id", String.valueOf(stote_pro_id));
                        params.put("quantity", String.valueOf(spinner_no));
                        params.put("user_id", u_id);


                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
        GetItems();
      //  GetItems();
        pin_set_txt=(TextView)findViewById(R.id.pin_set_txt);
        pin_set_button=(TextView)findViewById(R.id.pin_set_button);
        comny_mame=(TextView)findViewById(R.id.comny_mame);
        comny_compo=(TextView)findViewById(R.id.comny_compo);
        comny_compo3=(TextView)findViewById(R.id.comny_compo3);
        mrp=(TextView)findViewById(R.id.mrp) ;
        save_percernt=(TextView)findViewById(R.id.save_percernt) ;
        save_amt=(TextView)findViewById(R.id.save_amt) ;
        qty_per=(TextView)findViewById(R.id.qty_per) ;
       // recyclerView=(RecyclerView)findViewById(R.id.overlapImage);
     /*   recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelList=new ArrayList<>();
        modelList.add(new Item_model("12.25","Save 25%","10.00","15 tablates in 1 Strip","Not Refunable"));
     *//*   modelList.add(new Item_model("12.25","Save 25%","10.00","15 tablates in 1 Strip","Not Refunable"));
        modelList.add(new Item_model("12.25","Save 25%","10.00","15 tablates in 1 Strip","Not Refunable"));*//*
        item_view=new Item_view(modelList,this);
        recyclerView.setAdapter(item_view);
        item_view.notifyDataSetChanged();*/
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(DetailsItems.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        search_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(DetailsItems.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
     /*   cart_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notifiCart>0){
                    BadgeCounter.update(this,
                            menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                            notifiCart);
                }
                Intent intentCart=new Intent(DetailsItems.this,CartActivity.class);
                startActivity(intentCart);
            }
        });
*/
    }
    private void GetItems() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, product_details_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject person = new JSONObject(response);
                           /* String product_name=person.getString("product_name");
                            String company_name=person.getString("com_name");
                            String composition=person.getString("composition");
                            String save_percen=person.getString("save_percent");
                            String mrp_price=person.getString("mrp");
                            String qty_per_pro=person.getString("box");
                            String save_ammount=person.getString("offer_price");
                            prescription_required=person.getInt("prescription_required");*/
                            String pr_mn=person.getString("product_name");
                            String cmm_name=person.getString("com_name");
                            String compo=person.getString("composition");
                            String save_per=person.getString("normal_disct");
                            String mrp_priz=person.getString("mrp");
                            String qty_per_prod=person.getString("box");
                            String save_amt_of=person.getString("offer_price");
                           int prescrp_req=person.getInt("prescription_required");

                            SharedPreferences preferences = getSharedPreferences("PRESCRIPTION REQUIRED", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("Product Name",pr_mn);
                            editor.putString("Company Name",cmm_name);
                            editor.putString("compo Name",compo);
                            editor.putString("sav_percent",save_per);
                            editor.putString("Mrp Price",mrp_priz);
                            editor.putString("Product Qty Per",qty_per_prod);
                            editor.putString("Saving Amount",save_amt_of);
                            editor.putInt("Prescrip_requir",prescrp_req);
                            editor.apply();
                            String product_name=preferences.getString("Product Name","");
                            String company_name=preferences.getString("Company Name","");
                            String composition=preferences.getString("compo Name","");
                            String                                                                          save_percen=preferences.getString("sav_percent","");
                            String mrp_price=preferences.getString("Mrp Price","");
                            String qty_per_pro=preferences.getString("Product Qty Per","");
                            String save_ammount=preferences.getString("Saving Amount","");
                            prescription_required=preferences.getInt("Prescrip_requir",0);

                            comny_mame.setText(product_name);
                            comny_compo.setText(company_name);
                            comny_compo3.setText(composition);
                            save_percernt.setText("Save@"+save_percen+"%");
                            if (mrp != null) {
                                mrp.setText(mrp_price);
                                mrp.setPaintFlags(mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            }

                            qty_per.setText(qty_per_pro);
                            save_amt.setText(save_ammount);
                            if (prescription_required==0){
                                required_pres_img.setVisibility(View.GONE);
                            }
                            else {
                                required_pres_img.setVisibility(View.VISIBLE);
                                required_pres_img.setImageResource(R.drawable.prescriptionrequired);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                           // Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("id", String.valueOf(pro_id));


                return params;
            }
        };
        queue.add(postRequest);
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        RequestQueue queue2 = Volley.newRequestQueue(DetailsItems.this);
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
                            count_cart=person.getInt("count");
                            if(status.equals("0")){

                              /*  BadgeCounter.update(DetailsItems.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                                        count_cart);*/
                                BadgeCounter.update(DetailsItems.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart,null,null);

                            }
                            else if(status.equals("1")) {

                                BadgeCounter.update(DetailsItems.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                                        count_cart);

                            }





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
      /*  notifiCart=Cart_item_number_counter.getInt("Counter_Item",0);
        if(notifiCart>0){
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notifiCart);
        }
        else {
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notti);

        }*/
      /*  else if(notifiCart==0){
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notifiCart);
        }*/
      /*  else {
            BadgeCounter.hide(menu.findItem(R.id.action_cart));
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_offer) {
            return true;
        }
        if (id == R.id.action_cart) {
           // notifiCart--;

           // BadgeCounter.update(item, notifiCart);


            RequestQueue queue2 = Volley.newRequestQueue(DetailsItems.this);
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
                                count_cart=person.getInt("count");
                                if(status.equals("0")){

                                    BadgeCounter.update(item, count_cart);
                                    Intent intentCart=new Intent(DetailsItems.this,EmptyCart.class);
                                    startActivity(intentCart);
                                    finish();
                                  //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                }
                                else if(status.equals("1")) {

                                    BadgeCounter.update(item, count_cart);
                                    /*SharedPreferences preferences = getSharedPreferences("PRESCRIPTION REQUIRED", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("Prescrip_required",prescription_required);
                                    editor.commit();
                                    prescription_requird=preferences.getInt("Prescrip_required",0);*/
                                    Intent intentCart=new Intent(DetailsItems.this,CartActivity.class);
                                   // intentCart.putExtra("P_Required",prescription_requird);
                                    startActivity(intentCart);
                                    finish();
                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                              //  Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void  cart_count(){

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailsItems.this, SearchActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
