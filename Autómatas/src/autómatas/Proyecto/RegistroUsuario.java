package Autómatas.Proyecto;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistroUsuario extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoContrasena;
    private JPasswordField campoConfirmacion;
    private JButton botonRegistrar;
    private JButton botonVolver;

    public RegistroUsuario() {
        setTitle("Registro de Usuario");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10)); // Aumentado para incluir botón "Volver"

        campoUsuario = new JTextField();
        campoContrasena = new JPasswordField();
        campoConfirmacion = new JPasswordField();
        botonRegistrar = new JButton("Registrar");
        botonVolver = new JButton("Volver al Menú");

        add(new JLabel("Usuario:"));
        add(campoUsuario);
        add(new JLabel("Contraseña:"));
        add(campoContrasena);
        add(new JLabel("Confirmar Contraseña:"));
        add(campoConfirmacion);
        add(botonRegistrar);
        add(botonVolver);

        botonRegistrar.addActionListener(e -> registrarUsuario());
        botonVolver.addActionListener(e -> {
            new VentanaPrincipal();
            dispose();
        });

        setVisible(true);
    }

    private void registrarUsuario() {
        String usuario = campoUsuario.getText();
        String contrasena = new String(campoContrasena.getPassword());
        String confirmacion = new String(campoConfirmacion.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty() || confirmacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        if (!contrasena.equals(confirmacion)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            campoContrasena.setText("");
            campoConfirmacion.setText("");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/sistema_login", "root", "07012005");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO usuarios (usuario, contrasena) VALUES (?, ?)")) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            new VentanaPrincipal();
            dispose();

        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(this, "El usuario ya existe.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario: " + ex.getMessage());
        }
    }
}