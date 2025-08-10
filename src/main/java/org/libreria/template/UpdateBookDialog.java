package org.libreria.template;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.model.Book;

import javax.swing.*;

public class UpdateBookDialog extends BookDialogTemplate {

    private Book updatedBook;

    public UpdateBookDialog(JFrame parent, Book bookToEdit) {
        super(parent, "Modifica Libro");

        // Precompilazione con dati esistenti
        titleField.setText(bookToEdit.getTitle());
        authorField.setText(bookToEdit.getAuthor());
        isbnField.setText(bookToEdit.getIsbn());
        genreField.setText(bookToEdit.getGenre());
        ratingField.setText(String.valueOf(bookToEdit.getRating()));
        readingStatusField.setSelectedItem(bookToEdit.getReadingStatus());

        isbnField.setEnabled(false); // non modificabile
    }

    @Override
    protected void onConfirm() {
        if (!validateCommonFields()) return;

//        bookDTO = new BookUpdateDTO.Builder()
//                .title(titleField.getText().trim())
//                .author(authorField.getText().trim())
//                .isbn(isbnField.getText().trim())
//                .genre(genreField.getText().trim())
//                .rating(Integer.parseInt(ratingField.getText().trim()))
//                .readingStatus(readingStatusField.getText().trim())
//                .build();

        updatedBook = new Book(
                titleField.getText().trim(),
                authorField.getText().trim(),
                isbnField.getText().trim(),
                genreField.getText().trim(),
                Integer.parseInt(ratingField.getText().trim()),
                readingStatusField.getSelectedItem().toString()
                );

        System.out.println("Modificato: " + updatedBook.toString());
        dispose();
    }

    public Book showDialog() {
        setVisible(true);
        return updatedBook;
    }
}
