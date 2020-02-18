package fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jaayu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapter.Search_adapter;
import Model.Searchmodel;

import static com.android.volley.Request.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class Searchfragment extends Fragment {
    EditText search_layout_edit;
    RecyclerView recyclerView;
    Search_adapter searchAdapter;
    ArrayList<Searchmodel> names;
    private String search_url="https://work.primacyinfotech.com/jaayu/api/product/all";


    public Searchfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_searchfragment, container, false);
        Search_View();

      /*  names.add(new Searchmodel("Calpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("kalpol",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Roxin",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Rantac",R.drawable.ic_action_arrow));
        names.add(new Searchmodel("Casit",R.drawable.ic_action_arrow));*/

        search_layout_edit=(EditText)view.findViewById(R.id.search_layout_edit);
        recyclerView=(RecyclerView)view.findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


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
                filter(editable.toString());
            }
        });
        return view;
    }
    private void filter(String text) {
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
    }
    private  void   Search_View(){
        names=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                search_url,
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
    }
}
