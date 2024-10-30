package Logica;

import Clases.Vcliente;
import Interface.IDAO;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Fcliente implements IDAO<Vcliente> {

    private Conexion mysql = new Conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalregistros;

    @Override
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
        String[] titulos = {"ID", "Nombre", "Apellido Paterno", "Apellido Materno", "Tipo Documento", "Número Documento", "Dirección", "Teléfono", "Email", "Código Cliente"};
        String[] registro = new String[10];

        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);

        // Consulta SQL para seleccionar clientes que coincidan con el nombre o número de documento especificado en la búsqueda
        this.sSQL = "SELECT * FROM persona INNER JOIN cliente ON persona.idpersona = cliente.idpersona WHERE nombre LIKE '%" + buscar + "%' OR num_documento LIKE '%" + buscar + "%' ORDER BY persona.idpersona DESC";

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
                registro[9] = rs.getString("codigo_cliente");

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
    public boolean insertar(Vcliente dts) {
        sSQL = "INSERT INTO persona (nombre, apellido_paterno, apellido_materno, tipo_documento, num_documento, direccion, telefono, email) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        sSQL2 = "INSERT INTO cliente (idpersona, codigo_cliente) "
                + "VALUES ((SELECT idpersona FROM persona ORDER BY idpersona DESC LIMIT 1), ?)";
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
            pst2.setString(1, dts.getCodigo_cliente());

            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }

    }

    @Override
    public boolean editar(Vcliente dts) {
        sSQL = "UPDATE persona SET nombre=?, apellido_paterno=?, apellido_materno=?, tipo_documento=?, num_documento=?, direccion=?, telefono=?, email=? WHERE idpersona=?";
        sSQL2 = "UPDATE cliente SET codigo_cliente=? WHERE idpersona=?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApellido_paterno());
            pst.setString(3, dts.getApellido_materno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getIdpersona());

            pst2.setString(1, dts.getCodigo_cliente());
            pst2.setInt(2, dts.getIdpersona());
            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

    @Override
    public boolean eliminar(Vcliente dts) {
        sSQL = "DELETE FROM cliente WHERE idpersona = ?";
        sSQL2 = "DELETE FROM persona WHERE idpersona = ?";
        try {
            PreparedStatement pst = cn.prepareStatement(sSQL, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, dts.getIdpersona());
            pst2.setInt(1, dts.getIdpersona());
            int n = pst.executeUpdate();

            if (n != 0) {
                int n2 = pst2.executeUpdate();

                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }
    }

}
