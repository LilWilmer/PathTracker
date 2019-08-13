/*****************************************************************************
* AUTH: William Payne
* FILE: SelectionState.java
* LAST MOD: 13/05/19        
* PURPOSE: 
*****************************************************************************/
package controller.handler.states;

import controller.handler.*;
import controller.menu.NoSuchOptionException;
import controller.menu.Option;
import controller.menu.SubMenu;
import model.route.Route;
public class SelectionState extends ProgramState
{
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private SubMenu menu;

    
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public SelectionState(ProgramHandler handler, SubMenu menu)
    {
        super(handler);
        this.stateID = "SelectionState";
        this.menu = menu;
    }
    
    //STATE METHODS:------------------------------------------------------------
    //
    
    /**
     * PURP: Sets up the selection state with the default menu options and with
     *       a menu option for every route that is selectable.
     */
    @Override
    public void setup()
    {
        menu.addItem(new BackOption());
        menu.addItem(new LoadOption());
        
        //For every route loaded, create an option to select the route in a table
        for(Route r : this.handler.getRoutes().values())
        {
            menu.addItem(new SelectOption(r));
        }
        menu.notifyMenuActivated();
    }
    
    /**
     * PURP: Clears the menu items of the menu object.
     */
    
    @Override
    public void tearDown()
    {
        menu.clearItems();
    }

    /**
     * PURP: Takes an integer input and attempts to select and execute the
     *       option located at its index.
     *       If the selection is invalid notify the error handlers.
     * @param selection 
     */
    @Override
    public void input(int selection)
    {
        try
        {
            menu.select(selection);
        }
        catch (NoSuchOptionException e) 
        {
            this.handler.notifyError(e); 
        }
    }

    // INNER CLASSES:===========================================================
    //
    /**
     * INNER CLASS: SelectOption
     * PURP: Option object for the Select menu
     */
    public class BackOption extends Option
    {   

        public BackOption()
        {
            super("Exit");
        }
        
        @Override
        public void runItem()
        {
            //terminate the program handler
            SelectionState.this.handler.setIsRunning(false);
        }
    }
    /**
     * INNER CLASS: SelectOption
     * PURP: Option object for the Select menu
     */
    public class LoadOption extends Option
    {   
        public LoadOption()
        {
            super("Load Routes");
        }
        
        @Override
        public void runItem()
        {
            //transistion to the loading state
            SelectionState.this.handler.setState("LoadingState");
        }
    }

    /**
     * INNER CLASS: SelectOption
     * PURP: Option object for the Select menu
     */
    public class SelectOption extends Option
    {   
        //FIELDS:
        private Route route;

        public SelectOption(Route route)
        {
            super(route.toString());
            this.route = route;
        }

        @Override
        public void runItem()
        {
            //Set the selected route as the main route
            SelectionState.this.handler.setRoute(this.route);
            
            //Transition to the display state
            SelectionState.this.handler.setState("DisplayState");
        }
    }
}
