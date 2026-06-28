package src.com.unmsm.gym.models;

public class Reserva {
    private int id_reserva;
    private int idUsuario;
    private int idHorario;

    public Reserva(int id, int idUsuario, int idHorario) {
        this.id_reserva = id;
        this.idUsuario = idUsuario;
        this.idHorario = idHorario;
    }

    public Reserva() {}

    public int getId() { return id_reserva; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdHorario() { return idHorario; }

    public void setId(int id) { this.id_reserva = id; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }
}