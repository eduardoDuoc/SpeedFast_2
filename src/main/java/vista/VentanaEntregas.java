package vista;

import dao.EntregaDAO;
import dao.impl.EntregaDAOImpl;
import modelo.Entrega;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaEntregas extends JFrame {

    private JPanel panelPrincipal;
    private JTable tablaEntregas;

    private DefaultTableModel modeloTabla;

    public VentanaEntregas() {

        setTitle("SpeedFast - Entregas");
        setContentPane(panelPrincipal);
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID Entrega");
        modeloTabla.addColumn("ID Pedido");
        modeloTabla.addColumn("ID Repartidor");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");

        tablaEntregas.setModel(modeloTabla);

        cargarEntregas();

        setVisible(true);
    }

    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        EntregaDAO dao =
                new EntregaDAOImpl();

        for (Entrega entrega : dao.readAll()) {

            modeloTabla.addRow(new Object[]{

                    entrega.getIdEntrega(),
                    entrega.getIdPedido(),
                    entrega.getIdRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()

            });
        }
    }
}