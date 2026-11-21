package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class HistorialQuestion implements Question<Boolean> {
    private final int creditos;
    private final double promedio;

    public HistorialQuestion(int creditos, double promedio) {
        this.creditos = creditos;
        this.promedio = promedio;
    }

    public static HistorialQuestion con(int creditos, double promedio) {
        return new HistorialQuestion(creditos, promedio);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        int creditosActuales = SerenityRest.lastResponse().jsonPath().getInt("creditosAprobados");
        float promedioActual = SerenityRest.lastResponse().jsonPath().getFloat("promedioPonderado");

        // Retorna true si los créditos y el promedio coinciden (con un margen de error de 0.1)
        return creditosActuales == creditos
                && Math.abs(promedioActual - promedio) < 0.1;
    }
}