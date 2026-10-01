package vista;

import javax.swing.*;
import modelo.Pedido;
import modelo.Repartidor;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import dao.impl.PedidoDAOImpl;
import dao.impl.RepartidorDAOImpl;
import controlador.ControladorPedidos;

public class VentanaPrincipal extends JFrame {
    private JButton registrarPedidoButton;
    private JButton salirButton;
    private JButton listarPedidoButton;
    private JButton asignarRepartidorButton;
    private JPanel panelPrincipal;
    private JButton iniciarEntregaButton1;
    private JButton registrarRepartidorButton;
    private JComboBox comboPedido;
    private JComboBox comboRepartidor;
    private JButton entregasButton;

    private ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {


        this.controlador = controlador;

        setTitle("SpeedFast - Menú Principal");
        setContentPane(panelPrincipal);
        setSize(1200, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        registrarPedidoButton.addActionListener(e -> {

            new VentanaRegistroPedido(controlador);

        });

        listarPedidoButton.addActionListener(e -> {
            new VentanaListaPedidos(controlador);
        });

        asignarRepartidorButton.addActionListener(e -> {

            int cantidad = controlador.asignarPedidos();

            if (cantidad == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error al recuperar los pedidos desde MySQL.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

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

            if (!controlador.tienePedidosAsignados()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No hay pedidos asignados. "
                                + "Debes asignar los pedidos antes "
                                + "de iniciar la entrega."
                );
                return;
            }

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


        registrarRepartidorButton.addActionListener(e -> {

            new VentanaRegistroRepartidor();

        });

        salirButton.addActionListener(e -> {

            System.exit(0);

        });

        entregasButton.addActionListener(e -> {

            new VentanaEntregas();

        });

        cargarPedidosCombo();
        cargarRepartidoresCombo();

        setVisible(true);

    }

    private void cargarPedidosCombo() {

        comboPedido.removeAllItems();

        PedidoDAO pedidoDAO = new PedidoDAOImpl();

        for (Pedido pedido : pedidoDAO.readAll()) {

            comboPedido.addItem(pedido);
        }
    }

    private void cargarRepartidoresCombo() {

        comboRepartidor.removeAllItems();

        RepartidorDAO repartidorDAO =
                new RepartidorDAOImpl();

        for (Repartidor repartidor :
                repartidorDAO.readAll()) {

            comboRepartidor.addItem(repartidor);
        }
    }
}
