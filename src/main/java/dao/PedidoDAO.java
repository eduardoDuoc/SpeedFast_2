package dao;

import modelo.Pedido;

import java.util.List;

public interface PedidoDAO {

    void create(Pedido pedido);
    List<Pedido> readAll();
    void update(Pedido pedido);
    boolean delete(int id);
}
