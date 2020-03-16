package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jaayu.R;

import java.util.ArrayList;

import Model.OldPrescriptionModel;
import Model.OnlyOldPrescriptionModel;

public class OnlyOldPresciptionAdapter extends RecyclerView.Adapter<OnlyOldPresciptionAdapter.MyViewHolder> {
    public  static  ArrayList<OnlyOldPrescriptionModel> oldPrescriptionModels;
    Context context;

    public OnlyOldPresciptionAdapter(ArrayList<OnlyOldPrescriptionModel> oldPrescriptionModels, Context context) {
        this.oldPrescriptionModels = oldPrescriptionModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OnlyOldPresciptionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.old__prescription_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OnlyOldPresciptionAdapter.MyViewHolder holder, final int position) {
        holder.date_prescription.setText(oldPrescriptionModels.get(position).getOld_prescription_date());
        Glide.with(context).load("https://work.primacyinfotech.com/jaayu/upload/prescription/"+oldPrescriptionModels.get(position).getOldprescription_url()).into(holder.prescription_img);
        holder.select_prescription.setChecked(oldPrescriptionModels.get(position).getSelected());
       // holder.select_prescription.setTag(oldPrescriptionModels.get(position));
        holder.select_prescription.setTag(position);
        holder.select_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   CheckBox cb=(CheckBox) v;
                OnlyOldPrescriptionModel oldPModel=(OnlyOldPrescriptionModel) cb.getTag();
                oldPModel.setSelected(cb.isChecked());
                oldPrescriptionModels.get(position).setSelected(cb.isChecked());*/
                Integer pos = (Integer)  holder.select_prescription.getTag();
                if(oldPrescriptionModels.get(pos).getSelected()){
                    oldPrescriptionModels.get(pos).setSelected(false);
                }
                else {
                    oldPrescriptionModels.get(pos).setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return oldPrescriptionModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView prescription_img;
        TextView date_prescription;
        CheckBox select_prescription;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prescription_img=(ImageView)itemView.findViewById(R.id.prescription_img);
            date_prescription=(TextView)itemView.findViewById(R.id.date_prescription);
            select_prescription=(CheckBox)itemView.findViewById(R.id.select_prescription);
        }
    }
    public ArrayList<OnlyOldPrescriptionModel> getOldPrescription() {
        return oldPrescriptionModels;
    }
}
