package org.libreria.gui;

import org.assertj.swing.core.BasicRobot;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.libreria.model.SearchFilter;
import org.libreria.singleton.LibrarySingleton;
import javax.swing.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SearchDialogTest {

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
    void testTitleSearchNoKeyword() throws Exception {
        SearchDialog dialog = GuiActionRunner.execute(() -> new SearchDialog(null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e cattura il risultato
        AtomicReference<SearchFilter> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            SearchFilter filter = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(filter);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("searchField").enterText("");
        window.radioButton("titleRadio").click();
        window.comboBox("ratingCombo").selectItem(3); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        window.comboBox("statusCombo").selectItem("Letto");

        window.button("searchButton").click(); // chiude il dialog

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        SearchFilter res = resultRef.get();

        assertNotNull(res, "Il dialog avrebbe dovuto restituire un filtro valido");
        assertEquals("", res.getSearchTerm());
        assertTrue(res.isSearchByTitle());
        assertEquals(2, res.getMinRating()); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        assertEquals("Letto", res.getReadingStatusFilter());
    }

    @Test
    void testAuthorSearchNoKeyword() throws Exception {
        SearchDialog dialog = GuiActionRunner.execute(() -> new SearchDialog(null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e cattura il risultato
        AtomicReference<SearchFilter> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            SearchFilter filter = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(filter);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("searchField").enterText("");
        window.radioButton("authorRadio").click();
        window.comboBox("ratingCombo").selectItem(1); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        window.comboBox("statusCombo").selectItem("In lettura");

        window.button("searchButton").click(); // chiude il dialog

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        SearchFilter res = resultRef.get();

        assertNotNull(res, "Il dialog avrebbe dovuto restituire un filtro valido");
        assertEquals("", res.getSearchTerm());
        assertFalse(res.isSearchByTitle());
        assertEquals(0, res.getMinRating()); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        assertEquals("In lettura", res.getReadingStatusFilter());
    }

    @Test
    void testDefaultSearchNoKeyword() throws Exception {
        SearchDialog dialog = GuiActionRunner.execute(() -> new SearchDialog(null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e cattura il risultato
        AtomicReference<SearchFilter> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            SearchFilter filter = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(filter);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("searchField").enterText("");
        window.comboBox("ratingCombo").selectItem(1); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        window.comboBox("statusCombo").selectItem("In lettura");

        window.button("searchButton").click(); // chiude il dialog

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        SearchFilter res = resultRef.get();

        assertNotNull(res, "Il dialog avrebbe dovuto restituire un filtro valido");
        assertEquals("", res.getSearchTerm());
        assertTrue(res.isSearchByTitle());
        assertEquals(0, res.getMinRating()); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        assertEquals("In lettura", res.getReadingStatusFilter());
    }

    @Test
    void testBlankSearchEqualsEmptyFilter() throws Exception {
        SearchDialog dialog = GuiActionRunner.execute(() -> new SearchDialog(null));

        window = new DialogFixture(robot, dialog);

        //Avvia showDialog su un thread separato e cattura il risultato
        AtomicReference<SearchFilter> resultRef = new AtomicReference<>();
        Thread modalThread = new Thread(() -> {
            SearchFilter filter = dialog.showDialog();   // blocca finché non premi "Conferma" o "Annulla"
            resultRef.set(filter);
        });
        modalThread.start();

        //Mostra la finestra e inserisce i campi in modo automatico
        window.show(); // obbligatorio per rendere visibile il dialog

        window.textBox("searchField").enterText("");
        window.comboBox("ratingCombo").selectItem(0); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        window.comboBox("statusCombo").selectItem(0);

        window.button("searchButton").click(); // chiude il dialog

        //Attende la fine del thread e verifica il risultato
        modalThread.join(2000);
        SearchFilter res = resultRef.get();

        assertNotNull(res, "Il dialog avrebbe dovuto restituire un filtro valido");
        assertEquals("", res.getSearchTerm());
        assertTrue(res.isSearchByTitle());
        assertNull(res.getMinRating()); //Settando index = 3 --> [0,1,2,3,4,5] // Il terzo oggetto è il numero due
        assertNull(res.getReadingStatusFilter());
    }
    
}