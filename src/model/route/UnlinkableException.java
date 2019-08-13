/*****************************************************************************
* AUTH: William Payne
* FILE: UnlinkableException.java
* LAST MOD: 15/05/19
* PURPOSE: 
*****************************************************************************/
package model.route;

public class UnlinkableException extends Exception
{
    public UnlinkableException(String message)
    {
        super(message);
    }

    UnlinkableException(String message, Throwable e) 
    {
        super(message,e);
    }
}