/*****************************************************************************
* AUTH: William Payne
* FILE: Route.java
* DATE: 6/05/19
* PURP: Composite object that stores a complete route and acts as a route link.
*****************************************************************************/
package model.route;

import java.util.LinkedList;
import model.coordinate.ICoordinate;

import model.point.*;
public class Route implements IRoute
{
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private String name;
    private String description;
    private WayPoint start;
    private WayPoint end;
    private LinkedList<WayPoint> points;

    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public Route(String name, String description)
    {
        this.name = name;
        this.description = description;
        this.points = new LinkedList<>();
    }

    //
    //ACCESSORS AND MUTATORS:---------------------------------------------------
    //
    
    public String getName(){return this.name;}
    public String getDescription(){return this.description;}
    public WayPoint getStart(){return this.start;}
    public WayPoint getEnd(){return this.end;}
    public LinkedList<WayPoint> getPoints(){return this.points;}
    
    public void setDescription(String d){this.description = d;}

    /**
     * PURP: Adds a waypoint to the end of the route much like how a node is
     *       added into a double ended linked list.
     * @param newPoint 
     */
    public void add(WayPoint newPoint)
    {
        try
        {
            if(points.isEmpty())
            {
                this.start = newPoint;
                this.end = newPoint;
            }
            this.points.addLast(newPoint);
            this.end.link(newPoint);
            this.end = newPoint;
        }
        catch(UnlinkableException e)
        {
            e.getMessage();
        }
    }

    //
    // ROUTE METHODS:-----------------------------------------------------------
    //
    
    /**
     * PURP: Constructs a composite path of way points routes and segments into
     *       a simple linked list of Navigation Points.
     * @return navs
     */
    public LinkedList<NavPoint> buildRoute()
    {
        LinkedList<NavPoint> navs = new LinkedList<>();
        navs.addLast(NavPoint.createNavPoint(start,"Start"));
        start.buildRoute(navs);
        return navs;
    }
    
    @Override
    public boolean validate()
    {
        return this.start.validate();
    }

    /**
     * PURP: Same as above but this is part of the IRoute interface and forms
     *       the build path call chain.
     * @param navs 
     */
    @Override
    public void buildRoute(LinkedList<NavPoint> navs)
    {
        
        start.buildRoute(navs);
    }
    
    @Override
    public void getPoints(LinkedList<WayPoint> points) 
    {
        start.getPoints(points);
    }
    /**
     * PURP: Calculates the total distance horizontally and vertically up and 
     *       down.
     * @param lastPos
     * @param data 
     */
    @Override
    public void calcDistance(ICoordinate lastPos, double[] data)
    {
        start.calcDistance(lastPos, data);
    }
    
    /**
     * Starts the recursive call chain above.
     * @param data 
     */
    public void calcDistance(double[] data)
    {
        calcDistance(this.start.getPosition(),data);
    }

    @Override
    public String toString()
    {
        double[] dist = new double[3];
        double[] s = this.start.getPosition().getPosition();
        double[] e = this.start.getPosition().getPosition();
        calcDistance(dist);
        String out = String.format("%s Start:(%.2f, %.2f, %.2f) End:(%.2f, %.2f, %.2f)",
                this.name, s[0],s[1],s[2],e[0],e[1],e[2]);
        out += String.format("Hori: %.2f, Climb: %.2f, Descent: %.2f",dist[0], dist[1], dist[2]);

        return out;
    }


}