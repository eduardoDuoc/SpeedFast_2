package vista;

import javax.swing.*;

import controlador.ControladorPedidos;

public class VentanaPrincipal extends JFrame {
    private JButton registrarPedidoButton;
    private JButton salirButton;
    private JButton listarPedidoButton;
    private JButton asignarRepartidorIniciarEntregaButton;
    private JPanel panelPrincipal;

    private ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Menú Principal");
        setContentPane(panelPrincipal);
        setSize(750, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registrarPedidoButton.addActionListener(e -> {

            new VentanaRegistroPedido(controlador);

        });

        listarPedidoButton.addActionListener(e -> {
            new VentanaListaPedidos(controlador);
        });



        setVisible(true);

    }
}
