package vista;

import modelo.Repartidor;
import javax.swing.*;

public class VentanaRegistroRepartidor extends JFrame {

    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JButton guardarButton;

    public VentanaRegistroRepartidor() {

        setTitle("SpeedFast - Registro de repartidores");
        setContentPane(panelPrincipal);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

            RepartidorDAO dao = new RepartidorDAO();

            boolean guardado = dao.guardar(repartidor);

            if (guardado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor registrado correctamente. ID: "
                                + repartidor.getIdRepartidor()
                );

                txtNombre.setText("");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Error al registrar el repartidor."
                );
            }
        });

        setVisible(true);
    }
}