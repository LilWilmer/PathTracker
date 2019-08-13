/*****************************************************************************
* AUTH: William Payne
* FILE: ErrorObserver
* LAST MOD: 15/05/19
* PURPOSE: Decide what actions to take when errors occur in the system.
*          Possibly a good idea to add more specific kinds of ErrorObservers
*****************************************************************************/
package controller.handler;

public interface IErrorObserver
{
    void errorOccured(Throwable e);
}