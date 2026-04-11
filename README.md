# Centro deportivo
Sistema el cual permita al dueño de un centro deportivo gestionar tanto 
sus canchas como los turnos que irá programando para sus clientes.

## Lo que aprendí
Manejo de auditoria basica mediante etiquetas "@CreationTimestamp" y "@UpdateTimestamp". 
Muy útiles ya que delegan la tarea de asignar esos datos a la base de datos.

Configuracion de application-${entorno}.properties ya sea para dev, test o cualquier otro entorno que querramos tener predefinido.

## ¿Qué se puede hacer?

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
  "id": "Long",
  "nombre": "String",
  "tipo": "String",
  "creacion": "LocalDateTime",
  "ultimaActualizacion": "LocalDateTime"
}
````
