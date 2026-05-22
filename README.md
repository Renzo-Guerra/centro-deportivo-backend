# Centro deportivo
Sistema el cual permita al dueño de un centro deportivo gestionar tanto 
sus canchas como los turnos que irá programando para sus clientes.

## Lo que aprendí
Manejo de auditoria basica mediante etiquetas "@CreationTimestamp" y "@UpdateTimestamp". 
Muy útiles ya que delegan la tarea de asignar esos datos a la base de datos.

Configuracion de application-${entorno}.properties ya sea para dev, test o cualquier otro entorno que querramos tener predefinido.

---

## Indice

1. [Ir a AUTENTICACION endpoints](#autenticación)
1. [Ir a CANCHAS endpoints](#canchas)
1. [Ir a TURNOS endpoints](#turnos)

---

### AUTENTICACIÓN

POST: /api/autenticacion

Descripción: Loguearse mediante el mail y contraseña. El usuario debe previamente estár registrado en la base de datos.

Body:
````JSON
{
  "email": "String",
  "contrasenia": "String"
}
````
Payload:
````JSON
{
  "token": "String"
}
````

---

### CANCHAS

POST: /api/canchas

CanchaEnum = "FUTBOL" | "VOLEY" | "TENIS" | "PADEL"

Body:
````JSON
{
  "nombre": "String",
  "tipo": "FUTBOL | VOLEY | TENIS | PADEL"
}
````

Payload:
````JSON
{
  "id": "Long",
  "nombre": "String",
  "tipo": "FUTBOL | VOLEY | TENIS | PADEL",
  "creacion": "LocalDateTime",
  "ultimaActualizacion": "LocalDateTime"
}
````

---

GET: /api/canchas

Descripcion: Devuelve un paginado de canchas

Payload:
````JSON
{
  "content": [{
    "id": "Long",
    "nombre": "String",
    "tipo": "FUTBOL | VOLEY | TENIS | PADEL",
    "creacion": "LocalDateTime",
    "ultimaActualizacion": "LocalDateTime"
  }],
  "pageNo": "int",
  "pageSize": "int",
  "totalElements": "long",
  "totalPages": "int",
  "last": "boolean"
}
````

---

GET: /api/canchas/all

Descripcion: Devuelve TODAS las canchas

Payload:
````JSON
[
  {
    "id": "Long",
    "nombre": "String",
    "tipo": "FUTBOL | VOLEY | TENIS | PADEL",
    "creacion": "LocalDateTime",
    "ultimaActualizacion": "LocalDateTime"
  }
]

````

---

GET: /api/canchas/{id}

Descripcion: Devuelve una cancha por su id

Payload:
````JSON
{
  "id": "Long",
  "nombre": "String",
  "tipo": "FUTBOL | VOLEY | TENIS | PADEL",
  "creacion": "LocalDateTime",
  "ultimaActualizacion": "LocalDateTime"
}

````

---

PUT: /api/canchas/{id}

Descripcion: Edita los datos de una cancha (mientras esta no tenga turnos programados para el futuro)

Body:
````JSON
{
  "nombre": "String",
  "tipo": "FUTBOL | VOLEY | TENIS | PADEL"
}
````

Payload:
````JSON
{
  "id": "Long",
  "nombre": "String",
  "tipo": "FUTBOL | VOLEY | TENIS | PADEL",
  "creacion": "LocalDateTime",
  "ultimaActualizacion": "LocalDateTime"
}

````

---

DELETE: /api/canchas/{id}

Descripcion: Borra una cancha (mientras esta no tenga turnos programados para el futuro)

Payload: Void

---


### TURNOS

POST: /api/turnos

Descripcion: Crea un turno, un turno para ser valido debe iniciar en punto o y media.
Los turnos solo pueden durar 1 hora o media hora

Body:
````JSON
{
  "nombreCliente": "String",
  "apellidoCliente": "String",
  "celularCliente": "String",
  "idCancha": "Long",
  "inicioTurno": "LocalDateTime",
  "duracionTurnoMinutos": "Long"
}
````

Payload:

````JSON
{
  "id": "Long",
  "nombreCliente": "String",
  "apellidoCliente": "String",
  "celularCliente": "String",
  "creacionTurno": "LocalDateTime",
  "inicioTurno": "LocalDateTime",
  "duracionTurnoMinutos": "Long",
  "idCancha": "Long",
  "nombreCancha": "String",
  "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
}

````

---

GET: /api/turnos?pageNo=0&pageSize=10&sortBy=inicioTurno,asc&sortBy=id,desc

sortBy puede incluir: 
* id
* nombreCliente
* apellidoCliente
* celularCliente
* inicioTurno
* finTurno
* creacion
* ultimaActualizacion
Cada parametro debe de ordenarse ASC o DESC

Descripcion: Devuelve una pagina de turnos, opcionalmente puede ser ordenada por parametros

Payload:

````JSON
{
  "content": [{
    "id": "Long",
    "nombreCliente": "String",
    "apellidoCliente": "String",
    "celularCliente": "String",
    "creacionTurno": "LocalDateTime",
    "inicioTurno": "LocalDateTime",
    "duracionTurnoMinutos": "Long",
    "idCancha": "Long",
    "nombreCancha": "String",
    "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
  }],
  "pageNo": "int",
  "pageSize": "int",
  "totalElements": "long",
  "totalPages": "int",
  "last": "boolean"
}

````

GET: /api/turnos/all?sortBy=inicioTurno,asc&sortBy=id,desc

sortBy puede incluir:
* id
* nombreCliente
* apellidoCliente
* celularCliente
* inicioTurno
* finTurno
* creacion
* ultimaActualizacion
  Cada parametro debe de ordenarse ASC o DESC

Descripcion: Devuelve TODOS los turnos, opcionalmente puede ser ordenada por parametros

Payload:

````JSON
[
  {
    "id": "Long",
    "nombreCliente": "String",
    "apellidoCliente": "String",
    "celularCliente": "String",
    "creacionTurno": "LocalDateTime",
    "inicioTurno": "LocalDateTime",
    "duracionTurnoMinutos": "Long",
    "idCancha": "Long",
    "nombreCancha": "String",
    "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
  }
]
````

---

GET: /api/turnos/fecha?fecha={yyyy-MM-dd}&sortBy=inicioTurno&direction=ASC

sortBy puede incluir:
* id
* nombreCliente
* apellidoCliente
* celularCliente
* inicioTurno
* finTurno
* creacion
* ultimaActualizacion

Solo se puede ordenar por 1 parametro, el orden de dicho parametro se especifica con ASC o DESC

Descripcion: Devuelve TODOS los turnos de una fecha proporcionada, 
opcionalmente puede ser ordenada por un unico parametro

Payload:

````JSON
[
  {
    "id": "Long",
    "nombreCliente": "String",
    "apellidoCliente": "String",
    "celularCliente": "String",
    "creacionTurno": "LocalDateTime",
    "inicioTurno": "LocalDateTime",
    "duracionTurnoMinutos": "Long",
    "idCancha": "Long",
    "nombreCancha": "String",
    "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
  }
]
````

---

GET: /api/turnos/rango?desde={yyyy-MM-dd}&hasta={yyyy-MM-dd}&pageNo=0&pageSize=10&sortBy=inicioTurno,desc&sortBy=tipo,asc

sortBy puede incluir:
* id
* nombreCliente
* apellidoCliente
* celularCliente
* inicioTurno
* finTurno
* creacion
* ultimaActualizacion

Cada parametro debe de ordenarse ASC o DESC

Descripcion: Devuelve TODOS los turnos entre 2 fechas proporcionadas (ambas fechas incluidas),
opcionalmente puede ser ordenada por un una lista de parametros con ASC y DESC

Payload:

````JSON
[
  {
    "id": "Long",
    "nombreCliente": "String",
    "apellidoCliente": "String",
    "celularCliente": "String",
    "creacionTurno": "LocalDateTime",
    "inicioTurno": "LocalDateTime",
    "duracionTurnoMinutos": "Long",
    "idCancha": "Long",
    "nombreCancha": "String",
    "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
  }
]
````

---

GET: /api/canchas/{id}/turnos?pageNo=0&pageSize=10&sortBy=inicioTurno&direction=asc

sortBy puede incluir:
* id
* nombreCliente
* apellidoCliente
* celularCliente
* inicioTurno
* finTurno
* creacion
* ultimaActualizacion

Solo se puede ordenar por 1 parametro, el orden de dicho parametro se especifica con ASC o DESC

Descripcion: Devuelve los turnos de una cancha mediante la paginacion,
opcionalmente puede ser ordenada por un parametro con direction ASC o DESC

Payload:

````JSON
{
  "content": [{
    "id": "Long",
    "nombreCliente": "String",
    "apellidoCliente": "String",
    "celularCliente": "String",
    "creacionTurno": "LocalDateTime",
    "inicioTurno": "LocalDateTime",
    "duracionTurnoMinutos": "Long",
    "idCancha": "Long",
    "nombreCancha": "String",
    "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
  }],
  "pageNo": "int",
  "pageSize": "int",
  "totalElements": "long",
  "totalPages": "int",
  "last": "boolean"
}
````

---

PUT: /api/turnos/{id}

Descripcion: Edita los datos de un turno 

Body:
````JSON
{
  "nombreCliente": "String",
  "apellidoCliente": "String",
  "celularCliente": "String",
  "idCancha": "Long",
  "inicioTurno": "LocalDateTime",
  "duracionTurnoMinutos": "Long"
}
````

Payload:
````JSON
{
  "id": "Long",
  "nombreCliente": "String",
  "apellidoCliente": "String",
  "celularCliente": "String",
  "creacionTurno": "LocalDateTime",
  "inicioTurno": "LocalDateTime",
  "duracionTurnoMinutos": "Long",
  "idCancha": "Long",
  "nombreCancha": "String",
  "deporte": "FUTBOL | VOLEY | TENIS | PADEL"
}

````

---

DELETE: /api/turnos/{id}

Payload: Void

---