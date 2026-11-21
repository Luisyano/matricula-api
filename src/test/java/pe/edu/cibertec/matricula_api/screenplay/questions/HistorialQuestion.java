package pe.edu.cibertec.matricula_api.screenplay.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class HistorialQuestion implements Question<Boolean> {
    private final String condicion;
    private final int creditos;
    private final double promedio;

    public HistorialQuestion(String condicion, int creditos, double promedio) {
        this.condicion = condicion;
        this.creditos = creditos;
        this.promedio = promedio;
    }

    public static HistorialQuestion con(String condicion, int creditos, double promedio) {
        return new HistorialQuestion(condicion, creditos, promedio);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        var json = SerenityRest.lastResponse().jsonPath();
        String condicionActual = json.getString("cursos.find { it.codigoCurso == 'PI5620' }.condicion");
        return condicion.equals(condicionActual)
                && json.getInt("creditosAprobados") == creditos
                && Math.abs(json.getDouble("promedioPonderado") - promedio) < 0.01;
    }
}