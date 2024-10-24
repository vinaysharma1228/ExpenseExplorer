package com.example.expenseexplorer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class expenseAdapter extends RecyclerView.Adapter<expenseAdapter.ItemViewHolder> {
    private List<ExpenseAddModel> itemlist;

    public expenseAdapter(List<ExpenseAddModel> itemlist){
        this.itemlist=itemlist;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_view, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ExpenseAddModel exp= itemlist.get(position);


        holder.exp_name.setText(exp.getExpenseName());
        holder.exp_paymode.setText(exp.getExpenseAmountPayMode());
        holder.exp_cat.setText(exp.getExpenseCategory());
        holder.exp_place.setText(exp.getExpensePlace());
        holder.exp_amount.setText("Rs "+exp.getExpenseAmount());
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
