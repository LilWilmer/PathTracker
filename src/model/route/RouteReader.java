/*****************************************************************************
* AUTH: William Payne
* FILE: RouteReader
* DATE: 8/05/19
* PURP: Reads the formatted route data from GeoUtils into the a composite route
*       object. The constructed route objects are added to the imported map
*       if the route reading fails all entries are removed from the imported
*       map.
*****************************************************************************/
package model.route;

import java.util.*;

import model.coordinate.InvalidCoordinateException;
import model.coordinate.factory.CoordinateFactory;
import model.point.WayPoint;

public class RouteReader 
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    private CoordinateFactory coordFactory;
    
    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public RouteReader(CoordinateFactory coordFactory)
    {
        this.coordFactory = coordFactory;
    }
    
    public void getRoutes(String data, Map<String, Route> routes)
            throws RouteReadingException
    {
        //INIT:
        boolean endReached;
        int ii = 0;
        String[] lines = data.split("\n");
        String[] routeData;
        
        //START:
        while( ii < lines.length )
        {
            Route route = null;
            endReached = false;
            lines[ii] = lines[ii].trim();
            if(lines[ii].contains(" "))
            {

                routeData = lines[ii].split(" ",2);
                ii++; //moving cursor to the next line;
                
                if(routes.containsKey(routeData[0]))
                {
                    route = routes.get(routeData[0]);
                    
                    /*route was created as a subroute before init*/
                    if(route.getDescription() == null)
                    {
                        route.setDescription(routeData[1]);
                    }
                    /*route has been initialized before.. reset it*/
                    else
                    {
                        route = null;
                    }
                }
                
                /*if the route is completely new to the route Map*/
                if(route == null)
                {
                    route = new Route(routeData[0],routeData[1]);
                    routes.put(route.getName(), route);
                }

                
                int jj = 1;
                //Once the route object is created, start adding the way points
                while( ii < lines.length && lines[ii].contains(",") && !endReached)
                {
                    if(lines[ii].split(",").length == 3)
                    {
                        endReached = true;
                    }
                    
                    route.add(createPoint(jj, lines[ii], routes));
                    ii++;
                    jj++;
                }
                //If the route reached the end for adding a terminating waypoint
                //the data was invalid.
                if(!endReached)
                {
                    throw new RouteReadingException("Route ended on link");
                }
            }
            else
                ii++;
        }
        if(!validateRoutes(routes))
        {   
            throw new RouteReadingException("Route is invalid, check data");
        }
    }

    /**
     * FUNC: createPoint()
     * PURP: Creates a point and a link
     * @param label
     * @param data
     * @param links
     * @return
     * @throws RouteReadingException 
     */
    public WayPoint createPoint(int label, String data, Map<String, Route> links)
            throws RouteReadingException
    {
        WayPoint point = null;
        double[] coord = new double[3];
        String[] tokens = data.split(",",4);

        try
        {
            //Make sure the split string has at least 3 and at most 4 fields
            if(tokens.length >= 3 && tokens.length <=4)
            {
                for(int ii = 0; ii < coord.length; ii++)
                {
                    coord[ii] = Double.parseDouble(tokens[ii]);
                }
                //TODO: Dependencies are too high, fix with factory
                point = new WayPoint(label+"", coordFactory.createCoordinate(coord));
            }
            else
            {
                throw new RouteReadingException("Unexpected data fields found");
            }
        }
        catch(NumberFormatException e)
        {
            throw new RouteReadingException("Failed parsing coordinate data, ",e);
        }
        catch(InvalidCoordinateException e)
        {
            throw new RouteReadingException("Invalid waypoint data, ",e);
        }
        
        //the 4th field contains data for a route linking object
        if(tokens.length == 4)
        {
            RouteLink link = createLink(tokens[3], links);
            point.setLinker(link);
            try 
            {
                link.setPrev(point);
            } 
            catch (UnlinkableException e)
            {
                throw new RouteReadingException("Points are two far apart", e);
            }
        }
        return point;
    }

    /**
     * METHOD: createLink
     * PURP: Creates a RouteLink or segment depending on the data provided
     * @param data
     * @param routes
     * @return
     * @throws RouteReadingException 
     */
    public RouteLink createLink(String data, Map<String, Route> routes)
            throws RouteReadingException
    {
        Route route;
        RouteLink link = null;

	//If a Link description starts with "*" its a route name
        try
        {
            
            if(data.startsWith("*"))
            {
                //Remove the prefix "*"
                data = data.replaceFirst("\\*","");
                route = routes.get(data);

                if(route==null)
                {
                    route = new Route(data,null);
                    routes.put(data,route);
                }

                link = new SubRoute(route);
            }
            else //ELSE just make a normal WayPoint segment
            {
                link = new Segment(data);
            }
        }
        catch(IllegalArgumentException e)
        {
            throw new RouteReadingException("Invalid route link data, ",e);
        }
        return link;
    }
    
    public boolean validateRoutes(Map<String, Route> routes)
    {
        for(Route r : routes.values())
        {
            if(!r.validate())
            {
                return false;
            }
        }
        return true;
    }
}
