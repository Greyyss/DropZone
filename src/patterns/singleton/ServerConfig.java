package patterns.singleton;

public class ServerConfig {

    private static ServerConfig instance;

    private final int PORT = 5000;

    private ServerConfig() {
    }

    public static ServerConfig getInstance() {

        if (instance == null) {
            instance = new ServerConfig();
        }

        return instance;
    }

    public int getPort() {
        return PORT;
    }
}