package org.libreria.gui;

import javax.swing.*;

public class JFrameExample {
    public static void main(String[] args) {

        JFrame window = new JFrame("Example GUI");
        window.setTitle("Hello World"); //Per farlo cambiare in base alla schemata, esempio "Libreria" oppure "Ricerca"
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stoppa il programma quando premi la X
        window.setSize(400, 400);
        window.setResizable(false);
        window.setLocationRelativeTo(null); //Dopo che si setta la size, stabilisce dove compare la finestra
        window.setLocation(40,40); //Dove farlo comparire, sovrascrive il precedente comando
        window.setVisible(true);

        //adding component
        JButton button = new JButton("Button");
        window.add(button);

        //dato che setVisible è stato chiamato prima, il bottone non è visibile, bisognerebbe richiamarlo
        //in alternativa si può usare questo:
        window.repaint();
        window.revalidate();

    }
}
