package com.iterationtechnology.jalarambook.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.adapter.CategoryAllListAdapter;
import com.iterationtechnology.jalarambook.model.Cart;
import com.iterationtechnology.jalarambook.model.CartList;
import com.iterationtechnology.jalarambook.model.Category;
import com.iterationtechnology.jalarambook.model.CategoryList;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;
import com.iterationtechnology.jalarambook.network.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvAllCategoryList;
    ArrayList<Category> CatListArray = new ArrayList<>();
    SessionManager session;
    int flag = 0;
    TextView textCartItemCount;
    int mCartItemCount = 1;
    ArrayList<Cart> cartProductListArray = new ArrayList<>();
    GetProductDataService productDataService;
    String user_id,user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CatListArray.clear();

        session = new SessionManager(CategoryListActivity.this);
        flag = session.checkLogin();

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);
        user_name = user.get(SessionManager.user_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        MenuItem SocialMediaLinks = menu.findItem(R.id.nvSocialMediaLinks);
        SpannableString social = new SpannableString(SocialMediaLinks.getTitle());
        social.setSpan(new TextAppearanceSpan(this, R.style.NavigationTitle), 0, social.length(), 0);
        SocialMediaLinks.setTitle(social);

        MenuItem QuickLinks = menu.findItem(R.id.nvQuickLinks);
        SpannableString quick = new SpannableString(QuickLinks.getTitle());
        quick.setSpan(new TextAppearanceSpan(this, R.style.NavigationTitle), 0, quick.length(), 0);
        QuickLinks.setTitle(quick);

        MenuItem NvSize = menu.findItem(R.id.nvSize);
        SpannableString vSize = new SpannableString(NvSize.getTitle());
        vSize.setSpan(new TextAppearanceSpan(this, R.style.NavigationTitleSize), 0, vSize.length(), 0);
        NvSize.setTitle(vSize);

        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        TextView txt_login = (TextView)headerview.findViewById(R.id.txt_login);
        LinearLayout nav_header_ll = (LinearLayout)headerview.findViewById(R.id.nav_header_ll);

        if (flag == 1)
        {
            txt_login.setText(user_name);
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CategoryListActivity.this, MyProfileActivity.class);
                    startActivity(i);
                }
            });
        }
        else if (flag == 0)
        {
            txt_login.setText("Login / Register");
            nav_header_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CategoryListActivity.this, SignInActivity.class);
                    startActivity(i);
                }
            });
        }

        rvAllCategoryList = (RecyclerView)findViewById(R.id.rvAllCategoryList);
        rvAllCategoryList.setHasFixedSize(true);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        rvAllCategoryList.setLayoutManager(manager);

        Call<CategoryList> categoryListCall = productDataService.getCategoryData();
        categoryListCall.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                String Status = response.body().getStatus();
                String message = response.body().getMessage();
                if (Status.equals("1"))
                {
                    Log.d("message",""+message);
                    CatListArray = response.body().getCategoryList();
                    CategoryAllListAdapter categoryAllListAdapter = new CategoryAllListAdapter(CategoryListActivity.this,CatListArray);
                    rvAllCategoryList.setAdapter(categoryAllListAdapter);
                }
                else
                {
                    Log.d("message",""+message);
                }
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {
                Toast.makeText(CategoryListActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        final MenuItem menuItem = menu.findItem(R.id.menu_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    private void setupBadge() {
        if (flag == 1)
        {
            Call<CartList> CartListCall = productDataService.getCartData(user_id);
            CartListCall.enqueue(new Callback<CartList>() {
                @Override
                public void onResponse(Call<CartList> call, Response<CartList> response) {
                    String status = response.body().getStatus();
                    if (status.equals("1"))
                    {
                        cartProductListArray = response.body().getCartList();
                        mCartItemCount = cartProductListArray.size();
                        if (textCartItemCount != null) {
                            if (mCartItemCount == 0) {
                                if (textCartItemCount.getVisibility() != View.GONE) {
                                    textCartItemCount.setVisibility(View.GONE);
                                }
                            } else {
                                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                                    textCartItemCount.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    else
                    {
                        mCartItemCount = 0;
                        textCartItemCount.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<CartList> call, Throwable t) {
                    mCartItemCount = 0;
                    textCartItemCount.setVisibility(View.GONE);
                }
            });

        }
        else if (flag == 0)
        {
            mCartItemCount = 0;
            textCartItemCount.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_search)
        {
            Intent i = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(i);
        }
        else if (id == R.id.menu_cart)
        {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_cart)
        {
            Intent i = new Intent(getApplicationContext(),CartActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_wishlist)
        {
            Intent i = new Intent(getApplicationContext(), WishListActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_order)
        {
            Intent i = new Intent(getApplicationContext(), MyOrderActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_website)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://jalarambookstore.com"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("http://jalarambookstore.com"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_aboutus)
        {
            Intent i = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_contactus)
        {
            Intent i = new Intent(getApplicationContext(), ContactUsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_terms)
        {
            Intent i = new Intent(getApplicationContext(), LegalActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_facebook)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.facebook.com/jalarambookstore"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://www.facebook.com/jalarambookstore"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_instagram)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.instagram.com/jalarambookstore/"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://www.instagram.com/jalarambookstore/"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_pinterest)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://in.pinterest.com/jalarambookstore/?eq=jalarambookstore&etslf=10776"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://in.pinterest.com/jalarambookstore/?eq=jalarambookstore&etslf=10776"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_youtube)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://www.youtube.com/channel/UCaSh7Xl_PwIYQrjHel5pqZg"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://www.youtube.com/channel/UCaSh7Xl_PwIYQrjHel5pqZg"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_rate)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iterationtechnology.jalarambook"));
            if(!MyStartActivity(i))
            {
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iterationtechnology.jalarambook"));
                if(!MyStartActivity(i))
                {
                    Log.d("Like","Could not open browser");
                }
            }
        }
        else if (id == R.id.nav_share)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String body="https://play.google.com/store/apps/details?id=com.iterationtechnology.jalarambook";
            i.putExtra(Intent.EXTRA_SUBJECT,body);
            i.putExtra(Intent.EXTRA_TEXT,body);
            startActivity(Intent.createChooser(i,"Share using"));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean MyStartActivity(Intent i) {
        try
        {
            startActivity(i);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
