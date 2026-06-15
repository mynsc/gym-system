package src.com.unmsm.gym.db;
import src.com.unmsm.gym.config.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class database {
    public void insertar(String nombre, String contrasenia) {

        String sql = "INSERT INTO estudiantes_table(nombre, apellido) VALUES (?, ?)";

        try (
                Connection con = conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, contrasenia);

            ps.executeUpdate();

            System.out.println("Estudiante registrado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
