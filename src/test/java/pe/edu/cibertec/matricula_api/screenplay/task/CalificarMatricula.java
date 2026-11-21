package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CalificarMatricula implements Task {
    private final String idMatricula;
    private final double nota;
    public CalificarMatricula(String idMatricula, double nota) {
        this.idMatricula = idMatricula;
        this.nota = nota;
    }
    public static CalificarMatricula conNota(String idMatricula, double nota) {
        return instrumented(CalificarMatricula.class, idMatricula, nota);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("token");
        actor.attemptsTo(
                Patch.to("/api/matriculas/" + idMatricula + "/nota").with(req -> req
                        .header("Authorization", "Bearer " + token)
                        .contentType("application/json")
                        .body(Map.of("nota", nota)))
        );
    }
}