/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.route;

/**
 *
 * @author Will
 */
public class RouteReadingException extends Exception
{
    public RouteReadingException(String message)
    {
        super(message);
    }
    
    public RouteReadingException(String message, Throwable e)
    {
        super(message,e);
    }
}
