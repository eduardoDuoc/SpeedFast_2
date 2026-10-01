package modelo;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import dao.impl.EntregaDAOImpl;

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

        PedidoDAO pedidoDAO = new PedidoDAOImpl();
        EntregaDAO entregaDAO = new EntregaDAOImpl();

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
            pedido.setEstado(EstadoPedido.EN_REPARTO);
            pedidoDAO.update(pedido);

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
            pedido.setEstado(EstadoPedido.ENTREGADO);
            pedidoDAO.update(pedido);

            // Crear el registro de entrega
            Entrega entrega = new Entrega(
                    pedido.getIdPedido(),
                    idRepartidor,
                    LocalDate.now(),
                    LocalTime.now()
            );

            // Registrar la entrega en MySQL
            entregaDAO.create(entrega);

            System.out.println(
                    "[Repartidor - " + nombre + "] Estado: "
                            + pedido.getEstadoPedido()
            );
        }
    }

    @Override
    public String toString() {
        return idRepartidor + " - " + nombre;
    }
}
