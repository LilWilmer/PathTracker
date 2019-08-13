/*****************************************************************************
* AUTH: William Payne
* FILE: TerminalUI
* LAST MOD: 14/05/19
* PURPOSE: Pass information into the ProgramHandler
*****************************************************************************/
package view;

//
//IMPORTS:
//
import controller.handler.*;
import java.util.Timer;
import java.util.TimerTask;
public class TerminalUI implements Runnable, IErrorObserver, IStateObserver
{
    //
    //CONST:
    //
    public static final int[] STATE_POS = {3,40};
    public static final int[] INPUT_POS = {3,43};
    public static final int[] ERROR_POS = {3,48};
    
    //
    //FIELDS:
    //
    private UserInput reader;
    private ProgramHandler handler;
    
    //CONSTRUCTOR:
    public TerminalUI(ProgramHandler handler, UserInput reader)
    {
        this.handler = handler;
        this.reader = reader;
    }
    
    /**
     * PURP: Continuously prompts user for input until the program state is
     *       set to finis
     */
    @Override
    public void run()
    {
        int input;
        while(handler.isRunning())
        {
            UIUtils.drawBox(' ', 1, 42, 20, 3);
            UIUtils.drawWindowAt(20, 2, 1, 42);
            
            //TODO: Possibly change to a strategy pattern to allow
            //      for different kinds of input
            input = reader.readInt("ENTER", -Integer.MAX_VALUE, Integer.MAX_VALUE, INPUT_POS);
            input--;
            this.handler.input(input);
        }
    }

    @Override
    public void errorOccured(Throwable e)
    {
        UIUtils.pAt(handler.getStateID(),STATE_POS);
        UIUtils.drawBox(' ', 0, 47, 42, 4);
        UIUtils.drawWindowAt(40, 2, 1, 47);
        UIUtils.pAt("ERROR: "+e.getMessage(), ERROR_POS);
        Timer timer = new Timer();
        TimerTask t = new TimerTask()
        {
            @Override
            public void run()
            {
                UIUtils.mark();
                UIUtils.drawBox(' ', 0, 47, 42, 4);
                timer.cancel();
                UIUtils.recall();
            }
        };  
        timer.schedule(t, 4000);
        /* DEBUGGING
        for(int ii =0; ii < e.getStackTrace().length; ii++)
        {
            UIUtils.pAt(e.getStackTrace()[ii].toString(), 3, errorPos[1]+ii+1);
        }*/
    }

    @Override
    public void stateChanged(ProgramHandler handler)
    {
        UIUtils.drawBox(' ', 1, 39, 20, 3);
        UIUtils.drawWindowAt(20, 2, 1, 39);
        UIUtils.pAt(handler.getStateID(),STATE_POS);
    }
}
