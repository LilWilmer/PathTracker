/*****************************************************************************
* AUTH: William Payne
* FILE: LoadingState.java
* LAST MOD: 13/05/19        
* PURPOSE: 
*****************************************************************************/
package controller.handler.states;

import java.io.IOException;
import model.route.RouteReader;
import controller.handler.*;
import controller.geo.GeoUtils;
import java.util.HashMap;
import model.route.Route;
import model.route.RouteReadingException;
public class LoadingState extends ProgramState
{
    //FIELDS:
    private RouteReader reader;
    private GeoUtils geoUtils;
    
    //CONSTRUCTOR:
    public LoadingState(ProgramHandler handler, GeoUtils geoUtil, RouteReader r)
    {
        super(handler);
        this.stateID = "LoadingState";
        this.reader = r;
        this.geoUtils = geoUtil;
    }

    /**
     * METHOD: setup()
     * PURP: Attempts to read path data into the route map stored in the
     *       program handler by making a call on the GeoUtils class field
     */
    @Override
    public void setup()
    {
        HashMap<String, Route> newRoutes = new HashMap<>();
        String data;
        try
        {
            //Attempt to get data from the geoutil method
            data = geoUtils.retrieveRouteData();
            try 
            {
                //If the data is retrieved then parse it into route objects
                this.reader.getRoutes(data, newRoutes);
                this.handler.getRoutes().putAll(newRoutes);
            } 
            catch (RouteReadingException e) 
            {
                //if the data is in an invalid format notify the handlers
                this.handler.notifyError(e);
            }
        }
        catch(IOException e)
        {
            //if the data could not be retrieved notify the handlers
            this.handler.notifyError(e);
        }
        
        //Transition back to the Selection state
        this.handler.setState("SelectionState");
    }

}