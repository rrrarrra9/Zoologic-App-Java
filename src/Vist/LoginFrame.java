package Vist;

import Model.Usuario;
import Controller.UserController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Ventana de inicio de sesión dividida en dos mitades: izquierda (fondo verde) y derecha (formulario).
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("ZooLogic - Sistema de Gestión de Zoológico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(createLeftPanel());
        mainPanel.add(createRightPanel());
        add(mainPanel);
    }

    // Panel izquierdo: fondo verde con título y subtítulo
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Theme.PRIMARY_DARK);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(new EmptyBorder(60, 50, 50, 50));

        JLabel titleLabel = new JLabel("ZooLogic");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 52));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("<html>Sistema de Gestión<br>de Zoológico</html>");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        subtitleLabel.setForeground(new Color(200, 230, 200));

        JLabel taglineLabel = new JLabel("La mejor herramienta para administrar tu zoológico");
        taglineLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taglineLabel.setForeground(new Color(180, 220, 180));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(15));
        textPanel.add(subtitleLabel);
        textPanel.add(Box.createVerticalStrut(20));
        textPanel.add(taglineLabel);

        panel.add(textPanel, BorderLayout.NORTH);
        return panel;
    }

    // Panel derecho: formulario de login centrado
    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Theme.BACKGROUND);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Theme.SURFACE);
        formPanel.setBorder(new EmptyBorder(50, 60, 50, 60));

        JLabel welcomeLabel = new JLabel("¡Bienvenido!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeLabel.setForeground(Theme.TEXT_PRIMARY);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Inicia sesión para continuar");
        instructionLabel.setFont(Theme.BODY_FONT);
        instructionLabel.setForeground(Theme.TEXT_SECONDARY);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("Correo electrónico");
        emailLabel.setFont(Theme.HEADER_FONT);
        emailLabel.setForeground(Theme.TEXT_PRIMARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField = Theme.createTextField();
        emailField.setMaximumSize(new Dimension(400, 50));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Contraseña");
        passwordLabel.setFont(Theme.HEADER_FONT);
        passwordLabel.setForeground(Theme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        passwordField = Theme.createPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton loginButton = Theme.createPrimaryButton("Iniciar Sesión");
        loginButton.setMaximumSize(new Dimension(400, 50));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.addActionListener(e -> handleLogin());

        // Permite hacer login con Enter
        getRootPane().setDefaultButton(loginButton);

        // Botón de registro
        JButton registerButton = Theme.createPrimaryButton("Registrarse");
        registerButton.setMaximumSize(new Dimension(400, 50));
        registerButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        registerButton.addActionListener(e -> showRegisterDialog());

        JLabel versionLabel = new JLabel("v1.0.0");
        versionLabel.setFont(Theme.SMALL_FONT);
        versionLabel.setForeground(Theme.TEXT_SECONDARY);
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(welcomeLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(instructionLabel);
        formPanel.add(Box.createVerticalStrut(40));
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(35));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalStrut(15));
        // Botón de registro justo debajo del login
        formPanel.add(registerButton);
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(new JSeparator());
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(versionLabel);

        rightPanel.add(formPanel);
        return rightPanel;
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario user = UserController.authenticate(email, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DashboardFrame dashboard = new DashboardFrame(user);
        dashboard.setVisible(true);
        this.dispose();
    }

    private void showRegisterDialog() {
        JDialog dialog = new JDialog(this, "Registro de Usuario", true);
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));
        mainPanel.setBackground(Theme.SURFACE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        JTextField nombreField = Theme.createTextField();
        JTextField apellidoField = Theme.createTextField();
        JTextField emailField = Theme.createTextField();
        JPasswordField passwordField = Theme.createPasswordField();
        JComboBox<String> rolCombo = Theme.createComboBox(new String[]{"CLIENTE", "TRABAJADOR", "ADMIN"});

        // Helper to add label+field
        java.util.function.BiConsumer<String, JComponent> addFormField = (labelText, field) -> {
            JLabel label = new JLabel(labelText);
            label.setFont(Theme.HEADER_FONT);
            label.setForeground(Theme.TEXT_PRIMARY);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            formPanel.add(label);
            formPanel.add(Box.createVerticalStrut(8));
            formPanel.add(field);
            formPanel.add(Box.createVerticalStrut(15));
        };

        addFormField.accept("Nombre *", nombreField);
        addFormField.accept("Apellido *", apellidoField);
        addFormField.accept("Email *", emailField);
        addFormField.accept("Contraseña *", passwordField);
        addFormField.accept("Rol *", rolCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton cancelButton = Theme.createSecondaryButton("Cancelar");
        cancelButton.addActionListener(e -> dialog.dispose());

        JButton saveButton = Theme.createPrimaryButton("Registrar");
        saveButton.addActionListener(e -> {
            if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(dialog, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String email = emailField.getText();
            for (Usuario u : UserController.getAllUsers()) {
                if (u.getEmail().equals(email)) {
                    JOptionPane.showMessageDialog(dialog, "El email ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // Id will be auto-generated by the database
            Usuario newUser = new Usuario(0, nombreField.getText(), apellidoField.getText(), email, new String(passwordField.getPassword()), (String) rolCombo.getSelectedItem());
            UserController.registerUser(newUser);
            JOptionPane.showMessageDialog(dialog, "Usuario registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }
}
