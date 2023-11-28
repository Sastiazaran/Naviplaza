package Ventanas;

import javax.swing.*;

import Agentes.Agentes;
import Agentes.Cliente;
import Agentes.Santa;
import Agentes.Vendedora;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Principal extends JFrame {
    private static Vendedora[] vendedoras;
    private static Cliente[] clientes; 
    private static Santa[] santas;  
    private static Thread[] threads;
    private static Agentes[] agentes;

    private static int t;
    private static VVendedoras vVendedoras;
    private static VClientes vClientes;
    private static VSanta vSanta;

    private static int numberV;
    private static int numberC;
    private static int numberS;

    private static Semaphore santaConv;
    private static Semaphore descansoV;
    private static Semaphore comprar;

    private static JTextField textT;
    private JLabel labelTime;

    JTextField textV;
    JTextField textS;
    JTextField textC;

    static int MAXWIDTH = 400;
    static int MAXHEIGHT = 400;

    static boolean started = false;

    private static ArrayList<ImageIcon> vendedoraImages;

    public Principal() {
        // principal
        setTitle("Naviplaza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 300);

        JPanel contenedorPrincipal = new JPanel(new GridLayout(1, 3));

        //Imagen
        vendedoraImages = new ArrayList<ImageIcon>();
        vendedoraImages.add(new ImageIcon("./src/Imagenes/Resized/image1.png"));

        //titulo
        ImageIcon titleIcon = new ImageIcon("./src/Imagenes/naviplazatittle.png");
        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        // botones naviplaza
        JPanel panelBotones = new JPanel(new GridLayout(3, 1));

        // botones con imagen
        JButton btnV = createImageButton("./src/Imagenes/image1.png", 70, 70);
        JButton btnC = createImageButton("./src/Imagenes/image2.png", 50, 50);
        JButton btnS = createImageButton("./src/Imagenes/image3.png", 70, 70);

        btnV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Descansando", "Esperando Cliente", "Mostrando", "Cobrando", "Envolviendo", "Despidiendose", "Muerto", "Panico"};
                createAgentTable(vendedoras, numberV, events, 0);
                numberV = Integer.parseInt(textV.getText());
                JFrame f = new JFrame();
                vVendedoras = new VVendedoras(vendedoraImages);
                f.setSize(500,500);
                f.add(vVendedoras, BorderLayout.CENTER);
                f.show();
                vVendedoras.repaint();
            }
        });

        btnC.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Paseando", "Fila de Santa", "Con Santa", "Viendo Regalos", "Escogiendo Regalos", "Esperando Envoltura", "Pagando", "Muerto", "Panico"};
                createAgentTable(clientes, numberC, events, 1);
                numberC = Integer.parseInt(textC.getText());
                JFrame f = new JFrame();
                vClientes = new VClientes();
                f.setSize(500,500);
                f.add(vClientes, BorderLayout.CENTER);
                f.show();
                vClientes.repaint();
            }
        });

        btnS.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] events = { "Nombre", "Descansando", "Saludando", "Platicando", "Posando", "Despidiendose", "Muerto", "Panico"};
                createAgentTable(santas, numberS, events, 2);
                numberS = Integer.parseInt(textS.getText());
                JFrame f = new JFrame();
                vSanta = new VSanta();
                f.setSize(500,500);
                f.add(vSanta, BorderLayout.CENTER);
                f.show();
                vSanta.repaint();
            }
        });


        panelBotones.add(btnV);
        panelBotones.add(btnC);
        panelBotones.add(btnS);

        // numero threads
        JPanel panelTextFields = new JPanel(new GridLayout(3, 1));
         textV = new JTextField();
         textC = new JTextField();
         textS = new JTextField();

        panelTextFields.add(textV);
        panelTextFields.add(textC);
        panelTextFields.add(textS);

        // inicio
        JPanel panelBotonCentral = new JPanel(new BorderLayout());
        JButton btnInicio = createImageButton("./src/Imagenes/naviplazastart.png", 200, 100);
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


        contenedorPrincipal.add(titleLabel, BorderLayout.NORTH);
        panelBotonCentral.add(btnInicio, BorderLayout.CENTER);

        labelTime = new JLabel("Time in events:");
        panelTextFields.add(labelTime);
        textT = new JTextField();
        panelTextFields.add(textT);

        JPanel tPanel = new JPanel(new GridLayout(2, 2));
        tPanel.add(labelTime);
        tPanel.add(textT);

        contenedorPrincipal.add(panelBotones);
        contenedorPrincipal.add(panelTextFields);
        contenedorPrincipal.add(tPanel);
        contenedorPrincipal.add(panelBotonCentral);
        
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
        Semaphore clienteSS = new Semaphore((int) numberS);
        Semaphore clienteVS = new Semaphore((int) numberV);

        t = Integer.valueOf(textT.getText());

        for(int i = 0; i<numberV; i++){
            Vendedora v = new Vendedora(MAXWIDTH, MAXHEIGHT, descansoV, comprar, t); // Pasar semaphores ()? Dictionary
            v.setName("Vendedora "+String.valueOf(i));
            vendedoras[i] = v;
            agentes[i] = v;
            Thread t = new Thread(v);
            t.setName("Vendedora "+String.valueOf(i));
            threads[i] = t;
            t.start();
        }
        for(int i = 0; i<numberC; i++){
            Cliente c = new Cliente(MAXWIDTH, MAXHEIGHT, clienteSS, clienteVS, t, vendedoras, santas); // PASAR SEMPAHORES
            c.setName("Cliente "+String.valueOf(i));
            clientes[i] = c;
            agentes[i+numberV] = c;
            Thread t = new Thread(c);
            t.setName("Cliente "+String.valueOf(i));
            threads[i+numberV] = t;
            t.start();
        }
        for(int i = 0; i<numberS; i++){
            Santa s = new Santa(MAXWIDTH, MAXHEIGHT, santaConv, t); // SEMAPHore
            s.setName("Santa "+String.valueOf(i));
            santas[i] = s;
            agentes[i+numberV+numberC] = s;
            Thread t = new Thread(s);
            t.setName("Santa "+String.valueOf(i));
            threads[i+numberV+numberC] = t;
            t.start();
        }
        
        new Matar(vendedoras, clientes, santas);

        SwingUtilities.invokeLater(() -> {
            new Tabla(agentes, total);
        });

        started = true;
    }

    private void createAgentTable(Agentes[] a, int n, String[] columnNames, int t){
        if(started){
            SwingUtilities.invokeLater(() -> {
                new TablaAgente(a, n, columnNames, t);
            });
        }
    }

    private JButton createImageButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setContentAreaFilled(false);
        return button;
    }

    public static int getNumberV() {
        return numberV;
    }

    public static int getNumberS() {
        return numberS;
    }

    public static int getNumberC() {
        return numberC;
    }
}
