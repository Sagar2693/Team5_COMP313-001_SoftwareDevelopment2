package com.example.firebase.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class book_description extends AppCompatActivity {

    ImageView imageView ;
    RatingBar ratingBar;
    TextView txt_AuthorName;
    TextView txt_price;
    TextView txt_BookTitle;
    TextView txt_multiLine;
    TextView txt_category;
    DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);
        Intent t = getIntent();
        String bookID = t.getStringExtra("Selected_book");

        DatabaseReference selectedBookRef = myRootRef.child("Books").child(bookID);

        imageView =(ImageView) findViewById(R.id.imgView_book);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar_1);
        txt_AuthorName = (TextView)findViewById(R.id.txt_authorName);
        txt_BookTitle = (TextView)findViewById(R.id.txt_bookTitle);
        txt_price = (TextView)findViewById(R.id.txt_price);
        txt_multiLine = (TextView)findViewById(R.id.txt_description);
        txt_category = (TextView)findViewById(R.id.txt_category);


        selectedBookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                book selectedBook = dataSnapshot.getValue(book.class);
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


    }
}
