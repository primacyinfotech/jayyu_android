package Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaayu.AssignPatient;
import com.jaayu.Model.BaseUrl;
import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.AssignPrescriptionModel;

public class AssignPrescriptionAdapter extends RecyclerView.Adapter<AssignPrescriptionAdapter.MyViewHolder> {
    ArrayList<AssignPrescriptionModel> assignPrescriptionModels;
    Context context;
    SharedPreferences assign_press_id;
    String u_id;

    public AssignPrescriptionAdapter(ArrayList<AssignPrescriptionModel> assignPrescriptionModels, Context context) {
        this.assignPrescriptionModels = assignPrescriptionModels;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignPrescriptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assign_presctiption_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignPrescriptionAdapter.MyViewHolder holder, int position) {
        final AssignPrescriptionModel assignPrescriptionModel = assignPrescriptionModels.get(position);
        Picasso.with(context).load(BaseUrl.imageUrl + assignPrescriptionModel.getPress_img()).into(holder.assign_prescription);
        holder.assign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoTo_PatientList = new Intent(context, AssignPatient.class);
                GoTo_PatientList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ((Activity) context).overridePendingTransition(0, 0);
                context.startActivity(GoTo_PatientList);
                ((Activity) context).finish();
                assign_press_id = context.getSharedPreferences(
                        "ASSIGN_PRESCRIPTION", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = assign_press_id.edit();
                editor.putInt("Press_id", assignPrescriptionModel.getPress_id());
                editor.commit();


            }
        });
        holder.assign_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(context, R.style.AppBaseTheme);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(null);
                settingsDialog.setContentView(R.layout.full_screen_image);
                PhotoView imageView = (PhotoView) settingsDialog.findViewById(R.id.full_screen);
                ImageView imageView2 = (ImageView) settingsDialog.findViewById(R.id.close_full_img);
                Picasso.with(context)

                        .load(BaseUrl.imageUrl + assignPrescriptionModel.getPress_img())
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


    }

    @Override
    public int getItemCount() {
        return assignPrescriptionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView assign_prescription;
        Button assign_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            assign_prescription = (ImageView) itemView.findViewById(R.id.assign_prescription);
            assign_btn = (Button) itemView.findViewById(R.id.assign_btn);
        }
    }
}
