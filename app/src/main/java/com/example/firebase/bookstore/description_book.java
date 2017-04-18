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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class description_book extends AppCompatActivity {

    DrawerLayout mDrawerlayout;
    ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    Button add_to_cart;
    TextView username;
    TextView email_nav;
    String UserId;
    DatabaseReference userRef ;

   // CART newItem = new CART();

    static String  cartItem = "//";

    ImageView imageView ;
    RatingBar ratingBar;
    TextView txt_AuthorName;
    TextView txt_price;
    TextView txt_BookTitle;
    TextView txt_multiLine;
    TextView txt_category;

    book selectedBook = new book();
    FirebaseAuth userAuth ;

    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    public static String[] separeted ;
    static long numOfChild;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_book);
        Intent t = getIntent();

        final String bookID = t.getStringExtra("Selected_book");
        final DatabaseReference cartRef = myRootRef.child("CART");

        navigationView =(NavigationView)findViewById(R.id.navgation_view);
        View header = navigationView.getHeaderView(0);
        username=(TextView)header.findViewById(R.id.txt_UserName);
        email_nav=(TextView)header.findViewById(R.id.txt_email_nav);
        userAuth = FirebaseAuth.getInstance();
        UserId = userAuth.getCurrentUser().getUid();

        ValueEventListener valueEventListener = cartRef.orderByChild(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(UserId))
                {
                    //cartRef.child("user3").setValue(bookID);
                    cartItem = dataSnapshot.child(UserId).getValue().toString();
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
        //passing the values to navigation header




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



        //final String bookID = t.getStringExtra("Selected_book");
        add_to_cart = (Button)findViewById(R.id.btn_addtocart);
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
                        Intent i = new Intent(description_book.this,MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_viewCart:
                        Intent v = new Intent(description_book.this,viewCart_activity.class);
                        startActivity(v);
                        break;

                    case R.id.nav_contact:
                        Intent c = new Intent(description_book.this,contact_us_activity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
            //========================Code start here=====================================

        DatabaseReference selectedBookRef = myRootRef.child("Books").child(bookID);

        imageView =(ImageView) findViewById(R.id.imgView_book);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar_1);
        txt_AuthorName = (TextView)findViewById(R.id.txt_authorName);
        txt_BookTitle = (TextView)findViewById(R.id.txt_bookTitle);
        txt_price = (TextView)findViewById(R.id.txt_price);
        txt_multiLine = (TextView)findViewById(R.id.txt_description);
        txt_category = (TextView)findViewById(R.id.txt_category);


        selectedBookRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

              selectedBook = dataSnapshot.getValue(book.class);
                //Log.v("author"," is .................." + selectedBook.getAuthor());

                Picasso.with(getApplicationContext()).load(selectedBook.getImage()).into(imageView);
                ratingBar.setRating(selectedBook.getRating());
                txt_AuthorName.setText("Author: "+selectedBook.getAuthor());
                txt_price.setText("Price: $" +Double.toString(selectedBook.getPrice()));
                txt_BookTitle.setText("Title: "+selectedBook.getTitle());
                txt_multiLine.setText(selectedBook.getDesc());
                txt_category.setText("Category: "+selectedBook.getCategory());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     final DatabaseReference cartRef = myRootRef.child("CART");
                cartRef.orderByChild(UserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       /* if(!dataSnapshot.hasChild("user3"))
                        {
                            //cartRef.child("user3").setValue(bookID);
                            numOfChild = dataSnapshot.getChildrenCount();
                            cartRef.child("user3").child("Title"+numOfChild + 1).setValue(selectedBook.getTitle());

                        }*/
                        numOfChild = dataSnapshot.getChildrenCount();
                        cartRef.child(UserId).child(bookID).setValue(selectedBook.getTitle());

                        /*else {
                            cartItem = dataSnapshot.child("user3").getValue().toString();
                        }*/



                        Toast.makeText(description_book.this, "Book Added to the cart", Toast.LENGTH_SHORT).show();


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }

                });


               // String upCart = String.valueOf(cartItem) + String.valueOf(bookID);
               // additems("user3" ,upCart);
              ;
            }
        });
        //========================Code end here=====================================
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }
   /* public void additems(final String user, final String InCart) {
        DatabaseReference cartRef = myRootRef.child("CART");

        String cartup =  String.valueOf(InCart) + "//";

        long i = numOfChild + 1;

        cartRef.child(user).child("Title"+i).setValue(selectedBook.getTitle());

       // cartRef.child(user).setValue(cartup);

        Toast.makeText(description_book.this,"Book added To Cart",Toast.LENGTH_LONG).show();


        separeted = cartup.split("//");

        for (String x : separeted) {
            Log.i("x   " , x);
        }



    }*/

}
