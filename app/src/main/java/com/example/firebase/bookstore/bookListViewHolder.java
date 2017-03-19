package com.example.firebase.bookstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mekhal on 3/19/2017.
 */

public class bookListViewHolder extends RecyclerView.ViewHolder{



    View mView;
    public bookListViewHolder(View gridView){

        super(gridView);
        mView=gridView;

    }

    public void setTitle(String title){

       /* TextView txt_bookName = (TextView)mView.findViewById(R.id.txt_bookName);
        txt_bookName.setText(title);*/
    }

    public void setAuthor(String author)
    {
      /*  TextView txt_authorName = (TextView)mView.findViewById(R.id.txt_authorName);
        txt_authorName.setText(author);
*/
    }

    public void setImage(Context ctx, String image){

        /*ImageView bookImage = (ImageView)mView.findViewById(R.id.imageView_book);
        Picasso.with(ctx).load(image).into(bookImage);*/

    }

    public void setRating(Integer rating)
    {
        /*RatingBar ratings = (RatingBar)mView.findViewById(R.id.ratingBar);

        ratings.setRating(rating);
*/
    }

}
