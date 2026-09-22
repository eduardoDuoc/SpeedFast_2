package controlador;

import modelo.*;

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

    // Constructor
    public ControladorPedidos() {

        listaPedidos = new ArrayList<>();
        zonaDeCarga = new ZonaDeCarga();

    }

    // Método para agregar pedidos
    public void agregarPedido(Pedido pedido) {

        listaPedidos.add(pedido);

    }

    // Método para obtener los pedidos
    public List<Pedido> obtenerPedidos() {

        return listaPedidos;

    }


    public int asignarPedidos() {

        int cantidad = 0;

        for (Pedido pedido : listaPedidos) {

            if (!pedidosEnviados.contains(pedido.getIdPedido())) {

                zonaDeCarga.agregarPedido(pedido);

                pedido.setEstado(EstadoPedido.EN_ZONA_DE_CARGA);

                pedidosEnviados.add(pedido.getIdPedido());

                cantidad++;

            }

        }

        return cantidad;
    }

    public synchronized boolean iniciarEntregas() {

        if (entregasEnCurso) {
            return false;
        }

        entregasEnCurso = true;

        Thread simulacion = new Thread(() -> {

            ExecutorService executor = Executors.newFixedThreadPool(3);

            try {

                Repartidor repartidor1 =
                        new Repartidor("Eduardo", zonaDeCarga);

                Repartidor repartidor2 =
                        new Repartidor("Catalina", zonaDeCarga);

                Repartidor repartidor3 =
                        new Repartidor("Mauri", zonaDeCarga);

                executor.execute(repartidor1);
                executor.execute(repartidor2);
                executor.execute(repartidor3);

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

}

