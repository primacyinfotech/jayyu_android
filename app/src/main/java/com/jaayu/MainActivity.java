package com.jaayu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.juanlabrador.badgecounter.BadgeCounter;
import com.google.android.material.navigation.NavigationView;
import com.jaayu.Model.SharedPref;
import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fragment.Home;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    BottomifyNavigationView buttonBarLayout;
    SharedPreferences Cart_item_number_counter;
    SharedPreferences prefs_register;
    private String u_id,status;
    int notifiCart;
    int notti=0;
    TextView showpinbtn;

    int count_cart;
    ProgressDialog progressDialog;
 //   private String Chk_data_hasCart_url="https://work.primacyinfotech.com/jaayu/api/addtocart/all";
    private String Chk_data_hasCart_url="https://work.primacyinfotech.com/jaayu/api/addtocart_chk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cart_item_number_counter = getSharedPreferences(
                "CARTITEM_COUNTER", Context.MODE_PRIVATE);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");

        showpinbtn = findViewById(R.id.show_pin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (savedInstanceState == null) {
            Fragment fm = new Home();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentPanel, fm, "Home_fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.wlogo);
        buttonBarLayout=(BottomifyNavigationView)findViewById(R.id.bottomify_nav);
        buttonBarLayout.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged( BottomifyNavigationView.NavigationItem navigationItem) {
                if(navigationItem.getPosition()==0){
                    Fragment fm = new Home();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.contentPanel, fm, "Home_fragment")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
                if(navigationItem.getPosition()==1){
                   Intent intentGotoReminder=new Intent(MainActivity.this,ReminderList.class);
                   startActivity(intentGotoReminder);
                }
                if (navigationItem.getPosition()==2){
               Intent intentGoOrder=new Intent(MainActivity.this,OrderPage.class);
               startActivity(intentGoOrder);
                }
                if(navigationItem.getPosition()==3){
                   Intent intentGotoProfile=new Intent(MainActivity.this,AccountPage.class);
                   startActivity(intentGotoProfile);
                }

            }
        });

        showpinbtn.setText(new SharedPref(getApplicationContext()).getpincode());


        showpinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PinSearchActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
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
                               /* BadgeCounter.update(MainActivity.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                                        count_cart);*/
                                BadgeCounter.update(MainActivity.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, null,null
                                );
                              /*  BadgeCounter.update(MainActivity.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                                        count_cart);*/

                               /* Intent intentCart=new Intent(MainActivity.this,EmptyCart.class);
                                startActivity(intentCart);
                                overridePendingTransition(0,0);
                                finish();*/

                                //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("1")) {

                                BadgeCounter.update(MainActivity.this,
                                        menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                                        count_cart);
                               /* Intent intentCart=new Intent(MainActivity.this,CartActivity.class);
                                startActivity(intentCart);
                                overridePendingTransition(0,0);
                                finish();*/

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
        /* notifiCart=Cart_item_number_counter.getInt("Counter_Item",0);
        if(notifiCart>0){
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notifiCart);

        }
        else {
            SharedPreferences.Editor editor = Cart_item_number_counter.edit();
            editor.clear();
            editor.commit();
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notti);

        }*/
      /*  else if(notifiCart==0){
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart),R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notifiCart);
        }*/
       /* else {
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
          // BadgeCounter.update(item,count_cart);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_offer) {
            return true;
        }
        if (id == R.id.action_cart) {
           // notifiCart--;
           // BadgeCounter.update(item, notifiCart);

            RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
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
                                    Intent intentCart=new Intent(MainActivity.this,EmptyCart.class);
                                    startActivity(intentCart);
                                    overridePendingTransition(0,0);
                                    finish();

                                    //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                }
                                else if(status.equals("1")) {

                                    BadgeCounter.update(item, count_cart);
                                    Intent intentCart=new Intent(MainActivity.this,CartActivity.class);
                                    startActivity(intentCart);
                                    overridePendingTransition(0,0);
                                    finish();

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
