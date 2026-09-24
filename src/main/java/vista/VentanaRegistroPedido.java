package vista;

import javax.swing.*;

import controlador.ControladorPedidos;
import modelo.Pedido;

public class VentanaRegistroPedido extends JFrame {
    private JPanel RegistroPedidos;
    private JPanel panelRegistro;
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

            String direccion = txtDireccion.getText().trim();
            String tipo = comboBox1Tipo.getSelectedItem().toString();

            if (direccion.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Debe ingresar una dirección de entrega."
                );

                return;
            }

            Pedido pedido = new Pedido(
                    0,
                    direccion,
                    tipo
            );

            boolean guardado = controlador.agregarPedido(pedido);

            if (guardado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido registrado correctamente. ID: "
                                + pedido.getIdPedido()
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el pedido."
                );

            }



        });


        setVisible(true);

    }
}
