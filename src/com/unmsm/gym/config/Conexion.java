package src.com.unmsm.gym.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static String url = "jdbc:mysql://localhost:3306/jdbc";
    private static String usuario = "root";
    private static String contrasenia = "admin";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, usuario, contrasenia);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean hayConexion (Connection conexion) {
        if (conexion == null) {
            return false;
        } 

        return true;
    }
}