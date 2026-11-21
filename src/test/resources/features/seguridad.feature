# language: es
Característica: Seguridad de la sesión

  Escenario: Rotación del refresh token
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando renuevo el token con el refresh token
    Entonces el código de respuesta es 200
    Cuando intento reutilizar el refresh token original
    Entonces el código de respuesta es 401
    Y el código de error es "REFRESH_REUTILIZADO"
    Y la respuesta de matrícula cumple el esquema "error.json"
    Cuando intento consultar mi perfil con el token nuevo
    Entonces el código de respuesta es 401
    Y el código de error es "TOKEN_INVALIDO"
    Y la respuesta de matrícula cumple el esquema "error.json"

  Escenario: Cierre de sesión
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando cierro la sesión
    Entonces el código de respuesta es 204
    Cuando consulto mi perfil con el token anterior
    Entonces el código de respuesta es 401
    Y el código de error es "TOKEN_INVALIDO"
    Y la respuesta de matrícula cumple el esquema "error.json"

  Esquema del escenario: Bloqueo de cuenta por intentos fallidos
    Cuando el alumno "i202610004" intenta iniciar sesión con clave incorrecta en el intento <intento>
    Entonces el código de respuesta es <http>
    Y el código de error es "<codigo>"
    Y la respuesta de matrícula cumple el esquema "error.json"
    Ejemplos:
      | intento | http | codigo               |
      | 1       | 401  | CREDENCIALES_INVALIDAS |
      | 2       | 401  | CREDENCIALES_INVALIDAS |
      | 3       | 401  | CREDENCIALES_INVALIDAS |
      | 4       | 401  | CREDENCIALES_INVALIDAS |
      | 5       | 423  | CUENTA_BLOQUEADA     |

  Escenario: Con la clave correcta tampoco puede ingresar
    Cuando intento iniciar sesión con la clave correcta
    Entonces el código de respuesta es 423
    Y el código de error es "CUENTA_BLOQUEADA"
    Y la respuesta de matrícula cumple el esquema "error.json"

  Escenario: Alumno sin permiso de administrador
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando intento listar todos los alumnos
    Entonces el código de respuesta es 403
    Y el código de error es "ACCESO_DENEGADO"
    Y la respuesta de matrícula cumple el esquema "error.json"