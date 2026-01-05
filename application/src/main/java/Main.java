import javafx.application.Application;
import org.views.LoginUI;

/**
 * Main class of the XSON application.
 *
 * This class starts the JavaFX application and opens the login window.
 *
 * @author Aouad Abdelkarim
 */
public class Main {

    /**
     * The main method that launches the application.
     *
     * @param args command line arguments (optional)
     */
    public static void main(String[] args) {
        Application.launch(LoginUI.class, args);
    }

}