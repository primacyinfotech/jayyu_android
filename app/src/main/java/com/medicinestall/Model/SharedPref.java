package com.medicinestall.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    Context context;

    public SharedPref(Context context) {
        this.context = context;
    }

    public void saveUserDetails(String userid, String selectedpin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDet", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedpin", selectedpin);
        editor.putString("userid", userid);
        editor.commit();

    }
    public String getpincode()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDet", Context.MODE_PRIVATE);
        return sharedPreferences.getString("selectedpin","700001");
    }
    public String getUserID()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDet", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userid",null);
    }


    public void logout()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userDet", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("selectedpin");
        editor.remove("userid");
        editor.clear();
        editor.commit();
    }

}
