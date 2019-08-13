package view.canvas.component.modifiers;

import controller.menu.IMenuObserver;
import controller.menu.MenuItem;
import controller.menu.SubMenu;
import view.canvas.Canvas;
import view.canvas.component.TextComponent;
import view.canvas.component.WindowComponent;

public class MenuModifier implements IMenuObserver
{
    //
    // FIELDS:
    //
    
    private Canvas canvas;
    private WindowComponent window;
    
    //
    // CONSTRUCTOR:
    //
    
    public MenuModifier(Canvas canvas, WindowComponent window)
    {
        this.canvas = canvas;
        this.window = window;
    }

    @Override
    public void menuActivated(SubMenu menu)
    {
        int i = 1;
        int[] pos = this.window.getPos();
        this.canvas.clearComponenets();
        this.canvas.addComponent(this.window);
        for(MenuItem item : menu.getItems())
        {
            this.canvas.addComponent(
                new TextComponent("["+i+"]"+item.getName(),pos[0]+2,pos[1]+i));
            i++;
        }
        
        this.canvas.display();
    }
}
