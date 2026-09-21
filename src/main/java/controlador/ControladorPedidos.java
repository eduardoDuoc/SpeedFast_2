package controlador;

import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

public class ControladorPedidos {

    // Lista donde almacenaremos los pedidos
    private List<Pedido> listaPedidos;

    // Constructor
    public ControladorPedidos() {

        listaPedidos = new ArrayList<>();

    }

    // Método para agregar pedidos
    public void agregarPedido(Pedido pedido) {

        listaPedidos.add(pedido);

    }

    // Método para obtener los pedidos
    public List<Pedido> obtenerPedidos() {

        return listaPedidos;

    }

}