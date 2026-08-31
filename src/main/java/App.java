import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Ventanas.Principal;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Keep the default look if the platform LAF is unavailable.
        }
        SwingUtilities.invokeLater(() -> {
            new Principal();
            new Presentacion();
        });
    }
}
