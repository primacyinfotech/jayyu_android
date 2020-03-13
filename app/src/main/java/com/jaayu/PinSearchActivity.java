package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaayu.Model.BaseUrl;
import com.jaayu.Model.LocationPermission;
import com.jaayu.Model.SharedPref;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.widget.AdapterView.*;

public class PinSearchActivity extends AppCompatActivity implements LocationListener {
    SharedPreferences prefs_register;
    String u_id;
    AddressList addresadap;
    ListView addlist;
    LocationManager locationManager;
    TextView locchecktv;
    String pincode;
    Button checkbtn;
    EditText editpinet ;
    Boolean whetherchekedornotdefault = false;
    String specialidfordefault;
    ImageView closebtn;
    CheckBox checkboxclick;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> firstname = new ArrayList<>();
    ArrayList<String> phonenum = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> landmark = new ArrayList<>();
    ArrayList<String> zipcode = new ArrayList<>();
    ArrayList<String> atype = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_search);
        addlist = findViewById(R.id.addresslist);
        editpinet = findViewById(R.id.editpinet);
        checkbtn = findViewById(R.id.checkbtn);
        closebtn = findViewById(R.id.closebtn);


        locchecktv = findViewById(R.id.select);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", null);
        Log.i("tag", "user id " + u_id);
        if (u_id != null) {
            listingapicall();
        }
        defaultaddresschange();


        locchecktv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(PinSearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PinSearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Log.i("tag","permission denied");
                    LocationPermission permission = new LocationPermission(PinSearchActivity.this);
                    permission.checkLocationPermission();


                }
                else {

                    locationgetter();
                }
            }
        });

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pintoget = editpinet.getText().toString().trim();
                pincodeck(pintoget);
            }
        });

        addlist.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ziptosend = zipcode.get(position);
                pincodeck(ziptosend);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        closebtn.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v) {
                                            onBackPressed();
                                        }
                                    }
        );
    }


    private void locationgetter()
    {
        LocationPermission permission = new LocationPermission(PinSearchActivity.this);
        permission.checkLocationPermission();
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        Double currentLatitude = myLocation.getLatitude();
        Double currentLongitude = myLocation.getLongitude();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            pincode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            pincodeck(pincode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void pincodeck(final String pin)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(PinSearchActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"pincode_checking",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject jsonres = new JSONObject(response);
                            String status=jsonres.getString("status");
                            if(status.contentEquals("1")) {
                                new SharedPref(getApplicationContext()).saveUserDetails(u_id, pin);
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(PinSearchActivity.this, "This Pin is unavailable at the moment", Toast.LENGTH_SHORT).show();
                            }

                        }

                        catch (JSONException e) {
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
                params.put("pincode", pin);
                return params;
            }
        };

        requestQueue.add(postRequest);
    }


    private void listingapicall()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(PinSearchActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"order_address",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject jsonres = new JSONObject(response);
                            String status=jsonres.getString("status");
                            if(status.contentEquals("success")) {
                                JSONArray jsonArray= jsonres.getJSONArray("address");
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    firstname.add(jsonObject.getString("first_name"));
                                    id.add(jsonObject.getString("id"));
                                    phonenum.add(jsonObject.getString("phone"));
                                    address.add(jsonObject.getString("address"));
                                    landmark.add(jsonObject.getString("landmark"));
                                    zipcode.add(jsonObject.getString("zip_code"));
                                    atype.add(jsonObject.getString("atype"));
                            }
                               addresadap = new AddressList(getApplicationContext(),id, firstname, phonenum, address, landmark, zipcode, atype);
                                addlist.setAdapter(addresadap);
                                }

                            }

                        catch (JSONException e) {
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

    private String getZipCodeFromLocation(Location location) {
        Address addr = getAddressFromLocation(location);
        Log.i("tag","zip"+addr.getPostalCode());
        return addr.getPostalCode() == null ? "" : addr.getPostalCode();
    }

    private Address getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this);
        Address address = new Address(Locale.getDefault());
        try {
            List<Address> addr = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addr.size() > 0) {
                address = addr.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void onLocationChanged(Location location) {
      String zip = new PinSearchActivity().getZipCodeFromLocation(location);
      Log.i("tag","zip"+zip);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    class AddressList extends BaseAdapter
    {
        ArrayList<String> mid = new ArrayList<>();
        ArrayList<String> mfirstname = new ArrayList<>();
        ArrayList<String> mphonenum = new ArrayList<>();
        ArrayList<String> maddress = new ArrayList<>();
        ArrayList<String> mlandmark = new ArrayList<>();
        ArrayList<String>  mzipcode = new ArrayList<>();
        ArrayList<String> matype = new ArrayList<>();

        CheckBox cheboxclick;

        ImageView typeicon;
        TextView addresstv, landmarktv, nametv, namdtypetv, pincodetv, phonetv;


        public AddressList(Context applicationContext, ArrayList<String> id, ArrayList<String> firstname, ArrayList<String> phonenum, ArrayList<String> address, ArrayList<String> landmark, ArrayList<String> zipcode, ArrayList<String> atype) {
         mid = id;
         mfirstname = firstname;
         mphonenum = phonenum;
         maddress = address;
         mlandmark = landmark;
         mzipcode = zipcode;
         matype = atype;
        }

        @Override
        public int getCount() {
            return mid.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_address, null);
            addresstv = convertView.findViewById(R.id.addresstv);
            namdtypetv = convertView.findViewById(R.id.atypenname);
            nametv = convertView.findViewById(R.id.nametv);
            landmarktv = convertView.findViewById(R.id.landmarktv);
            pincodetv = convertView.findViewById(R.id.pincodetv);
            phonetv = convertView.findViewById(R.id.phonetv);
            typeicon = convertView.findViewById(R.id.typeicon);
            cheboxclick = convertView.findViewById(R.id.checkboxclick);

            if(whetherchekedornotdefault == true && specialidfordefault != null)
            {
                if (mid.get(position).contentEquals(specialidfordefault))
                {
                    cheboxclick.setChecked(true);
                }
            }
            if (mfirstname.get(position) != null)
            {
                nametv.setText(mfirstname.get(position));
            }
            if (mphonenum.get(position) != null)
            {
                phonetv.setText(mphonenum.get(position));
            }
            if (mlandmark.get(position) != null && !mlandmark.get(position).contentEquals("null"))
            {
                landmarktv.setText(mlandmark.get(position));
            }
            if (mzipcode.get(position) != null)
            {
                pincodetv.setText(mzipcode.get(position));
            }
            if (maddress.get(position) != null)
            {
                addresstv.setText(maddress.get(position));
            }
            if (matype.get(position) != null && mfirstname.get(position) != null)
            {
                namdtypetv.setText(mfirstname.get(position) + " "+matype.get(position));
            }
            else if (matype.get(position) == null && mfirstname.get(position) != null)
            {
              namdtypetv.setText(mfirstname.get(position));
            }
            else if (matype.get(position) != null && mfirstname.get(position) == null)
            {
                namdtypetv.setText(matype.get(position));
            }
            if (matype.get(position) != null)
            {
                if (matype.get(position).contentEquals("home"))
                {
                    typeicon.setImageResource(R.drawable.ic_action_home);
                }
                else
                {
                    typeicon.setImageResource(R.drawable.ic_belon_location);
                }
            }
            else
            {
                typeicon.setImageResource(R.drawable.ic_belon_location);
            }

            cheboxclick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        String addressid = id.get(position);
                        defaultcheckchange(addressid);
                        Log.i("tag","getting checked address id"+addressid);

                        defaultaddresschange();
                        if(whetherchekedornotdefault && specialidfordefault != null)
                        {
                            if (mid.get(position).contentEquals(specialidfordefault))
                            {
                                cheboxclick.setChecked(true);

                            }
                            else
                            {
                                cheboxclick.setChecked(false);
                            }
                        }
                    }else {
                        //do something else
                    }
                }
            });


            //////
//////
            return convertView;
        }
    }


    private void defaultcheckchange(final String a_id)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(PinSearchActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"order_addres_change",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject jsonres = new JSONObject(response);
                            String status=jsonres.getString("status");
                            if(status.contentEquals("1")) {
                               Log.i("tag","default address change");

                            }
                            else
                            {

                                //  Toast.makeText(PinSearchActivity.this, "This Pin is unavailable at the moment", Toast.LENGTH_SHORT).show();
                            }

                        }

                        catch (JSONException e) {
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
                params.put("aId", a_id);
                return params;
            }
        };

        requestQueue.add(postRequest);

    }

    private void defaultaddresschange()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(PinSearchActivity.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, BaseUrl.BaseUrlNew+"default_address_change",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            //Do it with this it will work
                            JSONObject jsonres = new JSONObject(response);
                            String status=jsonres.getString("status");
                            if(status.contentEquals("1")) {
                               specialidfordefault  = jsonres.getString("aId");
                              whetherchekedornotdefault = true;
                            }
                            else
                            {
                                whetherchekedornotdefault = false;
                              //  Toast.makeText(PinSearchActivity.this, "This Pin is unavailable at the moment", Toast.LENGTH_SHORT).show();
                            }

                        }

                        catch (JSONException e) {
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


}
