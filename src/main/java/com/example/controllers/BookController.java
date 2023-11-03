package com.example.controllers;

import com.example.pojos.Book;
import com.example.pojos.BookUpdate;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Controller("/books")
public class BookController {

    public Map<Integer, Book> books = new HashMap<>();

    public BookController() {
        this.books.put(1, new Book("The Art of Programming", "John Smith", "2023-01-15", 49.99F, "Computer Science", "9780123456789", 10));
        this.books.put(2, new Book("The Power of Words", "Emily Johnson", "2022-11-30", 29.99F, "Self-Help", "9789876543210", 5));
        this.books.put(3, new Book("A Journey Through Time", "David Thompson", "2023-03-22", 19.99F, "Fantasy", "9786543210987", 8));
        this.books.put(4, new Book("The Hidden Secrets", "Sarah Roberts", "2023-02-10", 14.99F, "Mystery", "9783210987654", 12));
        this.books.put(5, new Book("The Science of Nature", "Michael Anderson", "2023-04-18", 24.99F, "Science", "9785432109876", 3));
    }
    @Get
    //@Produces(MediaType.APPLICATION_JSON)
    //public Map<Integer, Book> getBooks() {
    public HttpResponse getBooks() {
        return HttpResponse.ok(books);
    }

    @Post
    public HttpResponse addBook(@Body Book post){
        if(!books.containsValue(post)) {
            Integer last = 0;
            for (Integer key : books.keySet()) {
                last = key;
            }
            books.put(last + 1, post);
            return HttpResponse.ok(post).status(201);
        } else {
            //return books;
            return HttpResponse.ok("Duplicate Record!").status(409);
        }
    }

    @Put
    public HttpResponse updateBook(@Body BookUpdate upd) {
        if (books.replace(upd.getId(), new Book(upd.getTitle(), upd.getAuthor(), upd.getPubDate(), upd.getPrice(), upd.getGenre(), upd.getIsbn(), upd.getQuantity())) != null) {
            return HttpResponse.ok(books);
        } else {
            return  HttpResponse.ok("Id(" + upd.getId() + ") not found!").status(409);
        }
    }

    @Delete
    public HttpResponse deleteBook(@Body BookUpdate del) {
        if (books.remove(del.getId()) != null) {
            return HttpResponse.ok(books);
        } else {
            return  HttpResponse.ok("Id(" + del.getId() + ") not found!").status(409);
        }
    }
}
