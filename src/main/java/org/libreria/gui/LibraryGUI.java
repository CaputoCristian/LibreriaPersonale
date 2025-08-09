package org.libreria.gui;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.command.AddBookCommand;
import org.libreria.command.CommandInterface;
import org.libreria.command.DeleteBookCommand;
import org.libreria.command.UpdateBookCommand;
import org.libreria.model.Book;
import org.libreria.model.SearchFilter;
import org.libreria.singleton.LibrarySingleton;
import org.libreria.strategy.SearchStrategy.AuthorSearchStrategy;
import org.libreria.strategy.SearchStrategy.FilteredSearchStrategy;
import org.libreria.strategy.SearchStrategy.SearchStrategy;
import org.libreria.strategy.SearchStrategy.TitleSearchStrategy;
import org.libreria.strategy.SortStrategy.SortStrategy;
import org.libreria.strategy.SortStrategy.SortStrategyManager;
import org.libreria.template.AddBookDialog;
import org.libreria.template.UpdateBookDialog;
import org.libreria.util.LibraryController;
import org.libreria.util.SortMode;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.util.List;

public class LibraryGUI extends JFrame {

    private JTable bookTable;
//    private DefaultTableModel tableModel;
    private BookTableModel tableModel;
    private JButton addButton, editButton, deleteButton, searchButton, sortButton, refreshButton;

    private LibraryController libraryController;
    private File jsonFile;

    private final SortStrategyManager sortManager = new SortStrategyManager(); //Necessario qua e non nel controller, per nome sul tasto
    private SortMode currentSortMode = SortMode.BY_INSERTION;       //default

    public LibraryGUI() {
        setTitle("Gestione Libreria Personale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

//        jsonFile = new File("database.json");
//        LibrarySingleton.getInstance().setSource(jsonFile);
        libraryController = new LibraryController();
        System.out.println("Libri caricati: " + LibrarySingleton.getInstance().getLibrary().getBooks().size());

        bookTable = new JTable(); // inizializzazione base
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//        tableModel = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        tableModel = new BookTableModel(LibrarySingleton.getInstance().getLibrary().getBooks());
        bookTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Aggiungi");
        editButton = new JButton("Modifica");
        deleteButton = new JButton("Elimina");
        searchButton = new JButton("Cerca");
        sortButton = new JButton("Ordina per Inserimento");
        refreshButton = new JButton("Aggiorna");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadBooks());

//        addButton.addActionListener(e -> {
//            BookFormDialog dialog = new BookFormDialog(this);
//            BookUpdateDTO book = dialog.showDialog();
//            if (book != null) {
//                Command command = new AddBookCommand(LibrarySingleton.getInstance().getLibrary(), book);
//                command.execute();
//
//                LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//
//                loadBooks();
//            }
//        });
//
//        editButton.addActionListener(e -> {
//            int selectedRow = bookTable.getSelectedRow();
//            if (selectedRow >= 0) {
//                Book selectedBook = tableModel.getBookAt(selectedRow);
//                BookFormDialog dialog = new BookFormDialog(this, selectedBook);
//                BookUpdateDTO updatedBook = dialog.showDialog();
//                if (updatedBook != null) {
//                    Command command = new UpdateBookCommand(LibrarySingleton.getInstance().getLibrary(), updatedBook);
//                    command.execute();
//
//                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                    loadBooks();
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
//            }
//        });

        // Aggiunta libro
        addButton.addActionListener(e -> {
            AddBookDialog dialog = new AddBookDialog(this);
            Book newBook = dialog.showDialog();
            if (newBook != null) {
//                CommandInterface command = new AddBookCommand(LibrarySingleton.getInstance().getLibrary(), newBook);
//                command.execute();
//
//                LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                loadBooks();

                libraryController.addBook(newBook);
                loadBooks(); //Aumenta la complessità temporale, spesso è superfluo rileggere da file, ma se così si è sicuri dell'aggiunta: si evita di aggiungere alla lista libri
                             //che per qualsiasi motivo non sono stati salvati (e non hanno sollevato errori)

            }
        });

        // Modifica libro
        editButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                UpdateBookDialog dialog = new UpdateBookDialog(this, selectedBook);
//                BookUpdateDTO bookToUpdate = dialog.showDialog();
                Book updatedBook = dialog.showDialog();
                if (updatedBook != null) {
//                    CommandInterface command = new UpdateBookCommand(LibrarySingleton.getInstance().getLibrary(), updatedBook);
//                    command.execute();
//
//                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                    loadBooks();

                    libraryController.updateBook(updatedBook);
                    loadBooks(); //Aumenta la complessità temporale, spesso è superfluo rileggere da file, ma se così si è sicuri dell'aggiunta: si evita di aggiungere alla lista libri
                    //che per qualsiasi motivo non sono stati salvati (e non hanno sollevato errori)
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });

//        deleteButton.addActionListener(e -> {
//            int selectedRow = bookTable.getSelectedRow();
//            if (selectedRow >= 0) {
//                Book selectedBook = tableModel.getBookAt(selectedRow);
//                int confirm = JOptionPane.showConfirmDialog(this,
//                        "Sei sicuro di voler eliminare il libro selezionato?",
//                        "Conferma eliminazione",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirm == JOptionPane.YES_OPTION) {
//                    Command command = new DeleteBookCommand(LibrarySingleton.getInstance().getLibrary(), selectedBook.getIsbn());
//                    command.execute();
//
//                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                    loadBooks();
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Seleziona un libro da eliminare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
//            }
//        });

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
//                        CommandInterface command = new DeleteBookCommand(LibrarySingleton.getInstance().getLibrary(), selectedBook.getIsbn());
//                        command.execute();

                        libraryController.deleteBook(selectedBook.getIsbn());

                        // Aggiungi un log per debug
                        System.out.println("Libro eliminato: " + selectedBook.getTitle());
                        System.out.println("Libri rimanenti: " +
                                LibrarySingleton.getInstance().getLibrary().getBooks().size());

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
        });

        sortButton.addActionListener(e -> {
            SortStrategy strategy = sortManager.nextStrategy();
//            List<Book> sorted = strategy.sort(LibrarySingleton.getInstance().getLibrary().getBooks());
//
//            sortButton.setText("Ordina per " + strategy.getName());

            List<Book> sorted = libraryController.sortBooks(strategy);

            BookTableModel model = new BookTableModel(sorted);
            bookTable.setModel(model);

        });

        searchButton.addActionListener(e -> {
            SearchDialog dialog = new SearchDialog(this);
            SearchFilter filter = dialog.showDialog();
//            if (filter != null) {
//                SearchStrategy baseStrategy = filter.isSearchByTitle()
//                        ? new TitleSearchStrategy()
//                        : new AuthorSearchStrategy();
//
//                SearchStrategy fullStrategy = new FilteredSearchStrategy(
//                        baseStrategy,
//                        filter.getReadingStatusFilter(),
//                        filter.getMinRating()
//                );
//
//                LibrarySingleton.getInstance().setSearchStrategy(fullStrategy);
//                List<Book> results = LibrarySingleton.getInstance().search(
//                        LibrarySingleton.getInstance().getLibrary().getBooks(),
//                        filter.getSearchTerm()
//                );

                List<Book> results = libraryController.searchBooks(filter);

                tableModel.setBooks(results);
             //}
        });

        sortManager.nextStrategy(); //Necessario, altrimenti il primo click su "Ordina" non modifica nulla
        loadBooks();
    }


//    private void loadBooks() {
//        List<BookUpdateDTO> myBookList = LibrarySingleton.getInstance()
//                .getLibrary()
//                .getAllBooks()
//                .stream()
//                .map(BookUpdateDTO::fromBook)
//                .toList();
//
//        BookTableModel tableModel = new BookTableModel(new ArrayList<>(myBookList));
//        bookTable.setModel(tableModel);
//    }

//    private void loadBooks() {
//        tableModel.setRowCount(0); // pulisce la tabella
//        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
//        for (Book book : books) {
//            tableModel.addRow(new Object[]{
//                    book.getTitle(),
//                    book.getAuthor(),
//                    book.getIsbn(),
//                    book.getGenre(),
//                    book.getRating(),
//                    book.getReadingStatus()
//            });
//        }
//
//        BookTableModel tableModel = new BookTableModel(new ArrayList<>(LibrarySingleton.getInstance().getLibrary().getBooks()));
//        bookTable.setModel(tableModel);
//    }

    private void loadBooks() {
        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
        tableModel.setBooks(books);

        System.out.println("Aggiornamento tabella con " + books.size() + " libri");

    }

    public static void main(String[] args) {

        new LibraryGUI().setVisible(true);

    }
}