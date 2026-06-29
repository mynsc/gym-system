package com.unmsm.gym.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    /*               Atributos               */
    private int idReserva;
    private int idUsuario;
    private int idHorario;

    /*              Constructores              */
    public Reserva(int idUsuario, int idHorario) {
        this.idUsuario = idUsuario;
        this.idHorario = idHorario;
    }

    public Reserva() {}

    /*               Metodos               */
    public List<Reserva> leerReservasDesdeBD(Connection conexion) {
        List<Reserva> reservas = new ArrayList<>();

        String sqlReservas = "SELECT * FROM reserva WHERE id = ?";

        PreparedStatement sentenciaReservas = null;
        ResultSet resultadoReservas = null;

        try {
            sentenciaReservas = conexion.prepareStatement(sqlReservas);
            sentenciaReservas.setInt(1, this.idUsuario);

            resultadoReservas = sentenciaReservas.executeQuery();

            while (resultadoReservas.next()) {
                this.idReserva = resultadoReservas.getInt("id_reserva");
                resultadoReservas.getInt("id_usuario");
                resultadoReservas.getInt("id_horario");
            }
        } catch (SQLException e) {

        } finally {

        }

        return reservas;
    }

    /*           Getters y setters           */
    public int obtenerId() { return idReserva; }
    public void establecerId(int id) { this.idReserva = id; }

    public int obtenerIdUsuario() { return idUsuario; }
    public void establecerIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int obtenerIdHorario() { return idHorario; }
    public void establecerIdHorario(int idHorario) { this.idHorario = idHorario; }
}