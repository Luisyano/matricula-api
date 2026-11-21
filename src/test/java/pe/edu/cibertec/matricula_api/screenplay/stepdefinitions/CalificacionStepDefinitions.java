package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import pe.edu.cibertec.matricula_api.screenplay.questions.HistorialQuestion;
import pe.edu.cibertec.matricula_api.screenplay.task.CalificarMatricula;
import pe.edu.cibertec.matricula_api.screenplay.task.ConsultarHistorial;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;

import java.util.Map;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class CalificacionStepDefinitions {

    private Actor actor = Actor.named("Docente").whoCan(CallAnApi.at("http://localhost:8080"));

    @Dado("que el docente {string} inicia sesión")
    public void queElDocenteIniciaSesion(String docente) {
        actor.attemptsTo(IniciarSesion.con(docente, "Cibertec2026"));
        actor.remember("token", SerenityRest.lastResponse().jsonPath().getString("accessToken"));
    }

    @Cuando("califico la matrícula de la alumna {string} en el curso {string} con nota {double}")
    public void calificoLaMatricula(String alumno, String curso, double nota) {
        SerenityRest.given().contentType("application/json")
                .body(Map.of("username", alumno, "password", "Cibertec2026"))
                .post("http://localhost:8080/api/auth/login");
        String tokenAlumna = SerenityRest.lastResponse().jsonPath().getString("accessToken");

        SerenityRest.given().header("Authorization", "Bearer " + tokenAlumna)
                .get("http://localhost:8080/api/matriculas?alumno=" + alumno + "&curso=" + curso);

        if (SerenityRest.lastResponse().statusCode() == 200 && !SerenityRest.lastResponse().jsonPath().getList("").isEmpty()) {
            String idMatricula = String.valueOf(SerenityRest.lastResponse().jsonPath().getList("id").get(0));
            actor.attemptsTo(CalificarMatricula.conNota(idMatricula, nota));
        } else {
            SerenityRest.lastResponse().then().statusCode(200);
        }
    }

    @Cuando("consulto el historial de la alumna {string}")
    public void consultoElHistorial(String alumno) {
        SerenityRest.given().contentType("application/json")
                .body(Map.of("username", alumno, "password", "Cibertec2026"))
                .post("http://localhost:8080/api/auth/login");
        String tokenAlumna = SerenityRest.lastResponse().jsonPath().getString("accessToken");
        actor.remember("token", tokenAlumna);

        actor.attemptsTo(ConsultarHistorial.de(alumno));
    }

    @Entonces("el historial muestra condición {string}, {int} créditos aprobados y promedio {double}")
    public void elHistorialMuestra(String condicion, int creditos, double promedio) {
        actor.should(seeThat(HistorialQuestion.con(condicion, creditos, promedio)));
    }
}