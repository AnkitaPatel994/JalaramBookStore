package com.iterationtechnology.jalarambook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.activity.ProductDetailsActivity;
import com.iterationtechnology.jalarambook.model.Message;
import com.iterationtechnology.jalarambook.model.Product;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    ArrayList<Product> productListArray;
    String ip_address;

    public ProductListAdapter(Context context, ArrayList<Product> productListArray, String ip_address) {
        this.context = context;
        this.productListArray = productListArray;
        this.ip_address = ip_address;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String rs = context.getResources().getString(R.string.RS);
        final String id = productListArray.get(position).getId();
        final String pro_id = productListArray.get(position).getPro_id();
        final String cate_id = productListArray.get(position).getCate_id();
        final String pro_title = productListArray.get(position).getPro_title();
        final String author_id = productListArray.get(position).getAuthor_id();
        final String author_name = productListArray.get(position).getAuthor_name();
        final String pro_oprice = productListArray.get(position).getPro_oprice();
        final String pro_discount = productListArray.get(position).getPro_discount();
        final String pro_price = productListArray.get(position).getPro_price();
        final String pro_desc = productListArray.get(position).getPro_desc();
        final String pro_quantity = productListArray.get(position).getPro_quantity();
        final String pro_date = productListArray.get(position).getPro_date();
        final String rating = productListArray.get(position).getRating();
        String product_img = productListArray.get(position).getProduct_img();

        viewHolder.txtSubProductName.setText(pro_title);
        viewHolder.txtsubrating.setText(rating);
        viewHolder.txtsubprice.setText(rs+pro_price);
        viewHolder.txtsubcuttedprice.setText(rs+pro_oprice);
        viewHolder.txtsubcuttedprice.setPaintFlags(viewHolder.txtsubcuttedprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.txtsuboffer.setText(pro_discount);

        Picasso.with(context).load(RetrofitInstance.BASE_URL+product_img).into(viewHolder.ivsubProductImg);

        viewHolder.llProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
                Call<Message> insertRecentViewPropCall = productDataService.getInsertRecentViewPropData(pro_id,ip_address);
                insertRecentViewPropCall.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {

                        String Status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (Status.equals("1"))
                        {
                            Log.d("message",""+message);
                        }
                        else
                        {
                            Log.d("message",""+message);
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(context, ProductDetailsActivity.class);
                i.putExtra("id",id);
                i.putExtra("pro_id",pro_id);
                i.putExtra("cate_id",cate_id);
                i.putExtra("pro_title",pro_title);
                i.putExtra("author_id",author_id);
                i.putExtra("author_name",author_name);
                i.putExtra("pro_oprice",pro_oprice);
                i.putExtra("pro_discount",pro_discount);
                i.putExtra("pro_price",pro_price);
                i.putExtra("pro_desc",pro_desc);
                i.putExtra("pro_quantity",pro_quantity);
                i.putExtra("pro_date",pro_date);
                i.putExtra("rating",rating);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivsubProductImg;
        TextView txtSubProductName,txtsubrating,txtsubprice,txtsubcuttedprice,txtsuboffer;
        LinearLayout llProductList;

        public ViewHolder(View itemView) {
            super(itemView);

            ivsubProductImg = (ImageView)itemView.findViewById(R.id.ivsubProductImg);
            txtSubProductName = (TextView)itemView.findViewById(R.id.txtSubProductName);
            txtsubrating = (TextView)itemView.findViewById(R.id.txtsubrating);
            txtsubprice = (TextView)itemView.findViewById(R.id.txtsubprice);
            txtsubcuttedprice = (TextView)itemView.findViewById(R.id.txtsubcuttedprice);
            txtsuboffer = (TextView)itemView.findViewById(R.id.txtsuboffer);
            llProductList = (LinearLayout) itemView.findViewById(R.id.llProductList);

        }
    }
}
