package Ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import Agentes.Cliente;
import Agentes.Santa;
import Agentes.Vendedora;

public class Matar extends JFrame {
    private static Vendedora[] vendedoras;
    private static Cliente[] clientes; 
    private static Santa[] santas;  

    public Matar(Vendedora[] v, Cliente[] c, Santa[] s){

        setSize(200, 200);

        vendedoras = v;
        clientes = c;
        santas = s;

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

        killBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoA = (String) tipo.getSelectedItem();
                int i = Integer.parseInt(number.getText());

                switch (tipoA) {
                    case "Vendedora":
                        if (vendedoras != null && i >= 0 && i < vendedoras.length) {
                            vendedoras[i].setDead(true);
                        }
                        break;
                    case "Cliente":
                        if (clientes != null && i >= 0 && i < clientes.length) {
                            clientes[i].setDead(true);
                        }
                        break;
                    case "Santa":
                        if (santas != null && i >= 0 && i < santas.length) {
                            santas[i].setDead(true);
                        }
                        break;
                }
                Tabla.setAllPanic(i);
            }
            
        });

        add(tipo);
        add(number);
        add(killBtn);
        this.setVisible(true);
    }
}
