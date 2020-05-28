package fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.jaayu.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.Search_adapter;
import Model.Searchmodel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Searchfragment extends Fragment {
    EditText search_layout_edit;
    RecyclerView recyclerView;
    Search_adapter searchAdapter;
    ArrayList<Searchmodel> names;
    ImageView fack_image;
    ProgressDialog progressDialog;
    private String search_url=BaseUrl.BaseUrlNew+"product/search";
   String srch_text;

    public Searchfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_searchfragment, container, false);
      /*  progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setMessage("loading....");*/


      /*  names.add(new Searchmodel("Calpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("kalpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Roxin",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Rantac",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Casit",R.drawable.ic_action_arrow));*/

        search_layout_edit=(EditText)view.findViewById(R.id.search_layout_edit);
       /* search_layout_edit.setFocusableInTouchMode(true);
        search_layout_edit.requestFocus();

        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(search_layout_edit.getWindowToken(), 1);*/

        //fack_image=(ImageView)view.findViewById(R.id.fack_image);
        recyclerView=(RecyclerView)view.findViewById(R.id.rv_search);
        //Search_View();




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
                //srch_text=search_layout_edit.getText().toString();
             //  final String edit=editable.toString();
               // search_layout_edit.setText(edit);
                srch_text=search_layout_edit.getText().toString();
                names=new ArrayList<>();
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                                        searchAdapter=new Search_adapter(names,getActivity());
                                        recyclerView.setAdapter(searchAdapter);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                        searchAdapter.notifyDataSetChanged();

                                        search_layout_edit.clearFocus();
                                        InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                        in.hideSoftInputFromWindow(search_layout_edit.getWindowToken(), 0);
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
        return view;
    }
  /*  private void filter(String text) {
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
   /*private  void   Search_View(){
       names=new ArrayList<>();
       RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                       searchAdapter=new Search_adapter(names,getActivity());
                       recyclerView.setAdapter(searchAdapter);
                       recyclerView.setHasFixedSize(true);
                       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
  /*  private  void   Search_View(){
        srch_text=search_layout_edit.getText().toString();
        names=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                               JSONArray jsonArray = person.getJSONArray("Product");
                               for(int i=0;i<jsonArray.length();i++) {
                                   Searchmodel searchmodel = new Searchmodel();

                                   JSONObject serch = jsonArray.getJSONObject(i);
                                   searchmodel.setSearch_id(serch.getInt("id"));
                                   searchmodel.setSearch_item(serch.getString("product_name"));
                                   searchmodel.setComposition_name(serch.getString("composition"));
                                   names.add(searchmodel);
                               }
                               searchAdapter=new Search_adapter(names,getActivity());
                               recyclerView.setAdapter(searchAdapter);
                               searchAdapter.notifyDataSetChanged();
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

                 params.put("search" ,srch_text);
                // params.put("user_id" ,"35");

                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(postRequest);
    }*/
}
