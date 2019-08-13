/*****************************************************************************
* AUTH: William Payne
* FILE: WayPoint.java
* DATE: 6/05/19
* PURP: 
*****************************************************************************/
package model.point;

import java.util.LinkedList;

import model.coordinate.ICoordinate;
import model.route.IRoute;
import model.route.RouteLink;
import model.route.UnlinkableException;

public class WayPoint extends TemplatePoint implements IRoute 
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    private RouteLink link;

    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public WayPoint(String label, ICoordinate coord) {
        super(label, coord);
        this.link = null;
    }

    public void setLinker(RouteLink link) {
        this.link = link;
    }

    public void link(WayPoint point) throws UnlinkableException
    {
        if(this.link == null)
        {
            throw new UnlinkableException("No linking object in point");
        }
        this.link.setNext(point);
    }

    //
    // ABSTRACT METHODS:--------------------------------------------------------
    //
    
    @Override
    public void calcDistance(ICoordinate lastPos, double[] dist)
    {
        int index;
        double vertdist = lastPos.vertDistance(this.coord);
        dist[0] += lastPos.flatDistance(this.coord);
        index = vertdist > 0 ? 1 : 2;
        dist[index] += Math.abs(vertdist);
        if(link!= null) this.link.calcDistance(this.coord, dist);
    }
    
    @Override
    public void buildRoute(LinkedList<NavPoint> ll) 
    {
        if(link!=null)
        {
            this.link.buildRoute(ll);
        }
    }

    @Override
    public void getPoints(LinkedList<WayPoint> points) 
    {
        points.add(this);
        if(link!=null)
        {
            this.link.getPoints(points);
        }
    }
    
    /**
    * toString:
    * --- --- --- ---
    * <text>
     * @return 
    */
    @Override
    public String toString()
    {
        String linkinfo = "End";
        if(link!= null)
        {
            linkinfo = link.toString();
        }
        return String.format("%-3s  | %s | %-25s",
                this.label,
                this.coord.toString(),
                linkinfo
        );
    }

    @Override
    public boolean validate()
    {
        if(link!=null)
            return this.link.validate();
        else
            return true;
    }

}
