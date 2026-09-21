package main;

import vista.VentanaPrincipal;
import controlador.ControladorPedidos;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ControladorPedidos controlador = new ControladorPedidos();
            new VentanaPrincipal(controlador);
        });

    }
}