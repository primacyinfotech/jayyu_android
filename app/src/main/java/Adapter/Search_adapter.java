package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaayu.DetailsItems;
import com.jaayu.R;

import java.util.ArrayList;
import java.util.List;

import Model.Searchmodel;

public class Search_adapter  extends RecyclerView.Adapter<Search_adapter.MyViewHolder> {
    private ArrayList<Searchmodel> modelList;
    private List<Searchmodel> mFilteredList;
    private Context context;


    public Search_adapter(ArrayList<Searchmodel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_rv, parent, false);

        context = parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Searchmodel mList = modelList.get(position);
        holder.item_name.setText(mList.getSearch_item());
        holder.item_compo_name.setText(mList.getComposition_name());
        holder.item_go_img.setImageResource(R.drawable.ic_action_arrow);
        holder.serch_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int product_items=mList.getSearch_id();
                Intent gotoProductpage=new Intent(context, DetailsItems.class);
                gotoProductpage.putExtra("Product Id",product_items);
                context.startActivity(gotoProductpage);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
   /* @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = modelList;
                } else {

                    ArrayList<Searchmodel> filteredList = new ArrayList<>();

                    for (Searchmodel androidVersion : modelList) {

                        if (androidVersion.getSearch_item().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }
                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Searchmodel>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_compo_name;
        ImageView item_go_img;
        LinearLayout serch_items;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_name=(TextView)itemView.findViewById(R.id.item_name);
            item_go_img=(ImageView)itemView.findViewById(R.id.item_go_img);
            serch_items=(LinearLayout)itemView.findViewById(R.id.serch_items);
            item_compo_name=(TextView)itemView.findViewById(R.id.item_compo_name);

        }
    }
    public void filterList(ArrayList<Searchmodel> filterdNames) {
        this.modelList = filterdNames;
        notifyDataSetChanged();
    }
}
