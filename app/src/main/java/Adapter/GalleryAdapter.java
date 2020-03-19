package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.jaayu.UploadToPrescription;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.PrescriptionModel;

public class GalleryAdapter  extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    ArrayList<PrescriptionModel> prescriptionModels;
    ArrayList<PrescriptionModel> prescriptionModels2;
    private Context context;
    private Context ctx;
    private String prescription_delete_url= BaseUrl.BaseUrlNew+"prescription_req_del";
    private String Oldprescription_delete_url=BaseUrl.BaseUrlNew+"prescription_old_del";


    public GalleryAdapter(ArrayList<PrescriptionModel> prescriptionModels, Context context) {
        this.prescriptionModels = prescriptionModels;
        this.context = context;
    }

   /* public GalleryAdapter( ArrayList<PrescriptionModel> prescriptionModels2, Context context, Context ctx) {

        this.prescriptionModels2 = prescriptionModels2;
        this.context = context;
        this.ctx = ctx;
    }*/

    @NonNull
    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clip_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Picasso.with(context).load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + prescriptionModels.get(position).getPrescription_img()).into(holder.ivGallery);

        holder.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(null);
                settingsDialog.setContentView(R.layout.full_screen_image);
                PhotoView imageView=(PhotoView)settingsDialog.findViewById(R.id.full_screen);
                ImageView  imageView2=(ImageView)settingsDialog.findViewById(R.id.close_full_img);
                Picasso.with(context)

                        .load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + prescriptionModels.get(position).getPrescription_img())
                        .into(imageView);
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.dismiss();
                    }
                });
                settingsDialog.show();
            }
        });

            holder.img_id.setText("" +prescriptionModels.get(position).getProduct_id());
            holder.delete_prescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, prescription_delete_url,
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
                                            Intent intent = new Intent(context, UploadToPrescription.class);
                                            // intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                            context.startActivity(intent);
                                            ((Activity) context).overridePendingTransition(0, 0);
                                            ((Activity) context).finish();

                                            prescriptionModels.remove(position);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, prescriptionModels.size());
                                            Toast.makeText(context, status + " " + "Removed", Toast.LENGTH_LONG).show();


                                        } else {
                                            Toast.makeText(context, status + " " + "Not Removed", Toast.LENGTH_LONG).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                            params.put("pid", String.valueOf(prescriptionModels.get(position).getProduct_id()));


                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            });






    }
    @Override
    public int getItemCount() {
        return prescriptionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete_prescription;
        ImageView ivGallery;
        TextView img_id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delete_prescription=(ImageButton)itemView.findViewById(R.id.delete_prescription);
            ivGallery=(ImageView)itemView.findViewById(R.id.ivGallery);
            img_id=(TextView)itemView.findViewById(R.id.pres_id);
        }
    }
}
