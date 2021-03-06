package com.example.db_14.travelplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.db_14.travelplanner.DB.DBHelper;
import com.example.db_14.travelplanner.Plans.BookmarkActivity;
import com.example.db_14.travelplanner.Plans.InviteListActivity;
import com.example.db_14.travelplanner.Plans.UserPlanActivity;
import com.example.db_14.travelplanner.Reviews.MyReviewActivity;
import com.example.db_14.travelplanner.Reviews.ReviewActivity;
import com.example.db_14.travelplanner.Services.InviteService;
import com.example.db_14.travelplanner.Sights.ListActivity;
import com.example.db_14.travelplanner.URLConnectors.URLConnector;
import com.example.db_14.travelplanner.UserManagements.LoginActivity;
import com.tsengvn.typekit.TypekitContextWrapper;

public class MainUIActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    DBHelper dbHelper;
    Intent intent = null;
    String usrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainui);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DBHelper(getApplicationContext(), "UserInfo.db", null, 1);
        usrid = dbHelper.getResult().get("usrid");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = new Intent(getApplicationContext(), InviteService.class);
        startService(intent);
    }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            intent = new Intent(MainUIActivity.this, ListActivity.class);
            URLConnector conn = new URLConnector();
            conn.APIareaCode("areaCode?");

            if(id == R.id.tour)
            {
                intent.putExtra("areaList", conn.getList());
                intent.putExtra("ContentTypeId", "12");
            }
            else if(id == R.id.food)
            {
                intent.putExtra("areaList", conn.getList());
                intent.putExtra("ContentTypeId", "39");
            }
            else if(id == R.id.room)
            {
                intent.putExtra("areaList", conn.getList());
                intent.putExtra("ContentTypeId", "32");
            }
            else if(id == R.id.recommend)
            {
                intent.putExtra("areaList", conn.getList());
                intent.putExtra("ContentTypeId", "25");
                intent.putExtra("is_recommend",1);
            }
            else if(id == R.id.festival)
            {
                intent.putExtra("areaList", conn.getList());
                intent.putExtra("ContentTypeId", "15");
            }
            else if(id == R.id.review)
            {
                intent = new Intent(MainUIActivity.this, ReviewActivity.class);
            }
            else
            {
                Toast.makeText(this, "준비중", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(intent);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myhome) {


        } else if (id == R.id.nav_myplan) {
            intent = new Intent(MainUIActivity.this, UserPlanActivity.class);
            intent.putExtra("ISBILL", 0);
            startActivity(intent);
        } else if (id == R.id.nav_cost) {
            intent = new Intent(MainUIActivity.this, UserPlanActivity.class);
            intent.putExtra("ISBILL", 1);
            startActivity(intent);
        } else if (id == R.id.nav_myreview) {
            intent = new Intent(MainUIActivity.this, MyReviewActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            intent = new Intent(MainUIActivity.this, BookmarkActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_invite) {
            intent = new Intent(MainUIActivity.this, InviteListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            dbHelper.delete(usrid);
            intent = new Intent(MainUIActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
