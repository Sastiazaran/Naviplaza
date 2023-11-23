package Ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Agentes.Estados;
import Agentes.Agentes;

public class Tabla extends JFrame {
    private static DefaultTableModel dtm;
    private JScrollPane scrollPane;
    private JTable table;
    private static Agentes[] agentes;
    private static int totalAg;

    public Tabla(Agentes[] a, int n) {
        this.agentes = a;
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
        int delay = 10;
        Timer timer = new Timer(delay, e -> {
            SwingUtilities.invokeLater(() -> {
                updateRows(); // Actualizar con EDT
            });
        });
        timer.start();
    }

    public static void setAllPanic(int except){
        for (int i = 0; i < totalAg; i++) {
            if(i==except){break;}
            agentes[i].setBuffer("AAAAAA");
            agentes[i].setEstado(Estados.PANICO);
            agentes[i].setSecCrit("AAAAAAAAAAA");
        }
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