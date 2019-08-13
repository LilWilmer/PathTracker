/*****************************************************************************
* AUTH: William Payne
* FILE: NavPoint.java
* LAST MOD: 12/05/19
* PURPOSE: Target point for a trekking app
*****************************************************************************/
package model.point;

import model.coordinate.ICoordinate;

public class NavPoint extends TemplatePoint
{
    //
    //FIELDS:
    //
    
    private NavPoint next;
    private String description;
    private double vertDist = 0;
    private double flatDist = 0;

    //
    // CONSTRUCTOR:
    //
    
    public NavPoint(ICoordinate coord, String name, String description)
    {
        super(name, coord);
        this.next = null;
        this.description = description;
    }

    //FACTORY:
    public static NavPoint createNavPoint(TemplatePoint point, String description)
    {
        return new NavPoint(point.coord, point.label , description);
    }

    //
    // MUTATORS AND ACCESSORS:
    //
    
    public double getVerticalDist()
    {
        if(this.next == null)
        {
            return 0.0f;
        }
        return this.coord.vertDistance(this.next.getPosition());
    }
    
    public double getHorizontalDist()
    {
        if(this.next == null)
        {
            return 0.0f;
        }
        return this.coord.flatDistance(this.next.getPosition());
    }
    
    /*(get description*/
    public String getDescription(){return this.description;}
    
    /*(set next):*/
    public void setNext(NavPoint next)
    {
        this.next = next;
        this.vertDist = getVerticalDist();
        this.flatDist = getHorizontalDist();
    }
    //
    // OTHER:
    //
    
    @Override
    public String toString()
    {
        return String.format("%5s : %25s : %f",
                this.label,
                this.description,
                flatDist);
    }
    
}