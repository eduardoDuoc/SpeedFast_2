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

            Pedido pedido = new Pedido(
                    idPedido,
                    direccion,
                    tipo
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );


            controlador.agregarPedido(pedido);

        });


        setVisible(true);

    }
}
