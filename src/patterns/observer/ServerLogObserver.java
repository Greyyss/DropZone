package patterns.observer;

import server.ServerLogger;

public class ServerLogObserver implements Observer {

    private ServerLogger logger;

    public ServerLogObserver(ServerLogger logger) {
        this.logger = logger;
    }

    @Override
    public void update(String message) {

        if (logger != null) {
            logger.log(message);
        }
    }
}