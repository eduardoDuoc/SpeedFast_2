package dao.impl;

import dao.RepartidorDAO;
import modelo.Repartidor;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RepartidorDAOImpl implements RepartidorDAO {

    @Override
    public void create(Repartidor repartidor) {

        String sql =
                "INSERT INTO repartidor (nombreRepartidor) VALUES (?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS
             )) {

            ps.setString(1, repartidor.getNombre());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    repartidor.setIdRepartidor(rs.getInt(1));
                }
            }

            System.out.println(
                    "Repartidor registrado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al registrar repartidor: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public List<Repartidor> readAll() {

        List<Repartidor> repartidores =
                new ArrayList<>();

        String sql = "SELECT * FROM repartidor";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id =
                        rs.getInt("idRepartidor");

                String nombre =
                        rs.getString("nombreRepartidor");

                Repartidor repartidor =
                        new Repartidor(id, nombre);

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar repartidores: "
                            + e.getMessage()
            );
        }

        return repartidores;
    }

    @Override
    public void update(Repartidor repartidor) {

        String sql =
                "UPDATE repartidor "
                        + "SET nombreRepartidor = ? "
                        + "WHERE idRepartidor = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(
                    1,
                    repartidor.getNombre()
            );

            ps.setInt(
                    2,
                    repartidor.getIdRepartidor()
            );

            ps.executeUpdate();

            System.out.println(
                    "Repartidor actualizado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar repartidor: "
                            + e.getMessage()
            );
        }
    }

    @Override
    public void delete(int id) {

        String sql =
                "DELETE FROM repartidor "
                        + "WHERE idRepartidor = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println(
                    "Repartidor eliminado correctamente."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar repartidor: "
                            + e.getMessage()
            );
        }
    }
}