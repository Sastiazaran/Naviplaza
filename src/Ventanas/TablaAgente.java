package Ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TablaAgente extends JFrame {
    private static DefaultTableModel dtm;
    private JTable table;
    private Thread[] hilos;
    private JScrollPane scrollPane;

    public TablaAgente(Thread[] hilos) {
        this.hilos = hilos;
        String[] columnNames = { "Nombre", "Accesos", "Esperando", "Seccion Critica", "Muriendo", "Muerto" };

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
        for (int i = 0; i < hilos.length; i++) {
            Object[] rowData = { hilos[i].getName(), hilos[i].getAccesos(), hilos[i].getStatus(0),
                    hilos[i].getStatus(1), hilos[i].getStatus(2), hilos[i].getStatus(3) };
            dtm.addRow(rowData);
        }
    }
}