# DropZone

DropZone es una aplicación distribuida desarrollada en Java que permite compartir archivos entre clientes mediante una arquitectura cliente-servidor utilizando sockets TCP.

## Características

- Login de usuarios.
- Creación de salas.
- Unión a salas.
- Subida de archivos.
- Descarga de archivos.
- Verificación de integridad mediante checksum.
- Interfaz gráfica para cliente.
- Interfaz gráfica para servidor.
- Comunicación mediante sockets.
- Manejo concurrente mediante hilos.

## Tecnologías utilizadas

- Java
- Swing
- Sockets TCP
- Threads
- ZeroTier

## Patrones de diseño implementados

- Singleton
- Factory
- Command
- Observer
- Strategy
- Builder

## Colecciones utilizadas

- List
- Map
- Set

## Genéricos utilizados

- Repository<T>
- Response<T>

## Ejecución

### Servidor

Ejecutar:

```txt
server.ServerMain
