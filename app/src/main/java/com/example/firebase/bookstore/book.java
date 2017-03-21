package com.example.firebase.bookstore;

/**
 * Created by Mekhal on 3/19/2017.
 */

public class book {

    String title;
    String author;
    String category;
    String image;
    String desc;
    Double price;
    Integer rating;


    public book() {
    }


    public book( String title, String author, String category, String image,String desc, Integer rating,Double price) {

        this.title = title;
        this.author = author;
        this.category = category;
        this.image = image;
        this.rating =rating;
        this.desc = desc;
        this.price =price;
    }



    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {return image; }

    public Integer getRating() { return rating; }

    public String getDesc() {  return desc;}

    public Double getPrice() { return price; }

    public String getCategory() {
        return category;
    }
}
