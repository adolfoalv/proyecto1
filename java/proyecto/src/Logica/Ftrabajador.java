package Logica;

import Clases.Vtrabajador;
import Interface.IDAO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Ftrabajador implements IDAO<Vtrabajador> {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalregistros;

    @Override
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Tipo Documento", "Número Documento", "Dirección", "Teléfono", "Email", "Sueldo", "Acceso", "Login", "Contraseña", "Estado"};
        String[] registro = new String[14];

        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar trabajadores que coincidan con el nombre o número de documento especificado en la búsqueda
        this.sSQL = "SELECT * FROM persona INNER JOIN trabajador ON persona.idpersona = trabajador.idpersona WHERE nombre LIKE '%" + buscar + "%' OR num_documento LIKE '%" + buscar + "%' ORDER BY persona.idpersona DESC";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()) {
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apellido_paterno");
                registro[3] = rs.getString("apellido_materno");
                registro[4] = rs.getString("tipo_documento");
                registro[5] = rs.getString("num_documento");
                registro[6] = rs.getString("direccion");
                registro[7] = rs.getString("telefono");
                registro[8] = rs.getString("email");
                registro[9] = rs.getString("sueldo");
                registro[10] = rs.getString("acceso");
                registro[11] = rs.getString("login");
                registro[12] = rs.getString("password");
                registro[13] = rs.getString("estado");

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
    public boolean insertar(Vtrabajador dts) {
        sSQL = "INSERT INTO persona (nombre, apellido_paterno, apellido_materno, tipo_documento, num_documento, direccion, telefono, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        sSQL2 = "INSERT INTO trabajador (idpersona, sueldo, acceso, login, password, estado) "
                + "VALUES ((SELECT idpersona FROM persona ORDER BY idpersona DESC LIMIT 1), ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApellido_paterno());
            pst.setString(3, dts.getApellido_materno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());

            // Resto de los parámetros de la primera consulta...
            pst2.setDouble(1, dts.getSueldo());
            pst2.setString(2, dts.getAcceso());
            pst2.setString(3, dts.getLogin());
            pst2.setString(4, dts.getPassword());
            pst2.setString(5, dts.getEstado());

            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                return n2 != 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean editar(Vtrabajador dts) {
        sSQL = "UPDATE persona SET nombre=?, apellido_paterno=?, apellido_materno=?, tipo_documento=?, num_documento=?, direccion=?, telefono=?, email=? WHERE idpersona=?";
        sSQL2 = "UPDATE trabajador SET sueldo=?, acceso=?, login=?, password=?, estado=? WHERE idpersona=?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApellido_paterno());
            pst.setString(3, dts.getApellido_materno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getIdpersona());

            pst2.setDouble(1, dts.getSueldo());
            pst2.setString(2, dts.getAcceso());
            pst2.setString(3, dts.getLogin());
            pst2.setString(4, dts.getPassword());
            pst2.setString(5, dts.getEstado());
            pst2.setInt(6, dts.getIdpersona());

            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                return n2 != 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Vtrabajador dts) {
        sSQL = "DELETE FROM trabajador WHERE idpersona = ?";
        sSQL2 = "DELETE FROM persona WHERE idpersona = ?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setInt(1, dts.getIdpersona());
            pst2.setInt(1, dts.getIdpersona());

            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                return n2 != 0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    public DefaultTableModel Login(String login, String password) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Acceso", "Login", "Contraseña", "Estado"};
        String[] registro = new String[8];

        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar trabajadores con PreparedStatement
        this.sSQL = "SELECT p.idpersona, p.nombre, p.apellido_paterno, p.apellido_materno, t.acceso, t.login, t.password, t.estado FROM persona p INNER JOIN trabajador t ON p.idpersona = t.idpersona WHERE t.login = ? AND t.password = ? AND t.estado = 'A'";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, login);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apellido_paterno");
                registro[3] = rs.getString("apellido_materno");

                registro[4] = rs.getString("acceso");
                registro[5] = rs.getString("login");
                registro[6] = rs.getString("password");
                registro[7] = rs.getString("estado");

                totalregistros++;
                modelo.addRow(registro);
            }
            return modelo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
            return null;
        }
    }

}
