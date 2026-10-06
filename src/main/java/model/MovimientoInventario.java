package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MovimientoInventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private int id_movimiento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "tipo_movimiento", nullable = false, length = 10)
    private String tipo_movimiento;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fecha_movimiento;

    // Constructor vacío obligatorio para JPA
    public MovimientoInventario() {
    }

    // Constructor con datos
    public MovimientoInventario(
            Producto producto,
            String tipo_movimiento,
            int cantidad,
            LocalDateTime fecha_movimiento) {

        this.producto = producto;
        this.tipo_movimiento = tipo_movimiento;
        this.cantidad = cantidad;
        this.fecha_movimiento = fecha_movimiento;
    }

    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(LocalDateTime fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    @Override
    public String toString() {
        return "MovimientoInventario [id_movimiento=" + id_movimiento
                + ", producto=" + producto
                + ", tipo_movimiento=" + tipo_movimiento
                + ", cantidad=" + cantidad
                + ", fecha_movimiento=" + fecha_movimiento + "]";
    }
}