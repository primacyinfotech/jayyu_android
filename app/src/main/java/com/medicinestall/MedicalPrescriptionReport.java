package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Adapter.PatientPastPrescrptionAdapter;
import Model.PatientPastPrescriptionModel;

public class MedicalPrescriptionReport extends AppCompatActivity {
    private LinearLayout camera_choose,gallery_choose;
    RecyclerView past_prescription_list;
    ImageView image_view, image_view2, back_button;
    ImageButton cam_btn,gallery_btn;
    private EditText report_name,report_up_date,report_name_gal,report_up_date_gal;
    private Button upload,upload_gal;
    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_IMAGE_REQUEST = 2;
    public static final int RequestPermissionCode = 1;
    public static final int RequestPermissionCodeWrite = 2;
    String encodedImage;
    private Uri filePath;
    private Bitmap bitmap;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String u_id;
    int patient_id;
    SharedPreferences prefs_register;
    private String Patient_upload_prescription_url= BaseUrl.BaseUrlNew+"patient_prescription_add";
    private String Patient_fetch_prescription_url=BaseUrl.BaseUrlNew+"patient_prescription_list";
    PatientPastPrescrptionAdapter patientPastPrescrptionAdapter;
    ArrayList<PatientPastPrescriptionModel> patientPastPrescriptionModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_prescription_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent fetchP_id=getIntent();
        patient_id=fetchP_id.getIntExtra("Patient_Id",0);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id=prefs_register.getString("USER_ID","");
        EnableRuntimePermissionToAccessCamera();
        back_button = (ImageView) findViewById(R.id.back_button);
        past_prescription_list=(RecyclerView)findViewById(R.id.past_prescription_list);
        camera_choose=(LinearLayout)findViewById(R.id.camera_choose);
        gallery_choose=(LinearLayout)findViewById(R.id.gallery_choose);
        getPatientUplodedPrescription();
        camera_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MedicalPrescriptionReport.this);
                dialog.setContentView(R.layout.camera_dialog_box);
                image_view=(ImageView)dialog.findViewById(R.id.image_view);
                cam_btn=(ImageButton)dialog.findViewById(R.id.cam_btn);
                report_name=(EditText)dialog.findViewById(R.id.report_name);
                report_up_date=(EditText)dialog.findViewById(R.id.report_up_date);
                upload=(Button)dialog.findViewById(R.id.upload);
                report_up_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MedicalPrescriptionReport.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        report_up_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                cam_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(intentCamera, CAMERA_IMAGE_REQUEST);
                    }
                });
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(MedicalPrescriptionReport.this);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, Patient_upload_prescription_url,
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
                                            if(status.equals("1")){
                                                progressDialog = new ProgressDialog(MedicalPrescriptionReport.this);
                                                progressDialog.setMessage("Uploading..."); // Setting Message
                                                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                                progressDialog.show(); // Display Progress Dialog
                                                progressDialog.setCancelable(false);
                                                new Thread(new Runnable() {
                                                    public void run() {
                                                        try {
                                                            dialog.dismiss();
                                                            Thread.sleep(2000);
                                                            Intent refreshPage=new Intent(getApplicationContext(),MedicalPrescriptionReport.class);
                                                            startActivity(refreshPage);
                                                            overridePendingTransition(0,0);

                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        progressDialog.dismiss();
                                                    }
                                                }).start();
                                                //Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
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

                                params.put("prescription", encodedImage);
                                params.put("date", report_up_date.getText().toString());
                                params.put("pres_name", report_name.getText().toString());
                                params.put("user_id", u_id);


                                return params;
                            }
                        };
                        queue.add(postRequest);

                    }
                });

                dialog.show();
            }
        });
        gallery_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog2 = new Dialog(MedicalPrescriptionReport.this);
                dialog2.setContentView(R.layout.gallery_dialog_box);
                image_view2=(ImageView)dialog2.findViewById(R.id.image_view2);
                gallery_btn=(ImageButton)dialog2.findViewById(R.id.gallery_btn);
                report_name_gal=(EditText)dialog2.findViewById(R.id.report_name_gal);
                report_up_date_gal=(EditText)dialog2.findViewById(R.id.report_up_date_gal);
                upload_gal=(Button)dialog2.findViewById(R.id.upload_gal);
                gallery_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });
                report_up_date_gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MedicalPrescriptionReport.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        report_up_date_gal.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });
                upload_gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestQueue queue = Volley.newRequestQueue(MedicalPrescriptionReport.this);
                        StringRequest postRequest = new StringRequest(Request.Method.POST, Patient_upload_prescription_url,
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
                                            if(status.equals("1")){
                                                progressDialog = new ProgressDialog(MedicalPrescriptionReport.this);
                                                progressDialog.setMessage("Uploading..."); // Setting Message
                                                // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                                progressDialog.show(); // Display Progress Dialog
                                                progressDialog.setCancelable(false);
                                                new Thread(new Runnable() {
                                                    public void run() {
                                                        try {
                                                            dialog2.dismiss();
                                                            Thread.sleep(2000);
                                                            Intent refreshPage=new Intent(getApplicationContext(),MedicalPrescriptionReport.class);
                                                            startActivity(refreshPage);
                                                            overridePendingTransition(0,0);

                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        progressDialog.dismiss();
                                                    }
                                                }).start();
                                                //Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
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

                                params.put("prescription", encodedImage);
                                params.put("date", report_up_date_gal.getText().toString());
                                params.put("pres_name", report_name_gal.getText().toString());
                                params.put("user_id", u_id);


                                return params;
                            }
                        };
                        queue.add(postRequest);

                    }
                });
                dialog2.show();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoBack=new Intent(MedicalPrescriptionReport.this,PatienEditPage.class);
                GoBack.putExtra("Patient_Id",patient_id);
                startActivity(GoBack);
                overridePendingTransition(0,0);
                finish();
            }
        });

    }
    public void EnableRuntimePermissionToAccessCamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            // Printing toast message after enabling runtime permission.
            Toast.makeText(this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
            case RequestPermissionCodeWrite:
                if (PResult.length > 0 && per[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // check whether storage permission granted or not.
                    if (PResult[0] == PackageManager.PERMISSION_GRANTED) {
                        //do what you want;
                        Toast.makeText(this, "Permission Granted, Now your application can access EXTERNAL.", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                image_view2.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //return encodedImage;
                System.out.println("Encode" + encodedImage);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            //imgPath = data.getData();
             image_view.setImageBitmap(mphoto);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            System.out.println("EncodeCam" + encodedImage);


        }
       /* sharePhoto=getActivity().getSharedPreferences("MyPhoto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePhoto.edit();
        editor.putString("PHOTO",encodedImage);
        editor.commit();*/


    }
 private  void   getPatientUplodedPrescription(){
     patientPastPrescriptionModels=new ArrayList<>();
     RequestQueue requestQueue = Volley.newRequestQueue(MedicalPrescriptionReport.this);
     StringRequest postRequest = new StringRequest(Request.Method.POST,Patient_fetch_prescription_url,
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
                             JSONArray jsonArray=person.getJSONArray("prtescription");
                             for(int i=0;i<jsonArray.length();i++){
                                 PatientPastPrescriptionModel oldPrescriptionModel=new PatientPastPrescriptionModel();
                                 JSONObject object=jsonArray.getJSONObject(i);
                                 oldPrescriptionModel.setPatient_pres_id(object.getInt("id"));
                                 oldPrescriptionModel.setPast_pres_head(object.getString("pres_name"));
                                 oldPrescriptionModel.setPast_pres_date(object.getString("date"));
                                 oldPrescriptionModel.setPast_pres_img(object.getString("prescription"));
                                 patientPastPrescriptionModels.add(oldPrescriptionModel);

                             }

                             patientPastPrescrptionAdapter=new PatientPastPrescrptionAdapter(patientPastPrescriptionModels,MedicalPrescriptionReport.this);
                             past_prescription_list.setHasFixedSize(true);
                             past_prescription_list.setLayoutManager(new LinearLayoutManager(MedicalPrescriptionReport.this));
                             past_prescription_list.setAdapter(patientPastPrescrptionAdapter);
                             patientPastPrescrptionAdapter.notifyDataSetChanged();

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
    }

    @Override
    public void onBackPressed() {
        Intent GoBack=new Intent(MedicalPrescriptionReport.this,PatienEditPage.class);
        GoBack.putExtra("Patient_Id",patient_id);
        startActivity(GoBack);
        overridePendingTransition(0,0);
        finish();
    }
}
