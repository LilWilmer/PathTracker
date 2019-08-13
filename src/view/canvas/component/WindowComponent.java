/******************************************************************************
* Author: William Payne
* File name: XXXXX
* Purpose: XXXXX
* Requires: XXXXX
* Last Mod: XXXXX
******************************************************************************/
package view.canvas.component;

import view.UIUtils;
public class WindowComponent extends Component
{
    //
    //FIELDS:
    //
    private int width;
    private int length;

    //
    //CONSTRUCTOR:
    //
    public WindowComponent(int x, int y, int width, int length)
    {
        super(x,y);
        this.width = width;
        this.length = length;
    }

    //
    //ABSTRACT METHODS:
    //
    @Override
    public void display(String data)
    {
        display();
        UIUtils.pAt(data, this.x+2,this.y+2);
    }

    @Override
    public void display() 
    {
        cleanUp();
        UIUtils.drawWindowAt(this.width, this.length, this.x, this.y);
    }
    
    @Override
    public void cleanUp()
    {
        UIUtils.drawBox(' ', this.x-1, this.y, this.width+2, this.length+1);
    }
}