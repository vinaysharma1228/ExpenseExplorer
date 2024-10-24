package com.example.expenseexplorer;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class profileAdapter extends RecyclerView.Adapter<profileAdapter.ItemViewHolder> {
    private List<ExpenseAddModel> itemlist;

    public profileAdapter(List<ExpenseAddModel> itemlist){
        this.itemlist=itemlist;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_view, parent, false);
        return new ItemViewHolder(view);

    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ExpenseAddModel exp= itemlist.get(position);


        holder.exp_name.setText(exp.getExpenseName());
        holder.exp_paymode.setText(exp.getExpenseAmountPayMode());
        holder.exp_cat.setText(exp.getExpenseCategory());
        holder.exp_place.setText(exp.getExpensePlace());
        double individual_amount=Double.parseDouble(exp.getExpenseAmount());
        individual_amount = individual_amount /12;
        holder.exp_amount.setText("Rs "+ String.format("%.2f",individual_amount));
        holder.exp_time.setText(exp.getExpenseTime());



    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView exp_name,exp_cat,exp_paymode,exp_amount,exp_place,exp_time;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // nameTextView = itemView.findViewById(R.id.nameTextView);
            exp_name=itemView.findViewById(R.id.expNameView);
            exp_cat=itemView.findViewById(R.id.expCatView);
            exp_paymode=itemView.findViewById(R.id.expPayModeView);
            exp_place=itemView.findViewById(R.id.expPlaceView);
            exp_amount=itemView.findViewById(R.id.expAmountView);
            exp_time=itemView.findViewById(R.id.expDateView);
        }
    }

}
