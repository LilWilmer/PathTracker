/*****************************************************************************
* AUTH: William Payne
* FILE: CoordinateFactory
* LAST MOD: 15/05/19
* PURPOSE: Handles the creation of ICoordinate subclasses
*****************************************************************************/
package model.coordinate.factory;

import model.coordinate.ICoordinate;
import model.coordinate.InvalidCoordinateException;

public abstract class CoordinateFactory
{
    public abstract ICoordinate createCoordinate(double[] pos)
            throws InvalidCoordinateException;
}
