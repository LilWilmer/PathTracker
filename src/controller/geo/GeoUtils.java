/*****************************************************************************
* AUTH: William Payne
* FILE: GeoUtils
* LAST MOD: 17/05/19
* PURPOSE: Interfacing class
*****************************************************************************/
package controller.geo;

import java.io.IOException;

public class GeoUtils
{
    public double calcMetresDistance(double lat1, double long1,
                                    double lat2, double long2) 
    {
        double x = 0.0f;
        return x;
    }

    public String retrieveRouteData() throws IOException 
    {
        String output = ""
        +"theClimb Amazing views!\n"
        +"-31.94,115.75,47.1,Easy start\n"
        +"-31.94,115.75,55.3,Tricky, watch for drop bears.\n"
        +"-31.94,115.75,71.0,I*feel,like.over-punctuating!@#$%^&*()[]{}<>.?_+\n"
        +"-31.93,115.75,108.0,Getting there\n"
        +"-31.93,115.75,131.9\n\n"
                
        +"mainRoute Since I was young\n"
        +"-31.96,115.80,63.0,I knew\n"
        +"-31.95,115.78,45.3,I'd find you\n"
        +"-31.95,115.77,44.8,*theStroll\n"
        +"-31.94,115.75,47.1,But our love\n"
        +"-31.93,115.72,40.1,Was a song\n"
        +"-31.94,115.75,47.1,*theClimb\n"
        +"-31.93,115.75,131.9,Sung by a dying swan\n"
        +"-31.92,115.74,128.1\n\n"
                
        +"theStroll Breathe in the light\n"
        +"-31.95,115.77,44.8,I'll stay here\n"
        +"-31.93,115.76,43.0,In the shadow\n"
        +"-31.94,115.75,47.1\n\n"

                
        +"TESTROUTE expect dist = 10, climb = 10, descent = 10\n"
        +"0   ,1 ,0  ,A\n"
        +"5 ,1 ,1  ,B\n"
        +"10   ,1 ,2  ,C\n"
        +"15 ,1 ,3  ,D\n"
        +"20   ,1 ,4  ,E\n"
        +"25 ,1 ,5  ,F\n"
        +"30   ,1 ,6  ,G\n"
        +"35 ,1 ,7  ,H\n"
        +"40   ,1 ,8  ,I\n"
        +"45 ,1 ,9  ,J\n"
        +"50   ,1 ,10 ,K\n"
        +"55 ,1 ,9  ,L\n"
        +"60   ,1 ,8  ,M\n"
        +"65 ,1 ,7  ,N\n"
        +"70   ,1 ,6  ,O\n"
        +"75 ,1 ,5  ,P\n"
        +"80   ,1 ,4  ,Q\n"
        +"85 ,1 ,3  ,R\n"
        +"90   ,1 ,2  ,S\n"
        +"95 ,1 ,1  ,T\n"
        +"100  ,1 ,0\n";
                
        return output;
    }
}