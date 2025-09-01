package org.libreria.template;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.jupiter.api.*;
import org.libreria.model.Book;

import javax.swing.*;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.libreria.singleton.LibrarySingleton;

import java.util.concurrent.atomic.AtomicReference;

class AddBookDialogTest {

    private static Robot robot;
    private DialogFixture window;

    @BeforeAll
    static void beforeAll() {
        // obbligatorio per AssertJ-Swing
        FailOnThreadViolationRepaintManager.install();
        robot = BasicRobot.robotWithNewAwtHierarchy();
        robot.settings().delayBetweenEvents(10);
    }

    @AfterEach
    void cleanup() {
        if (window != null) {
            window.cleanUp();
            window = null;
        }
        // reset della libreria condivisa per evitare conflitti tra test
        GuiActionRunner.execute(() ->
                LibrarySingleton.getInstance().getLibrary().setBooks(new java.util.ArrayList<>())
        );
    }

    @Test
    void testValidDataIsbn10() throws Exception {
        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show();

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("0123456789");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Avvia showDialog su un thread separato e ne cattura il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNotNull(newBook, "Il dialog avrebbe dovuto restituire un Book");
        assertEquals("Test Title", newBook.getTitle());
        assertEquals("Test Author", newBook.getAuthor());
        assertEquals("0123456789", newBook.getIsbn());
        assertEquals("Fiction", newBook.getGenre());
        assertEquals(5, newBook.getRating());
        assertEquals("Letto", newBook.getReadingStatus());
    }

    @Test
    void testValidDataIsbn13() throws Exception {
        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("0123456789012");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNotNull(newBook, "Il dialog avrebbe dovuto restituire un Book");
        assertEquals("Test Title", newBook.getTitle());
        assertEquals("Test Author", newBook.getAuthor());
        assertEquals("0123456789012", newBook.getIsbn());
        assertEquals("Fiction", newBook.getGenre());
        assertEquals(5, newBook.getRating());
        assertEquals("Letto", newBook.getReadingStatus());
    }

    @Test
    void testEmptyTitle() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");

    }

    @Test
    void testEmptyAuthor() throws InterruptedException {
        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testEmptyIsbn() throws InterruptedException {
        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testEmptyGenre() throws InterruptedException {
        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testNegativeRating() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("-1");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testRatingOverFive() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("6");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testNotIntegerRating() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("2.5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testTextRating() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("0123456789");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("Cinque");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testTextIsbn() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("ZeroOneTwoThreeFourFiveSixSevenEightNine");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("Cinque");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNull(newBook, "Il dialog dovrebbe restituire un errore");
    }

    @Test
    void testDoubleEntry() throws InterruptedException {

        AddBookDialog dialog = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        Book newBook = resultRef.get();

        assertNotNull(newBook, "Il dialog non dovrebbe restituire un errore");

        LibrarySingleton.getInstance().getLibrary().addBook(newBook);

        //Aggiunta secondo libro - duplicato

        AddBookDialog dialog2 = GuiActionRunner.execute(() -> new AddBookDialog((JFrame) null));

        window = new DialogFixture(robot, dialog2);

        //Avvia showDialog su un thread separato e ne cattura il risultato
        AtomicReference<Book> resultRef2 = new AtomicReference<>();
        Thread modalThread2 = new Thread(() -> {
            Book result = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(result);
        });
        modalThread2.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("titleField").enterText("Test Title");
        window.textBox("authorField").enterText("Test Author");
        window.textBox("isbnField").enterText("9781234567897");
        window.textBox("genreField").enterText("Fiction");
        window.textBox("ratingField").deleteText().enterText("5");
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("confirmButton").click(); // chiude il dialog impostando newBook

        //Attende la fine del thread e verifica il risultato
        modalThread.join(4000);
        Book duplicateBook = resultRef2.get();

        assertNull(duplicateBook, "Il dialog dovrebbe restituire un errore");

    }

}
