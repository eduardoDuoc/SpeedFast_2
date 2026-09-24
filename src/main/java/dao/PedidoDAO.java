package dao;

import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import modelo.EstadoPedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {


    public boolean guardar(Pedido pedido) throws SQLException {

        String sql = "INSERT INTO pedido (direccion, estado, tipo) VALUES (?,?,?)";

        try(Connection conexion = ConexionBD.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getEstadoPedido());
            ps.setString(3, pedido.getTipoPedido());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        pedido.setIdPedido(rs.getInt(1));
                        return  true;
                    }
                }
            }

            return false;

        }catch (SQLException e){
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }
    public List<Pedido> listarTodos() throws SQLException {

        List<Pedido> listaPedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("idpedido");

                String direccion = rs.getString("direccion");

                String tipo = rs.getString("tipo");

                String estado = rs.getString("estado");

                Pedido pedido = new Pedido(id, direccion, tipo);

                pedido.setEstado(
                        EstadoPedido.valueOf(estado)
                );

                listaPedidos.add(pedido);

            }

        }

        return listaPedidos;
    }
}
