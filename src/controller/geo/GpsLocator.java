/*****************************************************************************
* AUTH: William Payne
* FILE: GpsLocator
* LAST MOD: 15/05/19
* PURPOSE: Test file for hooking up to mobile application
*****************************************************************************/
package controller.geo;

public abstract class GpsLocator
{
    public abstract void locationReceived(double lat, double lon, double alt);

}