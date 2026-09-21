package vista;

import javax.swing.*;

import controlador.ControladorPedidos;

public class VentanaPrincipal extends JFrame {
    private JButton registrarPedidoButton;
    private JButton salirButton;
    private JButton listarPedidoButton;
    private JButton asignarRepartidorButton;
    private JPanel panelPrincipal;
    private JButton iniciarEntregaButton1;

    private ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Menú Principal");
        setContentPane(panelPrincipal);
        setSize(750, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registrarPedidoButton.addActionListener(e -> {

            new VentanaRegistroPedido(controlador);

        });

        listarPedidoButton.addActionListener(e -> {
            new VentanaListaPedidos(controlador);
        });

        asignarRepartidorButton.addActionListener(e -> {

            int cantidad = controlador.asignarPedidos();

            if (cantidad > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Se enviaron " + cantidad + " pedidos a la zona de carga."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No hay nuevos pedidos para asignar."
                );

            }

        });

        iniciarEntregaButton1.addActionListener(e -> {

            boolean iniciado = controlador.iniciarEntregas();

            if (iniciado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Se ha iniciado la simulación de entregas."
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe una simulación de entregas en curso."
                );

            }

        });

        salirButton.addActionListener(e -> {

            System.exit(0);

        });



        setVisible(true);

    }
}
