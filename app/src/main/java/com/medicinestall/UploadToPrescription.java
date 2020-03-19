package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.juanlabrador.badgecounter.BadgeCounter;
import com.medicinestall.Model.BaseUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.GalleryAdapter;
import Adapter.OldGAlaeryAdapter;
import Model.NewPrescriptionMoel;
import Model.PrescriptionModel;

public class UploadToPrescription extends AppCompatActivity {
    SharedPreferences Cart_item_number_counter;
    private int PICK_IMAGE_REQUEST = 1;
    private int CAMERA_IMAGE_REQUEST = 2;
    public static final int RequestPermissionCode = 1;
    public static final int RequestPermissionCodeWrite = 2;
    ImageView image_view, image_view2, back_button;
    int notifiCart;
    int notti = 0;
    int count_cart;
    SharedPreferences prefs_register;
    private String u_id,u_id2, status;
    String encodedImage;
    private Uri filePath;
    private Bitmap bitmap;
    private Button btn_continue;
    String imageEncoded;
    List<String> imagesEncodedList;
    ProgressDialog progressDialog,progressDialog2;
    private RecyclerView prescription_list_new,prescription_list_old;
    GalleryAdapter galleryAdapter;
   OldGAlaeryAdapter oldGAlaeryAdapter;
    ArrayList<PrescriptionModel> prescriptionModels;
    ArrayList<NewPrescriptionMoel> prescriptionModels2;
   /* ArrayList<PrescriptionModel> prescriptionModels2;*/
    ArrayList<String> pro_idList;
    ArrayList<String> pro_idList_two;
    String gson_prescription_id;


    private  String Upload_prescription_url= BaseUrl.BaseUrlNew+"prescription_required";
    private  String fetchnew_prescription_url=BaseUrl.BaseUrlNew+"prescription_req_display";
    private  String fetchold_prescription_url=BaseUrl.BaseUrlNew+"press_disp_old_add";



    private LinearLayout camera_choose, gallery_choose, past_prescription_choose;
    private String Chk_data_hasCart_url = BaseUrl.BaseUrlNew+"addtocart_chk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_to_prescription);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EnableRuntimePermissionToAccessCamera();
        prescriptionModels=new ArrayList<>();
        prescriptionModels2=new ArrayList<>();
        prescription_list_new=(RecyclerView)findViewById(R.id.prescription_list_new);
        prescription_list_old=(RecyclerView)findViewById(R.id.prescription_list_old);
        btn_continue=(Button)findViewById(R.id.btn_continue);
        camera_choose = (LinearLayout) findViewById(R.id.camera_choose);
        gallery_choose = (LinearLayout) findViewById(R.id.gallery_choose);
        past_prescription_choose=(LinearLayout)findViewById(R.id.past_prescription_choose);
        back_button = (ImageView) findViewById(R.id.back_button);
       /* back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMain = new Intent(UploadToPrescription.this, OrderSummery.class);
                startActivity(gotoMain);
                overridePendingTransition(0, 0);
                finish();
            }
        });*/
        past_prescription_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPastPrescription=new Intent(UploadToPrescription.this,OldPrescription.class);
                startActivity(goToPastPrescription);
                overridePendingTransition(0,0);
                finish();
            }
        });

       // getOldPrescription();






        Cart_item_number_counter = getSharedPreferences(
                "CARTITEM_COUNTER", Context.MODE_PRIVATE);
        prefs_register = getSharedPreferences(
                "Register Details", Context.MODE_PRIVATE);
        u_id = prefs_register.getString("USER_ID", "");
        u_id2 = prefs_register.getString("USER_ID", "");
        back_button = (ImageView) toolbar.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(UploadToPrescription.this,CartActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
        camera_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intentCamera, CAMERA_IMAGE_REQUEST);
            }
        });
        gallery_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
              /*  Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);*/
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* prescriptionModels2=new ArrayList<>();
                PrescriptionModel model=new PrescriptionModel();
                ArrayList<String> pro_idList=new ArrayList<>();
                ArrayList<String> pro_idList_two=new ArrayList<>();
                for(int j=0;j<prescriptionModels2.size();j++){

                    pro_idList.add(String.valueOf(model.getProduct_id()));
                }
                pro_idList_two.addAll(pro_idList);
                Gson gson=new Gson();
                String gson_prescription_id=gson.toJson(pro_idList_two);
                System.out.println("PRO_IMG"+gson_prescription_id);*/
                  if(prescriptionModels.size()<=0){

                  }
                  else {
                      Intent goToOrderSumm=new Intent(UploadToPrescription.this,OrderSummery.class);
                      //goToOrderSumm.putExtra("Prescription",gson_prescription_id);
                      startActivity(goToOrderSumm);
                      overridePendingTransition(0,0);
                  }
                  if(prescriptionModels2.size()<=0){

                  }
                  else {
                      Intent goToOrderSumm=new Intent(UploadToPrescription.this,OrderSummery.class);
                      //goToOrderSumm.putExtra("Prescription",gson_prescription_id);
                      startActivity(goToOrderSumm);
                      overridePendingTransition(0,0);
                  }
              /*  Intent goToOrderSumm=new Intent(UploadToPrescription.this,OrderSummery.class);
                //goToOrderSumm.putExtra("Prescription",gson_prescription_id);
                startActivity(goToOrderSumm);
                overridePendingTransition(0,0);*/

              /* Intent goToOrderSumm=new Intent(UploadToPrescription.this,OrderSummery.class);
               //goToOrderSumm.putExtra("Prescription",gson_prescription_id);
               startActivity(goToOrderSumm);
               overridePendingTransition(0,0);*/
            }
        });
        getNewPrescriptionModel();
        getOldPrescription();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second_menu, menu);
        notifiCart = Cart_item_number_counter.getInt("Counter_Item", 0);
        if (notifiCart > 0) {
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart), R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notifiCart);
        } else {
            BadgeCounter.update(this,
                    menu.findItem(R.id.action_cart), R.drawable.ic_action_cart, BadgeCounter.BadgeColor.RED,
                    notti);

        }
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
        if (id == R.id.action_search) {
            Intent intent = new Intent(UploadToPrescription.this, SearchActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
            return true;
        }
        if (id == R.id.action_cart) {
            // notifiCart--;

            BadgeCounter.update(item, notifiCart);


            RequestQueue queue = Volley.newRequestQueue(UploadToPrescription.this);
            StringRequest postRequest = new StringRequest(Request.Method.POST, Chk_data_hasCart_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            try {
                                //Do it with this it will work
                                JSONObject person = new JSONObject(response);
                                String status = person.getString("status");
                                count_cart = person.getInt("count");
                                if (status.equals("0")) {
                                    BadgeCounter.update(item, count_cart);
                                    Intent intentCart = new Intent(UploadToPrescription.this, EmptyCart.class);
                                    startActivity(intentCart);
                                    overridePendingTransition(0, 0);
                                    finish();
                                    //  Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                                } else if (status.equals("1")) {
                                    BadgeCounter.update(item, count_cart);
                                    Intent intentCart = new Intent(UploadToPrescription.this, CartActivity.class);
                                    startActivity(intentCart);
                                    overridePendingTransition(0, 0);
                                    finish();
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

                    params.put("user_id", u_id2);


                    return params;
                }
            };
            queue.add(postRequest);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                // image_view2.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //return encodedImage;
                RequestQueue queue = Volley.newRequestQueue(UploadToPrescription.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, Upload_prescription_url,
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
                                        progressDialog = new ProgressDialog(UploadToPrescription.this);
                                        progressDialog.setMessage("Uploading..."); // Setting Message
                                        // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                        progressDialog.show(); // Display Progress Dialog
                                        progressDialog.setCancelable(false);
                                        new Thread(new Runnable() {
                                            public void run() {
                                                try {

                                                    Intent refreshPage=new Intent(getApplicationContext(),UploadToPrescription.class);

                                                    startActivity(refreshPage);
                                                    overridePendingTransition(0,0);
                                                    Thread.sleep(2000);
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
                        params.put("user_id", u_id);


                        return params;
                    }
                };
                queue.add(postRequest);
                System.out.println("Encode" + encodedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        /*    String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imagesEncodedList = new ArrayList<String>();
            if (data.getData() != null) {

                Uri mImageUri = data.getData();

                // Get the cursor
                Cursor cursor = getContentResolver().query(mImageUri,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageEncoded = cursor.getString(columnIndex);
                cursor.close();

                ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                mArrayUri.add(mImageUri);
                galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                gvGallery.setAdapter(galleryAdapter);
                gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                        .getLayoutParams();
                mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();

                        galleryAdapter = new GalleryAdapter(getApplicationContext(), mArrayUri);
                        gvGallery.setAdapter(galleryAdapter);
                        gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                .getLayoutParams();
                        mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);

                    }
                }*/




            }
            if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
                Bitmap mphoto = (Bitmap) data.getExtras().get("data");
                //imgPath = data.getData();
                //  image_view.setImageBitmap(mphoto);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                System.out.println("EncodeCam" + encodedImage);
                RequestQueue queue = Volley.newRequestQueue(UploadToPrescription.this);
                StringRequest postRequest = new StringRequest(Request.Method.POST, Upload_prescription_url,
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
                                        progressDialog2 = new ProgressDialog(UploadToPrescription.this);
                                        progressDialog2.setMessage("Uploading..."); // Setting Message
                                        // progressDialog.setTitle("ADD TO CART...."); // Setting Title
                                        progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                                        progressDialog2.show(); // Display Progress Dialog
                                        progressDialog2.setCancelable(false);
                                        new Thread(new Runnable() {
                                            public void run() {
                                                try {
                                                    Thread.sleep(2000);
                                                    Intent refreshPage=new Intent(getApplicationContext(),UploadToPrescription.class);
                                                    startActivity(refreshPage);
                                                    overridePendingTransition(0,0);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                progressDialog2.dismiss();
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
                        params.put("user_id", u_id);


                        return params;
                    }
                };
                queue.add(postRequest);

            }
       /* sharePhoto=getActivity().getSharedPreferences("MyPhoto", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePhoto.edit();
        editor.putString("PHOTO",encodedImage);
        editor.commit();*/

        }
  private void getNewPrescriptionModel(){


      RequestQueue queue = Volley.newRequestQueue(UploadToPrescription.this);
      StringRequest postRequest = new StringRequest(Request.Method.POST, fetchnew_prescription_url,
              new Response.Listener<String>()
              {
                  @Override
                  public void onResponse(String response) {
                      // response
                      Log.d("Response", response);
                      try {
                          //Do it with this it will work

                          JSONObject person = new JSONObject(response);
                          JSONArray jsonArray=person.getJSONArray("prescription_image");
                          System.out.println("Num"+jsonArray.length());




                          for (int i=0;i<jsonArray.length();i++){
                               //  prescriptionModels2.clear();
                              PrescriptionModel  prescriptionModel=new PrescriptionModel();
                              JSONObject jsonObject=jsonArray.getJSONObject(i);
                              prescriptionModel.setProduct_id(jsonObject.getInt("id"));
                              //String img=jsonObject.getString("prescription");
                              prescriptionModel.setPrescription_img(jsonObject.getString("prescription"));

                              prescriptionModels.add(prescriptionModel);


                              galleryAdapter = new GalleryAdapter(prescriptionModels, UploadToPrescription.this);
                              prescription_list_new.setHasFixedSize(true);
                              prescription_list_new.setLayoutManager(new LinearLayoutManager(UploadToPrescription.this,RecyclerView.HORIZONTAL,false));
                              prescription_list_new.setAdapter(galleryAdapter);
                              galleryAdapter.notifyDataSetChanged();
                              /*pro_idList=new ArrayList<>();
                              pro_idList.add(jsonObject.getString("id"));*/






                          }
                             /* galleryAdapter = new GalleryAdapter(prescriptionModels, UploadToPrescription.this);
                              prescription_list.setHasFixedSize(true);
                              prescription_list.setLayoutManager(new LinearLayoutManager(UploadToPrescription.this,LinearLayoutManager.HORIZONTAL,false));
                              prescription_list.setAdapter(galleryAdapter);
                              galleryAdapter.notifyDataSetChanged();*/
                         /* pro_idList_two=new ArrayList<>();
                          for(int j=0;j<pro_idList.size();j++){
                              pro_idList_two.addAll(pro_idList);

                          }*/

                         /* pro_idList_two=new ArrayList<>();
                          pro_idList_two.addAll(pro_idList);

                          Gson gson=new Gson();
                          gson_prescription_id=gson.toJson(pro_idList_two);
                          System.out.println("PRO_IMG"+gson_prescription_id);*/

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
      queue.add(postRequest);

}

private void  getOldPrescription(){
    RequestQueue queue = Volley.newRequestQueue(UploadToPrescription.this);
    StringRequest postRequest = new StringRequest(Request.Method.POST, fetchold_prescription_url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    Log.d("Response", response);
                    try {
                        //Do it with this it will work

                        JSONObject person = new JSONObject(response);
                        JSONArray jsonArray=person.getJSONArray("Prescription");
                        System.out.println("Num"+jsonArray.length());




                        for (int i=0;i<jsonArray.length();i++){
                            //  prescriptionModels2.clear();
                            NewPrescriptionMoel  prescriptnModel=new NewPrescriptionMoel();
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            prescriptnModel.setProduct_id(jsonObject.getInt("id"));

                            prescriptnModel.setPrescription_img(jsonObject.getString("prescription"));

                            prescriptionModels2.add(prescriptnModel);


                            oldGAlaeryAdapter = new OldGAlaeryAdapter(prescriptionModels2, UploadToPrescription.this);
                            prescription_list_old.setHasFixedSize(true);
                            prescription_list_old.setLayoutManager(new LinearLayoutManager(UploadToPrescription.this,RecyclerView.HORIZONTAL,false));
                            prescription_list_old.setAdapter(oldGAlaeryAdapter);
                            oldGAlaeryAdapter.notifyDataSetChanged();
                              /*pro_idList=new ArrayList<>();
                              pro_idList.add(jsonObject.getString("id"));*/






                        }
                             /* galleryAdapter = new GalleryAdapter(prescriptionModels, UploadToPrescription.this);
                              prescription_list.setHasFixedSize(true);
                              prescription_list.setLayoutManager(new LinearLayoutManager(UploadToPrescription.this,LinearLayoutManager.HORIZONTAL,false));
                              prescription_list.setAdapter(galleryAdapter);
                              galleryAdapter.notifyDataSetChanged();*/
                         /* pro_idList_two=new ArrayList<>();
                          for(int j=0;j<pro_idList.size();j++){
                              pro_idList_two.addAll(pro_idList);

                          }*/

                         /* pro_idList_two=new ArrayList<>();
                          pro_idList_two.addAll(pro_idList);

                          Gson gson=new Gson();
                          gson_prescription_id=gson.toJson(pro_idList_two);
                          System.out.println("PRO_IMG"+gson_prescription_id);*/

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
    queue.add(postRequest);
}







    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UploadToPrescription.this,CartActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    }

