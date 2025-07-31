package org.libreria.gui;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;

public class LibraryGUI extends JFrame {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, searchButton, sortButton, refreshButton;

    public LibraryGUI() {
        setTitle("Gestione Libreria Personale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Aggiungi");
        editButton = new JButton("Modifica");
        searchButton = new JButton("Cerca");
        sortButton = new JButton("Ordina");
        refreshButton = new JButton("Aggiorna");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadBooks());

        // Altri pulsanti da implementare in seguito

        loadBooks();
    }

    private void loadBooks() {
        tableModel.setRowCount(0); // pulisce la tabella
        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getGenre(),
                    book.getRating(),
                    book.getReadingStatus()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}