package com.unmsm.gym.models;

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

    /*           Getters y setters           */
    public int obtenerId() { return idReserva; }
    public void establecerId(int id) { this.idReserva = id; }

    public int obtenerIdUsuario() { return idUsuario; }
    public void establecerIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int obtenerIdHorario() { return idHorario; }
    public void establecerIdHorario(int idHorario) { this.idHorario = idHorario; }
}