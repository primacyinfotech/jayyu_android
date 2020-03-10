package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.OrderStatusPressModel;

public class OrderStatusPressAdapter  extends RecyclerView.Adapter<OrderStatusPressAdapter.MyViewHolder>{
    ArrayList<OrderStatusPressModel> orderStatusPressModels;
    Context context;

    public OrderStatusPressAdapter(ArrayList<OrderStatusPressModel> orderStatusPressModels, Context context) {
        this.orderStatusPressModels = orderStatusPressModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderStatusPressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clip_press_img, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusPressAdapter.MyViewHolder holder, int position) {
        Picasso.with(context).load("https://work.primacyinfotech.com/jaayu/upload/prescription/" + orderStatusPressModels.get(position).getPress_img()).into(holder.ivGallery);

    }

    @Override
    public int getItemCount() {
        return orderStatusPressModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGallery;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivGallery=(ImageView)itemView.findViewById(R.id.ivGallery);
        }
    }
}
