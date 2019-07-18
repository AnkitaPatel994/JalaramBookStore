package com.iterationtechnology.jalarambook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.activity.SubCategoryActivity;
import com.iterationtechnology.jalarambook.model.Author;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AuthorListAdapter extends RecyclerView.Adapter<AuthorListAdapter.ViewHolder> {

    Context context;
    ArrayList<Author> authorListArray;

    public AuthorListAdapter(Context context, ArrayList<Author> authorListArray) {
        this.context = context;
        this.authorListArray = authorListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String Author_id = authorListArray.get(position).getAuthor_id();
        final String Author_name = authorListArray.get(position).getAuthor_name();
        String Author_img = authorListArray.get(position).getAuthor_img();

        viewHolder.txtAuthorName.setText(Author_name);

        Picasso.with(context).load(RetrofitInstance.BASE_URL +Author_img).into(viewHolder.ivAuthorImg);


        viewHolder.llAuthorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("pro_name","*");
                i.putExtra("cate_id","*");
                i.putExtra("cate_name","*");
                i.putExtra("Author_id",Author_id);
                i.putExtra("Author_name",Author_name);
                i.putExtra("min_price","1");
                i.putExtra("max_price","15000");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return authorListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAuthorImg;
        TextView txtAuthorName;
        LinearLayout llAuthorList;

        public ViewHolder(View itemView) {
            super(itemView);

            ivAuthorImg = (ImageView)itemView.findViewById(R.id.ivAuthorImg);
            txtAuthorName = (TextView)itemView.findViewById(R.id.txtAuthorName);
            llAuthorList = (LinearLayout) itemView.findViewById(R.id.llAuthorList);
        }
    }
}
