package modelo;

public class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private EstadoPedido estado;
    private String tipoPedido;


    public Pedido(int idPedido, String direccionEntrega) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Pedido(int idPedido, String direccionEntrega, String tipoPedido) {

        this(idPedido, direccionEntrega);

        this.tipoPedido = tipoPedido;

    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public EstadoPedido getEstadoPedido() {
        return estado;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;

    }

    public void mostrarResumen() {
        System.out.println("\nID: " + idPedido);
        System.out.println("Direccion: " + direccionEntrega);
    }
}
