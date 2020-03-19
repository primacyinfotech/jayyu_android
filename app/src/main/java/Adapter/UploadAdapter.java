package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;


import Model.UploadPrescriptionModel;

public class UploadAdapter  extends RecyclerView.Adapter<UploadAdapter.MyViewHolder> {
    private ArrayList<UploadPrescriptionModel> modelList;
    private Context context;

    public UploadAdapter(ArrayList<UploadPrescriptionModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public UploadAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upload_prescription_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadAdapter.MyViewHolder holder, int position) {
        UploadPrescriptionModel mList = modelList.get(position);
        holder.prescription_name.setText(mList.getReport_name());
        holder.upload_date.setText(mList.getUploaded_date());
        holder.upload_img_prescription.setImageResource(mList.getUpload_image());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView prescription_name,upload_date;
        ImageView upload_img_prescription;
        Button delete_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prescription_name=(TextView)itemView.findViewById(R.id.prescription_name);
            upload_date=(TextView)itemView.findViewById(R.id.upload_date);
            upload_img_prescription=(ImageView)itemView.findViewById(R.id.upload_img_prescription);
            delete_btn=(Button)itemView.findViewById(R.id.delete_btn);
        }
    }
}
