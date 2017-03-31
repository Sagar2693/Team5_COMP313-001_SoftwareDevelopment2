package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class contact_us_activity extends AppCompatActivity {

    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_activity);

        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);

        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView =(NavigationView)findViewById(R.id.navgation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id  = item.getItemId();
                switch (id) {
                    case R.id.nav_signOut:

                        // userAuth.signOut();

                        break;
                    case R.id.nav_home:
                        Intent i = new Intent(contact_us_activity.this,MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        Intent c = new Intent(contact_us_activity.this,viewCart_activity.class);
                        startActivity(c);
                        break;

                    case R.id.nav_contact:
                        mDrawerlayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

        //============================code start here========================






        //============================code end here========================
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }
}
