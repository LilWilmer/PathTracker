/*****************************************************************************
* AUTH: William Payne
* FILE: TemplateWayPoint.java
* DATE: 6/05/19
* PURP: Basic structure and layout of a waypoint object
*****************************************************************************/
package model.point;

import model.coordinate.ICoordinate;

public abstract class TemplatePoint
{
    //FIELDS:-------------------------------------------------------------------
    //
    
    protected String label;
    protected ICoordinate coord;

    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public TemplatePoint(String label, ICoordinate coord)
    {
        this.label = label;
        this.coord = coord;
    }

    public String getLabel(){return label;}
    public ICoordinate getPosition(){return coord;}

 
}