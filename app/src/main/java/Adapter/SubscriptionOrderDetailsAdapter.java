package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.SubscriptionOrderDetailsModel;
import Model.SubscriptionOrderModel;

public class SubscriptionOrderDetailsAdapter extends RecyclerView.Adapter<SubscriptionOrderDetailsAdapter.MyViewHolder> {
    private ArrayList<SubscriptionOrderDetailsModel> subscriptionOrderDetailsModels;
    Context context;

    public SubscriptionOrderDetailsAdapter(ArrayList<SubscriptionOrderDetailsModel> subscriptionOrderDetailsModels, Context context) {
        this.subscriptionOrderDetailsModels = subscriptionOrderDetailsModels;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionOrderDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscription_order_item_detail_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionOrderDetailsAdapter.MyViewHolder holder, int position) {
        holder.item_name.setText(subscriptionOrderDetailsModels.get(position).getItem_name());
        holder.unit.setText(subscriptionOrderDetailsModels.get(position).getItem_unit());
        holder.qyt.setText(subscriptionOrderDetailsModels.get(position).getItem_qty());

        holder.med_company.setText(subscriptionOrderDetailsModels.get(position).getCom_name());
        holder.price_amt.setText(subscriptionOrderDetailsModels.get(position).getPrice_normal());
        holder.item_total.setPaintFlags(holder.item_total.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.item_total.setText(subscriptionOrderDetailsModels.get(position).getMrp_price());
        String discount = subscriptionOrderDetailsModels.get(position).getNormal_disc().trim();
        if (discount != null && !discount.equals("null") && !discount.equals("") && !discount.equals("0"))
            holder.percentage.setText(discount);
        else
            holder.ll_saving.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return subscriptionOrderDetailsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name, unit, qyt, med_company, price_amt, item_total, percentage;
        LinearLayout ll_saving;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            unit = (TextView) itemView.findViewById(R.id.unit);
            qyt = (TextView) itemView.findViewById(R.id.qyt);
            med_company = itemView.findViewById(R.id.med_company);
            price_amt = itemView.findViewById(R.id.price_amt);
            item_total = itemView.findViewById(R.id.item_total);
            percentage = itemView.findViewById(R.id.percentage);
            ll_saving = itemView.findViewById(R.id.ll_saving);

        }
    }
}
