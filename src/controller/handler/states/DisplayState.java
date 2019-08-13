/*****************************************************************************
* AUTH: William Payne
* FILE: DisplayState.java
* LAST MOD: 13/05/19
* PURPOSE: Handles input while a route is being displayed.
*****************************************************************************/
package controller.handler.states;

import controller.handler.ProgramHandler;

public class DisplayState extends ProgramState
{
    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public DisplayState(ProgramHandler handler)
    {
        super(handler);
        this.stateID = "DisplayState";
    }

    //
    //STATE METHODS:------------------------------------------------------------
    //
    
    /**
     * PURP: Transitions the state to the SelectionState or the tracking state
     *       depending on the given input i.
     * NOTE: This should use my option strategy pattern instead. due to running
     *       out of time I have substituted in a switch statement.
     * @param i 
     */
    @Override
    public void input(int i)
    {
        //IMPLEMENT IOptions here
        switch (i) 
        {
            case 0:
                this.handler.setState("SelectionState");
                break;
                
            case 1:
                this.handler.setState("TrackingState");
                break;
                
            default:
                this.handler.notifyError(
                        new IllegalArgumentException("invalid input"));
                break;
        }
    }

}