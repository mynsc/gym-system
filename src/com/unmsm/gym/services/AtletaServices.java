package src.com.unmsm.gym.services;

import java.util.Scanner;

import src.com.unmsm.gym.models.Atleta;
import src.com.unmsm.gym.enums.Deportes;

public class AtletaServices {
    Scanner scanner = new Scanner(System.in);

    public Atleta AtletaDto(){
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Código de Estudiante: ");
        String codigo_estudiante = scanner.nextLine();
        System.out.print("Facultad: ");
        String facultad = scanner.nextLine();
        System.out.print("Carrera: ");
        String carrera = scanner.nextLine();
        System.out.print("Base de Inicio: ");
        String baseInicio = scanner.nextLine();
     
        System.out.println("Deporte Perteneciente :   ");
        int i = 1 ;
        for (Deportes deporte : Deportes.values()) {
            System.out.println(i + ") " + deporte);  
        }

        int DeporteSeleccionado = scanner.nextInt();

        Deportes deporte = Deportes.values()[DeporteSeleccionado - 1];

        Atleta nuevoAtleta = new Atleta(nombre, apellidos, codigo_estudiante, facultad, carrera, baseInicio, deporte);
        return nuevoAtleta;
    }
}
