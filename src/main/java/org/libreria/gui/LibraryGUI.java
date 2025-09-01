package org.libreria.gui;


import org.libreria.model.Book;
import org.libreria.model.SearchFilter;
import org.libreria.singleton.LibrarySingleton;
import org.libreria.strategy.SortStrategy.SortStrategy;
import org.libreria.strategy.SortStrategy.SortStrategyManager;
import org.libreria.template.AddBookDialog;
import org.libreria.template.UpdateBookDialog;
import org.libreria.util.LibraryController;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class LibraryGUI extends JFrame {

    private JTable bookTable;
    private BookTableModel tableModel;
    private JButton addButton, editButton, deleteButton, searchButton, sortButton, refreshButton;

    private LibraryController libraryController;

    private final SortStrategyManager sortManager = new SortStrategyManager(); //Necessario qua e non nel controller, per nome sul tasto

    public LibraryGUI() {
        setTitle("Gestione Libreria Personale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        libraryController = new LibraryController();

        bookTable = new JTable(); // inizializzazione base
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel = new BookTableModel(LibrarySingleton.getInstance().getLibrary().getBooks());
        bookTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Aggiungi");
        editButton = new JButton("Modifica");
        deleteButton = new JButton("Elimina");
        searchButton = new JButton("Cerca");
        sortButton = new JButton("Ordina per " + sortManager.getCurrentStrategy().getName());
        refreshButton = new JButton("Ricarica");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> {
            loadBooks();
            sortManager.setCurrentStrategy(0); // stato iniziale: ordinamento per inserimento
            sortButton.setText("Ordina per " + sortManager.getCurrentStrategy().getName());

            List<Book> sorted = libraryController.sortBooks(sortManager.getCurrentStrategy());

            tableModel.setBooks(sorted);

        });

        addButton.addActionListener(e -> {
            AddBookDialog dialog = new AddBookDialog(this);
            Book newBook = dialog.showDialog();
            if (newBook != null) {

                libraryController.addBook(newBook);
                loadBooks(); //Aumenta la complessità temporale, spesso è superfluo rileggere da file, ma se così si è sicuri dell'aggiunta: si evita di aggiungere alla lista libri
                             //che per qualsiasi motivo non sono stati salvati (e non hanno sollevato errori)
            }
            loadBooks();
        });

        editButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                UpdateBookDialog dialog = new UpdateBookDialog(this, selectedBook);
                Book updatedBook = dialog.showDialog();
                if (updatedBook != null) {

                    libraryController.updateBook(updatedBook);
                    loadBooks(); //Aumenta la complessità temporale, spesso è superfluo rileggere da file, ma se così si è sicuri dell'aggiunta: si evita di aggiungere alla lista libri
                    //che per qualsiasi motivo non sono stati salvati (e non hanno sollevato errori)
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
            loadBooks();
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Sei sicuro di voler eliminare il libro selezionato?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {

                        libraryController.deleteBook(selectedBook.getIsbn());

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Errore durante l'eliminazione: " + ex.getMessage(),
                                "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleziona un libro da eliminare.",
                        "Nessuna selezione",
                        JOptionPane.WARNING_MESSAGE);
            }
            loadBooks();
        });

        sortButton.addActionListener(e -> {
            SortStrategy strategy = sortManager.nextStrategy();
            sortButton.setText("Ordina per " + strategy.getName());

            List<Book> sorted = libraryController.sortBooks(strategy);
            tableModel.setBooks(sorted);

        });

        searchButton.addActionListener(e -> {
            SearchDialog dialog = new SearchDialog(this);
            SearchFilter filter = dialog.showDialog();

                List<Book> results = libraryController.searchBooks(filter);
                if (results != null) {
                    tableModel.setBooks(results);
                }

        });
        loadBooks();
    }

    private void loadBooks() {
        LibrarySingleton.getInstance().loadBooksFromJson();
        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
        tableModel.setBooks(books);
    }

    public static void main(String[] args) {
        new LibraryGUI().setVisible(true);
    }
}