package vista;

import javax.swing.*;
import controlador.ControladorPedidos;
import modelo.Pedido;
import javax.swing.table.DefaultTableModel;


public class VentanaListaPedidos extends JFrame {
    private JPanel panel1;
    private JTable table1;
    private JButton button1Actualizar;
    private JButton button2Limpiar;
    private DefaultTableModel modeloTabla;

    private ControladorPedidos controlador;

    public VentanaListaPedidos(ControladorPedidos controlador) {

        this.controlador = controlador;

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID Pedido");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado");

        table1.setModel(modeloTabla);

        setTitle("SpeedFast - Lista de pedidos");
        setContentPane(panel1);
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cargarPedidos();

        button1Actualizar.addActionListener(e -> {

            cargarPedidos();

        });

        button2Limpiar.addActionListener(e -> {

            modeloTabla.setRowCount(0);

        });

        setVisible(true);
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controlador.obtenerPedidos()) {

            modeloTabla.addRow(new Object[]{

                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstadoPedido()

            });

        }
    }
}
