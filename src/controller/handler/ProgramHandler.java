/*****************************************************************************
* AUTH: William Payne
* FILE: ProgramHandler.java
* LAST MOD: 21/05/19
* PURPOSE: Manages the current state of the program using a state pattern
            -Handles the interfacing of the view with the model and deeper
             controller components.
*****************************************************************************/
package controller.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import controller.handler.states.ProgramState;
import java.util.NoSuchElementException;
import model.coordinate.ICoordinate;
import model.route.Route;
public class ProgramHandler
{
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    /*(state)*/
    private boolean isRunning;
    private HashMap<String,ProgramState> states;
    private ProgramState state;

    /*(model)*/
    private Route route;
    private HashMap<String,Route> routes;

    /*(observers)*/
    private HashSet<IStateObserver> stateObservers;
    private HashSet<IHandlerObserver> handlerObservers;
    private HashSet<IErrorObserver> errorHandlers;
        
    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public ProgramHandler()
    {
        this.isRunning = false;
        this.state =null;
        this.states = new HashMap<>();

        this.route = null;
        this.routes = new HashMap<>();

        this.stateObservers = new HashSet<>();
        this.handlerObservers = new HashSet<>();
        this.errorHandlers = new HashSet<>();
    }

    //TODO: FACTORY:

    //
    //ACCESSORS:----------------------------------------------------------------
    //
    
    public boolean isRunning(){return this.isRunning;}
    public Map<String,Route> getRoutes(){return this.routes;}
    public Route getRoute(){return this.route;}
    public String getStateID(){return this.state.getStateID();}
    public ProgramState getState(String id){return states.get(id);}

    //
    //MUTATORS:-----------------------------------------------------------------
    //
    
    public void setIsRunning(boolean isRunning){this.isRunning = isRunning;}
    
    public void setRoute(Route route)
    {
        this.route = route;
        notifyRouteUpdated();
    }
    
    /**
    * SetState:
    * --- --- --- ---
    * Sets the current state of the ProgramHandler and ensures that the
    * previous state has cleaned up.
     * @param state
    */
    public void setState(ProgramState state)
    {
        if(this.state!=null)this.state.tearDown();
        this.state = state;
        this.state.setup();
        notifyStateChange();
    }
    public void setState(String id)
    {
        setState(this.states.get(id));
    }
    
    public void addState(String id, ProgramState state)
    {
        this.states.put(id,state);
    }

    //
    // PROGRAM HANDLER METHODS:-------------------------------------------------
    //
    
    /**
    * Start():
    * --- --- --- ---
    * Starts the Program by setting the program handler to its initial state.
    */
    public void start()
    {
        try
        {
            this.isRunning = true;
            setState("SelectionState");
        }
        catch(NoSuchElementException e)
        {
            notifyError(e);
        }
    }

    /**
    * posUpdate:
    * --- --- --- ---
    * I don't believe this function is currently in use.
     * @param coord
    */
    public void posUpdate(ICoordinate coord)
    {
        this.state.posUpdate(coord);
    }

    /**
     * FUNC: input()
     * PURP: Takes input from the view and performs an operation based on
     *       the current state of the program.
     * @param i 
     */
    public void input(int i)
    {
        this.state.input(i);
    }

    //
    //OBSERVER METHODS:---------------------------------------------------------
    //
    
    public void addStateObserver(IStateObserver ob){this.stateObservers.add(ob);}
    public void addHandlerObserver(IHandlerObserver ob){handlerObservers.add(ob);}
    public void addErrorHandler(IErrorObserver ob){this.errorHandlers.add(ob);}

    public void notifyRouteUpdated()
    {
        for(IHandlerObserver ob : handlerObservers)
        {
            ob.routeUpdated(this.route);
        }
    }
    public void notifyStateChange()
    {
        for(IStateObserver ob : stateObservers)
        {
            ob.stateChanged(this);
        }
    }

    public void notifyError(Throwable e)
    {
        for(IErrorObserver ob : errorHandlers)
        {
            ob.errorOccured(e);
        }
    }
}
