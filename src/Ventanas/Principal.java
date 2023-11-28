package Ventanas;

import javax.swing.*;

import Agentes.Agentes;
import Agentes.Cliente;
import Agentes.Santa;
import Agentes.Vendedora;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

public class Principal extends JFrame {
    private static Vendedora[] vendedoras;
    private static Cliente[] clientes;
    private static Santa[] santas;
    private static Thread[] threads;
    private static Agentes[] agentes;

    private static int t;
    private static int numberV;
    private static int numberC;
    private static int numberS;

    private static Semaphore santaConv;
    private static Semaphore descansoV;
    private static Semaphore comprar;

    private static JTextField textT;
    private JLabel labelTime;

    static int MAXWIDTH = 600;
    static int MAXHEIGHT = 400;

    static boolean started = false;

    public Principal() {
        // principal
        setTitle("Naviplaza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(MAXWIDTH, MAXHEIGHT);

        JPanel contenedorPrincipal = new JPanel(new GridLayout(2, 3));

        // botones naviplaza
        JPanel panelBotones = new JPanel(new GridLayout(3, 1));

        // botones con imagen
        JButton btnV = createImageButton("./src/Imagenes/image1.png", 70, 70);
        JButton btnC = createImageButton("./src/Imagenes/image2.png", 50, 50);
        JButton btnS = createImageButton("./src/Imagenes/image3.png", 70, 70);

        btnV.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Descansando", "Esperando Cliente", "Mostrando", "Cobrando",
                        "Envolviendo", "Despidiendose", "Muerto", "Panico" };
                createAgentTable(vendedoras, numberV, events, 0);
            }
        });

        btnC.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Paseando", "Fila de Santa", "Con Santa", "Viendo Regalos",
                        "Escogiendo Regalos", "Esperando Envoltura", "Pagando", "Muerto", "Panico" };
                createAgentTable(clientes, numberC, events, 1);
            }
        });

        btnS.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Descansando", "Saludando", "Platicando", "Posando", "Despidiendose",
                        "Muerto", "Panico" };
                createAgentTable(santas, numberS, events, 2);
            }
        });

        panelBotones.add(btnV);
        panelBotones.add(btnC);
        panelBotones.add(btnS);

        // numero threads
        JPanel panelTextFields = new JPanel(new GridLayout(3, 1));
        JTextField textV = new JTextField();
        JTextField textC = new JTextField();
        JTextField textS = new JTextField();

        panelTextFields.add(textV);
        panelTextFields.add(textC);
        panelTextFields.add(textS);

        // inicio
        JPanel panelBotonCentral = new JPanel(new BorderLayout());
        JButton btnInicio = new JButton("Crear");
        btnInicio.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberV = Integer.valueOf(textV.getText());
                numberC = Integer.valueOf(textC.getText());
                numberS = Integer.valueOf(textS.getText());
                btnInicio.setEnabled(false);
                createAndStartThreads();
            }
        });

        panelBotonCentral.add(btnInicio, BorderLayout.CENTER);

        labelTime = new JLabel("Time in events:");
        panelTextFields.add(labelTime);
        textT = new JTextField();
        panelTextFields.add(textT);

        JPanel tPanel = new JPanel(new GridLayout(1, 2));
        tPanel.add(labelTime);
        tPanel.add(textT);

        contenedorPrincipal.add(panelBotones);
        contenedorPrincipal.add(panelTextFields);
        contenedorPrincipal.add(panelBotonCentral);
        contenedorPrincipal.add(tPanel);
        add(contenedorPrincipal);

        setVisible(true);
    }

    public static void createAndStartThreads() {
        vendedoras = new Vendedora[numberV];
        clientes = new Cliente[numberC];
        santas = new Santa[numberS];

        int total = numberV + numberC + numberS;
        threads = new Thread[total];
        agentes = new Agentes[total];

        descansoV = new Semaphore((int) numberV / 10 + 1);
        santaConv = new Semaphore((int) numberS);
        comprar = new Semaphore((int) numberV);

        t = Integer.valueOf(textT.getText());

        for (int i = 0; i < numberV; i++) {
            Vendedora v = new Vendedora(MAXWIDTH, MAXHEIGHT, descansoV, comprar, t); // Pasar semaphores ()? Dictionary
            v.setName("Vendedora " + String.valueOf(i));
            vendedoras[i] = v;
            agentes[i] = v;
            Thread t = new Thread(v);
            t.setName("Vendedora " + String.valueOf(i));
            threads[i] = t;
            t.start();
        }
        for (int i = 0; i < numberC; i++) {
            Cliente c = new Cliente(MAXWIDTH, MAXHEIGHT, santaConv, comprar, t); // PASAR SEMPAHORES
            c.setName("Cliente " + String.valueOf(i));
            clientes[i] = c;
            agentes[i + numberV] = c;
            Thread t = new Thread(c);
            t.setName("Cliente " + String.valueOf(i));
            threads[i + numberV] = t;
            t.start();
        }
        for (int i = 0; i < numberS; i++) {
            Santa s = new Santa(MAXWIDTH, MAXHEIGHT, santaConv, t); // SEMAPHore
            s.setName("Santa " + String.valueOf(i));
            santas[i] = s;
            agentes[i + numberV + numberC] = s;
            Thread t = new Thread(s);
            t.setName("Santa " + String.valueOf(i));
            threads[i + numberV + numberC] = t;
            t.start();
        }

        new Matar(vendedoras, clientes, santas);

        SwingUtilities.invokeLater(() -> {
            new Tabla(agentes, total);
        });

        started = true;
    }

    private void createAgentTable(Agentes[] a, int n, String[] columnNames, int t) {
        if (started) {
            SwingUtilities.invokeLater(() -> {
                new TablaAgente(a, n, columnNames, t);
            });
        }
    }

    private JButton createImageButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        // button.setBorderPainted(false);
        // button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}
