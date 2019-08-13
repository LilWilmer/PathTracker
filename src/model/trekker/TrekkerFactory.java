/*****************************************************************************
* AUTH: William Payne
* FILE: TrekkerFactory
* LAST MOD: 21/05/19
* PURPOSE: Creates an instance of a trekker and adds the observers passed in
*          during the construction of the factory
*****************************************************************************/
package model.trekker;

import java.util.HashSet;
import java.util.LinkedList;
import model.coordinate.ICoordinate;
import model.coordinate.InvalidCoordinateException;
import model.coordinate.factory.CoordinateFactory;
import model.point.NavPoint;
import model.route.Route;

public class TrekkerFactory 
{
    
    // FIELDS:------------------------------------------------------------------
    //
    private HashSet<ITrekkerObserver> observers;
    private CoordinateFactory cFactory;

    //CONSTRUCTOR---------------------------------------------------------------
    //
    public TrekkerFactory(CoordinateFactory cFactory)
    {
        this.cFactory = cFactory;
        this.observers = new HashSet<>();
    }

    //FACTORY-------------------------------------------------------------------
    //
    public Trekker createTrekker(Route route) throws TrekkerException
    {
        Trekker trekker;
        double[] pos = new double[3], dist = new double[3];
        ICoordinate coord;
        
        LinkedList<NavPoint> navs = route.buildRoute();
        route.calcDistance(dist);
        
        try 
        {
            coord = cFactory.createCoordinate(pos);
        }
        catch (InvalidCoordinateException e) 
        {
            throw new TrekkerException("Failed to construct coordinate, ",e);
        }
        
        trekker = new Trekker(coord, navs, dist);
        
        //Adding the observers before returning new trekker object
        for(ITrekkerObserver ob : this.observers)
        {
            trekker.addObserver(ob);
        }
        
        return trekker;
    }
    
    //OBSERVER METHODS----------------------------------------------------------
    //
    public void addTrekkerObserver(ITrekkerObserver ob)
    {
        this.observers.add(ob);
    }
    
}
