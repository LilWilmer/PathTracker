/*****************************************************************************
* AUTH: William Payne
* FILE: XXXXX
* LAST MOD: XXXXX
* PURPOSE: XXXXX
*****************************************************************************/
package controller.handler.states.tracking;

import java.util.TimerTask;
import controller.handler.ProgramHandler;
import controller.handler.states.TrackingState;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.coordinate.ICoordinate;
import model.trekker.Trekker;
import model.trekker.TrekkerException;
public class ReceivingState extends GpsState
{
    //STATIC:
    public static final long TIME_OUT = 10000;

    //FIELDS:
    ICoordinate lastCoord;
    TrackingState tracker;
    Trekker trekker;

    public ReceivingState( TrackingState tracker, Trekker trekker)
    {
        this.stateID = "TrackingState";
        this.lastCoord = null;
        this.tracker = tracker;
        this.trekker = trekker;
    }
    
    /**
     * 
     * @param coord 
     * @throws model.trekker.TrekkerException 
     */
    @Override
    public void posUpdate(ICoordinate coord) throws TrekkerException 
    {
        this.trekker.setPosition(coord);
        Timer timer = new Timer();
        TimerTask t = new TimerTask()
        {
            @Override
            public void run()
            {
                if(coord == lastCoord)
                {
                    ReceivingState.this.tracker.setState("NoSignalState");
                    if(ReceivingState.this.trekker != null)
                        ReceivingState.this.trekker.setIsTracking(false);

                }
                timer.cancel();
            }
        };
        timer.schedule(t, TIME_OUT);

        this.lastCoord = coord;
    }

    @Override
    public void input(int input) 
    {
        if(input == 0)
        {
            this.tracker.back();
        }
        
    }
}
