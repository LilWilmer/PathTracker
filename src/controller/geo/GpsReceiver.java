/*****************************************************************************
* AUTH: William Payne
* FILE: GpsReceiver
* LAST MOD: 21/05/19
* PURPOSE: Receives GPS data from another part of the program, formats and
*          and validates the information and finally passes it along to the
*         program handler.
*****************************************************************************/
package controller.geo;

import controller.handler.ProgramHandler;
import model.coordinate.ICoordinate;
import model.coordinate.InvalidCoordinateException;
import model.coordinate.factory.CoordinateFactory;

public class GpsReceiver extends GpsLocator
{
    //FIELDS:-------------------------------------------------------------------
    //
    private CoordinateFactory cFactory;
    private ProgramHandler handler;
    
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    public GpsReceiver( ProgramHandler handler, CoordinateFactory cFactory)
    {
        this.handler = handler;
        this.cFactory = cFactory;
    }
    
    //GPS LOCATOR METHODS:------------------------------------------------------
    //
    @Override
    public void locationReceived(double lat, double lon, double alt)
    {
        double[] pos = {lat,lon,alt};
        ICoordinate coord;
        try
        {
            //Passes in a constructed coordinate to the program rather than raw
            //gps data.
            coord = cFactory.createCoordinate(pos);
            handler.posUpdate(coord);
        }
        catch(InvalidCoordinateException e)
        {
            handler.notifyError(e);
        }
    }
}