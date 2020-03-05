package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_prescription_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EnableRuntimePermissionToAccessCamera();
        back_button = (ImageView) findViewById(R.id.back_button);
        past_prescription_list=(RecyclerView)findViewById(R.id.past_prescription_list);
        camera_choose=(LinearLayout)findViewById(R.id.camera_choose);
        gallery_choose=(LinearLayout)findViewById(R.id.gallery_choose);
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
                        dialog.dismiss();
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
                upload_gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       dialog2.dismiss();
                    }
                });
                dialog2.show();
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
}
