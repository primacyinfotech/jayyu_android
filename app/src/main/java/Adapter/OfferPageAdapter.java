package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.OfferPageModel;

public class OfferPageAdapter extends RecyclerView.Adapter<OfferPageAdapter.MyViewHolder> {
    ArrayList<OfferPageModel>offerPageModels;
    Context context;

    public OfferPageAdapter(ArrayList<OfferPageModel> offerPageModels, Context context) {
        this.offerPageModels = offerPageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferPageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_list, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferPageAdapter.MyViewHolder holder, int position) {
        Picasso.with(context).load("https://work.primacyinfotech.com/jaayu/upload/slider/"+offerPageModels.get(position).getOffer_img()).into(holder.offer_img);
        holder.offer_heading.setText(offerPageModels.get(position).getOffer_heading());
        holder.offer_des.setText(offerPageModels.get(position).getOffer_des());
        holder.offer_exp_date.setText(offerPageModels.get(position).getOffer_exp_date());



    }

    @Override
    public int getItemCount() {
        return offerPageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView offer_img;
        TextView offer_heading,offer_des,offer_exp_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            offer_img=(ImageView)itemView.findViewById(R.id.offer_img);
            offer_heading=(TextView)itemView.findViewById(R.id.offer_heading);
            offer_des=(TextView)itemView.findViewById(R.id.offer_des);
            offer_exp_date=(TextView)itemView.findViewById(R.id.offer_exp_date);
        }
    }
}
