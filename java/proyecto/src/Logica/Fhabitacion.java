package Logica;

import Clases.Vhabitacion;
import Interface.IDAO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fhabitacion implements IDAO<Vhabitacion> {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    @Override
    // Función para mostrar habitaciones en una tabla
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;

        // Definición de los títulos de las columnas de la tabla
        String[] titulos = {"ID", "Número", "piso", "descripcion", "Características", "Precio", "Estado", "tipo_habitación"};

        String[] registro = new String[8];

        this.totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar habitaciones que coincidan con el piso especificado en la búsqueda
        this.sSQL = "SELECT * FROM habitacion WHERE piso LIKE '%" + buscar + "%' ORDER BY idhabitacion";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            // Llenar el modelo de la tabla con los resultados de la consulta
            while (rs.next()) {
                registro[0] = rs.getString("idhabitacion");
                registro[1] = rs.getString("numero");
                registro[2] = rs.getString("piso");
                registro[3] = rs.getString("descripcion");
                registro[4] = rs.getString("caracteristicas");
                registro[5] = rs.getString("precio_diario");
                registro[6] = rs.getString("estado");
                registro[7] = rs.getString("tipo_habitacion");

                totalregistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (SQLException e) { // Imprimir la traza de error en la consola
            // Imprimir la traza de error en la consola
            JOptionPane.showMessageDialog(null, e);
            return null; // Devuelve null en caso de error
        }
    }

    @Override
    // Función para insertar una nueva habitación en la base de datos
    public boolean insertar(Vhabitacion dts) {
        sSQL = "INSERT INTO habitacion (numero, piso, descripcion, caracteristicas, precio_diario, estado, tipo_habitacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    // Función para editar una habitación existente en la base de datos
    @Override
    public boolean editar(Vhabitacion dts) {
        sSQL = "update habitacion set numero=?, piso=?, descripcion=?, caracteristicas=?,precio_diario=?, estado=?, tipo_habitacion=? where idhabitacion=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            pst.setInt(8, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    // Función para eliminar una habitación de la base de datos
    public boolean eliminar(Vhabitacion dts) {
        sSQL = "DELETE FROM habitacion WHERE idhabitacion = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setInt(1, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
     //Funcion para Desocupar la habitacion
    public boolean desocupar(Vhabitacion dts) {
        sSQL = "UPDATE habitacion SET estado = 'Disponible'"
                + " WHERE idhabitacion=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
    
    //Funcion para Desocupar la habitacion
     public boolean ocupar(Vhabitacion dts) {
        sSQL = "UPDATE habitacion SET estado = 'Ocupado'"
                + " WHERE idhabitacion=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            
            pst.setInt(1, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }
     
    //Función para mostrar las habitaciones Disponibles
    public DefaultTableModel mostrarvista(String buscar) {
        DefaultTableModel modelo;

        // Definición de los títulos de las columnas de la tabla
        String[] titulos = {"ID", "Número", "piso", "descripcion", "Características", "Precio", "Estado", "tipo_habitación"};

        String[] registro = new String[8];

        this.totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar habitaciones que coincidan con el piso especificado en la búsqueda
        this.sSQL = "SELECT * FROM habitacion WHERE piso LIKE '%" + buscar + "%' AND estado = 'Disponible' ORDER BY idhabitacion";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            // Llenar el modelo de la tabla con los resultados de la consulta
            while (rs.next()) {
                registro[0] = rs.getString("idhabitacion");
                registro[1] = rs.getString("numero");
                registro[2] = rs.getString("piso");
                registro[3] = rs.getString("descripcion");
                registro[4] = rs.getString("caracteristicas");
                registro[5] = rs.getString("precio_diario");
                registro[6] = rs.getString("estado");
                registro[7] = rs.getString("tipo_habitacion");

                totalregistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, e);
            return null; 
        }
    }
}
