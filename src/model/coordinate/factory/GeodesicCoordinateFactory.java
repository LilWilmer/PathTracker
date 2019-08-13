/*****************************************************************************
* AUTH: William Payne
* FILE: CartesianCoordinateFactory
* LAST MOD: 15/05/19
* PURPOSE: Handles the creation of geodesic coordinates
*****************************************************************************/
package model.coordinate.factory;

import controller.geo.GeoUtils;
import model.coordinate.GeodesicCoordinate;
import model.coordinate.ICoordinate;
import model.coordinate.InvalidCoordinateException;

public class GeodesicCoordinateFactory extends CoordinateFactory
{
    //
    //FIELDS:
    //
    private GeoUtils geoutils;
    
    public GeodesicCoordinateFactory(GeoUtils geoutil)
    {
        this.geoutils = geoutil;
    }
    
    /**
     *
     * @param pos
     * @return
     * @throws InvalidCoordinateException
     */
    @Override
    public ICoordinate createCoordinate(double[] pos) 
            throws InvalidCoordinateException
    {
        //error checking exists inside the GeodesicCoordinate class
        return new GeodesicCoordinate(pos, geoutils);
    }
}
