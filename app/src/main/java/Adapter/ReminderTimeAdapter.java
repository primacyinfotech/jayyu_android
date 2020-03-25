package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.CartActivity;
import com.jaayu.R;
import com.jaayu.ReminderAddPage;

import java.util.ArrayList;

import Model.ReminderTimeModel;

public class ReminderTimeAdapter extends RecyclerView.Adapter<ReminderTimeAdapter.MyViewHolder> {
  public  static   ArrayList<ReminderTimeModel> reminderTimeModels;
    Context context;
    private int selectedPosition = -1;

    public ReminderTimeAdapter(ArrayList<ReminderTimeModel> reminderTimeModels, Context context) {
        this.reminderTimeModels = reminderTimeModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ReminderTimeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_time_schdule, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReminderTimeAdapter.MyViewHolder holder, int position) {
        holder.set_time.setText(reminderTimeModels.get(position).getTime_scheduled());
        holder.qty_items.setText(reminderTimeModels.get(position).getQty_medicine());
        holder.selected_time.setChecked(reminderTimeModels.get(position).getSelected());
        holder.selected_time.setTag(position);
        holder.selected_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer pos = (Integer)   holder.selected_time.getTag();
                if(reminderTimeModels.get(pos).getSelected()){
                    reminderTimeModels.get(pos).setSelected(false);
                }
                else {
                    reminderTimeModels.get(pos).setSelected(true);
                }
            }
        });

        holder.edit_qty_schdule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }
    public String getSelectedItem(){
        if(selectedPosition!=-1){
            String assign_time=reminderTimeModels.get(selectedPosition).getTime_scheduled();
            Intent intent= new Intent(context, ReminderAddPage.class);
            intent.putExtra("time_value",assign_time);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            ((Activity) context).overridePendingTransition(0,0);
            context.startActivity(intent);
            return String.valueOf(reminderTimeModels.get(selectedPosition));
        }
       return "";
    }
    @Override
    public int getItemCount() {
         return (null != reminderTimeModels ? reminderTimeModels.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox selected_time;
        TextView set_time,qty_items;
        ImageView edit_qty_schdule;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            selected_time=(CheckBox)itemView.findViewById(R.id.selected_time);
            set_time=(TextView)itemView.findViewById(R.id.set_time);
            qty_items=(TextView)itemView.findViewById(R.id.qty_items);
            edit_qty_schdule=(ImageView)itemView.findViewById(R.id.edit_qty_schdule);




        }
    }
}
