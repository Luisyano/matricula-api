package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConsultarHistorial implements Task {
    private final String alumno;
    public ConsultarHistorial(String alumno) {
        this.alumno = alumno;
    }
    public static ConsultarHistorial de(String alumno) {
        return instrumented(ConsultarHistorial.class, alumno);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("token");
        actor.attemptsTo(
                Get.resource("/api/alumnos/" + alumno + "/historial").with(req -> req
                        .header("Authorization", "Bearer " + token))
        );
    }
}