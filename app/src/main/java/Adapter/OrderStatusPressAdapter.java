package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.jaayu.Model.BaseUrl;
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
    public void onBindViewHolder(@NonNull OrderStatusPressAdapter.MyViewHolder holder, final int position) {
        Picasso.with(context).load(BaseUrl.imageUrl + orderStatusPressModels.get(position).getPress_img()).into(holder.ivGallery);
        holder.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(context,R.style.AppBaseTheme);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(null);
                settingsDialog.setContentView(R.layout.full_screen_image);
                PhotoView imageView=(PhotoView)settingsDialog.findViewById(R.id.full_screen);
                ImageView  imageView2=(ImageView)settingsDialog.findViewById(R.id.close_full_img);
                Picasso.with(context)
                        .load(BaseUrl.imageUrl + orderStatusPressModels.get(position).getPress_img())
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
