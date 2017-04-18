package com.example.firebase.bookstore;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.firebase.bookstore.R.id.imageView;
import static com.example.firebase.bookstore.R.id.title;

public class viewCart_activity extends AppCompatActivity {

    String[] Titles;
    String[] Description;
    ListView listview;
    FirebaseAuth userAuth ;
   static String[] separeted;
    TextView username;
    TextView email_nav;
    TextView t_price;
    String UserId;
    DatabaseReference userRef ;

ArrayList<String> list = new ArrayList<>();
    ArrayList<String> ID = new ArrayList<>();
    ArrayList<String> del = new ArrayList<>();
    ArrayList<Double> totalPrice = new ArrayList<>();
    ArrayAdapter<String> adapter;
    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
  //  RecyclerView cart_rv;
    Button checkout,delete;
    static  Double abc = 0.0;
    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
   public static String tp = "0" ;

  DatabaseReference selectedBookRef = myRootRef.child("Books");


//    viewCart_activity x = new viewCart_activity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Resources res = getResources();

        setContentView(R.layout.activity_view_cart_activity);
        checkout = (Button) findViewById(R.id.checkout);
        delete = (Button) findViewById(R.id.cart_delete);
        //  t_price=(TextView)findViewById(R.id.totalprice);

        t_price = (TextView)findViewById(R.id.totalPrice);



        navigationView = (NavigationView) findViewById(R.id.navgation_view);
        View header = navigationView.getHeaderView(0);
        username = (TextView) header.findViewById(R.id.txt_UserName);
        email_nav = (TextView) header.findViewById(R.id.txt_email_nav);

        //passing the values to navigation header
        userAuth = FirebaseAuth.getInstance();
        UserId = userAuth.getCurrentUser().getUid();
        userRef = myRootRef.child("Users").child(UserId);
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

       final DatabaseReference cartRef = myRootRef.child("CART").child(UserId); //("User5")
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Log.i("Going In....", dataSnapshot.getKey().toString());

                    //   Log.i("Value In....",dataSnapshot.getValue().toString());

                    Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();



                        for (Map.Entry<String, String> entry : data.entrySet()) {

                            ID.add(entry.getKey());
                            Log.i("KEY........", entry.getKey());
                            Log.i("VALUE.........", entry.getValue());

                        }
                        for (String x : ID) {
                            Log.i("ID.........", x);
                        }
                        //  Log.i("VALUE OF STRING IN KEY" , val);

                        int i = displayCart(ID);

                }
                else {
                    Toast.makeText(viewCart_activity.this, "Cart Empty", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.i("ERRRRRRR...", UserId);

            }
        });

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);

        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_signOut:
                        userAuth.signOut();
                        break;

                    case R.id.nav_home:
                        Intent i = new Intent(viewCart_activity.this, MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        mDrawerlayout.closeDrawers();
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(viewCart_activity.this, MainActivity.class);
                        startActivity(c);
                        break;


                }
                return false;
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent t = new Intent(viewCart_activity.this, payment.class);
                t.putExtra("items_selected", tp);
                startActivity(t);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SparseBooleanArray selected = listview.getCheckedItemPositions();
                int len = listview.getCount();
                for (int i = 0; i < len; i++) {
                    if (selected.get(i)) {
                        String temp = listview.getItemAtPosition(i).toString();
                        del.add(ID.get(i).toString());
                        Log.i("TEMP.....", temp);
                    }
                }
                for (String x : del) {

                    Log.i("cd........", x);


                    Query applesQuery = cartRef.child(x);

                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           dataSnapshot.getRef().removeValue();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("VALLKD", "onCancelled", databaseError.toException());
                        }
                    });
                }

               // Log.i("REF..xxxxxxxx...",ref );
                finish();
                startActivity(getIntent());

            }

        });

        listview = (ListView) findViewById(R.id.cart_listview);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterview, View view,
                                    int position, long id) {

                SparseBooleanArray selected = listview.getCheckedItemPositions();
               // String s = ;
                  abc = 0.0;
                for (int i = (selected.size() - 1); i >= 0; i--) {

                    if (selected.valueAt(i)) {
                        Double temp = totalPrice.get(i);

                        abc = abc + temp;



                     tp = String.format("%.2f", abc);

                        t_price.setText("Total Price : $ " +tp);


                    }
                }
                Log.i("E", abc.toString());
              //  t_price.setText( + s);



            }
        });




    }

        //======================== Code start here =====================

        int displayCart(ArrayList<String> ID)
        {
            for(String a : ID)
            {
                selectedBookRef.child(a).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        book  select = dataSnapshot.getValue(book.class);

                        list.add( select.getTitle() + "Price : "+ select.getPrice().toString() );
                        totalPrice.add(select.getPrice());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }



        return 1;
        }






        //======================== Code end here =====================



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }


}
/*
class listAdapter extends ArrayAdapter<String>
{
    private Context context;
    private String[] images;
    private String[] titlesArray;
    private String[] descArray;
    listAdapter(Context c, String[] titles, String[] imgs, String[] desc) {
        super(c, R.layout.cart_single_row, R.id.cart_btitle, titles);
        this.context = c;
        this.images = imgs;
        this.titlesArray = titles;
        this.descArray = desc;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =  inflater.inflate(R.layout.cart_single_row,parent,false);

        ImageView myImage=(ImageView)row.findViewById(R.id.cart_bimage);
        TextView textv1 =(TextView)row.findViewById(R.id.cart_btitle);
        TextView textv2 =(TextView)row.findViewById(R.id.cart_bdesc);

        Picasso.with(getContext()).load(images[position]).into(myImage);
       // myImage.setImageResource(images[position]);
        textv1.setText(titlesArray[position]);
        textv2.setText(descArray[position]);
        return row;
        //Picasso.with(getApplicationContext()).load(selectedBook.getImage()).into(imageView[finalI]);

       // ImageView bookImage = (ImageView)mView.findViewById(R.id.imgView_detail);


    }

}*/


