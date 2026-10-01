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
    private JButton btnEditar;
    private JButton btnEliminar;
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

        btnEditar.addActionListener(e -> {

            int filaSeleccionada = table1.getSelectedRow();

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido para editar."
                );

                return;
            }

            int idPedido = Integer.parseInt(
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0
                    ).toString()
            );

            String direccionActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            1
                    ).toString();

            String tipoActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            2
                    ).toString();

            String estadoActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            3
                    ).toString();

            String nuevaDireccion =
                    JOptionPane.showInputDialog(
                            this,
                            "Nueva dirección:",
                            direccionActual
                    );

            if (nuevaDireccion == null
                    || nuevaDireccion.trim().isEmpty()) {

                return;
            }

            String nuevoTipo =
                    (String) JOptionPane.showInputDialog(
                            this,
                            "Seleccione el tipo:",
                            "Editar Pedido",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{
                                    "COMIDA",
                                    "ENCOMIENDA",
                                    "EXPRESS"
                            },
                            tipoActual
                    );

            if (nuevoTipo == null) {
                return;
            }

            String nuevoEstado =
                    (String) JOptionPane.showInputDialog(
                            this,
                            "Seleccione el estado:",
                            "Editar Pedido",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new String[]{
                                    "PENDIENTE",
                                    "EN_REPARTO",
                                    "ENTREGADO"
                            },
                            estadoActual
                    );

            if (nuevoEstado == null) {
                return;
            }

            Pedido pedido = new Pedido(
                    idPedido,
                    nuevaDireccion.trim(),
                    nuevoTipo
            );

            pedido.setEstado(
                    modelo.EstadoPedido.valueOf(
                            nuevoEstado
                    )
            );

            controlador.actualizarPedido(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
            );

            cargarPedidos();
        });

        btnEliminar.addActionListener(e -> {

            int filaSeleccionada = table1.getSelectedRow();

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un pedido para eliminar."
                );

                return;
            }

            int idPedido = Integer.parseInt(
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0
                    ).toString()
            );

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar el pedido #"
                            + idPedido + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            boolean eliminado =
                    controlador.eliminarPedido(idPedido);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido eliminado correctamente."
                );

                cargarPedidos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se puede eliminar el pedido porque tiene una entrega asociada."
                );
            }
        });


        setVisible(true);
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : controlador.consultarPedidos()) {

            modeloTabla.addRow(new Object[]{

                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getEstadoPedido()

            });

        }

    }
}
