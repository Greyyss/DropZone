package patterns.factory;

/**
 * Factory class used to create command types from text input.
 *
 * Converts a string received from the client into the
 * corresponding CommandType value.
 *
 * @author Grace
 * @author Dilan
 * @author Luis
 */
public class CommandFactory {

    /**
     * Creates a command type from a string value.
     *
     * @param command command received from the client
     * @return matching CommandType or UNKNOWN if invalid
     */
    public static CommandType createCommand(String command) {

        try {

            return CommandType.valueOf(
                    command.toUpperCase());

        } catch (Exception e) {

            return CommandType.UNKNOWN;
        }
    }
}