package Ventanas;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import Agentes.Agentes;
import Agentes.Estados;

public class TablaAgente extends JFrame {
    private final DefaultTableModel dtm;
    private final Agentes[] agentes;
    private final int totalAg;
    private final int type;

    public TablaAgente(Agentes[] a, int n, String[] columnNames, int t) {
        agentes = a;
        totalAg = n;
        type = t;

        dtm = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(dtm);
        add(new JScrollPane(table));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if (type == 0) {
            setTitle("Vendedoras");
        } else if (type == 1) {
            setTitle("Clientes");
        } else {
            setTitle("Santas");
        }
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        Timer timer = new Timer(200, e -> updateRows());
        timer.start();
    }

    public void updateRows() {
        dtm.setRowCount(0);
        if (agentes == null) {
            return;
        }
        if (type == 0) {
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.DESCANSANDO),
                        agentes[i].getEstado(Estados.ESPERANDOCLIENTE), agentes[i].getEstado(Estados.MOSTRANDO),
                        agentes[i].getEstado(Estados.COBRANDO), agentes[i].getEstado(Estados.ENVOLVIENDO),
                        agentes[i].getEstado(Estados.DESPIDIENDOSE), agentes[i].getEstado(Estados.MUERTO),
                        agentes[i].getEstado(Estados.PANICO) };
                dtm.addRow(rowData);
            }
        } else if (type == 1) {
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.PASEANDO),
                        agentes[i].getEstado(Estados.ESPERANDOSANTA), agentes[i].getEstado(Estados.CONVIVIENDO),
                        agentes[i].getEstado(Estados.VIENDOREG), agentes[i].getEstado(Estados.ESCOGIENDOREG),
                        agentes[i].getEstado(Estados.ESPERANDOENVOLTURA), agentes[i].getEstado(Estados.PAGANDO),
                        agentes[i].getEstado(Estados.MUERTO), agentes[i].getEstado(Estados.PANICO) };
                dtm.addRow(rowData);
            }
        } else {
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.DESCANSANDO),
                        agentes[i].getEstado(Estados.SALUDANDO), agentes[i].getEstado(Estados.PLATICANDO),
                        agentes[i].getEstado(Estados.POSANDO), agentes[i].getEstado(Estados.DESPIDIENDOSE),
                        agentes[i].getEstado(Estados.MUERTO), agentes[i].getEstado(Estados.PANICO) };
                dtm.addRow(rowData);
            }
        }
    }
}
