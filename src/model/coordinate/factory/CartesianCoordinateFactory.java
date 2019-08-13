/*****************************************************************************
* AUTH: William Payne
* FILE: CartesianCoordinateFactory
* LAST MOD: 15/05/19
* PURPOSE: Handles the creation of Cartesian coordinates
*****************************************************************************/
package model.coordinate.factory;

import model.coordinate.*;

public class CartesianCoordinateFactory extends CoordinateFactory
{
    @Override
    public ICoordinate createCoordinate(double[] pos)
            throws InvalidCoordinateException
    {
        //TODO: Provide real logic here
        return new CartesianCoordinate(pos);
    }
}