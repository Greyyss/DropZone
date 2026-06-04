package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import patterns.strategy.ChecksumStrategy;
import patterns.strategy.MD5ChecksumStrategy;

public class ClientFrame extends JFrame {

    private final Object socketLock = new Object();

    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;
    private ChecksumStrategy checksumStrategy;

    private JTextArea log;

    public ClientFrame() {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("DropZone - Cliente");
        setSize(850, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(24, 18, 35));
        setLayout(new BorderLayout(0, 0));

        checksumStrategy = new MD5ChecksumStrategy();

        JLabel title = new JLabel("DROPZONE", SwingConstants.CENTER);
        title.setForeground(new Color(230, 210, 255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(18, 0, 8, 0));

        JLabel subtitle = new JLabel(
                "Plataforma de transferencia de archivos",
                SwingConstants.CENTER
        );
        subtitle.setForeground(new Color(170, 140, 210));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(24, 18, 35));
        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.CENTER);

        log = new JTextArea();
        log.setEditable(false);
        log.setBackground(new Color(18, 13, 28));
        log.setForeground(new Color(235, 225, 255));
        log.setCaretColor(new Color(210, 160, 255));
        log.setFont(new Font("Consolas", Font.PLAIN, 14));
        log.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnConnect = new JButton("Conectar");
        JButton btnLogin = new JButton("Login");
        JButton btnCreateRoom = new JButton("Crear sala");
        JButton btnJoinRoom = new JButton("Unirse a sala");
        JButton btnListFiles = new JButton("Listar archivos");
        JButton btnUpload = new JButton("Subir archivo");
        JButton btnDownload = new JButton("Descargar archivo");
        JButton btnClear = new JButton("Limpiar log");

        styleButton(btnConnect);
        styleButton(btnLogin);
        styleButton(btnCreateRoom);
        styleButton(btnJoinRoom);
        styleButton(btnListFiles);
        styleButton(btnUpload);
        styleButton(btnDownload);
        styleButton(btnClear);

        JPanel panelButtons = new JPanel(new GridLayout(2, 4, 10, 10));
        panelButtons.setBackground(new Color(34, 24, 50));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        panelButtons.add(btnConnect);
        panelButtons.add(btnLogin);
        panelButtons.add(btnCreateRoom);
        panelButtons.add(btnJoinRoom);
        panelButtons.add(btnListFiles);
        panelButtons.add(btnUpload);
        panelButtons.add(btnDownload);
        panelButtons.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(24, 18, 35));
        topPanel.add(header, BorderLayout.NORTH);
        topPanel.add(panelButtons, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(log);
        scroll.getViewport().setBackground(new Color(18, 13, 28));
        scroll.setBorder(BorderFactory.createEmptyBorder(15, 18, 18, 18));

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnConnect.addActionListener(e -> connect());
        btnLogin.addActionListener(e -> login());
        btnCreateRoom.addActionListener(e -> createRoom());
        btnJoinRoom.addActionListener(e -> joinRoom());
        btnListFiles.addActionListener(e -> listFiles());
        btnUpload.addActionListener(e -> uploadFile());
        btnDownload.addActionListener(e -> downloadFile());
        btnClear.addActionListener(e -> log.setText(""));
    }

    private void connect() {

        try {
            String ip = JOptionPane.showInputDialog(
                    this,
                    "IP del servidor:",
                    "localhost"
            );

            if (ip == null || ip.isBlank()) {
                return;
            }

            socket = new Socket(ip, 5000);

            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());

            appendLog(reader.readUTF());

        } catch (Exception e) {
            appendLog("ERROR conectando al servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isConnected() {

        if (socket == null || socket.isClosed()) {
            appendLog("ERROR: primero conectese al servidor");
            return false;
        }

        return true;
    }

    private void login() {

    if (!isConnected()) {
        return;
    }

    synchronized (socketLock) {

        try {
            String user = JOptionPane.showInputDialog(
                    this,
                    "Nombre de usuario:"
            );

            if (user == null || user.isBlank()) {
                return;
            }

            String password = JOptionPane.showInputDialog(
                    this,
                    "Contraseña:"
            );

            if (password == null || password.isBlank()) {
                return;
            }

            send("LOGIN");

            appendLog(reader.readUTF());

            send(user);

            appendLog(reader.readUTF());

            send(password);

            appendLog(reader.readUTF());

        } catch (Exception e) {
            appendLog("ERROR en login: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

    private void createRoom() {

        if (!isConnected()) {
            return;
        }

        synchronized (socketLock) {

            try {
                String room = JOptionPane.showInputDialog(
                        this,
                        "Nombre de la sala:"
                );

                if (room == null || room.isBlank()) {
                    return;
                }

                send("CREATE_ROOM");
                appendLog(reader.readUTF());

                send(room);
                appendLog(reader.readUTF());

            } catch (Exception e) {
                appendLog("ERROR creando sala: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void joinRoom() {

        if (!isConnected()) {
            return;
        }

        synchronized (socketLock) {

            try {
                String room = JOptionPane.showInputDialog(
                        this,
                        "Nombre de la sala:"
                );

                if (room == null || room.isBlank()) {
                    return;
                }

                send("JOIN_ROOM");
                appendLog(reader.readUTF());

                send(room);
                appendLog(reader.readUTF());

            } catch (Exception e) {
                appendLog("ERROR uniendose a sala: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void listFiles() {

        if (!isConnected()) {
            return;
        }

        new Thread(() -> {

            synchronized (socketLock) {

                try {
                    send("LIST_FILES");

                    String response = reader.readUTF();
                    appendLog(response);

                    if ("FILES_LIST".equals(response)) {

                        String fileLine;

                        while (true) {

                            fileLine = reader.readUTF();

                            if ("END_FILES".equals(fileLine)) {
                                break;
                            }

                            appendLog(fileLine);
                        }
                    }

                } catch (Exception e) {
                    appendLog("ERROR listando archivos: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void uploadFile() {

        if (!isConnected()) {
            return;
        }

        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        new Thread(() -> {

            synchronized (socketLock) {

                try {
                    appendLog("Iniciando subida: " + file.getName());

                    send("UPLOAD_FILE");

                    String ready = reader.readUTF();
                    appendLog(ready);

                    if (!"READY".equals(ready)) {
                        return;
                    }

                    String checksum =
                            checksumStrategy.calculate(file.getAbsolutePath());

                    send(file.getName());
                    send(String.valueOf(file.length()));
                    send(checksum);

                    FileInputStream fis = new FileInputStream(file);
                    OutputStream output = socket.getOutputStream();

                    byte[] buffer = new byte[4096];

                    int bytesRead;
                    long totalSent = 0;
                    int lastProgress = -1;

                    while ((bytesRead = fis.read(buffer)) != -1) {

                        output.write(buffer, 0, bytesRead);

                        totalSent += bytesRead;

                        int progress =
                                (int) ((totalSent * 100) / file.length());

                        if (progress != lastProgress
                                && (progress % 10 == 0 || progress == 100)) {

                            appendLog("Subida: " + progress + "%");
                            lastProgress = progress;
                        }
                    }

                    output.flush();
                    fis.close();

                    String finalResponse = reader.readUTF();
                    appendLog(finalResponse);

                    appendLog("Subida finalizada. Puede subir otro archivo o listar.");

                } catch (Exception e) {
                    appendLog("ERROR subiendo archivo: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void downloadFile() {

        if (!isConnected()) {
            return;
        }

        String fileName = JOptionPane.showInputDialog(
                this,
                "Nombre del archivo:"
        );

        if (fileName == null || fileName.isBlank()) {
            return;
        }

        new Thread(() -> {

            synchronized (socketLock) {

                try {
                    send("DOWNLOAD_FILE");

                    String response = reader.readUTF();
                    appendLog(response);

                    if (!"Ingrese nombre del archivo:".equals(response)) {
                        return;
                    }

                    send(fileName);

                    String status = reader.readUTF();
                    appendLog(status);

                    if (!"FILE_READY".equals(status)) {
                        return;
                    }

                    String originalName = reader.readUTF();
                    long fileSize = Long.parseLong(reader.readUTF());

                    File folder = new File("client_downloads");

                    if (!folder.exists()) {
                        folder.mkdirs();
                    }

                    File outputFile = new File(
                            folder,
                            "downloaded_" + originalName
                    );

                    FileOutputStream fos = new FileOutputStream(outputFile);
                    InputStream input = socket.getInputStream();

                    byte[] buffer = new byte[4096];

                    long totalRead = 0;
                    int lastProgress = -1;

                    while (totalRead < fileSize) {

                        int bytesToRead =
                                (int) Math.min(
                                        buffer.length,
                                        fileSize - totalRead
                                );

                        int bytesRead =
                                input.read(buffer, 0, bytesToRead);

                        if (bytesRead == -1) {
                            break;
                        }

                        fos.write(buffer, 0, bytesRead);

                        totalRead += bytesRead;

                        int progress =
                                (int) ((totalRead * 100) / fileSize);

                        if (progress != lastProgress
                                && (progress % 10 == 0 || progress == 100)) {

                            appendLog("Descarga: " + progress + "%");
                            lastProgress = progress;
                        }
                    }

                    fos.close();

                    appendLog(reader.readUTF());
                    appendLog("Guardado en: "
                            + outputFile.getAbsolutePath());

                } catch (Exception e) {
                    appendLog("ERROR descargando archivo: "
                            + e.getMessage());
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void send(String message) throws Exception {
        writer.writeUTF(message);
        writer.flush();
    }

    private void styleButton(JButton button) {

        button.setBackground(new Color(145, 80, 220));
        button.setForeground(Color.WHITE);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        button.setFont(new Font("Bahnschrift", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(11, 16, 11, 16));
    }

    private void appendLog(String message) {

        SwingUtilities.invokeLater(() -> {
            log.append("> " + message + "\n");
        });
    }
}