import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

public class presentacion extends JFrame {

    public presentacion() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Presentacion");
        setSize(600, 400); 
        setLayout(new BorderLayout());

        try {
            BufferedImage image = ImageIO.read(new File("./src/Imagenes/up.jpg"));
            if (image != null) {
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel imagePanel = new JPanel(new BorderLayout());
                imagePanel.add(imageLabel, BorderLayout.CENTER);
                add(imagePanel, BorderLayout.CENTER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel[] infoLabels = {
            new JLabel("Alumnos: Celia Lucia Castañeda Arizaga (0237098)"),
            new JLabel("Jessica Fernanda Isunza Lopez (0220547)"),
            new JLabel("Sebastian Astiazaran Lopez (0226403)"),
            new JLabel("Materia: FUNDAMENTOS DE PROGRAMACION EN PARALELO"),
            new JLabel("Facilitador: Dr. Juan Carlos Lopez Pimentel")
        };

        for (JLabel label : infoLabels) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
            infoPanel.add(label);
        }

        add(infoPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

}








