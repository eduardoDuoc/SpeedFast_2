package modelo;

import java.time.LocalDate;
import java.time.LocalTime;


public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;
    private int idRepartidor;

    public Repartidor(int idRepartidor, String nombre) {

        this.idRepartidor = idRepartidor;
        this.nombre = nombre;

    }

    public Repartidor(int idRepartidor, String nombre, ZonaDeCarga zonaDeCarga) {

        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }
    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }


    @Override
    public void run() {

        PedidoDAO pedidoDAO = new PedidoDAO();
        EntregaDAO entregaDAO = new EntregaDAO();

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            System.out.println(
                    "[Repartidor - " + nombre + "] Retirando pedido #"
                            + pedido.getIdPedido() + "..."
            );

            // Actualizar el estado en MySQL
            if (!pedidoDAO.actualizarEstado(
                    pedido.getIdPedido(), EstadoPedido.EN_REPARTO)) {

                System.err.println(
                        "No se pudo iniciar el pedido #"
                                + pedido.getIdPedido());
                continue;
            }

            pedido.setEstado(EstadoPedido.EN_REPARTO);

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: "
                            + pedido.getEstadoPedido()
            );

            System.out.println(
                    "[Repartidor - " + nombre + "] Entregando pedido #"
                            + pedido.getIdPedido() + "..."
            );

            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            // Guardar el estado ENTREGADO en MySQL
            if (!pedidoDAO.actualizarEstado(
                    pedido.getIdPedido(), EstadoPedido.ENTREGADO)) {

                System.err.println(
                        "No se pudo finalizar el pedido #"
                                + pedido.getIdPedido());
                continue;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            // Crear el registro de entrega
            Entrega entrega = new Entrega(
                    pedido.getIdPedido(),
                    idRepartidor,
                    LocalDate.now(),
                    LocalTime.now()
            );

            // Registrar la entrega en MySQL
            if (!entregaDAO.guardar(entrega)) {

                System.err.println(
                        "No se pudo registrar la entrega del pedido #"
                                + pedido.getIdPedido());
                continue;
            }

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: "
                            + pedido.getEstadoPedido()
            );
        }
    }


}
