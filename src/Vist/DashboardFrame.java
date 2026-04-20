package Vist;

import Model.Usuario;
import javax.swing.*;


public class DashboardFrame extends JFrame {
    public DashboardFrame(Usuario user) {
        setTitle("Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcome = new JLabel("Bienvenido, " + user.getNombreCompleto() + " (" + user.getRol() + ")");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcome);
    }
}
