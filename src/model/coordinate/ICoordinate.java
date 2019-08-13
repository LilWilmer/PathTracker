
/*****************************************************************************
* AUTH: William Payne
* FILE: ICoordinate
* LAST MOD: 21/05/19
* PURPOSE: Strategy Pattern interface for a type of 3 dimensional coordinate
*****************************************************************************/
package model.coordinate;

public interface ICoordinate
{
    double vertDistance(ICoordinate c);
    double flatDistance(ICoordinate c);
    boolean within(double[] distance, ICoordinate coord) throws IncompatibleCoordinateException;
    double[] getPosition();
}