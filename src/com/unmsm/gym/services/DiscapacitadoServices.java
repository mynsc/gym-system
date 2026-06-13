package src.com.unmsm.gym.services;

import java.util.Scanner;

import src.com.unmsm.gym.models.Discapacitado;
import src.com.unmsm.gym.enums.TipoDiscapacidad;
import src.com.unmsm.gym.enums.NivelDiscapacidad;

public class DiscapacitadoServices {
    Scanner scanner = new Scanner(System.in);


    public Discapacitado DiscapacitadoDto() {
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
     
        System.out.println(" Que tipo de discapacidad presenta:  ");
        int i = 1; 
        for (TipoDiscapacidad tipo : TipoDiscapacidad.values()) {
            System.out.println( i + ") " + tipo);
            i++;
        }
        int tipoSeleccionado = scanner.nextInt();
        TipoDiscapacidad tipoDiscapacidad = TipoDiscapacidad.values()[tipoSeleccionado - 1];

        System.out.println(" Que nivel de discapacidad presenta:  ");

        int j = 1;
        for (NivelDiscapacidad nivel : NivelDiscapacidad.values()) {
            System.out.println(j + ") " + nivel);
            j++;   
        }
        int nivelSeleccionado = scanner.nextInt();

        NivelDiscapacidad nivelDiscapacidad = NivelDiscapacidad.values()[nivelSeleccionado - 1];

        Discapacitado discapacitado = new Discapacitado(nombre, apellidos, codigo_estudiante, facultad, carrera, baseInicio, tipoDiscapacidad, nivelDiscapacidad);
        return discapacitado; 


    }

}
