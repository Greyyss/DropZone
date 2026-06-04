package server;

import javax.swing.SwingUtilities;

/**
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 * */
public class ServerMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ServerFrame frame = new ServerFrame();
            frame.setVisible(true);
        });
    }
}