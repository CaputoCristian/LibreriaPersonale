package org.libreria.gui;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BookFormDialog extends JDialog {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField isbnField;
    private JTextField genreField;
    private JTextField ratingField;
    private JTextField readingStatusField;
    private JButton confirmButton;
    private JButton cancelButton;

    private Book bookResult;

    // Costruttore per aggiunta
    public BookFormDialog(JFrame parent) {
        this(parent, null);
    }

    // Costruttore per modifica
    public BookFormDialog(JFrame parent, BookUpdateDTO bookToEdit) {
        super(parent, true);
        setTitle(bookToEdit == null ? "Aggiungi Libro" : "Modifica Libro");
        setSize(300, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Campi input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Titolo:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Autore:"));
        authorField = new JTextField();
        inputPanel.add(authorField);

        inputPanel.add(new JLabel("ISBN:"));
        genreField = new JTextField();
        inputPanel.add(genreField);

        inputPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField();
        inputPanel.add(isbnField);

        inputPanel.add(new JLabel("Rating (0-5):"));
        ratingField = new JTextField();
        inputPanel.add(ratingField);

        inputPanel.add(new JLabel("Stato: letto, in lettura, da leggere"));
        readingStatusField = new JTextField();
        inputPanel.add(readingStatusField);

        add(inputPanel, BorderLayout.CENTER);

        // Bottoni
        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Conferma");
        cancelButton = new JButton("Annulla");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Se stiamo modificando, precompila i campi
        if (bookToEdit != null) {
            titleField.setText(bookToEdit.getTitle());
            authorField.setText(bookToEdit.getAuthor());
            isbnField.setText(bookToEdit.getIsbn());
            genreField.setText(bookToEdit.getGenre());
            ratingField.setText(String.valueOf(bookToEdit.getRating()));
            readingStatusField.setText(bookToEdit.getReadingStatus());
//            isbnField.setEnabled(false); // L'ISBN non va modificato
        }

        // Azione del bottone conferma
        confirmButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String genre = genreField.getText().trim();
            String ratingText = ratingField.getText().trim();
            String readingStatus = readingStatusField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || ratingText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.");
                return;
            }

            int rating;
            try {
                rating = Integer.parseInt(ratingText);
                if (rating < 0 || rating > 5) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Il rating deve essere un numero tra 0 e 5.");
                return;
            }

            bookResult = new Book(title, author, isbn, genre, rating, readingStatus);
            dispose();
        });

        // Azione del bottone annulla
        cancelButton.addActionListener(e -> {
            bookResult = null;
            dispose();
        });
    }

    public Book showDialog() {
        setVisible(true);
        return bookResult;
    }
}