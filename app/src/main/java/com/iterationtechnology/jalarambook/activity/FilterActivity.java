package com.iterationtechnology.jalarambook.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.model.Author;
import com.iterationtechnology.jalarambook.model.AuthorList;
import com.iterationtechnology.jalarambook.model.Category;
import com.iterationtechnology.jalarambook.model.CategoryList;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    TextView txtMinPrice,txtMaxPrice,txtCategory,txtAuthor;
    RangeSeekBar rangeseekbar;
    LinearLayout llFilterCategory,llFilterAuthor;
    ArrayList<Category> CategoryArray = new ArrayList<>();
    ArrayList<String> CategoryIdArray = new ArrayList<>();
    ArrayList<String> CategoryNameArray = new ArrayList<>();
    ArrayList<Author> AuthorArray = new ArrayList<>();
    ArrayList<String> AuthorIdArray = new ArrayList<>();
    ArrayList<String> AuthorNameArray = new ArrayList<>();
    GetProductDataService productDataService;
    Button btnFilter;
    String min_price,max_price,pro_name,cate_id,author_id,cate_name,author_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rangeseekbar = (RangeSeekBar)findViewById(R.id.rangeseekbar);
        txtCategory = (TextView) findViewById(R.id.txtCategory);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        llFilterCategory = (LinearLayout)findViewById(R.id.llFilterCategory);
        llFilterAuthor = (LinearLayout)findViewById(R.id.llFilterAuthor);

        pro_name = getIntent().getExtras().getString("pro_name");
        cate_id = getIntent().getExtras().getString("cate_id");
        if(cate_id.equals("*"))
        {
            cate_id = "*";
        }
        cate_name = getIntent().getExtras().getString("cate_name");
        if(cate_name.equals("*"))
        {
            txtCategory.setText("All Category");
        }
        else
        {
            txtCategory.setText(cate_name);
        }
        author_id = getIntent().getExtras().getString("author_id");
        if(author_id.equals("*"))
        {
            author_id = "*";
        }
        author_name = getIntent().getExtras().getString("author_name");
        if(author_name.equals("*"))
        {
            txtAuthor.setText("All Author");
        }
        else
        {
            txtAuthor.setText(author_name);
        }
        min_price = getIntent().getExtras().getString("min_price");
        max_price = getIntent().getExtras().getString("max_price");

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        txtMinPrice = (TextView) findViewById(R.id.txtMinPrice);
        txtMinPrice.setText(min_price);
        txtMaxPrice = (TextView) findViewById(R.id.txtMaxPrice);
        txtMaxPrice.setText(max_price);

        int min = Integer.parseInt(min_price);
        int max = Integer.parseInt(max_price);
        rangeseekbar.setSelectedMinValue(min);
        rangeseekbar.setSelectedMaxValue(max);

        rangeseekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                min_price = String.valueOf((int)min_value);
                max_price = String.valueOf((int)max_value);

                txtMinPrice.setText(min_price);
                txtMaxPrice.setText(max_price);
            }
        });



        llFilterCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryArray.clear();
                CategoryIdArray.clear();
                CategoryNameArray.clear();
                final Dialog dialog = new Dialog(FilterActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.type_dialog);
                dialog.setCancelable(true);
                TextView txtTypeName = (TextView)dialog.findViewById(R.id.txtTypeName);
                txtTypeName.setText("Category");
                ImageView ivTypeClose = (ImageView)dialog.findViewById(R.id.ivTypeClose);
                final ListView lvListType = (ListView)dialog.findViewById(R.id.lvListType);

                lvListType.setChoiceMode(lvListType.CHOICE_MODE_SINGLE);

                ivTypeClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Call<CategoryList> categoryListCall = productDataService.getCategoryData();
                categoryListCall.enqueue(new Callback<CategoryList>() {
                    @Override
                    public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {

                        String Status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (Status.equals("1"))
                        {
                            Log.d("message",""+message);
                            CategoryArray = response.body().getCategoryList();
                            for (int i=0;i<CategoryArray.size();i++)
                            {
                                String Cat_Id = CategoryArray.get(i).getCategory_id();
                                CategoryIdArray.add(Cat_Id);
                                String Cat_Name = CategoryArray.get(i).getCategory_title();
                                CategoryNameArray.add(Cat_Name);
                            }

                            final ArrayAdapter<String> CategoryAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, CategoryNameArray);
                            lvListType.setAdapter(CategoryAdapter);
                            lvListType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    cate_name = CategoryAdapter.getItem(position);
                                    cate_id = CategoryIdArray.get(position);
                                    dialog.dismiss();
                                    txtCategory.setText(cate_name);
                                }
                            });
                        }
                        else
                        {
                            Log.d("message",""+message);
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryList> call, Throwable t) {
                        Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        llFilterAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorArray.clear();
                AuthorIdArray.clear();
                AuthorNameArray.clear();
                final Dialog dialog = new Dialog(FilterActivity.this,android.R.style.Theme_Light_NoTitleBar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.type_dialog);
                dialog.setCancelable(true);
                TextView txtTypeName = (TextView)dialog.findViewById(R.id.txtTypeName);
                txtTypeName.setText("Author");
                ImageView ivTypeClose = (ImageView)dialog.findViewById(R.id.ivTypeClose);
                final ListView lvListType = (ListView)dialog.findViewById(R.id.lvListType);
                lvListType.setChoiceMode(lvListType.CHOICE_MODE_SINGLE);
                ivTypeClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Call<AuthorList> authorListCall = productDataService.getAuthorData();
                authorListCall.enqueue(new Callback<AuthorList>() {
                    @Override
                    public void onResponse(Call<AuthorList> call, Response<AuthorList> response) {

                        String Status = response.body().getStatus();
                        String message = response.body().getMessage();
                        if (Status.equals("1"))
                        {
                            Log.d("message",""+message);
                            AuthorArray = response.body().getAuthorList();
                            for (int i=0;i<AuthorArray.size();i++)
                            {
                                String Author_Id = AuthorArray.get(i).getAuthor_id();
                                AuthorIdArray.add(Author_Id);
                                String Author_Name = AuthorArray.get(i).getAuthor_name();
                                AuthorNameArray.add(Author_Name);
                            }
                            final ArrayAdapter<String> AuthorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, AuthorNameArray);
                            lvListType.setAdapter(AuthorAdapter);
                            lvListType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    author_name = AuthorAdapter.getItem(position);
                                    author_id = AuthorIdArray.get(position);
                                    dialog.dismiss();
                                    txtAuthor.setText(author_name);
                                }
                            });
                        }
                        else
                        {
                            Log.d("message",""+message);
                        }

                    }

                    @Override
                    public void onFailure(Call<AuthorList> call, Throwable t) {
                        Toast.makeText(FilterActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();
            }
        });

        btnFilter = (Button)findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minva = txtMinPrice.getText().toString();
                String maxva = txtMaxPrice.getText().toString();
                String Catname = txtCategory.getText().toString();
                String Brname = txtAuthor.getText().toString();

                Intent i = new Intent(getApplicationContext(), SubCategoryActivity.class);
                i.putExtra("pro_name",pro_name);
                i.putExtra("cate_id",cate_id);
                i.putExtra("cate_name",Catname);
                i.putExtra("author_id",author_id);
                i.putExtra("author_name",Brname);
                i.putExtra("min_price",minva);
                i.putExtra("max_price",maxva);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
