/*****************************************************************************
* AUTH: William Payne
* FILE: GpsState.java
* LAST MOD: 15/05/19
* PURPOSE: Modifies the functionality of TrackingState depending on whether 
*          or not the program is relieving any tracking information.
*****************************************************************************/
package controller.handler.states.tracking;

import model.coordinate.ICoordinate;
import model.trekker.TrekkerException;

public abstract class GpsState
{
    public String stateID;
    public abstract void posUpdate(ICoordinate coord) throws TrekkerException;
    public abstract void input(int input);
}