package fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.jaayu.CustomSlider;
import com.jaayu.Model.BaseUrl;
import com.jaayu.OnlyUploadPrescription;
import com.jaayu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {
    LinearLayout search_layout;
    private Button upload_prescription;
    private String sliderUrl= BaseUrl.BaseUrlNew+"slider";
    private SliderLayout banner_slider;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_home, container, false);
        banner_slider = (SliderLayout) view.findViewById(R.id.relative_banner);
        search_layout=(LinearLayout)view.findViewById(R.id.search_layout);
        upload_prescription=(Button)view.findViewById(R.id.upload_prescription);
        upload_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGotoUploadPrescription=new Intent(getActivity(), OnlyUploadPrescription.class);
                getActivity().startActivity(intentGotoUploadPrescription);
            }
        });
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
                Toast.makeText(getActivity(),"Ok",Toast.LENGTH_LONG).show();
            }
        });
        makeGetBannerSliderRequest();
        return view;
    }
/*private void makeGetBannerSliderRequest(){
    ArrayList<HashMap<String, String>> listarray = new ArrayList<>();
    List<String> images = new ArrayList<>();
    HashMap<String,String> file_maps = new HashMap<String,String>();
    file_maps.put("Hannibal","https://work.primacyinfotech.com/jaayu/upload/slider/1579076199.jpg");
    file_maps.put("Big Bang Theory","https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?cs=srgb&dl=nature-red-love-romantic-67636.jpg&fm=jpg");
    file_maps.put("House of Cards","https://images.pexels.com/photos/67636/rose-blue-flower-rose-blooms-67636.jpeg?cs=srgb&dl=nature-red-love-romantic-67636.jpg&fm=jpg");
    listarray.add(file_maps);

   *//* HashMap<String, String> url_maps = new HashMap<String, String>();
    url_maps.put("slider_title", "slider_title");
    url_maps.put("sub_cat", "sub_cat");
    url_maps.put("slider_image", "https://www.pexels.com/photo/nature-red-love-romantic-67636/");
    listarray.add(url_maps);*//*
  *//*  for (HashMap<String, String> name : listarray) {
        CustomSlider textSliderView = new CustomSlider(getActivity());
        textSliderView.description(name.get("")).image(name.get("slider_image")).setScaleType(BaseSliderView.ScaleType.Fit);
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("extra", name.get("slider_title"));
        textSliderView.getBundle().putString("extra", name.get("sub_cat"));
        banner_slider.addSlider(textSliderView);
    }*//*
    for(HashMap<String, String> name : listarray) {

        // initialize a SliderLayout
        CustomSlider textSliderView = new CustomSlider(getActivity());
        textSliderView.description(name.get("")).image(name.get("slider_image")).setScaleType(BaseSliderView.ScaleType.Fit);
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle().putString("extra", name.get("slider_title"));
        textSliderView.getBundle().putString("extra", name.get("sub_cat"));
        banner_slider.addSlider(textSliderView);
    }
}*/
private void makeGetBannerSliderRequest(){
    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
    StringRequest postRequest = new StringRequest(Request.Method.POST,sliderUrl,
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
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();
                            JSONArray jsonArray=person.getJSONArray("slider");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject =jsonArray.getJSONObject(i);
                                HashMap<String, String> url_maps = new HashMap<String, String>();
                                url_maps.put("slider_id",jsonObject.getString("id"));
                                url_maps.put("slider_image","https://work.primacyinfotech.com/jaayu/"+jsonObject.getString("image"));
                                listarray.add(url_maps);
                            }
                            for (HashMap<String, String> name : listarray){
                                CustomSlider textSliderView = new CustomSlider(getActivity());
                                textSliderView.description(name.get("")).image(name.get("slider_image")).setScaleType(BaseSliderView.ScaleType.Fit);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle().putString("extra", name.get("slider_id"));
                                banner_slider.addSlider(textSliderView);
                                final String sub_cat = (String) textSliderView.getBundle().get("extra");
                                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {
                                        Toast.makeText(getActivity(), "" + sub_cat, Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }


                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error

                }
            }
    ) ;/*{
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();

            params.put("user_id", u_id);
            params.put("first_name", f_nm);
            params.put("last_name", l_nm);
            params.put("phone", mob_num);
            params.put("email", e_mail);
            params.put("address", addr);
            params.put("landmark", l_mark);
            params.put("state", state);
            params.put("city", city);
            params.put("zip_code", pin_no);
            if(work_pref2.equals("")){
                params.put("atype", work_pref);
            }
            else {
                params.put("atype", work_pref2);
            }

            return params;
        }*/


    requestQueue.add(postRequest);
}
}
