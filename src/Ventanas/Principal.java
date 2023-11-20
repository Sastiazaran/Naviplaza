package Ventanas;

import javax.swing.*;
import java.awt.*;

public class Principal extends JFrame {

    public Principal() {
        // principal
        setTitle("Naviplaza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        JPanel contenedorPrincipal = new JPanel(new GridLayout(1, 3));

        // botones naviplaza
        JPanel panelBotones = new JPanel(new GridLayout(3, 1));

        // botones con imagen
        panelBotones.add(createImageButton("./src/Imagenes/image1.png", 70, 70));
        panelBotones.add(createImageButton("./src/Imagenes/image2.png", 50, 50));
        panelBotones.add(createImageButton("./src/Imagenes/image3.png", 70, 70));

        // numero threads
        JPanel panelTextFields = new JPanel(new GridLayout(3, 1));
        panelTextFields.add(new JTextField());
        panelTextFields.add(new JTextField());
        panelTextFields.add(new JTextField());

        // inicio
        JPanel panelBotonCentral = new JPanel(new BorderLayout());
        panelBotonCentral.add(new JButton("Crear"), BorderLayout.CENTER);

        contenedorPrincipal.add(panelBotones);
        contenedorPrincipal.add(panelTextFields);
        contenedorPrincipal.add(panelBotonCentral);
        add(contenedorPrincipal);

        setVisible(true);
    }

    private JButton createImageButton(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        //button.setBorderPainted(false);
        //button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }
}
