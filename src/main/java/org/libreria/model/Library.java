package org.libreria.model;

import org.libreria.DTO.BookUpdateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Library extends AbstractLibrary {

    public Library() {
        super(new ArrayList<>());
    }

    public Library(List<Book> books) {
        super(books);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }


    @Override
    public void updateBook(String isbn, Book updatedBook) {
        Optional<Book> optionalBook = books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst();

        if (optionalBook.isPresent()) {
            Book bookToUpdate = optionalBook.get();

//            if (updateDTO.isTitleModified()) {
//                bookToUpdate.setTitle(updateDTO.getTitle());
//            }
//
////            if (updateDTO.isIsbnModified()) {
////                bookToUpdate.setIsbn(updateDTO.getTitle());
////            }
//
//            if (updateDTO.isAuthorModified()) {
//                bookToUpdate.setAuthor(updateDTO.getAuthor());
//            }
//
//            if (updateDTO.isGenreModified()) {
//                bookToUpdate.setGenre(updateDTO.getGenre());
//            }
//
//            if (updateDTO.isRatingModified()) {
//                bookToUpdate.setRating(updateDTO.getRating());
//            }
//
//            if (updateDTO.isReadingStatusModified()) {
//                bookToUpdate.setReadingStatus(updateDTO.getReadingStatus());
//            }

            bookToUpdate.setTitle(updatedBook.getTitle());
            bookToUpdate.setAuthor(updatedBook.getAuthor());
            bookToUpdate.setIsbn(updatedBook.getIsbn());
            bookToUpdate.setGenre(updatedBook.getGenre());
            bookToUpdate.setRating(updatedBook.getRating());
            bookToUpdate.setReadingStatus(updatedBook.getReadingStatus());


        }

    }

//    @Override
//    public List<Book> searchByTitle(String title) {
//        return books.stream()
//                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<Book> searchByAuthor(String author) {
//        return books.stream()
//                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void sortByTitle() {
//        books.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
//    }
//
//    @Override
//    public void sortByRating() {
//        books.sort(Comparator.comparingInt(Book::getRating).reversed());
//    }
}