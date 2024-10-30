
package Clases;

import java.sql.Date;

public class Vreserva {
    
    private int idreserva;
    private int idhabitacion;
    private int idcliente;
    private int idtrabajador;
    private String tipo_reserva;
    private Date fecha_reserva;
    private Date fecha_ingreso;
    private Date fecha_salida;
    private Double coste_alojamiento;
    private String estado;

    public Vreserva() {
    }

    public Vreserva(int idreserva, int idhabitacion, int idcliente, int trabajador, String tipo_reserva, Date fecha_reserva, Date fecha_ingresa, Date fecha_salida, Double coste_alojamiento, String estado) {
        this.idreserva = idreserva;
        this.idhabitacion = idhabitacion;
        this.idcliente = idcliente;
        this.idtrabajador = trabajador;
        this.tipo_reserva = tipo_reserva;
        this.fecha_reserva = fecha_reserva;
        this.fecha_ingreso = fecha_ingresa;
        this.fecha_salida = fecha_salida;
        this.coste_alojamiento = coste_alojamiento;
        this.estado = estado;
    }

    public int getIdreserva() {
        return idreserva;
    }

    public void setIdreserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public int getIdhabitacion() {
        return idhabitacion;
    }

    public void setIdhabitacion(int idhabitacion) {
        this.idhabitacion = idhabitacion;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getIdtrabajador() {
        return idtrabajador;
    }

    public void setIdtrabajador(int trabajador) {
        this.idtrabajador = trabajador;
    }

    public String getTipo_reserva() {
        return tipo_reserva;
    }

    public void setTipo_reserva(String tipo_reserva) {
        this.tipo_reserva = tipo_reserva;
    }

    public Date getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(Date fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public Date getFecha_ingresa() {
        return fecha_ingreso;
    }

    public void setFecha_ingresa(Date fecha_ingresa) {
        this.fecha_ingreso = fecha_ingresa;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Double getCoste_alojamiento() {
        return coste_alojamiento;
    }

    public void setCoste_alojamiento(Double coste_alojamiento) {
        this.coste_alojamiento = coste_alojamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
