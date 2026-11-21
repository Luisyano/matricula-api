package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class IniciarSesion implements Task {
    private final String usuario;
    private final String clave;
    public IniciarSesion(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    public static IniciarSesion con(String usuario, String clave) {
        return instrumented(IniciarSesion.class, usuario, clave);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/auth/login").with(req -> req
                        .contentType("application/json")
                        .body(Map.of("username", usuario, "password", clave)))
        );
    }
}