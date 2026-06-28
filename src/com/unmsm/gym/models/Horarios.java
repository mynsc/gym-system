package src.com.unmsm.gym.models;

import java.time.LocalTime;

public class Horarios {

    private int id_horarios;
    private LocalTime hora;
    private int cupos;
    private int cantVisitas;

    public Horarios(int id_horarios, LocalTime hora, int cupos, int cantVisitas) {
        this.id_horarios = id_horarios;
        this.hora = hora;
        this.cupos = cupos;
        this.cantVisitas = cantVisitas;
    }

    // constructor vacío
    public Horarios() {}

    public int getId_horarios() { return id_horarios; }
    public LocalTime getHora() { return hora; }
    public int getCupos() { return cupos; }
    public int getCantVisitas() { return cantVisitas; }

    public void setCupos(int cupos) { this.cupos = cupos; }
    public void setCantVisitas(int cantVisitas) { this.cantVisitas = cantVisitas; }
}