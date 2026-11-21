package pe.edu.cibertec.matricula_api.screenplay.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import java.util.Map;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RenovarToken implements Task {
    private final String refreshToken;
    public RenovarToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public static RenovarToken con(String refreshToken) {
        return instrumented(RenovarToken.class, refreshToken);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to("/api/auth/refresh").with(req -> req
                        .contentType("application/json")
                        .body(Map.of("refreshToken", refreshToken)))
        );
    }
}