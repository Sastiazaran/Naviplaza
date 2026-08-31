import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.Resources;

public class Presentacion extends JFrame {

    public Presentacion() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Presentacion");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        ImageIcon banner = Resources.icon("up.jpg");
        JLabel imageLabel = new JLabel(banner);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));

        String[] lines = {
            "Alumnos: Celia Lucia Castañeda Arizaga (0237098)",
            "Jessica Fernanda Isunza Lopez (0220547)",
            "Sebastian Astiazaran Lopez (0226403)",
            "Materia: FUNDAMENTOS DE PROGRAMACION EN PARALELO",
            "Facilitador: Dr. Juan Carlos Lopez Pimentel"
        };

        for (String line : lines) {
            JLabel label = new JLabel(line, SwingConstants.CENTER);
            label.setAlignmentX(CENTER_ALIGNMENT);
            label.setFont(label.getFont().deriveFont(Font.PLAIN, 13f));
            infoPanel.add(label);
        }

        add(infoPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
