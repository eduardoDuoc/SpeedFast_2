package dao;

import modelo.Entrega;

import java.util.List;

public interface EntregaDAO {

    void create(Entrega entrega);
    List<Entrega> readAll();
    void update(Entrega entrega);
    void delete(int id);
}
