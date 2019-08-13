/*****************************************************************************
* AUTH: William Payne
* FILE: IRouteLink.java
* DATE: 6/05/19
* PURP: Route link interface
*****************************************************************************/
package model.route;

import java.util.LinkedList;
import model.coordinate.ICoordinate;

import model.point.*;
public abstract class RouteLink implements IRoute
{
//
//FIELDS:-----------------------------------------------------------------------
//
    
    protected String description;
    protected WayPoint a;
    protected WayPoint b;

//
//ABSTRACT CONSTRUCOR:----------------------------------------------------------
//

    public RouteLink(String description)
    {
        this.a = null;
        this.b = null;
        this.description = description;
    }

//
// MUTATORS:--------------------------------------------------------------------
//
    
    /*(set next):*/
    public void setNext(WayPoint point) throws UnlinkableException
    {
        this.b = point;
    }
    
    /*(set previous):*/
    public void setPrev(WayPoint point) throws UnlinkableException
    {
        this.a = point;
    }

//
// Abstract Interface METHODS:--------------------------------------------------
//
    
    @Override
    public void buildRoute(LinkedList<NavPoint> ll)
    {
        NavPoint newPoint = NavPoint.createNavPoint(this.b, this.description);
        ll.peekLast().setNext(newPoint);
        ll.addLast( newPoint );
        this.b.buildRoute(ll);
    }

    @Override
    public void calcDistance(ICoordinate lastPos, double[] dist)
    {
        this.b.calcDistance(lastPos, dist);
    }
    
    @Override
    public void getPoints(LinkedList<WayPoint> points)
    {
        this.b.getPoints(points);
    }
    
    @Override
    public boolean validate()
    {
        return this.b.validate();
    }
    
    @Override
    public String toString()
    {
        return this.description;
    }
}