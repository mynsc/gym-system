package src.com.unmsm.gym.db;

import src.com.unmsm.gym.config.conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class db_estudiantes {
    public void create(
            String nombre,
            String apellido,
            String usuario,
            String contrasenia,
            String facultad,
            String carrera,
            String baseInicio,
            boolean matriculadoSemestreActual,
            boolean autoseguroActivo) {

        String sql = """
                INSERT INTO estudiantes_reg(
                    nombre,
                    apellido,
                    usuario,
                    contrasenia,
                    facultad,
                    carrera,
                    base_incio,
                    actualmente_matriculado,
                    autoseguro_activo
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, usuario);
            ps.setString(4, contrasenia);
            ps.setString(5, facultad);
            ps.setString(6, carrera);
            ps.setString(7, baseInicio);
            ps.setBoolean(8, matriculadoSemestreActual);
            ps.setBoolean(9, autoseguroActivo);

            ps.executeUpdate();

            System.out.println("Estudiante regular registrado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
