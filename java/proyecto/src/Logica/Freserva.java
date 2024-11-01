package Logica;

import Clases.Vreserva;
import Interface.IDAO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Freserva implements IDAO<Vreserva> {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    @Override
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "IDHabitación", "Numero", "IDCliente", "Cliente", "IDTrabajador", "Trabajador", "Tipo Reserva", "Fecha Reserva", "Fecha Ingreso", "Fecha Salida", "Coste Alojamiento", "Estado"};
        String[] registro = new String[13];

        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar reservas que coincidan con el cliente especificado en la búsqueda
        this.sSQL = "SELECT r.idreserva, r.idhabitacion, h.numero, r.idcliente,"
                + "(SELECT nombre FROM persona WHERE idpersona=r.idcliente)as clienten,"
                + "(SELECT apellido_paterno FROM persona WHERE idpersona= r.idcliente)as clienteap,"
                + "r.idtrabajador, (SELECT nombre FROM persona WHERE idpersona=r.idtrabajador)as trabajadorn,"
                + "(SELECT apellido_paterno FROM persona WHERE idpersona=r.idtrabajador)as trabajadorap, "
                + "r.tipo_reserva, r.fecha_reserva, r.fecha_ingreso, r.fecha_salida,"
                + "r.coste_alojamiento, r.estado FROM reserva r INNER JOIN habitacion h on r.idhabitacion= h.idhabitacion WHERE r.fecha_reserva LIKE '%" + buscar + "%' ORDER BY idreserva DESC";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registro[0] = rs.getString("idreserva");
                registro[1] = rs.getString("idhabitacion");
                registro[2] = rs.getString("numero");
                registro[3] = rs.getString("idcliente");
                registro[4] = rs.getString("clienten") + " " + rs.getString("clienteap");
                registro[5] = rs.getString("idtrabajador");
                registro[6] = rs.getString("trabajadorn") + " " + rs.getString("trabajadorap");
                registro[7] = rs.getString("tipo_reserva");
                registro[8] = rs.getString("fecha_reserva");
                registro[9] = rs.getString("fecha_ingreso");
                registro[10] = rs.getString("fecha_salida");
                registro[11] = rs.getString("coste_alojamiento");
                registro[12] = rs.getString("estado");

                totalregistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    @Override
    public boolean insertar(Vreserva dts) {
        sSQL = "INSERT INTO reserva (idhabitacion, idcliente, idtrabajador, tipo_reserva, fecha_reserva, fecha_ingreso, fecha_salida, coste_alojamiento, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, dts.getIdhabitacion());
            pst.setInt(2, dts.getIdcliente());
            pst.setInt(3, dts.getIdtrabajador());
            pst.setString(4, dts.getTipo_reserva());
            pst.setDate(5, dts.getFecha_reserva());
            pst.setDate(6, dts.getFecha_ingresa());
            pst.setDate(7, dts.getFecha_salida());
            pst.setDouble(8, dts.getCoste_alojamiento());
            pst.setString(9, dts.getEstado());

            int n = pst.executeUpdate();

            return n != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean editar(Vreserva dts) {
        sSQL = "UPDATE reserva SET idhabitacion=?, idcliente=?, idtrabajador=?, tipo_reserva=?, fecha_reserva=?, fecha_ingreso=?, fecha_salida=?, coste_alojamiento=?, estado=? WHERE idreserva=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdhabitacion());
            pst.setInt(2, dts.getIdcliente());
            pst.setInt(3, dts.getIdtrabajador());
            pst.setString(4, dts.getTipo_reserva());
            pst.setDate(5, dts.getFecha_reserva());
            pst.setDate(6, dts.getFecha_ingresa());
            pst.setDate(7, dts.getFecha_salida());
            pst.setDouble(8, dts.getCoste_alojamiento());
            pst.setString(9, dts.getEstado());
            pst.setInt(10, dts.getIdreserva());

            int n = pst.executeUpdate();

            return n != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Vreserva dts) {
        sSQL = "DELETE FROM reserva WHERE idreserva = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdreserva());

            int n = pst.executeUpdate();

            return n != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    //Metodo para pagar una reserva
    public boolean pagar(Vreserva dts) {
        sSQL = "UPDATE reserva SET estado = 'Pagada'"
                + " WHERE idreserva=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, dts.getIdreserva());

            int n = pst.executeUpdate();

            return n != 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
}
