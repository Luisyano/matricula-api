# language: es
Característica: Seguridad de la sesión

  Escenario: Rotación del refresh token
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando renuevo el token con el refresh token
    Entonces el código de respuesta es 200
    Cuando intento reutilizar el refresh token original
    Entonces el código de respuesta es 401
    Y el código de error es "REFRESH_REUTILIZADO"

  Escenario: Cierre de sesión
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando cierro la sesión
    Entonces el código de respuesta es 204
    Cuando consulto mi perfil con el token anterior
    Entonces el código de respuesta es 401
    Y el código de error es "TOKEN_INVALIDO"

  Escenario: Bloqueo de cuenta por intentos fallidos
    Dado que el alumno "i202610004" intenta iniciar sesión con clave incorrecta 5 veces
    Entonces el quinto intento devuelve código 423
    Y el código de error es "CUENTA_BLOQUEADA"
    Cuando intento iniciar sesión con la clave correcta
    Entonces el código de respuesta es 423
    Y el código de error es "CUENTA_BLOQUEADA"

  Escenario: Alumno sin permiso de administrador
    Dado que el alumno "i202610001" inicia sesión para seguridad
    Cuando intento listar todos los alumnos
    Entonces el código de respuesta es 403
    Y el código de error es "ACCESO_DENEGADO"