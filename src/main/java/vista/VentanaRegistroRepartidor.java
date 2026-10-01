package vista;

import dao.RepartidorDAO;
import dao.impl.RepartidorDAOImpl;
import modelo.Repartidor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaRegistroRepartidor extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton guardarButton;
    private JTable table1;
    private JButton editarButton;
    private JButton eliminarButton;
    private DefaultTableModel modeloTabla;

    public VentanaRegistroRepartidor() {

        setTitle("SpeedFast - Registro de repartidores");
        setContentPane(panelPrincipal);
        setSize(550, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID Repartidor");
        modeloTabla.addColumn("Nombre");

        table1.setModel(modeloTabla);

        cargarRepartidores();

        guardarButton.addActionListener(e -> {

            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar el nombre del repartidor."
                );
                return;
            }

            Repartidor repartidor =
                    new Repartidor(0, nombre);

            RepartidorDAO dao = new RepartidorDAOImpl();

            dao.create(repartidor);

            if (repartidor.getIdRepartidor() > 0){

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor registrado correctamente. ID: "
                                + repartidor.getIdRepartidor()
                );

                txtNombre.setText("");
                cargarRepartidores();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el repartidor."
                );
            }
        });

        editarButton.addActionListener(e -> {

            int filaSeleccionada = table1.getSelectedRow();

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un repartidor para editar."
                );

                return;
            }

            int idRepartidor = Integer.parseInt(
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0
                    ).toString()
            );

            String nombreActual =
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            1
                    ).toString();

            String nuevoNombre =
                    JOptionPane.showInputDialog(
                            this,
                            "Ingrese el nuevo nombre:",
                            nombreActual
                    );

            if (nuevoNombre == null ||
                    nuevoNombre.trim().isEmpty()) {

                return;
            }

            Repartidor repartidor =
                    new Repartidor(
                            idRepartidor,
                            nuevoNombre.trim()
                    );

            RepartidorDAO dao =
                    new RepartidorDAOImpl();

            dao.update(repartidor);

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            cargarRepartidores();
        });

        eliminarButton.addActionListener(e -> {

            int filaSeleccionada = table1.getSelectedRow();

            if (filaSeleccionada == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe seleccionar un repartidor para eliminar."
                );

                return;
            }

            int idRepartidor = Integer.parseInt(
                    modeloTabla.getValueAt(
                            filaSeleccionada,
                            0
                    ).toString()
            );

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro de eliminar el repartidor #"
                            + idRepartidor + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            RepartidorDAO dao =
                    new RepartidorDAOImpl();

            boolean eliminado =
                    dao.delete(idRepartidor);

            if (eliminado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor eliminado correctamente."
                );

                cargarRepartidores();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se puede eliminar el repartidor porque tiene una entrega asociada."
                );
            }
        });

        setVisible(true);
    }

    private void cargarRepartidores() {

        modeloTabla.setRowCount(0);

        RepartidorDAO dao = new RepartidorDAOImpl();

        for (Repartidor repartidor : dao.readAll()) {

            modeloTabla.addRow(new Object[]{
                    repartidor.getIdRepartidor(),
                    repartidor.getNombre()
            });
        }
    }
}