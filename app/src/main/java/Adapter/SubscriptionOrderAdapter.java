package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;
import com.jaayu.SubscriptionDetails;

import java.util.ArrayList;

import Model.HelpModel;
import Model.SubscriptionOrderModel;

public class SubscriptionOrderAdapter extends RecyclerView.Adapter<SubscriptionOrderAdapter.MyViewHolder> {
    private ArrayList<SubscriptionOrderModel> subscriptionOrderModels;
    Context context;

    public SubscriptionOrderAdapter(ArrayList<SubscriptionOrderModel> subscriptionOrderModels, Context context) {
        this.subscriptionOrderModels = subscriptionOrderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_ord_suscription, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionOrderAdapter.MyViewHolder holder, final int position) {
    holder.ord_id.setText(subscriptionOrderModels.get(position).getSubscription_order());
    holder.ord_date.setText(subscriptionOrderModels.get(position).getOrd_date());
    holder.touch_sub_id.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent sendSubSOrdId=new Intent(context, SubscriptionDetails.class);
            sendSubSOrdId.putExtra("Ord_subs_id",String.valueOf(subscriptionOrderModels.get(position).getSubscription_tbl_id()));
            sendSubSOrdId.putExtra("Invoice_Id",subscriptionOrderModels.get(position).getSubscription_order());
            context.startActivity(sendSubSOrdId);
            ((Activity)context).finish();

        }
    });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return subscriptionOrderModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ord_id,ord_date;
        CardView  touch_sub_id;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ord_date=(TextView)itemView.findViewById(R.id.ord_date);
            ord_id=(TextView)itemView.findViewById(R.id.ord_id);
            touch_sub_id=(CardView)itemView.findViewById(R.id.touch_sub_id);
        }
    }
}
