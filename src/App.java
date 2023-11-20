import javax.swing.SwingUtilities;
import Ventanas.Principal;

public class App {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> new Principal());
    }
}
