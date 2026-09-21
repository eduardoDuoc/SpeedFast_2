package modelo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaDeCarga {

    private BlockingQueue<Pedido> pedidos = new LinkedBlockingQueue<>();

    public ZonaDeCarga() {
        System.out.println("\n\n[Zona de carga inicializada]\n\n");
    }

    public synchronized void agregarPedido(Pedido p) {
        pedidos.add(p);
        System.out.println(
                "Pedido #" + p.getIdPedido()
                        + " agregado. Destino: " + p.getDireccionEntrega()
        );
    }

    public synchronized Pedido retirarPedido() {
        return pedidos.poll();
    }

}