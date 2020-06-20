package com.jaayu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.Search_adapter;
import Model.Searchmodel;

public class SearchActivity extends AppCompatActivity {
    EditText search_layout_edit;
    RecyclerView recyclerView;
    Search_adapter searchAdapter;
    ArrayList<Searchmodel> names;
    ImageView back_button;
    ImageView fack_image;
    String srch_text;
    ProgressDialog progressDialog;
    private String search_url=BaseUrl.BaseUrlNew+"product/search";
    //private String search_url="https://work.primacyinfotech.com/jaayu/api/product/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
       // Search_View();



      /*  names.add(new Searchmodel("Calpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("kalpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Roxin",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Rantac",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Casit",R.drawable.ic_action_arrow));*/

        names=new ArrayList<>();
        search_layout_edit=(EditText)findViewById(R.id.search_layout_edit);
        search_layout_edit.requestFocus();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
        );
      //  fack_image=(ImageView)findViewById(R.id.fack_image);
        back_button=(ImageView)findViewById(R.id.back_button);
        recyclerView=(RecyclerView)findViewById(R.id.rv_search);


        search_layout_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                //filter(editable.toString());
                srch_text=search_layout_edit.getText().toString();
                names.clear();
                RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST,search_url,
                        new Response.Listener<String>()  {
                            @Override
                            public void onResponse(String response) {
                                // Do something with response
                                //mTextView.setText(response.toString());

                                // Process the JSON
                                // Loop through the array elements
                                try {
                                    JSONObject person = new JSONObject(response);
                                    String status=person.getString("status");
                                    if(status.equals("1")) {
                                        recyclerView.setVisibility(View.VISIBLE);

                                        JSONArray jsonArray = person.getJSONArray("Product");
                                        for(int i=0;i<jsonArray.length();i++) {
                                            Searchmodel searchmodel = new Searchmodel();

                                            JSONObject serch = jsonArray.getJSONObject(i);
                                            searchmodel.setSearch_id(serch.getInt("id"));
                                            searchmodel.setSearch_item(serch.getString("product_name"));
                                            searchmodel.setComposition_name(serch.getString("composition"));
                                            names.add(searchmodel);
                                        }
                                        searchAdapter=new Search_adapter(names,SearchActivity.this);
                                        recyclerView.setAdapter(searchAdapter);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                                        searchAdapter.notifyDataSetChanged();
                                        /*search_layout_edit.clearFocus();
                                        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        in.hideSoftInputFromWindow(search_layout_edit.getWindowToken(), 0);*/

                                    }
                                    else {
                                        recyclerView.setVisibility(View.GONE);
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        },
                        new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                // Do something when error occurred


                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("search" , srch_text);
                        // params.put("user_id" ,"35");

                        return params;
                    }
                };

                // Add JsonArrayRequest to the RequestQueue
                requestQueue.add(postRequest);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                search_layout_edit.clearFocus();
                InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(search_layout_edit.getWindowToken(), 0);

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    /*private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Searchmodel> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Searchmodel s : names) {
            //if the existing elements contains the search input
            if (s.getSearch_item().toLowerCase().contains(text.toLowerCase())) {
                filterdNames.add(s);
            }

            //calling a method of the adapter class and passing the filtered list
            searchAdapter.filterList(filterdNames);
        }
    }*/
   /* private  void   Search_View(){
        names=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                BaseUrl.BaseUrlNew+"product/all",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());

                        // Process the JSON
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            Searchmodel searchmodel=new Searchmodel();
                            try {
                                JSONObject serch=response.getJSONObject(i);
                                searchmodel.setSearch_id(serch.getInt("id"));
                                searchmodel.setSearch_item(serch.getString("product_name"));
                                searchmodel.setComposition_name(serch.getString("composition"));
                                names.add(searchmodel);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        searchAdapter=new Search_adapter(names,SearchActivity.this);
                        recyclerView.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred


                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }*/
    @Override
    public void onBackPressed() {
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(search_layout_edit.getWindowToken(), 0);
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }

}
