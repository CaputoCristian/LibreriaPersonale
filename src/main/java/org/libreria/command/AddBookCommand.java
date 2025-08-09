package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

public class AddBookCommand implements CommandInterface {
//    private final Library library;
    private final Book book;

    public AddBookCommand(Book book) {
//        this.library = library;
        this.book = book;
    }

    @Override
    public void execute() {
//        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.getRating(), book.getReadingStatus()); //si ha un DTO con tutto modificato, in quanto è un'aggiunta
//        library.addBook(newBook);
        LibrarySingleton.getInstance().addBook(book);
    }

    @Override
    public void undo() {
//        library.removeBook(book.getIsbn());
        LibrarySingleton.getInstance().removeBook(book.getIsbn());
    }
}
