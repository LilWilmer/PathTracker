/*****************************************************************************
* AUTH: William Payne
* FILE: TrackingState
* LAST MOD: 13/05/19
* PURPOSE: 
*****************************************************************************/
package controller.handler.states;

import controller.handler.*;
import controller.handler.states.tracking.*;
import java.util.HashMap;
import model.coordinate.*;
import model.trekker.*;

public class TrackingState extends ProgramState
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    private HashMap<String, GpsState> states;
    private GpsState state;
    private Trekker trekker;
    private TrekkerFactory trekFactory;

    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public TrackingState(ProgramHandler handler, TrekkerFactory trekFactory) 
    {
        super(handler);
        this.trekFactory = trekFactory;
        this.trekFactory.addTrekkerObserver(new Observer());
        this.trekker = null;
        this.states = new HashMap<>();
        this.stateID = "TrackingState";
    }

    //
    // MUTATORS AND ACCESSORS:--------------------------------------------------
    //
    
    public Trekker getTrekker(){return this.trekker;}
    
    public void setState(GpsState state) 
    {
        this.state = state;
    }
    
    public void addState(String stateId, GpsState state)
    {
        this.states.put(stateId, state);
    }
    
    public void setState(String stateID)
    {
        this.state = states.get(stateID);
    }

    //
    // STATE METHODS:-----------------------------------------------------------
    //
    
    /**
     * PURP: Transition back to the selection state
     */
    public void back() 
    {
        this.handler.setState("SelectionState");
    }

    /**
     * PURP: Sets up the tracking by getting a new trekker object from the
     *       TrekkerFactory and initializing the two GpsStates receiving and 
     *       no signal.
     *       When the trek is complete or an error occurs, return to the
     *       selection state.
     */
    @Override
    public void setup() 
    {
        try 
        {
            this.trekker = trekFactory.createTrekker(this.handler.getRoute());
            this.trekker.notifyStart();
            this.states.put("ReceivingState", new ReceivingState(this,trekker));
            this.states.put("NoSignalState", new NoSignalState(this,trekker));
            this.state = this.states.get("NoSignalState");
        } 
        catch (TrekkerException e) 
        {
            this.handler.notifyError(e);
            this.handler.setState("SelectionState");
        }
    }

    @Override
    public void tearDown() 
    {
        this.states.clear();
        super.tearDown();
    }

    @Override
    public void posUpdate(ICoordinate coord) 
    {
        try 
        {
            if( this.state != null)
            {
                this.state.posUpdate(coord);
            }
        } 
        catch (TrekkerException e) 
        {
            this.handler.notifyError(e);
            this.handler.setState("SelectionState");
        }
    }

    @Override
    public void input(int i) 
    {
        if(i == 2)
        {
            double[] pos = trekker.getTarget().getPosition().getPosition();
            posUpdate(new CartesianCoordinate(pos));
        }
        else
        {
            this.state.input(i);
        }
    }

    //
    // INNER OBSERVER CLASSES:
    //
    
    public class Observer implements ITrekkerObserver
    {

        @Override
        public void positionUpdated(Trekker t) 
        {
            //no-op
        }

        @Override
        public void targetReached(Trekker t) 
        {
            //no-op
        }

        @Override
        public void routeEnded(Trekker t)
        {
            TrackingState.this.back();
        }

        @Override
        public void signalUpdated(boolean isTracking) 
        {
            //no-op
        }

    }
}
