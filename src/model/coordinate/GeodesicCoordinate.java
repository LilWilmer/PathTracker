/*****************************************************************************
* AUTH: William Payne
* FILE: SphericalCoordinate
* LAST MOD: 12/05/19
* PURPOSE: This is a mostly completed object that will handle the final build
*          of the program when the Geodesic coordinate distance algorithm
*          has been implemented.
*****************************************************************************/
package model.coordinate;

import controller.geo.GeoUtils;

public final class GeodesicCoordinate implements ICoordinate
{
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private final GeoUtils geoutil;
    private final double[] pos = new double[3];

    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public GeodesicCoordinate(double[] pos, GeoUtils geoutil) 
            throws InvalidCoordinateException
    {
        if(pos.length < 3)
        {
            throw new IllegalArgumentException();
        }
        
        if(pos[0] > 90 || pos[0] < -90) 
            throw new InvalidCoordinateException("latitude out of range");
        
        if(pos[1] > 180 || pos[1] < -180) 
            throw new InvalidCoordinateException("Longitude out of range");

        System.arraycopy(pos, 0, this.pos, 0, this.pos.length);
        this.geoutil = geoutil;
    }

    //
    // ACCESSORS:---------------------------------------------------------------
    //
    
    /**
     * 
     * @return 
     */
    @Override
    public double[] getPosition(){return this.pos;}
    
    /**
     * PURP: Calculate the distance between this coordinate and the import and
     *       determine if it lays within the queried range.
     * @param distance
     * @param coord
     * @return
     * @throws IncompatibleCoordinateException 
     */
    @Override
    public boolean within(double[] distance, ICoordinate coord) 
            throws IncompatibleCoordinateException 
    {
        double hori = Math.abs(flatDistance(coord));
        double verti = Math.abs(vertDistance(coord));
        
        return hori <= distance[0] && verti <= distance[1];
    }

    /**
     * PURP: Calculate the vertical distance between two coordinates
     * @param c
     * @return 
     */
    @Override
    public double vertDistance(ICoordinate c) 
    {
        return c.getPosition()[2] - this.pos[2];
    }

    /**
     * PURP: Calculate the horizontal distance between two coordinates
     * @param c
     * @return 
     */
    @Override
    public double flatDistance(ICoordinate c) 
    {
        double[] pos2 = c.getPosition();
        return this.geoutil.calcMetresDistance(pos[0], pos2[0], pos[1], pos2[1]);
    }

}