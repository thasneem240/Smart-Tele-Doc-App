package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Order model) {

        holder.OrderId.setText(model.getOrderId());
        holder.OrderStatus.setText(model.getOrderStatus());



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status, parent, false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView OrderId,OrderStatus;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            OrderId = (TextView)itemView.findViewById(R.id.orderId);
            OrderStatus = (TextView)itemView.findViewById(R.id.orderStatus);
        }
    }

}
