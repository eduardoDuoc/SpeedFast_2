package vista;

import javax.swing.*;

import controlador.ControladorPedidos;
import modelo.Pedido;

public class VentanaRegistroPedido extends JFrame {
    private JPanel RegistroPedidos;
    private JPanel panelRegistro;
    private JTextField txtIdPedido;
    private JTextField txtDireccion;
    private JComboBox comboBox1Tipo;
    private JButton guardarButton;

    private ControladorPedidos controlador;

    public VentanaRegistroPedido(ControladorPedidos controlador) {

        this.controlador = controlador;

        setTitle("SpeedFast - Registro de pedidos");

        setContentPane(RegistroPedidos);

        setSize(500, 350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        guardarButton.addActionListener(e -> {

            String idTexto = txtIdPedido.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String tipo = comboBox1Tipo.getSelectedItem().toString();

            if (idTexto.isEmpty() || direccion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe completar todos los campos."
                );

                return;
            }

            int idPedido;

            try {

                idPedido = Integer.parseInt(idTexto);

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "El ID debe ser un número válido."
                );

                return;
            }

            if (idPedido <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El ID del pedido debe ser mayor que cero."
                );

                return;
            }

            for (Pedido pedidoExistente : controlador.obtenerPedidos()) {

                if (pedidoExistente.getIdPedido() == idPedido) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Ya existe un pedido con ese ID."
                    );

                    return;
                }
            }

            Pedido pedido = new Pedido(
                    idPedido,
                    direccion,
                    tipo
            );

            controlador.agregarPedido(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );


        });


        setVisible(true);

    }
}
