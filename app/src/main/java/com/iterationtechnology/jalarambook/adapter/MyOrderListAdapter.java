package com.iterationtechnology.jalarambook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.activity.OrderDeliveredActivity;
import com.iterationtechnology.jalarambook.activity.OrderPlacedActivity;
import com.iterationtechnology.jalarambook.activity.OrderShippedActivity;
import com.iterationtechnology.jalarambook.activity.PDFActivity;
import com.iterationtechnology.jalarambook.model.Order;

import java.util.ArrayList;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder>  {

    Context context;
    ArrayList<Order> myOrderProductListArray;
    String user_id;

    public MyOrderListAdapter(Context context, ArrayList<Order> myOrderProductListArray, String user_id) {
        this.context = context;
        this.user_id = user_id;
        this.myOrderProductListArray = myOrderProductListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String rs = context.getResources().getString(R.string.RS);
        String order_date = myOrderProductListArray.get(position).getOrder_date();
        final String invoice_no = myOrderProductListArray.get(position).getInvoice_no();
        final String total = myOrderProductListArray.get(position).getTotal();
        final String shipping_id = myOrderProductListArray.get(position).getShipping_id();
        final String order_status = myOrderProductListArray.get(position).getOrder_status();

        viewHolder.txtOInvoiceNo.setText(invoice_no);
        viewHolder.txtODate.setText(order_date);
        viewHolder.txtOTotal.setText(rs+" "+total);
        viewHolder.txtOStatus.setText(order_status);

        if (order_status.equals("Delivered"))
        {
            viewHolder.btnTrackOrder.setVisibility(View.GONE);
        }

        if (order_status.equals("Cancel"))
        {
            viewHolder.btnTrackOrder.setVisibility(View.GONE);
        }

        viewHolder.btnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (order_status.equals("Pending"))
                {
                    Intent i = new Intent(context, OrderPlacedActivity.class);
                    i.putExtra("invoice_no",invoice_no);
                    i.putExtra("ordersta","Your Order has been Placed Sucessfully");
                    context.startActivity(i);
                }
                else if (order_status.equals("Confirmed"))
                {
                    Intent i = new Intent(context, OrderPlacedActivity.class);
                    i.putExtra("invoice_no",invoice_no);
                    i.putExtra("ordersta","Your Order has been Confirmed, if will Ready to Ship");
                    context.startActivity(i);
                }
                else if (order_status.equals("Shipped"))
                {
                    Intent i = new Intent(context, OrderShippedActivity.class);
                    i.putExtra("invoice_no",invoice_no);
                    i.putExtra("shipping_id",shipping_id);
                    i.putExtra("total",total);
                    context.startActivity(i);
                }
                else if (order_status.equals("Delivered"))
                {
                    Intent i = new Intent(context, OrderDeliveredActivity.class);
                    context.startActivity(i);
                }

            }
        });

        viewHolder.btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PDFActivity.class);
                i.putExtra("invoice_no",invoice_no);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderProductListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnTrackOrder,btnViewOrder;
        TextView txtOInvoiceNo,txtODate,txtOTotal,txtOStatus;
        LinearLayout llOrderProList;
        public ViewHolder(View itemView) {
            super(itemView);

            txtOInvoiceNo = (TextView) itemView.findViewById(R.id.txtOInvoiceNo);
            txtODate = (TextView) itemView.findViewById(R.id.txtODate);
            txtOTotal = (TextView) itemView.findViewById(R.id.txtOTotal);
            txtOStatus = (TextView) itemView.findViewById(R.id.txtOStatus);
            btnTrackOrder = (Button) itemView.findViewById(R.id.btnTrackOrder);
            btnViewOrder = (Button) itemView.findViewById(R.id.btnViewOrder);

        }
    }
}
