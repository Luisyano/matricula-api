package pe.edu.cibertec.matricula_api.screenplay.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import pe.edu.cibertec.matricula_api.screenplay.task.CerrarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.IniciarSesion;
import pe.edu.cibertec.matricula_api.screenplay.task.RenovarToken;

public class SeguridadStepDefinitions {

    private Actor actor = Actor.named("Ana").whoCan(CallAnApi.at("http://localhost:8080"));

    @Dado("que el alumno {string} inicia sesión para seguridad")
    public void queElAlumnoIniciaSesionParaSeguridad(String usuario) {
        actor.attemptsTo(IniciarSesion.con(usuario, "Cibertec2026"));
        String token = SerenityRest.lastResponse().jsonPath().getString("accessToken");
        String refresh = SerenityRest.lastResponse().jsonPath().getString("refreshToken");
        actor.remember("token", token);
        actor.remember("refresh", refresh);
    }

    @Cuando("renuevo el token con el refresh token")
    public void renuevoElTokenConElRefreshToken() {
        String refresh = actor.recall("refresh");
        actor.attemptsTo(RenovarToken.con(refresh));
        actor.remember("nuevoToken", SerenityRest.lastResponse().jsonPath().getString("accessToken"));
        actor.remember("nuevoRefresh", SerenityRest.lastResponse().jsonPath().getString("refreshToken"));
    }

    @Cuando("intento reutilizar el refresh token original")
    public void intentoReutilizarElRefreshTokenOriginal() {
        String refresh = actor.recall("refresh");
        actor.attemptsTo(RenovarToken.con(refresh));
    }

    @Cuando("cierro la sesión")
    public void cierroLaSesion() {
        actor.attemptsTo(CerrarSesion.ahora());
    }

    @Cuando("consulto mi perfil con el token anterior")
    public void consultoMiPerfilConElTokenAnterior() {
        String token = actor.recall("token");
        actor.attemptsTo(Get.resource("/api/auth/perfil").with(req -> req.header("Authorization", "Bearer " + token)));
    }

    @Dado("que el alumno {string} intenta iniciar sesión con clave incorrecta 5 veces")
    public void intentaIniciarSesionConClaveIncorrecta5Veces(String usuario) {
        for (int i = 0; i < 5; i++) {
            actor.attemptsTo(IniciarSesion.con(usuario, "ClaveIncorrecta123"));
        }
    }

    @Cuando("intento iniciar sesión con la clave correcta")
    public void intentoIniciarSesionConLaClaveCorrecta() {
        actor.attemptsTo(IniciarSesion.con("i202610004", "Cibertec2026"));
    }

    @Cuando("intento listar todos los alumnos")
    public void intentoListarTodosLosAlumnos() {
        String token = actor.recall("token");
        actor.attemptsTo(Get.resource("/api/alumnos").with(req -> req.header("Authorization", "Bearer " + token)));
    }

    // Este es el único "Entonces" que es exclusivo de esta clase
    @Entonces("el quinto intento devuelve código {int}")
    public void elQuintoIntentoDevuelveCodigo(int codigo) {
        SerenityRest.lastResponse().then().statusCode(codigo);
    }
}