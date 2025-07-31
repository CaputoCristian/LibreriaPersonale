package org.libreria.gui;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.model.Book;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Titolo", "Autore", "Anno", "Genere", "Rating"};
    private final List<Book> books;

    public BookTableModel(List<Book> books) {
        this.books = books;
    }

    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = books.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> book.getTitle();
            case 1 -> book.getAuthor();
            case 2 -> book.getIsbn();
            case 3 -> book.getGenre();
            case 4 -> book.getRating();
            case 5 -> book.getReadingStatus();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public Book getBookAt(int rowIndex) {
        return books.get(rowIndex);
    }

    public void setBooks(List<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        fireTableDataChanged();
    }

    public void removeBookAt(int rowIndex) {
        books.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addBook(Book book) {
        books.add(book);
        fireTableRowsInserted(books.size() - 1, books.size() - 1);
    }

    public void updateBook(int rowIndex, Book updatedBook) {
        books.set(rowIndex, updatedBook);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }


}