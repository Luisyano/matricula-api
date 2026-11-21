package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class MatriculaStepDefinitions {

    private String token;
    private String idMatricula;
    private Map<String, Object> datosMatricula;

    @Dado("que el alumno {string} inicia sesión")
    public void queElAlumnoIniciaSesion(String usuario) {
        SerenityRest.given()
                .contentType("application/json")
                .body(Map.of("username", usuario, "password", "Cibertec2026"))
                .post("http://localhost:8080/api/auth/login");
        token = SerenityRest.lastResponse().jsonPath().getString("accessToken");
    }

    @Cuando("cotizo el curso {string} para el alumno {string}")
    public void cotizoElCursoParaElAlumno(String curso, String alumno) {
        SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("codigoAlumno", alumno, "cursos", List.of(curso)))
                .post("http://localhost:8080/api/matriculas/cotizacion");
    }

    @Cuando("cotizo los cursos {string}, {string}, {string}, {string}, {string} y {string} para el alumno {string}")
    public void cotizoLosCursosParaElAlumno(String c1, String c2, String c3, String c4, String c5, String c6, String alumno) {
        SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("codigoAlumno", alumno, "cursos", List.of(c1, c2, c3, c4, c5, c6)))
                .post("http://localhost:8080/api/matriculas/cotizacion");
    }

    @Cuando("matriculo al alumno {string} en el curso {string}")
    public void matriculoAlAlumnoEnElCurso(String alumno, String curso) {
        SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("codigoAlumno", alumno, "codigoCurso", curso))
                .post("http://localhost:8080/api/matriculas");
        if (SerenityRest.lastResponse().statusCode() == 201) {
            datosMatricula = SerenityRest.lastResponse().jsonPath().getMap("");
            idMatricula = String.valueOf(datosMatricula.get("id"));
        }
    }

    @Cuando("pago la matrícula con el método {string}")
    public void pagoLaMatriculaConElMetodo(String metodo) {
        SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(Map.of("metodo", metodo))
                .patch("http://localhost:8080/api/matriculas/" + idMatricula + "/pagar");
    }

    @Entonces("el código de respuesta es {int}")
    public void elCodigoDeRespuestaEs(int codigo) {
        SerenityRest.lastResponse().then().statusCode(codigo);
    }

    @Entonces("el total de la cotización es {string}")
    public void elTotalDeLaCotizacionEs(String total) {
        SerenityRest.lastResponse().then().body("total", equalTo(Float.parseFloat(total)));
    }

    @Entonces("el estado de la matrícula es {string}")
    public void elEstadoDeLaMatriculaEs(String estado) {
        SerenityRest.lastResponse().then().body("estado", equalTo(estado));
    }

    @Entonces("el monto de la matrícula es {string}")
    public void elMontoDeLaMatriculaEs(String monto) {
        SerenityRest.lastResponse().then().body("monto", equalTo(Float.parseFloat(monto)));
    }

    @Entonces("el código de error es {string}")
    public void elCodigoDeErrorEs(String codigoError) {
        SerenityRest.lastResponse().then().body("codigo", equalTo(codigoError));
    }

    @Entonces("el pago existe con método {string} y número de operación válido")
    public void elPagoExisteConMetodoYNumeroDeOperacionValido(String metodo) {
        SerenityRest.given()
                .header("Authorization", "Bearer " + token)
                .get("http://localhost:8080/api/pagos?alumno=i202610001");
        SerenityRest.lastResponse().then().body("metodo", hasItem(metodo));
        SerenityRest.lastResponse().then().body("numeroOperacion", everyItem(startsWith("OP-")));
    }
}