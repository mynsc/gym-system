package src.com.unmsm.gym.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "admin";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
