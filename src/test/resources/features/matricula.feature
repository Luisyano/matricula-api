# language: es
Característica: Matrícula del alumno

  Antecedentes:
    Dado que el alumno "i202610001" inicia sesión

  Escenario: Cotizar el curso PI5620
    Cuando cotizo el curso "PI5620" para el alumno "i202610001"
    Entonces el código de respuesta es 200
    Y el total de la cotización es "576.0"

  Escenario: Matricularse y pagar el curso PI5620
    Cuando matriculo al alumno "i202610001" en el curso "PI5620"
    Entonces el código de respuesta es 201
    Y el estado de la matrícula es "REGISTRADA"
    Y el monto de la matrícula es "576.0"
    Cuando pago la matrícula con el método "TARJETA"
    Entonces el código de respuesta es 200
    Y el estado de la matrícula es "PAGADA"
    Y el pago existe con método "TARJETA" y número de operación válido

  Escenario: Regla de negocio que debe fallar - Límite de créditos
    Cuando cotizo los cursos "PS5511", "QA5517", "DM5512", "TS5514", "SA5516" y "BD5410" para el alumno "i202610001"
    Entonces el código de respuesta es 422
    Y el código de error es "LIMITE_CREDITOS"