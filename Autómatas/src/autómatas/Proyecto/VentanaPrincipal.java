package Autómatas.Proyecto;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Sistema de Login - Menú Principal");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel titulo = new JLabel("¿Qué deseas hacer?", SwingConstants.CENTER);
        JButton botonRegistro = new JButton("Registrarse");
        JButton botonLogin = new JButton("Iniciar Sesión");

        botonRegistro.addActionListener(e -> {
            new RegistroUsuario();
            dispose();
        });

        botonLogin.addActionListener(e -> {
            new LoginUsuario();
            dispose();
        });

        add(titulo);
        add(botonRegistro);
        add(botonLogin);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}