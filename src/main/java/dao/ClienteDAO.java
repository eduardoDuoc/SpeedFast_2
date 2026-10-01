package dao;

import modelo.Cliente;

import java.util.List;



public interface ClienteDAO {

    void create(Cliente cliente);

    List<Cliente> readAll();

    void update(Cliente cliente);

    void delete(int id);

}
