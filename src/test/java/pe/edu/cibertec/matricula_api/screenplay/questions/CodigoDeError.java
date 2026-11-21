package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class CodigoDeError implements Question<String> {
    public static CodigoDeError obtenido() {
        return new CodigoDeError();
    }
    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString("codigo");
    }
}