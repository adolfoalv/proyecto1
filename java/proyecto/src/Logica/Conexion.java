package Logica;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    public String bd = "reserva";
    public String url = "jdbc:mysql://127.0.0.1/" + bd;
    public String user = "root";
    public String password = "";

    public Connection conectar() {
        Connection link = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            link = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            mostrarError(e.getMessage());
        }

        return link;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, "Error de conexión: " + mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
