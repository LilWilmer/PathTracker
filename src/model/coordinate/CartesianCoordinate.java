/*****************************************************************************
* AUTH: William Payne
* FILE: CartesianCoordinate.java
* LAST MOD: 12/05/19
* PURPOSE: For storing a Cartesian coordinate. This object is used for testing
*          the trekking program using a simpler metric.
*          Class is immutable to avoid incorrectly changing the 
*****************************************************************************/
package model.coordinate;

public final class CartesianCoordinate implements ICoordinate
{
    //FIELDS:-------------------------------------------------------------------
    //
    
    private final double[] pos;

    
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public CartesianCoordinate(double[] pos)
    {
        if(pos.length != 3)
        {
            throw new IllegalArgumentException();
        }
        this.pos = pos;
    }

    
    // COORDINATE METHODS:------------------------------------------------------
    //
    
    /**
     * PURP: Calculates the distance from this coordinate to the imported
     *       coordinate and returns true if it is within the provided distance
     * @param distance
     * @param coord
     * @return
     * @throws IncompatibleCoordinateException 
     */
    @Override
    public boolean within(double[] distance, ICoordinate coord) 
            throws IncompatibleCoordinateException
    {
        double hori, verti;
        
        //Strategy pattern wasnt intended to mix and match coordinates
        //its to allow the program to switch the kind of coordinates it uses
        //they all still need to be the same type however
        if(!(coord instanceof CartesianCoordinate))
            throw new IncompatibleCoordinateException("Coordinate type mismatch");
        
        hori = Math.abs(flatDistance(coord));
        verti = Math.abs(vertDistance(coord));
        
        return hori <= distance[0] && verti <= distance[1];
    }

    @Override
    public double[] getPosition()
    {
        return pos;
    }

    @Override
    public double vertDistance(ICoordinate c) 
    {
        return c.getPosition()[2] - this.pos[2];
    }

    @Override
    public double flatDistance(ICoordinate c) 
    {
        //Getting point one and two for the calculation
        double[] a = {pos[0],pos[1]};
        double[] b = c.getPosition();
        
        //finding hypotenuse
        double xDist = Math.pow((b[0]-a[0]), 2);
        double yDist = Math.pow((b[1]-a[1]), 2);
        double dist = Math.sqrt(xDist+yDist);

        return Math.abs(dist);
    }
    
    @Override
    public String toString()
    {
        return String.format("(%7.2f, %7.2f, %7.2f)", pos[0], pos[1], pos[2]);
    }

}