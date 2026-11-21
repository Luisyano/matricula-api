# language: es
Característica: Calificación de matrículas

  Antecedentes:
    Dado que el docente "D0001" inicia sesión

  Esquema del escenario: El docente registra la nota y el historial se actualiza
    Cuando califico la matrícula de la alumna "i202610005" en el curso "PI5620" con nota <nota>
    Entonces el código de respuesta es 200
    Y consulto el historial de la alumna "i202610005"
    Entonces el código de respuesta es 200
    Y el historial muestra condición "<condicion>", <creditos> créditos aprobados y promedio <promedio>
    Ejemplos:
      | nota | condicion   | creditos | promedio |
      | 11   | DESAPROBADO | 7        | 13,38    |
      | 12,5 | DESAPROBADO | 7        | 14,08    |

  Escenario: Un docente que no dicta el curso intenta registrar la nota
    Dado que el docente "D0002" inicia sesión
    Cuando califico la matrícula de la alumna "i202610005" en el curso "PI5620" con nota 15
    Entonces el código de respuesta es 403
    Y el código de error es "ACCESO_DENEGADO"

  Escenario: No se puede calificar una matrícula que no está pagada
    Cuando califico la matrícula de la alumna "i202610005" en el curso "PS5511" con nota 15
    Entonces el código de respuesta es 409
    Y el código de error es "MATRICULA_NO_PAGADA"