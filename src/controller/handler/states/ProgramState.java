/*****************************************************************************
* AUTH: William Payne
* FILE: IProblemState
* DATE: 8/05/19
* PURP: Change Program State
*****************************************************************************/
package controller.handler.states;

import controller.handler.*;
import model.coordinate.ICoordinate;
public abstract class ProgramState
{
    //FIELDS:
    protected String stateID;
    protected ProgramHandler handler;

    //CONSTRUCTOR:
    public ProgramState(ProgramHandler handler)
    {
        this.handler = handler;
    }

    //STATE METHODS:
    public String getStateID(){return stateID;}
    
    public void setup()
    {
        //DEFAULT: NO-OP
    }
    
    public void tearDown()
    {
        //DEFAULT: NO-OP
    }

    public void input(int i)
    {
        //DEFAULT: NO-OP
    }

    //GPS WILL BE FUNNELING INFORMATION INTO THE PROGRAM
    public void posUpdate(ICoordinate coord)
    {
        //DEFAULT: NO-OP
    }
}