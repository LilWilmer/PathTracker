package model.trekker;

/**
 *
 * @author Will
 */
public class TrekkerException extends Exception 
{

    public TrekkerException(String message) 
    {
        super(message);
    }

    TrekkerException(String message, Throwable e) 
    {
        super(message,e);
    }
    
}
