package Ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Agentes.Cliente;
import Agentes.Santa;
import Agentes.Vendedora;
import util.Inputs;

public class Matar extends JFrame {
    private final Vendedora[] vendedoras;
    private final Cliente[] clientes;
    private final Santa[] santas;

    public Matar(Vendedora[] v, Cliente[] c, Santa[] s) {
        vendedoras = v;
        clientes = c;
        santas = s;

        setTitle("Kill agent");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(280, 200);

        JComboBox<String> tipo = new JComboBox<>();
        JTextField number = new JTextField();
        JButton killBtn = new JButton("Kill");

        if (vendedoras != null && vendedoras.length > 0) {
            tipo.addItem("Vendedora");
        }
        if (clientes != null && clientes.length > 0) {
            tipo.addItem("Cliente");
        }
        if (santas != null && santas.length > 0) {
            tipo.addItem("Santa");
        }

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(new JLabel("Type"));
        add(tipo);
        add(new JLabel("Index (0-based)"));
        add(number);
        add(killBtn);

        killBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoA = (String) tipo.getSelectedItem();
                if (tipoA == null) {
                    return;
                }
                int i;
                try {
                    i = Inputs.parseNonNegativeInt(number.getText(), "Index");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(Matar.this, ex.getMessage(), "Invalid index",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int globalIndex = -1;
                switch (tipoA) {
                    case "Vendedora":
                        if (vendedoras != null && i < vendedoras.length) {
                            vendedoras[i].setDead(true);
                            globalIndex = i;
                        }
                        break;
                    case "Cliente":
                        if (clientes != null && i < clientes.length) {
                            clientes[i].setDead(true);
                            globalIndex = length(vendedoras) + i;
                        }
                        break;
                    case "Santa":
                        if (santas != null && i < santas.length) {
                            santas[i].setDead(true);
                            globalIndex = length(vendedoras) + length(clientes) + i;
                        }
                        break;
                    default:
                        break;
                }
                if (globalIndex < 0) {
                    JOptionPane.showMessageDialog(Matar.this, "Index is out of range for " + tipoA, "Invalid index",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Tabla.setAllPanic(globalIndex);
            }
        });

        setLocation(80, 80);
        setVisible(true);
        toFront();
        requestFocus();
    }

    private static int length(Object[] array) {
        return array == null ? 0 : array.length;
    }
}
