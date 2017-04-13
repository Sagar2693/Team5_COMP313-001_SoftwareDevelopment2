package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    String UserId;
    TextView username;
    TextView email_nav;

    String[] category = {"Action","Comedy","Drama","Fiction","Horror","Mystery","Romance"};
    ListView list;

    DatabaseReference userRef ;
    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth userAuth ;
    FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView =(NavigationView)findViewById(R.id.navgation_view);
        View header = navigationView.getHeaderView(0);

        username=(TextView)header.findViewById(R.id.txt_UserName);
        email_nav=(TextView)header.findViewById(R.id.txt_email_nav);

        mDrawerlayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);

        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id  = item.getItemId();
                switch (id) {
                    case R.id.nav_signOut:

                        userAuth.signOut();

                        break;
                    case R.id.nav_home:

                        mDrawerlayout.closeDrawers();
                        break;
                    case R.id.nav_viewCart:
                        Intent v = new Intent(MainActivity.this,viewCart_activity.class);
                        startActivity(v);
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(MainActivity.this,contact_us_activity.class);
                        startActivity(c);
                        break;


                }
                return false;
            }
        });

        userAuth = FirebaseAuth.getInstance();
        DatabaseReference BookRef = myRootRef.child("Books");
        BookRef.keepSynced(true);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){ //if user is not logged in

                    Intent loginLogin = new Intent(MainActivity.this,login_activity.class);
                    loginLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginLogin);

                }
                else
                {
                    UserId = userAuth.getCurrentUser().getUid();
                    // Log.v("User ID"," is .................." + UserId);
                    userRef =myRootRef.child("Users").child(UserId);

                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user loggedUser = dataSnapshot.getValue(user.class);

                            username.setText(loggedUser.getName());
                            email_nav.setText(loggedUser.getEmail());


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        };

        userAuth.addAuthStateListener(authListener);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, category);
        list = (ListView) findViewById(R.id.listViewCategory);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //  int f =  list.getSelectedItemPosition();

                String val =(String) adapterView.getItemAtPosition(position);
                Log.v("MainActivity"," is .................." + val);

                Intent t = new Intent(MainActivity.this,Book_list.class);

                t.putExtra("category_selected",val);
                startActivity(t);
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

}
