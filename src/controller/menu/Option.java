/*****************************************************************************
* AUTH: William Payne
* FILE: Option.java
* LAST MOD: 21/02/2019
* PURPOSE: Display a name and run 1 or more Option Commands
*****************************************************************************/
package controller.menu;

public abstract class Option implements MenuItem
{
    //CLASS FIELDS:-----------------------------------------------------------
    private String name;

    //CONSTRUCTORS:-----------------------------------------------------------
    public Option(String name)
    {
        this.name = name;
    }

    /*SETTERS:----------------------------------------------------------------
    */
    public void setName(String name)
    {
        this.name = name;
    }

    //GETTERS:----------------------------------------------------------------
    @Override
    public String getName(){return this.name;}


    /*************************************************************************
    * SUBMODULE: runItem() < abstract method from : MenuItem Interface>
    *   PURPOSE: + Iterates through the optionCommands and runs doOption()
    *            + Handle any errors that occur
    *   IMPORTS: none
    *   EXPORTS: (boolean) isSuccessful
    *************************************************************************/
    @Override
    public abstract void runItem() throws IllegalArgumentException;
}
