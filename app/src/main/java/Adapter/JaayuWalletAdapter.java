package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.R;

import java.util.ArrayList;

import Model.NormalWalletModel;

public class JaayuWalletAdapter extends RecyclerView.Adapter<JaayuWalletAdapter.MyViewHolder> {
    ArrayList<NormalWalletModel> normalWalletModels;
    Context context;

    public JaayuWalletAdapter(ArrayList<NormalWalletModel> normalWalletModels, Context context) {
        this.normalWalletModels = normalWalletModels;
        this.context = context;
    }

    @NonNull
    @Override
    public JaayuWalletAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_normal_wallet, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JaayuWalletAdapter.MyViewHolder holder, int position) {
        NormalWalletModel walletModel=normalWalletModels.get(position);
        if(!walletModel.getCredit().equals("0")){
            holder.received_amount.setText("\u20B9"+walletModel.getCredit());
            holder.txt_recived.setVisibility(View.VISIBLE);
            holder.txt_paid.setVisibility(View.GONE);
            // holder.paid_amount.setVisibility(View.GONE);
        }

        if(!walletModel.getDebit().equals("0")){
            holder.paid_amount.setText("\u20B9"+walletModel.getDebit());
            holder.txt_paid.setVisibility(View.VISIBLE);
            holder.txt_recived.setVisibility(View.GONE);
            holder.received_amount.setVisibility(View.GONE);
        }
      /*  if(Integer.valueOf(walletModel.getCredit())>0){
            holder.debit_amount.setText("\u20B9"+walletModel.getDebit());
            holder.txt_debit.setVisibility(View.VISIBLE);
            holder.txt_credit.setVisibility(View.GONE);
            //holder.credit_amount.setVisibility(View.GONE);


        }
        if (Integer.valueOf(walletModel.getDebit())>0){

            holder.credit_amount.setText("\u20B9"+walletModel.getCredit());
            holder.txt_credit.setVisibility(View.VISIBLE);
            holder.txt_debit.setVisibility(View.GONE);
            holder.debit_amount.setVisibility(View.GONE);

        }*/
        holder.remark.setText(walletModel.getRemark());
        holder.date_value.setText(walletModel.getDate_val());

    }

    @Override
    public int getItemCount() {
        return normalWalletModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_recived,received_amount,txt_paid,paid_amount,remark,date_value,txt_debit,debit_amount,txt_credit,
                credit_amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_recived=(TextView)itemView.findViewById(R.id.txt_recived);
            txt_paid=(TextView)itemView.findViewById(R.id.txt_paid);
            txt_debit=(TextView)itemView.findViewById(R.id.txt_debit);
            txt_credit=(TextView)itemView.findViewById(R.id.txt_credit);
            debit_amount=(TextView)itemView.findViewById(R.id.debit_amount);
            credit_amount=(TextView)itemView.findViewById(R.id.credit_amount);
            received_amount=(TextView)itemView.findViewById(R.id.received_amount);
            paid_amount=(TextView)itemView.findViewById(R.id.paid_amount);
            remark=(TextView)itemView.findViewById(R.id.remark);
            date_value=(TextView)itemView.findViewById(R.id.date_value);
        }
    }
}
