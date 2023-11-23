package Ventanas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Agentes.Agentes;
import Agentes.Estados;

public class TablaAgente extends JFrame {
    private DefaultTableModel dtm;
    private JScrollPane scrollPane;
    private JTable table;
    private Agentes[] agentes;
    private int totalAg;
    private int type;

    public TablaAgente(Agentes[] a, int n, String[] columnNames, int t) {
        agentes = a;
        totalAg = n;
        type = t;

        // Tabla
        dtm = new DefaultTableModel(columnNames, 0);
        table = new JTable(dtm);

        // ScrollPane
        scrollPane = new JScrollPane(table);
        add(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if(type==0){
            setTitle("Vendedoras");
        }else if(type==1){
            setTitle("Clientes");
        }else{
            setTitle("Santas");
        }
        setSize(1000, 400);
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

    public void updateRows() {
        // Quitar info previa
        dtm.setRowCount(0);
        if(type==0){
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.DESCANSANDO),
                    agentes[i].getEstado(Estados.ESPERANDOCLIENTE), agentes[i].getEstado(Estados.MOSTRANDO),
                    agentes[i].getEstado(Estados.COBRANDO), agentes[i].getEstado(Estados.ENVOLVIENDO),
                    agentes[i].getEstado(Estados.DESPIDIENDOSE), agentes[i].getEstado(Estados.MUERTO),
                    agentes[i].getEstado(Estados.PANICO)};
                dtm.addRow(rowData);
            }
        }else if(type==1){
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.PASEANDO),
                    agentes[i].getEstado(Estados.ESPERANDOSANTA), agentes[i].getEstado(Estados.CONVIVIENDO),
                    agentes[i].getEstado(Estados.VIENDOREG), agentes[i].getEstado(Estados.ESCOGIENDOREG),
                    agentes[i].getEstado(Estados.ESPERANDOENVOLTURA), agentes[i].getEstado(Estados.PAGANDO),
                    agentes[i].getEstado(Estados.MUERTO), agentes[i].getEstado(Estados.PANICO)};
                dtm.addRow(rowData);
            }
        }else{
            for (int i = 0; i < totalAg; i++) {
                Object[] rowData = { agentes[i].getName(), agentes[i].getEstado(Estados.DESCANSANDO),
                    agentes[i].getEstado(Estados.SALUDANDO), agentes[i].getEstado(Estados.PLATICANDO),
                    agentes[i].getEstado(Estados.POSANDO), agentes[i].getEstado(Estados.DESPIDIENDOSE),
                    agentes[i].getEstado(Estados.MUERTO),
                    agentes[i].getEstado(Estados.PANICO)};
                dtm.addRow(rowData);
            }
        }
    }
}