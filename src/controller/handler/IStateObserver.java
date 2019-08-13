/*****************************************************************************
* AUTH: William Payne
* FILE: StateObserver.java
* LAST MOD: 15/05/19
* PURPOSE: Used to send messages to the user interface when a state changes
*****************************************************************************/
package controller.handler;

public interface IStateObserver
{
    void stateChanged(ProgramHandler handler);
}