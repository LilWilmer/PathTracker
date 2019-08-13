/*****************************************************************************
* AUTH: William Payne
* FILE: TextComponent.java
* LAST MOD: 16/05/19
* PURPOSE: Prints a string of text at the given screen coordinate
*****************************************************************************/
package view.canvas.component;

import view.UIUtils;

public class TextComponent extends Component
{
    public static final String DEFAULT = "\033[0m";
    //
    //FIELDS:-------------------------------------------------------------------
    //
    
    private String colour = "default";
    private String oldText = "";
    private String text = "";

    //
    //CONSTRUCTOR:--------------------------------------------------------------
    //
    
    public TextComponent(int x, int y)
    {
        super(x,y);
    }
    public TextComponent(String text, int x, int y)
    {
        super(x,y);
        this.text = text;
    }

    //
    // MUTATORS AND ACCESSORS:--------------------------------------------------
    //
    
    public void setText(String text)
    {
        this.text = text;
        notifyObservers();
    }
    
    public void setColour(String colour)
    {
        this.colour = colour;
        notifyObservers();
    }
    
    //
    // Display methods:---------------------------------------------------------
    //
    
    public void touch(){notifyObservers();}
    
    @Override
    public void display(String data)
    {
        //save the current position of the cursor.
        UIUtils.mark();
        
        //Tear down the previous text from the last display.
        cleanUp();
        
        //Using ascii escape code to change the text colour .
        UIUtils.getColour(this.colour);
        
        //Print the text at the text field coordinate.
        UIUtils.pAt(data,this.x,this.y);
        
        //Use an escape code to return the colour back the system default
        UIUtils.getColour("default");
        
        //Return cursor back to its original location.
        UIUtils.recall();
        
        //Update old text for the cleanUp().
        this.oldText = data;
    }

    @Override
    public void display()
    {
        display(this.text);
        //DEFAULT no-op
    }
    
    @Override
    public void cleanUp()
    {
        UIUtils.mark();
        UIUtils.drawLineAt(" ", oldText.length(), this.x-1, this.y);
        UIUtils.recall();
    }
}