package dao;


import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EntregaDAO {

    public boolean guardar(Entrega entrega) {

        String sql = "INSERT INTO entrega " +
                "(id_pedido, id_repartidor, fecha, hora) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3,
                    java.sql.Date.valueOf(entrega.getFecha()));
            ps.setTime(4,
                    java.sql.Time.valueOf(entrega.getHora()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Error al guardar entrega: " + e.getMessage());
            return false;
        }
    }
}
