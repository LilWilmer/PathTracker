/*****************************************************************************
* AUTH: William Payne
* FILE: IComponent.java
* LAST MOD: 16/05/19
* PURPOSE: Terminal user interface components;
*****************************************************************************/
package view.canvas.component;

import java.util.HashSet;

public abstract class Component
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    protected int x;
    protected int y;
    protected HashSet<IComponentObserver> observers;

    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public Component(){this.x = 0; this.y = 0;}
    public Component(int x, int y)
    {
        this.observers = new HashSet<>();
        this.setPosition(x,y);
    }

    //
    // ACCESSORS AND MUTATORS:--------------------------------------------------
    //
    
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int[] getPos(){int[] pos = {this.x,this.y};return pos;}
    
    /*Set to final as it is used the in constructor or a Component
      overriding this method could cause the constructor to leave fields
      unintialized.*/
    public final void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        notifyObservers();
    }
    
    public void setPosition(int[] pos){
        setPosition(pos[0], pos[1]);
    }
    

    //
    // OBSERVER METHODS:--------------------------------------------------------
    //
    
    
    public void addObserver(IComponentObserver ob)
    {
        this.observers.add(ob);
    }
    
    //Observer Methods:
    public void notifyObservers()
    {
        this.observers.forEach((ob)->{
            ob.componentUpdated(this);
        });
    }

    //
    // ABSTRACT METHODS:--------------------------------------------------------
    //
    
    public abstract void display(String data);
    public abstract void display();
    public abstract void cleanUp();

    public void removeObserver()
    {
        this.observers = new HashSet<>();
    }
}