package dao.impl;

import dao.EntregaDAO;
import modelo.Entrega;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class EntregaDAOImpl implements EntregaDAO {

    @Override
    public void create(Entrega entrega) {

        String sql =
                "INSERT INTO entrega " +
                        "(id_pedido, id_repartidor, fecha, hora) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());

            ps.setDate(
                    3,
                    java.sql.Date.valueOf(entrega.getFecha())
            );

            ps.setTime(
                    4,
                    java.sql.Time.valueOf(entrega.getHora())
            );

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    entrega.setIdEntrega(rs.getInt(1));
                }
            }

            System.out.println(
                    "Entrega registrada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al registrar entrega: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public List<Entrega> readAll() {

        List<Entrega> entregas =
                new ArrayList<>();

        String sql =
                "SELECT * FROM entrega";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int idEntrega =
                        rs.getInt("identrega");

                int idPedido =
                        rs.getInt("id_pedido");

                int idRepartidor =
                        rs.getInt("id_repartidor");

                java.time.LocalDate fecha =
                        rs.getDate("fecha").toLocalDate();

                java.time.LocalTime hora =
                        rs.getTime("hora").toLocalTime();

                Entrega entrega =
                        new Entrega(
                                idEntrega,
                                idPedido,
                                idRepartidor,
                                fecha,
                                hora
                        );

                entregas.add(entrega);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar entregas: "
                            + e.getMessage()
            );
        }

        return entregas;
    }

    @Override
    public void update(Entrega entrega) {

        String sql =
                "UPDATE entrega " +
                        "SET id_pedido = ?, " +
                        "id_repartidor = ?, " +
                        "fecha = ?, " +
                        "hora = ? " +
                        "WHERE identrega = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());

            ps.setDate(
                    3,
                    java.sql.Date.valueOf(entrega.getFecha())
            );

            ps.setTime(
                    4,
                    java.sql.Time.valueOf(entrega.getHora())
            );

            ps.setInt(
                    5,
                    entrega.getIdEntrega()
            );

            ps.executeUpdate();

            System.out.println(
                    "Entrega actualizada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar entrega: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql =
                "DELETE FROM entrega " +
                        "WHERE identrega = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println(
                    "Entrega eliminada correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar entrega: "
                            + e.getMessage()
            );
        }
    }
}