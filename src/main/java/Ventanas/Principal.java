package Ventanas;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.concurrent.Semaphore;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Agentes.Agentes;
import Agentes.Cliente;
import Agentes.Santa;
import Agentes.Vendedora;
import util.Inputs;
import util.Resources;

public class Principal extends JFrame {
    private static Vendedora[] vendedoras = new Vendedora[0];
    private static Cliente[] clientes = new Cliente[0];
    private static Santa[] santas = new Santa[0];
    private static Thread[] threads;
    private static Agentes[] agentes;

    private static int t;
    private static int numberV;
    private static int numberC;
    private static int numberS;

    static final int MAXWIDTH = 400;
    static final int MAXHEIGHT = 400;

    private static boolean started = false;

    private final JTextField textV;
    private final JTextField textC;
    private final JTextField textS;
    private final JTextField textT;
    private final JButton btnInicio;

    public Principal() {
        setTitle("Naviplaza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel(Resources.icon("naviplazatittle.png"), SwingConstants.CENTER);

        textV = new JTextField("5", 6);
        textC = new JTextField("10", 6);
        textS = new JTextField("2", 6);
        textT = new JTextField("500", 6);

        JButton btnV = createImageButton("image1.png", 70, 70);
        JButton btnC = createImageButton("image2.png", 50, 50);
        JButton btnS = createImageButton("image3.png", 70, 70);
        btnV.setToolTipText("Open shopkeeper view");
        btnC.setToolTipText("Open client view");
        btnS.setToolTipText("Open Santa view");

        btnV.addActionListener(e -> openVendedoras());
        btnC.addActionListener(e -> openClientes());
        btnS.addActionListener(e -> openSantas());

        btnInicio = createImageButton("naviplazastart.png", 200, 100);
        btnInicio.addActionListener(e -> startSimulation());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.anchor = GridBagConstraints.WEST;

        addFormRow(form, gc, 0, "Vendedoras", textV, btnV);
        addFormRow(form, gc, 1, "Clientes", textC, btnC);
        addFormRow(form, gc, 2, "Santas", textS, btnS);

        gc.gridx = 0;
        gc.gridy = 3;
        form.add(new JLabel("Event time (ms):"), gc);
        gc.gridx = 1;
        form.add(textT, gc);

        JPanel startPanel = new JPanel();
        startPanel.add(btnInicio);

        setLayout(new BorderLayout(8, 8));
        add(titleLabel, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(startPanel, BorderLayout.SOUTH);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static void addFormRow(JPanel form, GridBagConstraints gc, int row, String label, JTextField field,
            JButton button) {
        gc.gridy = row;
        gc.gridx = 0;
        form.add(new JLabel(label + ":"), gc);
        gc.gridx = 1;
        form.add(field, gc);
        gc.gridx = 2;
        form.add(button, gc);
    }

    private void startSimulation() {
        try {
            numberV = Inputs.parseNonNegativeInt(textV.getText(), "Vendedoras");
            numberC = Inputs.parseNonNegativeInt(textC.getText(), "Clientes");
            numberS = Inputs.parseNonNegativeInt(textS.getText(), "Santas");
            t = Inputs.parsePositiveInt(textT.getText(), "Event time");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (numberV + numberC + numberS == 0) {
            JOptionPane.showMessageDialog(this, "Start at least one agent.", "Invalid input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (started) {
            return;
        }
        btnInicio.setEnabled(false);
        textV.setEnabled(false);
        textC.setEnabled(false);
        textS.setEnabled(false);
        textT.setEnabled(false);
        createAndStartThreads();
    }

    private void openVendedoras() {
        if (!ensureStarted()) {
            return;
        }
        String[] events = { "Nombre", "Descansando", "Esperando Cliente", "Mostrando", "Cobrando", "Envolviendo",
                "Despidiendose", "Muerto", "Panico" };
        createAgentTable(vendedoras, numberV, events, 0);
        openAgentFrame("Vendedoras", new VVendedoras(vendedoras));
    }

    private void openClientes() {
        if (!ensureStarted()) {
            return;
        }
        String[] events = { "Nombre", "Paseando", "Fila de Santa", "Con Santa", "Viendo Regalos", "Escogiendo Regalos",
                "Esperando Envoltura", "Pagando", "Muerto", "Panico" };
        createAgentTable(clientes, numberC, events, 1);
        openAgentFrame("Clientes", new VClientes(clientes));
    }

    private void openSantas() {
        if (!ensureStarted()) {
            return;
        }
        String[] events = { "Nombre", "Descansando", "Saludando", "Platicando", "Posando", "Despidiendose", "Muerto",
                "Panico" };
        createAgentTable(santas, numberS, events, 2);
        openAgentFrame("Santas", new VSanta(santas));
    }

    private boolean ensureStarted() {
        if (started) {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Start the simulation first.", "Naviplaza",
                JOptionPane.INFORMATION_MESSAGE);
        return false;
    }

    private static void openAgentFrame(String title, JPanel view) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(520, 520);
        frame.add(view, BorderLayout.CENTER);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void createAndStartThreads() {
        vendedoras = new Vendedora[numberV];
        clientes = new Cliente[numberC];
        santas = new Santa[numberS];

        int total = numberV + numberC + numberS;
        threads = new Thread[total];
        agentes = new Agentes[total];

        Semaphore descansoV = new Semaphore(numberV / 10 + 1);
        Semaphore santaConv = new Semaphore(Math.max(1, numberS));
        Semaphore comprar = new Semaphore(Math.max(1, numberV));
        Semaphore clienteSS = new Semaphore(Math.max(1, numberS));
        Semaphore clienteVS = new Semaphore(Math.max(1, numberV));

        for (int i = 0; i < numberV; i++) {
            Vendedora v = new Vendedora(MAXWIDTH, MAXHEIGHT, descansoV, comprar, t);
            v.setName("Vendedora " + i);
            vendedoras[i] = v;
            agentes[i] = v;
            Thread thread = new Thread(v, "Vendedora " + i);
            threads[i] = thread;
            thread.start();
        }
        for (int i = 0; i < numberC; i++) {
            Cliente c = new Cliente(MAXWIDTH, MAXHEIGHT, clienteSS, clienteVS, t, vendedoras, santas);
            c.setName("Cliente " + i);
            clientes[i] = c;
            agentes[i + numberV] = c;
            Thread thread = new Thread(c, "Cliente " + i);
            threads[i + numberV] = thread;
            thread.start();
        }
        for (int i = 0; i < numberS; i++) {
            Santa s = new Santa(MAXWIDTH, MAXHEIGHT, santaConv, t);
            s.setName("Santa " + i);
            santas[i] = s;
            agentes[i + numberV + numberC] = s;
            Thread thread = new Thread(s, "Santa " + i);
            threads[i + numberV + numberC] = thread;
            thread.start();
        }

        new Matar(vendedoras, clientes, santas);
        new Tabla(agentes, total);
        started = true;
    }

    private void createAgentTable(Agentes[] a, int n, String[] columnNames, int type) {
        if (started && a != null && n > 0) {
            new TablaAgente(a, n, columnNames, type);
        }
    }

    private JButton createImageButton(String resourcePath, int width, int height) {
        Image scaled = Resources.scaled(resourcePath, width, height);
        JButton button = new JButton(new ImageIcon(scaled));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
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
