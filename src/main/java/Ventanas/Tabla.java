package Ventanas;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import Agentes.Agentes;
import Agentes.Estados;

public class Tabla extends JFrame {
    private final DefaultTableModel dtm;
    private static Agentes[] agentes;
    private static int totalAg;

    public Tabla(Agentes[] a, int n) {
        agentes = a;
        totalAg = n;
        String[] columnNames = { "Nombre", "Estado", "Seccion Critica", "Buffer", "Muerto" };

        dtm = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(dtm);
        add(new JScrollPane(table));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Tabla de Control");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        Timer timer = new Timer(200, e -> updateRows());
        timer.start();
    }

    public static void setAllPanic(int except) {
        panicAllExcept(agentes, except);
    }

    static void panicAllExcept(Agentes[] agents, int except) {
        if (agents == null) {
            return;
        }
        for (int i = 0; i < agents.length; i++) {
            if (i == except || agents[i] == null) {
                continue;
            }
            agents[i].setBuffer("AAAAAA");
            agents[i].setEstado(Estados.PANICO);
            agents[i].setSecCrit("AAAAAAAAAAA");
        }
    }

    public void updateRows() {
        dtm.setRowCount(0);
        for (int i = 0; i < totalAg; i++) {
            Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(), agentes[i].getSecCrit(),
                    agentes[i].getBuffer(), agentes[i].getDeadString() };
            dtm.addRow(rowData);
        }
    }
}
