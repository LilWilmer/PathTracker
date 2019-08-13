/*****************************************************************************
* AUTH: William Payne
* FILE: GpsUpdater
* LAST MOD: 23/05/19
* PURPOSE: This class is designed to simulate a gps coordinates being sent
*          from a phone GPS
*****************************************************************************/
package controller.geo;

import controller.handler.ProgramHandler;
import java.util.Random;

/**
 *
 * @author Will
 */
public class GpsUpdater implements Runnable
{

    //FIELDS:
    private GpsReceiver receiver;
    private ProgramHandler handler;
    
    //
    //CONSTRUCTOR:
    //
    public GpsUpdater(ProgramHandler h, GpsReceiver r )
    {
        this.handler = h;
        this.receiver = r;
    }
            
    @Override
    public void run() 
    {
        Random rand = new Random();
        double[] c = new double[3];
        
        while(handler.isRunning())
        {
            for(int ii = 0; ii < 3; ii++)
            {
                c[ii] = rand.nextDouble()%30;
            }
            try 
            {
                if(rand.nextInt()%10 == 1)
                {
                    Thread.sleep(Math.abs(rand.nextInt()%20000));
                }
                else
                {
                    Thread.sleep(Math.abs(rand.nextInt()%200));
                }
            } 
            catch (InterruptedException ex) 
            {
            }
            receiver.locationReceived(c[0], c[1], c[2]);
        }
    }
}
