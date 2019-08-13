/*****************************************************************************
* AUTH: William Payne
* FILE: TerminalDisplay.java
* LAST MOD: 16/05/19
* PURPOSE: Display information to the user depending on the current state of
*          the program.
*****************************************************************************/
package view;

import java.util.HashMap;

import view.canvas.Canvas;
import view.canvas.ICanvasObserver;
import view.canvas.component.Component;
import controller.handler.IStateObserver;
import controller.handler.ProgramHandler;

public class TerminalDisplay
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    private HashMap<String, Canvas> canvases;
    private Canvas currentCanvas;

    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public TerminalDisplay() 
    {
        this.currentCanvas = new Canvas();
        this.canvases = new HashMap<>();
    }

    //
    //GETTERS AND SETTERS:------------------------------------------------------
    //
    
    public void addCanvas(String name, Canvas canvas)
    {
        canvas.addObserver(new CanvasObserver());
        this.canvases.put(name, canvas);
    }

    public void setCanvas(String id)
    {
        this.currentCanvas = canvases.get(id);
    }

    //
    // Display Methods:---------------------------------------------------------
    //
    
    public void display()
    {
        this.currentCanvas.display();
    }
    
    //
    //INNER OBSERVERS:----------------------------------------------------------
    //
    
    public StateObserver getStateObserver(){return new StateObserver();}
    public class StateObserver implements IStateObserver
    {

        @Override
        public void stateChanged(ProgramHandler handler) 
        {
            TerminalDisplay.this.currentCanvas.cleanUp();
            TerminalDisplay.this.setCanvas(handler.getStateID());
            display();
        }
        
    }
    
    public class CanvasObserver implements ICanvasObserver
    {

        @Override
        public void componentUpdated(Canvas canv, Component comp) 
        {
            if(canv == TerminalDisplay.this.currentCanvas)
            {
                comp.display();
            }
        }
        
    }
}