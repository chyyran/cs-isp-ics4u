package menu.text;

/**
 * Handles an exception that would occur in the menu
 */
public abstract class ErrorHandler {
    /**
     * Handles the given exception
     * Use instanceof to determine the type of exception that had occured,
     * MenuBuilder erases all exception information during runtime.
     * @param e The exception that occurred
     */
    public abstract void handle(Exception e);
}
