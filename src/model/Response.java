package model;

/**
 * Generic response object used to return operation results.
 *
 * Stores the status of the operation, a message,
 * and optional data associated with the response.
 *
 * @param <T> Type of data contained in the response.
 *
 * @author Luis Gomez
 */
public class Response<T> {

    private boolean success;
    private String message;
    private T data;

    /**
     * Creates a new response object.
     *
     * @param success Indicates whether the operation was successful.
     * @param message Response message.
     * @param data Associated response data.
     */
    public Response(
            boolean success,
            String message,
            T data) {

        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Returns the operation status.
     *
     * @return true if successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the response message.
     *
     * @return Response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the response data.
     *
     * @return Response data.
     */
    public T getData() {
        return data;
    }
}