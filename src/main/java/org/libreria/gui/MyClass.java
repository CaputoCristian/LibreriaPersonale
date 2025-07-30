package org.libreria.gui;

import javax.swing.*;

public class MyClass extends JFrame {

    public MyClass() {

        super("Example GUI");
        setTitle("Hello World"); //Per farlo cambiare in base alla schemata, esempio "Libreria" oppure "Ricerca"
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stoppa il programma quando premi la X
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null); //Dopo che si setta la size, stabilisce dove compare la finestra
        setLocation(40,40); //Dove farlo comparire, sovrascrive il precedente comando
        setVisible(true);

        //adding component
        JButton button = new JButton("Button");
        add(button);

        //dato che setVisible è stato chiamato prima, il bottone non è visibile, bisognerebbe richiamarlo
        //in alternativa si può usare questo:
        repaint();
        revalidate();

    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
    }


}
