/*****************************************************************************
 * AUTHOR: William Payne
 * FILE: SubMenu.java
 * LAST MOD: 7/09/18
 ****************************************************************************/
package controller.menu;

import java.util.*;

/*****************************************************************************
* PUBLIC CLASS: SubMenu -implements MenuItem
*-----------------------------------------------------------------------------
* PURPOSE:
*   Provides a user interface for selecting MenuItem's
*
* PUBLIC CONSTANTS:
*   BORDER(String)  ~ Border used in the UI.
*   RETURN(String)  ~ Special string telling the SubMenu to end the loop.
*
* CLASS FIELDS:
*   menus()
*
* METHODS:
*   SubMenu()   ~ Creates a new SubMenu.
*   setName()   ~ Sets the name field to the imported string.
*   setMenus()  ~ Sets the menu array to the imported MenuItem array.
*   display()   ~ Returns the name of the sub menu.
*   display()   ~ Returns the place and name in a formatted string.
*   run()       ~ Executes the menu if the imported data is valid.
*   displayMenus() ~ Prints a list of IMenuItems to the screen and waits for a
*                    a selection from the user, running the MenuItem they
*                    select. The menu will loop till it receives the return
*                    string from the activated function.
*
* JUSTIFCATION:
*   Having a generic SubMenu class allowed for very modular and easy to update
*   menus for the program. Arrays where used to store the IMenuItems because
*   access time on any element is O(1) and the size of the array is always
*   known and set at compile time (saving space).
*
*****************************************************************************/
public class SubMenu implements MenuItem
{
    //
    //CLASS FIELDS:-----------------------------------------------------------
    //

    private LinkedList<MenuItem> menus;
    private String name;
    private HashSet<IMenuObserver> observers;

    //CONSTRUCTOR-------------------------------------------------------------
    public SubMenu( String name)
    {
        this.name = name;
        this.menus = new LinkedList<>();
        this.observers = new HashSet<>();
    }
    public SubMenu( String name, LinkedList<MenuItem> menus)
    {
        setName(name);
        setMenus(menus);
        this.observers = new HashSet<>();
    }

    //GETTERS-----------------------------------------------------------------
    @Override
    public String getName(){return name;}
    public LinkedList<MenuItem> getItems(){return menus;}
    public int size(){return menus.size();}

    //SETTERS-----------------------------------------------------------------
    public void setName( String name )
    {
        if( name == null )
        {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void setMenus( LinkedList<MenuItem> menus)
    {
        this.menus = menus;
    }
    
    public void addItem(MenuItem item){this.menus.addLast(item);}
    
    public void clearItems(){this.menus = new LinkedList<>();}

    //OTHER METHODS:----------------------------------------------------------
    /*************************************************************************
    * SUBMODULE: run
    *   IMPORTS: object(Object)
    *   EXPORTS: message(String)
    *   PURPOSE: Loop through menu items and allow user to select option
     * @param choice
     * @throws controller.menu.NoSuchOptionException
    *************************************************************************/
    public void select(int choice) throws NoSuchOptionException
    {
        try
        {
            menus.get(choice).runItem();
        }
        catch(IndexOutOfBoundsException e)
        {
            throw new NoSuchOptionException("Option not in menun, ", e);
        }
    }
    
    @Override
    public void runItem()
    {
        //no-op
    }

    //ADD OBSERVER:
    public void addObserver(IMenuObserver ob)
    {
        this.observers.add(ob);
    }

    //CALL OBSERVER:----------------------------------------------------------
    public void notifyMenuActivated()
    {
        observers.forEach((ob) -> {
            ob.menuActivated(this);
        });
    }
}
