package Autómatas.Proyecto;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginUsuario extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JButton botonIngresar;
    private JButton botonVolver;
    private int intentosFallidos = 0;
    private final int MAX_INTENTOS = 3;

    public LoginUsuario() {
        setTitle("Inicio de Sesión");
        setSize(350, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10)); // Aumentado para incluir botón "Volver"

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();
        botonIngresar = new JButton("Ingresar");
        botonVolver = new JButton("Volver al Menú");

        add(new JLabel("Usuario:"));
        add(campoUsuario);
        add(new JLabel("Contraseña:"));
        add(campoContrasena);
        add(botonIngresar);
        add(botonVolver);

        botonIngresar.addActionListener(e -> validarCredenciales());
        botonVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }

    private void validarCredenciales() {
        String usuario = campoUsuario.getText();
        String contrasena = new String(campoContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos vacíos.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sistema_login", "root", "07012005");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?")) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "✅ Acceso concedido. ¡Bienvenido, " + usuario + "!");
                dispose();
                // Aquí podrías abrir una nueva ventana tipo "PanelUsuario"
            } else {
                intentosFallidos++;
                JOptionPane.showMessageDialog(this, "❌ Credenciales incorrectas. Intentos: " + intentosFallidos);
                campoContrasena.setText("");

                if (intentosFallidos >= MAX_INTENTOS) {
                    JOptionPane.showMessageDialog(this, "🚫 Acceso bloqueado. Excediste el número de intentos.");
                    System.exit(0);
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }
}