# DESPLIEGUE_IMPLEMENTACION.md

## Proyecto

DropZone - Plataforma distribuida para compartir archivos en tiempo real mediante arquitectura cliente-servidor.

## Requisitos

- Java JDK.
- NetBeans.
- ZeroTier instalado.
- Ambos equipos conectados a la misma red virtual ZeroTier.
- Puerto 5000 disponible.

## Red ZeroTier

Network ID:

743993800f551373

IP ZeroTier del servidor:

10.255.135.40

Puerto utilizado:

5000

## Ejecución del servidor

1. Abrir el proyecto en NetBeans.
2. Ejecutar la clase:

server.ServerMain

3. En la ventana del servidor presionar:

Iniciar servidor

4. El servidor queda escuchando conexiones en el puerto 5000.

## Ejecución del cliente

1. Abrir el proyecto en NetBeans.
2. Ejecutar la clase:

client.ClientMain

3. Presionar el botón:

Conectar

4. Cuando el sistema solicite la IP del servidor, ingresar:

10.255.135.117

Para pruebas locales también se puede usar:

localhost

## Pasos de demostración

1. Iniciar el servidor.
2. Iniciar el cliente.
3. Conectarse usando la IP ZeroTier del servidor.
4. Realizar login con un usuario.
5. Crear una sala.
6. Unirse a la sala.
7. Subir un archivo.
8. Listar los archivos.
9. Descargar el archivo.
10. Verificar que el archivo descargado aparece en la carpeta:

client_downloads

## Funcionalidades implementadas

- Arquitectura cliente-servidor.
- Comunicación por sockets.
- Manejo de múltiples clientes mediante hilos.
- Login de usuarios.
- Creación de salas.
- Unión a salas.
- Subida de archivos binarios.
- Descarga de archivos binarios.
- Transferencia por bloques usando buffers.
- Uso de InputStream y OutputStream.
- Validación de archivos mediante checksum MD5.
- Progreso de subida y descarga.
- Interfaz gráfica para cliente.
- Interfaz gráfica para servidor.
- Logs del servidor.

## Colecciones utilizadas

- List: usuarios y archivos dentro de las salas.
- Map: almacenamiento de usuarios y salas.
- Set: manejo de nombres de salas.

## Genéricos utilizados

- Repository<T>
- Response<T>

## Patrones de diseño utilizados

- Singleton: configuración del servidor.
- Factory: identificación y creación de comandos.
- Command: manejo de acciones del sistema.
- Observer: estructura para notificaciones.
- Strategy: cálculo de checksum.
- Builder: construcción de metadatos de archivos.

## Observaciones

Los datos se almacenan en memoria durante la ejecución. 
Si el servidor se reinicia, se deben crear nuevamente las salas y 
subir nuevamente los archivos para la demostración.
