import Vist.LoginFrame;

import javax.swing.*;

// Clase principal que arranca la aplicación
public class Main {
    public static void main(String[] args) {
        // Aplicamos el Look and Feel por defecto de Java (igual en todos los sistemas operativos)
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true); // hace visible la ventana de login
        });

    }
}