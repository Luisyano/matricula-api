package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CerrarSesion implements Task {
    public static CerrarSesion ahora() {
        return instrumented(CerrarSesion.class);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("token");
        actor.attemptsTo(
                Post.to("/api/auth/logout").with(req -> req
                        .header("Authorization", "Bearer " + token))
        );
    }
}