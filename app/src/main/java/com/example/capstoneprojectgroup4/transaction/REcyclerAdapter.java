package com.example.capstoneprojectgroup4.transaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

public class REcyclerAdapter extends RecyclerView.Adapter<REcyclerDataHolder> {
    ArrayList<TransactionHistoryData> data;

    public REcyclerAdapter(ArrayList<TransactionHistoryData> data){
        this.data = data;
    }
    @Override
    public REcyclerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaction_history_item,parent,false);
        REcyclerDataHolder myDataVHolder = new REcyclerDataHolder(view);
        return myDataVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull REcyclerDataHolder holder, int position) {
        TransactionHistoryData singleData = data.get(position);
        holder.nameTextBox.setText(singleData.getName());
        holder.dateTextBox.setText(singleData.getDate());
        holder.priceTextBox.setText(singleData.getPrice());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
