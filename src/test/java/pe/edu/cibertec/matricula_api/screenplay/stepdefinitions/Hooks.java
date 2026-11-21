package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    @Before
    public void imprimirDatosEstudiante() {
        // 1. Iniciar el escenario de Serenity (para que no fallen las pruebas del profesor)
        OnStage.setTheStage(new OnlineCast());

        // 2. Tus datos
        String nombre = "FREDDY PENAGOS";
        String codigo = "I202504051";
        String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        System.out.println("=====================================================");
        System.out.println("ESTUDIANTE: " + nombre);
        System.out.println("CÓDIGO:     " + codigo);
        System.out.println("FECHA/HORA: " + fechaHora);
        System.out.println("=====================================================");
    }
}