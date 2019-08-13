/*****************************************************************************
* AUTH: William Payne
* FILE: OutOfService.java
* LAST MOD: 21/05/19
* PURPOSE: Handles the tracking mode when no GPS signal is being received.
*****************************************************************************/
package controller.handler.states.tracking;

import controller.handler.states.TrackingState;
import model.coordinate.ICoordinate;
import model.trekker.Trekker;
import model.trekker.TrekkerException;

public class NoSignalState extends GpsState
{
    //FIELDS:-------------------------------------------------------------------
    //
    
    private TrackingState tracker;
    private Trekker trekker;

    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public NoSignalState(TrackingState tracker, Trekker trekker)
    {
        this.stateID = "TrackingState";
        this.tracker = tracker;
        this.trekker = trekker;
    }

    /**
     * PURP: Set state back to tracking when data is received.
     * @param coord
     * @throws TrekkerException 
     */
    @Override
    public void posUpdate(ICoordinate coord) throws TrekkerException
    {
        //TODO: ADD OBSERVER???
        this.trekker.setIsTracking(true);
        this.tracker.setState("ReceivingState");
        this.tracker.posUpdate(coord);
    }

    /**
     * PURP: Allows a user to manually check off points when GPS signal is lost.
     * @param input 
     */
    @Override
    public void input(int input) 
    {
        if(input == 0)
        {
            this.tracker.back();
        }
        else
        {
            try
            {
                this.trekker.targetReached();
            }
            catch (TrekkerException e)
            {
                //TODO: Notify some error handler.
                //Nothing can be done here
                this.tracker.back();
            }
        }
    }
}
