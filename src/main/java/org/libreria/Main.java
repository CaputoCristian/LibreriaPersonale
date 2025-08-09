package org.libreria;

import org.libreria.gui.LibraryGUI;
import org.libreria.singleton.LibrarySingleton;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        LibrarySingleton.getInstance().setSource(new File("database.json"));
//        LibrarySingleton.getInstance().loadBooksFromJson();
        File jsonFile = new File("database.json");
        LibrarySingleton.getInstance().setSource(jsonFile);
        new LibraryGUI().setVisible(true);


    }
}