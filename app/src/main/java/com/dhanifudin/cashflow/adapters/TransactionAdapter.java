package com.dhanifudin.cashflow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhanifudin.cashflow.R;
import com.dhanifudin.cashflow.models.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private Context context;
    private List<Transaction> items;
    private OnItemTransactionListener listener;
    public static final int DEBITS=1;
    public static final int CREDITS=2;
    public TransactionAdapter(List<Transaction> items, OnItemTransactionListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public interface OnItemTransactionListener{
        void onTransactionClicked(int index, Transaction item);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView descriptionText;
        TextView amountText;
        public  ViewHolder(@NonNull View itemView){
            super(itemView);
            descriptionText = itemView.findViewById(R.id.text_description);
            amountText = itemView.findViewById(R.id.text_amount);
        }

        public void bind(final int index, final Transaction item){
            descriptionText.setText(item.getDescription());
            amountText.setText(String.valueOf(item.getAmount()));
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    listener.onTransactionClicked(index, item);
                }
            });
        }
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
//        return new ViewHolder(view);

        View view = null;
        if (viewType == DEBITS) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction, parent, false);
        } else if (viewType == CREDITS) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_transaction_blue, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction item = items.get(position);
        holder.bind(position, item);
    }

    @Override
    public int getItemCount() {
        return (items!=null)?items.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return (items.get(position).getType()==Transaction.Type.CREDIT) ? DEBITS : CREDITS;
    }

}
