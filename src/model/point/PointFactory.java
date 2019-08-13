/*****************************************************************************
* AUTH: William Payne
* FILE: CoordinateFactory
* LAST MOD: 15/05/19
* PURPOSE: Handles the creation of ICoordinate subclasses
*****************************************************************************/
package model.point;

import model.coordinate.InvalidCoordinateException;

public abstract class PointFactory
{
    public abstract TemplatePoint createPoint(double[] pos)
            throws InvalidCoordinateException;
}
