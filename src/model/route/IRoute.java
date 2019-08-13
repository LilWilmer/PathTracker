/*****************************************************************************
* AUTH: William Payne
* FILE: IRoute.java
* DATE: 6/05/19
* PURP: Interface for a self managing doubly linked list of alternating
*       waypoint and links
*****************************************************************************/
package model.route;

import java.util.LinkedList;
import model.coordinate.ICoordinate;

import model.point.NavPoint;
import model.point.WayPoint;

public interface IRoute
{
    //METHODS:
    void buildRoute(LinkedList<NavPoint> points);
    void calcDistance(ICoordinate lastPos, double[] dist);
    void getPoints(LinkedList<WayPoint> points);
    boolean validate();
}