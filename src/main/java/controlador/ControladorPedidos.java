package controlador;

import modelo.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControladorPedidos {

    // Lista donde almacenaremos los pedidos
    private List<Pedido> listaPedidos;

    private ZonaDeCarga zonaDeCarga;

    private Set<Integer> pedidosEnviados = new HashSet<>();

    private boolean entregasEnCurso = false;

    private PedidoDAO pedidoDAO;

    // Constructor
    public ControladorPedidos() {

        listaPedidos = new ArrayList<>();
        zonaDeCarga = new ZonaDeCarga();
        pedidoDAO = new PedidoDAO();

    }

    // Método para agregar pedidos
    public boolean agregarPedido(Pedido pedido) {

        try {

            boolean guardado = pedidoDAO.guardar(pedido);

            if (guardado) {

                listaPedidos.add(pedido);

                return true;
            }

            return false;

        } catch (SQLException e) {

            System.out.println(
                    "Error al registrar pedido: " + e.getMessage()
            );

            return false;
        }
    }

    // Método para obtener los pedidos
    public List<Pedido> obtenerPedidos() {

        return listaPedidos;

    }

    public List<Pedido> consultarPedidos() throws SQLException {

        return pedidoDAO.listarTodos();

    }

    public int asignarPedidos() {

        int cantidad = 0;

        try {
            // Recuperar los pedidos de MySQL
            List<Pedido> pedidos = pedidoDAO.listarTodos();

            for (Pedido pedido : pedidos) {

                // Asignar únicamente pedidos pendientes
                if (EstadoPedido.PENDIENTE.name()
                        .equals(pedido.getEstadoPedido())
                        && !pedidosEnviados.contains(
                        pedido.getIdPedido())) {

                    zonaDeCarga.agregarPedido(pedido);

                    pedidosEnviados.add(
                            pedido.getIdPedido());

                    cantidad++;
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error al consultar pedidos: "
                            + e.getMessage());

            return -1;
        }

        return cantidad;
    }


    public synchronized boolean iniciarEntregas() {

        if (entregasEnCurso) {
            return false;
        }
        if (!zonaDeCarga.tienePedidos()) {
            return false;
        }

        entregasEnCurso = true;

        Thread simulacion = new Thread(() -> {

            ExecutorService executor = Executors.newFixedThreadPool(3);

            try {

                RepartidorDAO dao = new RepartidorDAO();

                List<Repartidor> repartidores = dao.listarTodos();

                for (Repartidor repartidor : repartidores) {

                    Repartidor trabajador = new Repartidor(
                            repartidor.getIdRepartidor(),
                            repartidor.getNombre(),
                            zonaDeCarga
                    );

                    executor.execute(trabajador);
                }

            } catch (SQLException e) {

                System.out.println(
                        "Error al consultar repartidores: "
                                + e.getMessage()
                );

            } finally {

                executor.shutdown();

                try {

                    executor.awaitTermination(
                            Long.MAX_VALUE,
                            java.util.concurrent.TimeUnit.NANOSECONDS
                    );

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();

                } finally {

                    synchronized (ControladorPedidos.this) {
                        entregasEnCurso = false;
                    }

                }

            }

        });

        simulacion.start();

        return true;
    }

    public boolean tienePedidosAsignados() {
        return zonaDeCarga.tienePedidos();
    }

}

