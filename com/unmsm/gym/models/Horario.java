package com.unmsm.gym.models;

import java.time.LocalTime;

public class Horario {
    /*               Atributos               */
    private int idHorario;
    private LocalTime hora;
    private int cupos;
    private int cantidadDeVisitas;

    /*              Constructores              */
    public Horario(LocalTime hora, int cupos, int cantidadDeVisitas) {
        this.hora = hora;
        this.cupos = cupos;
        this.cantidadDeVisitas = cantidadDeVisitas;
    }

    public Horario() {}

    /*           Getters y setters           */
    public int obtenerIdHorario() { return idHorario; }
    public void establecerIdHorario(int idHorario) { this.idHorario = idHorario; }

    public LocalTime obtenerHora() { return hora; }
    public void establecerHora(LocalTime hora) { this.hora = hora; }

    public int obtenerCupos() { return cupos; }
    public void establecerCupos(int cupos) { this.cupos = cupos; }

    public int obtenerCantidadDeVisitas() { return cantidadDeVisitas; }
    public void establecerCantidadDeVisitas(int cantidadDeVisitas) { this.cantidadDeVisitas = cantidadDeVisitas; }
}