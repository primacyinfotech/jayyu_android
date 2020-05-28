package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.HelpModel;
import Model.SubscriptionDeliveryDateModel;

public class SubscriptionDeliveryDateAdapter  extends RecyclerView.Adapter<SubscriptionDeliveryDateAdapter.MyViewHolder>  {
    ArrayList<SubscriptionDeliveryDateModel> subscriptionDeliveryDateModels;
    Context context;

    public SubscriptionDeliveryDateAdapter(ArrayList<SubscriptionDeliveryDateModel> subscriptionDeliveryDateModels, Context context) {
        this.subscriptionDeliveryDateModels = subscriptionDeliveryDateModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionDeliveryDateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscription_delivery_date_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionDeliveryDateAdapter.MyViewHolder holder, int position) {
        holder.date_of_delivery.setText(subscriptionDeliveryDateModels.get(position).getDelivery_date());
        holder.date_of_delivery_status.setText(subscriptionDeliveryDateModels.get(position).getDelivery_status());

    }

    @Override
    public int getItemCount() {
        return subscriptionDeliveryDateModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date_of_delivery_status,date_of_delivery;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date_of_delivery=(TextView)itemView.findViewById(R.id.date_of_delivery);
            date_of_delivery_status=(TextView)itemView.findViewById(R.id.date_of_delivery_status);
        }
    }
}
