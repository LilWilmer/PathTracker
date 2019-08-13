/*****************************************************************************
* AUTH: William Payne
* FILE: Segment.java
* DATE: 6/05/19
* PURP: Concrete composite/decoration for an IRoute
*****************************************************************************/
package model.route;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.coordinate.ICoordinate;
import model.coordinate.IncompatibleCoordinateException;

import model.point.NavPoint;
import model.point.WayPoint;
public class SubRoute extends RouteLink
{
    //CONSTANTS:----------------------------------------------------------------
    //
    public static final double[] CLOSE_ENOUGH = {10,2};
    
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private Route route;

    //
    //CONSTRUCOR:---------------------------------------------------------------
    //
    
    public SubRoute(Route route)
    {
        super("Go to -> "+route.getName());
        this.route = route;
    }

    /*(set next):*/

    /**
     *
     * @param point
     * @throws UnlinkableException
     */

    @Override
    public void setNext(WayPoint point) throws UnlinkableException
    {
        try 
        {
            if(this.route.getEnd() != null)
            {
                if(!point.getPosition().within(
                        CLOSE_ENOUGH, this.route.getEnd().getPosition()))
                {
                    throw new UnlinkableException("Points are not close enough");
                }
            }
            this.b = point;
        }
        catch (IncompatibleCoordinateException e) 
        {
            throw new UnlinkableException("Different coordinate types, ",e);
        }
    }
    
    /*(set previous):*/

    /**
     *
     * @param point
     * @throws UnlinkableException
     */

    @Override
    public void setPrev(WayPoint point) throws UnlinkableException
    {
        try 
        {
            if(this.route.getStart() != null)
            {
                if(!point.getPosition().within(
                        CLOSE_ENOUGH, this.route.getStart().getPosition()))
                {
                    throw new UnlinkableException("Points are not close enough");
                }
            }
            this.a = point;
        }
        catch (IncompatibleCoordinateException e) 
        {
            throw new UnlinkableException("Different coordinate types, ",e);
        }
    }
    
    @Override
    public void buildRoute(LinkedList<NavPoint> ll)
    {
        //Goes through all of the points in the subroute before continuing
        NavPoint newPoint = NavPoint.createNavPoint(
                this.route.getStart(), this.description);
        ll.peekLast().setNext(newPoint);
        ll.addLast( newPoint );
        this.route.buildRoute(ll);
        newPoint = NavPoint.createNavPoint(
                this.b, "Leaving Route: "+this.route.getName());
        ll.peekLast().setNext(newPoint);
        ll.addLast(newPoint);
        this.b.buildRoute(ll);
    }
    
    @Override
    public void getPoints(LinkedList<WayPoint> points) 
    {
        this.route.getPoints(points);
        this.b.getPoints(points);
    }
    
    @Override
    public void calcDistance(ICoordinate lastPos, double[] dist) 
    {
        this.route.calcDistance(lastPos, dist);
        this.b.calcDistance(route.getEnd().getPosition(), dist);
    }
    
    @Override
    public boolean validate()
    {
        //CHECK IF THE START OF THE ROUTE AND POINT A ARE CLOSE ENOUGH
        try 
        {
            if(!b.getPosition().within(
                    CLOSE_ENOUGH, this.route.getEnd().getPosition()))
            {
                return false;
            }
        } 
        catch(IncompatibleCoordinateException e) 
        {
            return false;
        }
        
        //CHECK IF THE END OF THE ROUTE AND POINT B ARE CLOSE ENOUGH
        try 
        {
            if(!a.getPosition().within(
                    CLOSE_ENOUGH, this.route.getStart().getPosition()))
            {
                return false;
            }
        } 
        catch(IncompatibleCoordinateException e) 
        {
            return false;
        }
        
        //IF ROUTE IS INVALID RETURN FALSE
        if(!this.route.validate())
            return false;
        //ELSE KEEP ON KEEPIN ON
        else
            return this.b.validate();
    }
}