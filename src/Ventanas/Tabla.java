package Ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Agentes.Agentes;

public class Tabla extends JFrame {
    private static DefaultTableModel dtm;
    private JScrollPane scrollPane;
    private JTable table;
    private Agentes[] agentes;
    private int totalAg;

    public Tabla(Agentes[] agentes, int n) {
        this.agentes = agentes;
        this.totalAg = n;
        String[] columnNames = { "Nombre", "Estado", "Seccion Critica", "Buffer", "Muerto" };

        // Tabla
        dtm = new DefaultTableModel(columnNames, 0);
        table = new JTable(dtm);

        // ScrollPane
        scrollPane = new JScrollPane(table);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tabla de Control");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        // Timer
        int delay = 40;
        Timer timer = new Timer(delay, e -> {
            SwingUtilities.invokeLater(() -> {
                updateRows(); // Actualizar con EDT
            });
        });
        timer.start();
    }

    public void updateRows() {
        // Quitar info previa
        dtm.setRowCount(0);
        for (int i = 0; i < totalAg; i++) {
            Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(), agentes[i].getSecCrit(),
                    agentes[i].getBuffer(), agentes[i].getDeadString()};
            dtm.addRow(rowData);
        }
    }
}