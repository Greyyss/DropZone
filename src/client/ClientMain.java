package client;

import javax.swing.SwingUtilities;

/**
 * Entry point of the DropZone client application.
 *
 * Launches the graphical user interface using
 * the Swing Event Dispatch Thread.
 *
 * @author Luis Gomez
 */
public class ClientMain {

    /**
     * Starts the client application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            ClientFrame frame = new ClientFrame();
            frame.setVisible(true);
        });
    }
}