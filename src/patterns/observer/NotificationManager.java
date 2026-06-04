package patterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages observers and sends notifications to them.
 *
 * Implements the Observer Pattern to allow
 * communication between system components.
 *
 * @author Grace Solano
 * @author Luis Gomez
 * @author Dilan Badilla
 */

public class NotificationManager implements Subject {

    private List<Observer> observers;

    /**
     * Creates a new notification manager.
     */
    public NotificationManager() {
        observers = new ArrayList<>();
    }

    /**
     * Registers a new observer.
     *
     * @param observer Observer to add.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer.
     *
     * @param observer Observer to remove.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Sends a notification message to all registered observers.
     *
     * @param message Notification message.
     */
    @Override
    public void notifyObservers(String message) {

        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}