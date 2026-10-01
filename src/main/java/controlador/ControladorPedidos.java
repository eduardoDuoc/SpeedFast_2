package controlador;

import dao.PedidoDAO;
import modelo.*;
import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import dao.RepartidorDAO;
import dao.impl.RepartidorDAOImpl;
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
        pedidoDAO = new PedidoDAOImpl();

    }

    // Método para agregar pedidos
    public boolean agregarPedido(Pedido pedido) {

        pedidoDAO.create(pedido);

        if (pedido.getIdPedido() > 0) {

            listaPedidos.add(pedido);

            return true;
        }

        return false;
    }

    // Método para obtener los pedidos
    public List<Pedido> obtenerPedidos() {

        return listaPedidos;

    }

    public List<Pedido> consultarPedidos() {

        return pedidoDAO.readAll();

    }

    public int asignarPedidos() {

        int cantidad = 0;

        List<Pedido> pedidos = pedidoDAO.readAll();

        for (Pedido pedido : pedidos) {

            if (EstadoPedido.PENDIENTE.name()
                    .equals(pedido.getEstadoPedido())
                    && !pedidosEnviados.contains(
                    pedido.getIdPedido())) {

                zonaDeCarga.agregarPedido(pedido);

                pedidosEnviados.add(
                        pedido.getIdPedido()
                );

                cantidad++;
            }
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

                RepartidorDAO dao = new RepartidorDAOImpl();

                List<Repartidor> repartidores = dao.readAll();

                for (Repartidor repartidor : repartidores) {

                    Repartidor trabajador = new Repartidor(
                            repartidor.getIdRepartidor(),
                            repartidor.getNombre(),
                            zonaDeCarga
                    );

                    executor.execute(trabajador);
                }

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

