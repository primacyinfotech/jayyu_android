package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.Model.BaseUrl;
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
    public void onBindViewHolder(@NonNull final OfferPageAdapter.MyViewHolder holder, final int position) {
        Picasso.with(context).load(BaseUrl.imageUrlSub + "upload/slider/" +offerPageModels.get(position).getOffer_img()).into(holder.offer_img);
        holder.offer_heading.setText(offerPageModels.get(position).getOffer_heading());
        holder.offer_des.setText(offerPageModels.get(position).getOffer_des());
        holder.offer_code.setText("CODE:"+offerPageModels.get(position).getOffer_code());
        holder.offer_exp_date.setText(offerPageModels.get(position).getOffer_exp_date());
        holder.view_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog settingsDialog = new Dialog(context,R.style.AppBaseTheme);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.getWindow().setBackgroundDrawable(null);
                settingsDialog.setContentView(R.layout.full_screen_offer_dialog);
                TextView offer_title=(TextView)settingsDialog.findViewById(R.id.offer_title);
                ImageView close_full_img=(ImageView)settingsDialog.findViewById(R.id.close_full_img);
                ImageView offer_img=(ImageView)settingsDialog.findViewById(R.id.offer_img);
                TextView short_des=(TextView)settingsDialog.findViewById(R.id.short_des);
                TextView cop_code=(TextView)settingsDialog.findViewById(R.id.cop_code);
                TextView expdate=(TextView)settingsDialog.findViewById(R.id.expdate);
                TextView long_des=(TextView)settingsDialog.findViewById(R.id.long_des);
                Picasso.with(context).load(BaseUrl.imageUrlSub + "upload/slider/" +offerPageModels.get(position).getOffer_img()).into(offer_img);
                offer_title.setText(offerPageModels.get(position).getOffer_heading());
                short_des.setText(offerPageModels.get(position).getOffer_des());
                long_des.setText(offerPageModels.get(position).getOffer_long_des());
                cop_code.setText(offerPageModels.get(position).getOffer_code());
                expdate.setText(offerPageModels.get(position).getOffer_exp_date());
                close_full_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingsDialog.dismiss();
                    }
                });
                settingsDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerPageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView offer_img;
        TextView offer_heading,offer_des,offer_exp_date,offer_code,view_details_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            offer_img=(ImageView)itemView.findViewById(R.id.offer_img);
            offer_heading=(TextView)itemView.findViewById(R.id.offer_heading);
            offer_des=(TextView)itemView.findViewById(R.id.offer_des);
            offer_exp_date=(TextView)itemView.findViewById(R.id.offer_exp_date);
            offer_code=(TextView)itemView.findViewById(R.id.offer_code);
            view_details_btn=(TextView)itemView.findViewById(R.id.view_details_btn);
        }
    }
}
