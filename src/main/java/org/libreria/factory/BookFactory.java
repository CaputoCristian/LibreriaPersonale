package org.libreria.factory;

import org.libreria.model.Book;

import java.util.Map;

public class BookFactory {

    public static Book createBook(Map<String, Object> data) throws IllegalArgumentException {
        String title = (String) data.get("title");
        String author = (String) data.get("author");
        String isbn = (String) data.get("isbn");
        String genre = (String) data.get("genre");
        Integer rating = (Integer) data.get("rating");
        String readingStatus = (String) data.get("readingStatus");

        return new Book(title, author, isbn, genre, rating, readingStatus);
    }
}

