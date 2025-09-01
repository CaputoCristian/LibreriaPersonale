package org.libreria.gui;

import org.libreria.model.SearchFilter;

import javax.swing.*;
import java.awt.*;

public class SearchDialog extends JDialog {

    private JTextField searchField;
    private JRadioButton titleRadio;
    private JRadioButton authorRadio;
    private JComboBox<String> statusCombo;
    private JComboBox<Integer> ratingCombo;
    private JButton searchButton;
    private JButton cancelButton;

    private SearchFilter result;

    public SearchDialog(JFrame parent) {
        super(parent, "Ricerca testo", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Campo ricerca
        panel.add(new JLabel("Termine da cercare:"));
        searchField = new JTextField();
        panel.add(searchField);

        // Scelta titolo o autore
        panel.add(new JLabel("Cerca per:"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titleRadio = new JRadioButton("Titolo", true);
        authorRadio = new JRadioButton("Autore");
        ButtonGroup group = new ButtonGroup();
        group.add(titleRadio);
        group.add(authorRadio);
        radioPanel.add(titleRadio);
        radioPanel.add(authorRadio);
        panel.add(radioPanel);

        // Stato di lettura
        panel.add(new JLabel("Stato di lettura:"));
        String[] stati = {"", "Letto", "In lettura", "Da leggere"};
        statusCombo = new JComboBox<>(stati);
        panel.add(statusCombo);

        // Valutazione minima
        panel.add(new JLabel("Valutazione minima:"));
        Integer[] ratings = {null, 0, 1, 2, 3, 4, 5};
        ratingCombo = new JComboBox<>(ratings);
        panel.add(ratingCombo);

        add(panel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        searchButton = new JButton("Cerca");
        cancelButton = new JButton("Annulla");
        buttons.add(searchButton);
        buttons.add(cancelButton);
        add(buttons, BorderLayout.SOUTH);

        searchField.setName("searchField");
        titleRadio.setName("titleRadio");
        authorRadio.setName("authorRadio");
        statusCombo.setName("statusCombo");
        ratingCombo.setName("ratingCombo");
        statusCombo.setName("statusCombo");
        searchButton.setName("searchButton");
        cancelButton.setName("cancelButton");

        // Eventi
        searchButton.addActionListener(e -> {
            String term = searchField.getText().trim();
            boolean byTitle = titleRadio.isSelected();
            String stato = (String) statusCombo.getSelectedItem();
            Integer minRating = (Integer) ratingCombo.getSelectedItem();

            if (term.isEmpty()) {
                term = ""; //Permette la ricerca solo per filtro, senza autore o titolo
            }

            if (stato != null && stato.isBlank()) stato = null;

            result = new SearchFilter(term, byTitle, stato, minRating);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            result = null;
            dispose();
        });
    }

    public SearchFilter showDialog() {
        setVisible(true);
        return result;
    }
}