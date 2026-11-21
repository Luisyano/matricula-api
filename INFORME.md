# Informe de Examen - Pruebas de Software
Alumno: FREDDY PENAGOS
Código: I202504051

## Pregunta 1: Automatización BDD con Gherkin + Cucumber

### Captura de la ejecución (Consola con Hook)
![Captura de la consola](captura_consola.png.png)

### Tabla de cálculo - Cotización PI5620
| Concepto | Cálculo | Monto |
| :--- | :--- | :--- |
| **Créditos del curso** | - | 6 |
| **Subtotal** | 6 créditos × S/ 120 | S/ 720.0 |
| **Descuento (20%)** | 720 × 0.20 (promedio 18) | - S/ 144.0 |
| **Total a pagar** | 720 - 144 | **S/ 576.0** |

**Explicación del error (Límite de créditos):**
La cotización de los 6 cursos falla con código HTTP 422 y el código `LIMITE_CREDITOS` porque la suma de créditos supera el tope máximo de 24 permitidos por periodo.