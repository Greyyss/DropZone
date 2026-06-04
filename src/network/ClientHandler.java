package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import model.FileMetadata;
import model.Room;
import model.User;
import patterns.factory.CommandFactory;
import patterns.factory.CommandType;
import patterns.observer.NotificationManager;
import patterns.observer.ServerLogObserver;
import patterns.strategy.ChecksumStrategy;
import patterns.strategy.MD5ChecksumStrategy;
import server.ServerLogger;
import service.FileService;
import service.RoomService;
import service.UserService;

/**
 * Handles communication between the server and a connected client.
 *
 * @author Grace
 * @author Dilan
 * @author Luis
 */
public class ClientHandler extends Thread {

    /** Client socket connection. */
    private Socket socket;

    /** User currently logged in. */
    private User currentUser;

    /** Room currently joined by the user. */
    private Room currentRoom;

    /** Manager used to notify server events. */
    private NotificationManager notificationManager;

    /** Service used for user operations. */
    private UserService userService;

    /** Service used for room operations. */
    private RoomService roomService;

    /** Service used for file operations. */
    private FileService fileService;

    /** Strategy used to calculate file checksums. */
    private ChecksumStrategy checksumStrategy;

    /** Logger used to store server messages. */
    private ServerLogger logger;

    /**
     * Creates a client handler without logger.
     *
     * @param socket client socket
     */
    public ClientHandler(Socket socket) {

        this.socket = socket;

        this.userService = new UserService();
        this.roomService = new RoomService();
        this.fileService = new FileService();
        this.checksumStrategy = new MD5ChecksumStrategy();

        this.notificationManager = new NotificationManager();
    }

    /**
     * Creates a client handler with logger support.
     *
     * @param socket client socket
     * @param logger server logger
     */
    public ClientHandler(Socket socket, ServerLogger logger) {

        this.socket = socket;
        this.logger = logger;

        this.userService = new UserService();
        this.roomService = new RoomService();
        this.fileService = new FileService();
        this.checksumStrategy = new MD5ChecksumStrategy();

        this.notificationManager = new NotificationManager();

        if (logger != null) {
            this.notificationManager.addObserver(
                    new ServerLogObserver(logger)
            );
        }
    }

    /**
     * Runs the client communication loop.
     */
    @Override
    public void run() {

        try {

            DataInputStream reader
                    = new DataInputStream(socket.getInputStream());

            DataOutputStream writer
                    = new DataOutputStream(socket.getOutputStream());

            send(writer, "Conectado al servidor DropZone");

            while (true) {

                String line = reader.readUTF();

                CommandType command
                        = CommandFactory.createCommand(line);

                switch (command) {

                    case LOGIN:
                        handleLogin(reader, writer);
                        break;

                    case CREATE_ROOM:
                        handleCreateRoom(reader, writer);
                        break;

                    case JOIN_ROOM:
                        handleJoinRoom(reader, writer);
                        break;

                    case LIST_FILES:
                        handleListFiles(writer);
                        break;

                    case UPLOAD_FILE:
                        handleUploadFile(reader, writer);
                        break;

                    case DOWNLOAD_FILE:
                        handleDownloadFile(reader, writer);
                        break;
                    case NOTIFICATION:
                        send(writer, "RESPONSE Notificación recibida");
                        break;

                    case RESPONSE:
                        send(writer, "RESPONSE Respuesta recibida");
                        break;

                    default:
                        send(writer, "ERROR Comando desconocido");
                        break;
                }
            }

        } catch (Exception e) {

            System.out.println("Cliente desconectado o error en ClientHandler");
            e.printStackTrace();

            notifyLog("Cliente desconectado o error: " + e.getMessage());

        } finally {

            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("No se pudo cerrar el socket");
            }
        }
    }

    /**
     * Handles the user login process.
     *
     * @param reader input stream
     * @param writer output stream
     * @throws Exception if an input or output error occurs
     */
    private void handleLogin(
        DataInputStream reader,
        DataOutputStream writer) throws Exception {

    send(writer, "Ingrese nombre de usuario:");

    String nombre = reader.readUTF();

    send(writer, "Ingrese contraseña:");

    String password = reader.readUTF();

    currentUser = userService.login(nombre, password);

    if (currentUser == null) {
        send(writer, "ERROR Usuario o contraseña incorrectos");
        return;
    }

    send(writer, "LOGIN_OK Bienvenido " + currentUser.getName());

    notifyLog(currentUser.getName() + " inició sesión");
}

    /**
     * Handles the creation of a room.
     *
     * @param reader input stream
     * @param writer output stream
     * @throws Exception if an input or output error occurs
     */
    private void handleCreateRoom(
            DataInputStream reader,
            DataOutputStream writer) throws Exception {

        if (currentUser == null) {
            send(writer, "ERROR Primero debe hacer LOGIN");
            return;
        }

        send(writer, "Ingrese nombre de la sala:");

        String roomName = reader.readUTF();

        boolean created = roomService.createRoom(roomName);

        if (created) {

            send(writer, "ROOM_CREATED Sala creada: " + roomName);

            notifyLog(currentUser.getName()
                    + " creó la sala "
                    + roomName);

        } else {
            send(writer, "ERROR La sala ya existe");
        }
    }

    /**
     * Handles joining an existing room.
     *
     * @param reader input stream
     * @param writer output stream
     * @throws Exception if an input or output error occurs
     */
    private void handleJoinRoom(
            DataInputStream reader,
            DataOutputStream writer) throws Exception {

        if (currentUser == null) {
            send(writer, "ERROR Primero debe hacer LOGIN");
            return;
        }

        send(writer, "Ingrese nombre de la sala:");

        String roomName = reader.readUTF();

        boolean joined = roomService.joinRoom(roomName, currentUser);

        if (joined) {

            currentRoom = roomService.getRoom(roomName);

            send(writer, "JOIN_OK Unido a sala: " + roomName);

            notifyLog(currentUser.getName()
                    + " se unió a la sala "
                    + roomName);

        } else {
            send(writer, "ERROR La sala no existe");
        }
    }

    /**
     * Sends the file list of the current room.
     *
     * @param writer output stream
     * @throws Exception if an output error occurs
     */
    private void handleListFiles(DataOutputStream writer) throws Exception {

        if (currentRoom == null) {
            send(writer, "ERROR Primero debe unirse a una sala");
            return;
        }

        List<FileMetadata> files = fileService.listFiles(currentRoom);

        send(writer, "FILES_LIST");

        if (files.isEmpty()) {
            send(writer, "No hay archivos en esta sala");
        } else {

            for (FileMetadata file : files) {
                send(writer,
                        file.getName()
                        + " | "
                        + file.getSize()
                        + " bytes | propietario: "
                        + file.getOwner());
            }
        }

        send(writer, "END_FILES");
    }

    /**
     * Handles file upload from the client.
     *
     * @param reader input stream
     * @param writer output stream
     * @throws Exception if an input or output error occurs
     */
    private void handleUploadFile(
            DataInputStream reader,
            DataOutputStream writer) throws Exception {

        if (currentUser == null) {
            send(writer, "ERROR Primero debe hacer LOGIN");
            return;
        }

        if (currentRoom == null) {
            send(writer, "ERROR Primero debe unirse a una sala");
            return;
        }

        send(writer, "READY");

        String fileName = reader.readUTF();
        long fileSize = Long.parseLong(reader.readUTF());
        String clientChecksum = reader.readUTF();

        File folder = new File("server_files");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String filePath
                = "server_files"
                + File.separator
                + System.currentTimeMillis()
                + "_"
                + fileName;

        FileOutputStream fos = new FileOutputStream(filePath);
        InputStream input = socket.getInputStream();

        byte[] buffer = new byte[4096];

        long totalRead = 0;

        while (totalRead < fileSize) {

            int bytesToRead
                    = (int) Math.min(
                            buffer.length,
                            fileSize - totalRead
                    );

            int bytesRead
                    = input.read(buffer, 0, bytesToRead);

            if (bytesRead == -1) {
                break;
            }

            fos.write(buffer, 0, bytesRead);

            totalRead += bytesRead;
        }

        fos.close();

        String serverChecksum
                = checksumStrategy.calculate(filePath);

        if (!serverChecksum.equals(clientChecksum)) {
            send(writer, "ERROR Checksum inválido. Archivo corrupto.");
            return;
        }

        FileMetadata metadata
                = fileService.createMetadata(
                        fileName,
                        fileSize,
                        currentUser.getName(),
                        serverChecksum,
                        filePath
                );

        fileService.addFileToRoom(currentRoom, metadata);

        send(writer, "UPLOAD_OK Archivo recibido correctamente");

        notifyLog(currentUser.getName()
                + " subió "
                + fileName
                + " a la sala "
                + currentRoom.getName());
    }

    /**
     * Handles file download to the client.
     *
     * @param reader input stream
     * @param writer output stream
     * @throws Exception if an input or output error occurs
     */
    private void handleDownloadFile(
            DataInputStream reader,
            DataOutputStream writer) throws Exception {

        if (currentRoom == null) {
            send(writer, "ERROR Primero debe unirse a una sala");
            return;
        }

        send(writer, "Ingrese nombre del archivo:");

        String requestedName = reader.readUTF();

        FileMetadata selectedFile = null;

        for (FileMetadata file : currentRoom.getFiles()) {

            if (file.getName().equalsIgnoreCase(requestedName)) {
                selectedFile = file;
                break;
            }
        }

        if (selectedFile == null) {
            send(writer, "ERROR Archivo no encontrado");
            return;
        }

        File fileToSend = new File(selectedFile.getPath());

        if (!fileToSend.exists()) {
            send(writer, "ERROR Archivo físico no existe en servidor");
            return;
        }

        send(writer, "FILE_READY");
        send(writer, selectedFile.getName());
        send(writer, String.valueOf(selectedFile.getSize()));

        FileInputStream fis = new FileInputStream(fileToSend);
        OutputStream output = socket.getOutputStream();

        byte[] buffer = new byte[4096];

        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        output.flush();
        fis.close();

        send(writer, "DOWNLOAD_OK Archivo enviado correctamente");

        notifyLog(currentUser.getName()
                + " descargó "
                + selectedFile.getName());
    }

    /**
     * Sends a message to the client.
     *
     * @param writer output stream
     * @param message message to send
     * @throws Exception if an output error occurs
     */
    private void send(
            DataOutputStream writer,
            String message) throws Exception {

        writer.writeUTF(message);
        writer.flush();
    }

    /**
     * Notifies a server log event.
     *
     * @param message log message
     */
    private void notifyLog(String message) {

        if (notificationManager != null) {
            notificationManager.notifyObservers(message);
        } else if (logger != null) {
            logger.log(message);
        }
    }
}