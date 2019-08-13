package controller.menu;

/**
 *
 * @author Will
 */
public class NoSuchOptionException extends Exception {

    public NoSuchOptionException(String message, Throwable e)
    {
        super(message, e);
    }
    
}
