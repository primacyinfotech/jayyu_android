package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.OrderDetails;
import com.jaayu.R;

import java.util.ArrayList;

import Model.OrderModel;

public class OrderAdapter  extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private ArrayList<OrderModel> modelList;
    private Context context;

    public OrderAdapter(ArrayList<OrderModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_view, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        final OrderModel mList = modelList.get(position);

        holder.order_name.setText(mList.getOrder_mame());
       // holder.order_name.setText(order_people_name);
        holder.order_id.setText(mList.getOrder_id());
        holder.order_date.setText(mList.getOrder_date());
        holder.active_order_two.setVisibility(View.GONE);
        holder.active_order_three.setVisibility(View.GONE);
        holder.card_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGotodetaails=new Intent(context, OrderDetails.class);
                intentGotodetaails.putExtra("Order_id",mList.getTbl_order_id());
                intentGotodetaails.putExtra("Instant",mList.getInstant());
                context.startActivity(intentGotodetaails);
                ((Activity) context).overridePendingTransition(0,0);
                ((Activity) context).finish();


            }
        });

        String status=mList.getShip_status();
        if(status.equals("0")){
            holder.order_status_icon.setImageResource(R.drawable.tickyellow);
            holder.active_order.setVisibility(View.VISIBLE);
            holder.active_order_two.setVisibility(View.GONE);
            holder.active_order_three.setVisibility(View.GONE);
            holder.active_order_four.setVisibility(View.GONE);
            holder.active_order_five.setVisibility(View.GONE);
            holder.reoder_btn.setVisibility(View.GONE);
        }
        if(status.equals("1")){
            holder.order_status_icon.setImageResource(R.drawable.tick);
            holder.active_order.setVisibility(View.GONE);
            holder.active_order_two.setVisibility(View.VISIBLE);
            holder.active_order_three.setVisibility(View.GONE);
            holder.active_order_four.setVisibility(View.GONE);
            holder.active_order_five.setVisibility(View.GONE);
            holder.reoder_btn.setVisibility(View.GONE);
        }
        if(status.equals("2")){
            holder.order_status_icon.setImageResource(R.drawable.tick);
            holder.active_order.setVisibility(View.GONE);
            holder.active_order_two.setVisibility(View.GONE);
            holder.active_order_three.setVisibility(View.VISIBLE);
            holder.active_order_four.setVisibility(View.GONE);
            holder.active_order_five.setVisibility(View.GONE);
            holder.reoder_btn.setVisibility(View.GONE);
        }
        if(status.equals("3")){
            holder.order_status_icon.setImageResource(R.drawable.tick);
            holder.active_order.setVisibility(View.GONE);
            holder.active_order_two.setVisibility(View.GONE);
            holder.active_order_three.setVisibility(View.GONE);
            holder.active_order_four.setVisibility(View.VISIBLE);
            holder.active_order_five.setVisibility(View.GONE);
            holder.reoder_btn.setVisibility(View.GONE);
        }
        if (status.equals("4")){
            holder.order_status_icon.setImageResource(R.drawable.tickyellow);
            holder.active_order.setVisibility(View.GONE);
            holder.active_order_two.setVisibility(View.GONE);
            holder.active_order_three.setVisibility(View.GONE);
            holder.active_order_four.setVisibility(View.GONE);
            holder.active_order_five.setVisibility(View.VISIBLE);
            holder.reoder_btn.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_name,order_id,order_date,reoder_btn,active_order,active_order_two,active_order_three,active_order_four,active_order_five;
        ImageView order_status_icon;
        CardView card_order;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            order_name=(TextView)itemView.findViewById(R.id.order_name);
            order_id=(TextView)itemView.findViewById(R.id.order_id);
            order_date=(TextView)itemView.findViewById(R.id.order_date);
            reoder_btn=(TextView)itemView.findViewById(R.id.reoder_btn);
            active_order=(TextView)itemView.findViewById(R.id.active_order);
            active_order_two=(TextView)itemView.findViewById(R.id.active_order_two);
            active_order_three=(TextView)itemView.findViewById(R.id.active_order_three);
            active_order_four=(TextView)itemView.findViewById(R.id.active_order_four);
            active_order_five=(TextView)itemView.findViewById(R.id.active_order_five);
            order_status_icon=(ImageView)itemView.findViewById(R.id.order_status_icon);
            card_order=(CardView)itemView.findViewById(R.id.card_order);
        }
    }
}
