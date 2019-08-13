/*****************************************************************************
* AUTH: William Payne
* FILE: Trekker.java
* LAST MOD: 12/05/19
* PURPOSE: Keep track of a trekker position and next target location
*****************************************************************************/
package model.trekker;

import java.util.*;
import model.coordinate.*;
import model.point.*;
import view.UIUtils;
public class Trekker
{
    //
    //CONST:--------------------------------------------------------------------
    //
    
    private double[] NEAR_ENOUGH = {10,10,10};

    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private int nodeIndex;
    private boolean isTracking;
    
    private double remainingDist;
    private double remainingClimb;
    private double remainingDescent;
    
    private ICoordinate curPos;
    private NavPoint target;
    private LinkedList<NavPoint> route;
    private HashSet<ITrekkerObserver> observers;

    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public Trekker(ICoordinate coord, LinkedList<NavPoint> points, double[] d)
    {
        this.isTracking = false;
        
        this.remainingDist    = d[0];
        this.remainingClimb   = d[1];
        this.remainingDescent = d[2];
        
        this.nodeIndex = 0;
        this.curPos = coord;
        this.route = points;
        this.target = points.get(nodeIndex);
        this.observers = new HashSet<>();
    }

    //
    //GETTERS : SETTERS---------------------------------------------------------
    //
    
    public ICoordinate getPosition(){return this.curPos;}
    public NavPoint getTarget(){return this.target;}
    
    public double getRemainingDist()
    {
        return this.remainingDist + curPos.flatDistance(target.getPosition());
    }

    public double getRemainingClimb()
    {
        double dist = curPos.vertDistance(target.getPosition());
        if(dist < 0)
        {
            dist = 0.0f;
        }
        return this.remainingClimb + dist;
    }
    
    public double getRemainingDescent()
    {
        double dist = curPos.vertDistance(target.getPosition());
        if(dist > 0)
        {
            dist = 0.0f;
        }
        return remainingDescent - dist;
    }

    //FACTORY:
    public static Trekker createTrekker(LinkedList<NavPoint> points, double[] d)
    {
        double[] pos = {0.0,0.0,0.0};
        ICoordinate coord = new CartesianCoordinate(pos);
        return new Trekker( coord, points, d);
    }

    
    //
    // MUTATORS:----------------------------------------------------------------
    //
    
    public void setIsTracking(boolean isTracking)
    {
        this.isTracking = isTracking;
        notifySignal();
    }
    
    /**
    * Update the trekker position, if within range or target NavPoint:
    * --- --- --- ---
     * @param newPos
     * @throws model.trekker.TrekkerException
    */
    public void setPosition(ICoordinate newPos) throws TrekkerException
    {
        this.curPos = newPos;

        try
        {
            if(this.curPos.within(NEAR_ENOUGH, this.target.getPosition()))
            {
                targetReached();
            }
        }
        catch(IncompatibleCoordinateException | TrekkerException e)
        {
            throw new TrekkerException("Coordinate used to update is invalid");
        }
        notifyPositionUpdate();
    }

            

    /**
     * targetReached(): --- --- --- --- When the trekker position is close enough
     * to the target point the next NavPoint in the route is loaded as the target.
     * 
     * @throws model.trekker.TrekkerException
     */
    public void targetReached() throws TrekkerException
    {
        this.nodeIndex++;
        if(this.nodeIndex > route.size())
        {
            //TODO:
            int ii = 1;
            for(NavPoint p : route)
            {
                UIUtils.pAt(p.toString(), 80, ii++);
            }
            throw new TrekkerException(
                    "Trekker is trying to get a point at the last node");
        }

        if(this.nodeIndex == this.route.size())
        {
            
            notifyRouteEnded();
        }
        else
        {
            calculateRemainingDist();
            this.target = this.route.get(this.nodeIndex);
            notifyTargetReached();
        }
       
    }
    
    /**
     * METHOD: Calculated the remaining distance from the target node to the
     *         end of the route. This calculated distance IGNORES the users
     *         position.
     */
    public void calculateRemainingDist()
    {
        double dist = this.target.getVerticalDist();
        if(dist > 0)
        {
            this.remainingClimb -= dist;
        }
        else if(dist < 0)
        {
            this.remainingDescent += dist;
        }
        this.remainingDist -= this.target.getHorizontalDist();
    }

    @Override
    public String toString()
    {
        String out = "";
        for(NavPoint n : route)
        {
            out += n.toString()+"\n";
        }
        return out;
    }

    //
    //OBSERVER METHODS:---------------------------------------------------------
    //
    
    public void addObserver(ITrekkerObserver ob)
    {
        this.observers.add(ob);
    }
    
    public void notifyStart()
    {
        notifyPositionUpdate();
        notifyTargetReached();
    }

    public void notifySignal()
    {
        for(ITrekkerObserver ob : this.observers)
        {
            ob.signalUpdated(this.isTracking);
        }
    }
                
    public void notifyPositionUpdate()
    {
        for(ITrekkerObserver ob : this.observers)
        {
            ob.positionUpdated(this);
        }
    }

    public void notifyTargetReached()
    {
        for(ITrekkerObserver ob : observers)
        {
            ob.targetReached(this);
        }
    }

    public void notifyRouteEnded()
    {
        for(ITrekkerObserver ob : observers)
        {
            ob.routeEnded(this);
        }
    }
}
