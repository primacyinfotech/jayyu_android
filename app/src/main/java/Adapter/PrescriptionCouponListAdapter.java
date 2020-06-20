package Adapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.Model.BaseUrl;
import com.jaayu.PrescriptionOrderSummery;
import com.jaayu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.CouponListModel;
import Model.SaveCoupon;

public class PrescriptionCouponListAdapter extends RecyclerView.Adapter<PrescriptionCouponListAdapter.MyViewHolder> {
    private ArrayList<CouponListModel> modelList;
    private Context context;
    BroadcastReceiver mMessageReceiver;
    SaveCoupon myDb;
    public PrescriptionCouponListAdapter(ArrayList<CouponListModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }


    @NonNull
    @Override
    public PrescriptionCouponListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_list_item, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionCouponListAdapter.MyViewHolder holder, int position) {
        final CouponListModel mList = modelList.get(position);
        // holder.cpm.setImageResource(mList.getCoupon_img());
        myDb = new SaveCoupon(context);
        Picasso.with(context).load(BaseUrl.imageUrlSub + "upload/slider/" +mList.getCoupon_img()).into(holder.cpm);

        holder.cpn_txt.setText(mList.getCoupon_code());
        holder.main_off.setText(mList.getCoupon_code_des());
        holder.main_off_des.setText(mList.getCoupn_code_details());
        holder.main_off_time.setText("Exp Date: "+mList.getCoupon_time());
        holder.cpn_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coupon_code=mList.getCoupon_code();
                // Toast.makeText(context,coupon_code,Toast.LENGTH_LONG).show();
                Intent intentdataPass=new Intent(context, PrescriptionOrderSummery.class);
                intentdataPass.putExtra("Coupon Code",coupon_code);
                intentdataPass.putExtra("Coupon_id",mList.getCoupon_id());
                context.startActivity(intentdataPass);

                if (context instanceof Activity)
                    ((Activity)context).finish();
                else if (context instanceof ContextWrapper)
                    ((Activity)((ContextWrapper)context).getBaseContext()).finish();

                boolean isUpdate=myDb.insertData(String.valueOf(mList.getCoupon_id()),coupon_code);

                if(isUpdate){
                    //Toast.makeText(context,"Data Update",Toast.LENGTH_LONG).show();
                }
                else {
                    //Toast.makeText(context,"Data not Updated",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cpm;
        TextView cpn_txt,main_off,main_off_des,main_off_time;
        Button cpn_apply_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cpm=(ImageView)itemView.findViewById(R.id.cpm);
            cpn_txt=(TextView)itemView.findViewById(R.id.cpn_txt);
            main_off=(TextView)itemView.findViewById(R.id.main_off);
            main_off_des=(TextView)itemView.findViewById(R.id.main_off_des);
            main_off_time=(TextView)itemView.findViewById(R.id.main_off_time);
            cpn_apply_btn=(Button)itemView.findViewById(R.id.cpn_apply_btn);
        }
    }
}
