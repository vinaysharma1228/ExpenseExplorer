package com.example.expenseexplorer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.ItemViewHolder> {
    private List<user> itemlist;

    public pendingAdapter(List<user> itemlist) {
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public pendingAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_view, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull pendingAdapter.ItemViewHolder holder, int position) {

        user u = itemlist.get(position);

        double ttlexp=Double.parseDouble(u.getTotalExp());
        double depAmt=Double.parseDouble(u.getDepositAmt());
       // double remAmt=Double.parseDouble(u.getRemainAmt());
        double remAmt=depAmt - ttlexp;

        holder.member_name.setText(u.getName());
        holder.mem_totalExp.setText(String.format("%.2f",ttlexp));
        holder.mem_RemainAmt.setText(String.format("%.2f",remAmt));
        holder.mem_depositAmt.setText(String.format("%.2f",depAmt));


    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView member_name,mem_depositAmt,mem_totalExp,mem_RemainAmt;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // nameTextView = itemView.findViewById(R.id.nameTextView);
            member_name = itemView.findViewById(R.id.pendingUsername);
            mem_depositAmt = itemView.findViewById(R.id.pendingDepositAmt);
            mem_totalExp = itemView.findViewById(R.id.pendingTotalExp);
            mem_RemainAmt = itemView.findViewById(R.id.pendingRemainAmt);
        }
    }
}
