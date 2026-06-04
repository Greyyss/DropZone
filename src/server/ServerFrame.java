package server;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import network.ClientHandler;

/**
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 *
 */
public class ServerFrame extends JFrame implements ServerLogger {

    private JTextArea log;
    private ServerSocket serverSocket;
    private boolean running;

    public ServerFrame() {

        setTitle("DropZone - Servidor");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        log = new JTextArea();
        log.setEditable(false);

        JButton btnStart = new JButton("Iniciar servidor");
        JButton btnStop = new JButton("Detener servidor");

        JPanel panelButtons = new JPanel();

        panelButtons.add(btnStart);
        panelButtons.add(btnStop);

        add(panelButtons, BorderLayout.NORTH);
        add(new JScrollPane(log), BorderLayout.CENTER);

        btnStart.addActionListener(e -> startServer());
        btnStop.addActionListener(e -> stopServer());
    }

    private void startServer() {

        if (running) {
            log.append("El servidor ya esta iniciado\n");
            return;
        }

        new Thread(() -> {

            try {
                serverSocket = new ServerSocket(
                        patterns.singleton.ServerConfig.getInstance().getPort()
                );
                running = true;

                log.append("Servidor iniciado en puerto "
                        + patterns.singleton.ServerConfig.getInstance().getPort()
                        + "\n");

                while (running) {

                    Socket clientSocket = serverSocket.accept();

                    log.append("Cliente conectado: "
                            + clientSocket.getInetAddress()
                            + "\n");

                    ClientHandler handler = new ClientHandler(clientSocket, this);
                    handler.start();
                }

            } catch (IOException e) {

                if (running) {
                    log.append("ERROR en servidor: " + e.getMessage() + "\n");
                }
            }

        }).start();
    }

    private void stopServer() {

        try {
            running = false;

            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            log.append("Servidor detenido\n");

        } catch (IOException e) {
            log.append("ERROR deteniendo servidor\n");
        }
    }

    @Override
    public void log(String message) {
        log.append(message + "\n");
    }
}
