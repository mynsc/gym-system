package src.com.unmsm.gym.models;

import java.util.List;

public class Rutina {
    /*               Atributos               */
    private int id;
    private String nombre;
    private String objetivo;
    private Estudiante estudiante;
    private List<List<String>> ejercicios;

    /*              Constructor              */
    public Rutina(int id, String nombre, String objetivo, Estudiante estudiante, List<List<String>> ejercicios) {
        this.id = id;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.estudiante = estudiante;
        this.ejercicios = ejercicios;
    }

    /*               Metodos               */
    public String mostrarDetallesDeRutina() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("=========================================\n")
                .append("Nombre: ").append(this.nombre).append("\n")
                .append("Objetivo: ").append(this.objetivo).append("\n")
                .append("-----------------------------------------\n");
        
        // listar ejercicios de una rutina
        detalles.append(obtenerDetallesDeEjercicios());
        detalles.append("=========================================");
        
        return detalles.toString();
    }

    public String obtenerDetallesDeEjercicios() {
        if (this.ejercicios == null || this.ejercicios.isEmpty()) {
            return "(!) No hay ejercicios asignados a esta rutina aún\n";
        }

        StringBuilder listaFormateada = new StringBuilder();
        listaFormateada.append("   Ejercicios asignados:\n");

        for (int i = 0; i < this.ejercicios.size(); i++) {
            List<String> ejercicio = this.ejercicios.get(i);
            
            // verificar que la sublista tenga exactamente los 3 elementos para evitar errores de indice
            if (ejercicio != null && ejercicio.size() >= 3) {
                String tipo = ejercicio.get(0);
                String sets = ejercicio.get(1);
                String repeticiones = ejercicio.get(2);
                
                listaFormateada.append(String.format("   %d. %s  -->  %s series x %s reps\n", 
                        (i + 1), tipo, sets, repeticiones));
            }
        }
        
        return listaFormateada.toString();
    }

    /*           Getters y setters           */
    public int obtenerId() { return id; }
    public void establecerId(int id) { this.id = id; }
    
    public String obtenerNombre() {return nombre; }
    public void establecerNombre(String nombre) { this.nombre = nombre; }

    public String obtenerObjetivo() { return objetivo; }
    public void establecerObjetivo(String objetivo) { this.objetivo = objetivo; }

    public Estudiante obtenerEstudiante() { return estudiante; }
    public void establecerEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public List<List<String>> obtenerEjercicios() {  return ejercicios;  }
}