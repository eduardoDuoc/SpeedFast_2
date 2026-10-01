package dao.impl;

import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.Pedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public void create(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (direccion, estado, tipo) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getEstadoPedido());
            ps.setString(3, pedido.getTipoPedido());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                }
            }

            System.out.println("Pedido registrado correctamente.");

        } catch (SQLException e) {

            System.out.println(
                    "Error al registrar pedido: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public List<Pedido> readAll() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("idpedido");
                String direccion = rs.getString("direccion");
                String tipo = rs.getString("tipo");
                String estado = rs.getString("estado");

                Pedido pedido =
                        new Pedido(id, direccion, tipo);

                pedido.setEstado(
                        EstadoPedido.valueOf(estado)
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar pedidos: "
                            + e.getMessage()
            );
        }

        return pedidos;
    }

    @Override
    public void update(Pedido pedido) {

        String sql =
                "UPDATE pedido " +
                        "SET direccion = ?, tipo = ?, estado = ? " +
                        "WHERE idpedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipoPedido());
            ps.setString(3, pedido.getEstadoPedido());
            ps.setInt(4, pedido.getIdPedido());

            ps.executeUpdate();

            System.out.println("Pedido actualizado correctamente.");

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar pedido: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM pedido WHERE idpedido = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar pedido: "
                            + e.getMessage()
            );

            return false;
        }
    }
}