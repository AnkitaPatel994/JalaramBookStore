package com.iterationtechnology.jalarambook.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iterationtechnology.jalarambook.R;

public class LegalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

        TextView txtRefundPolicy = (TextView)findViewById(R.id.txtRefundPolicy);
        TextView txtShippingPolicy = (TextView)findViewById(R.id.txtShippingPolicy);
        TextView txtPrivacyPolicy = (TextView)findViewById(R.id.txtPrivacyPolicy);
        TextView txtTC = (TextView)findViewById(R.id.txtTC);
        TextView txtReturnPolicy = (TextView)findViewById(R.id.txtReturnPolicy);

        txtRefundPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RefundPolicyActivity.class);
                startActivity(i);
            }
        });

        txtShippingPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ShippingPolicyActivity.class);
                startActivity(i);
            }
        });

        txtPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
                startActivity(i);
            }
        });

        txtTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(i);
            }
        });

        txtReturnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReturnPolicyActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
