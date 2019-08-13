package view.canvas;

import java.util.HashSet;
import java.util.LinkedList;
import view.canvas.component.Component;
import view.canvas.component.IComponentObserver;

public class Canvas extends Component
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    
    private LinkedList<Component> components;
    private HashSet<ICanvasObserver> canvObservers;
    
    //
    // CONSTRUCTOR:-------------------------------------------------------------
    //
    
    public Canvas()
    {
        this.components = new LinkedList<>();
        this.canvObservers  = new HashSet<>();
    }
    
    public Canvas(LinkedList<Component> comp)
    {
        this.components = comp;
    }
    
    //
    //MUTATORS AND ACCESSORS:---------------------------------------------------
    //
    
    public void addComponent(Component c)
    {
        //Adding an observer to the component;
        c.addObserver(new Observer() );
        this.components.add(c);
    }
    
    public void remove(Component component) 
    {
        component.removeObserver();
        this.components.remove(component);
    }
    
    public void clearComponenets(){this.components = new LinkedList<>();}
    
    //
    //DISPLAY METHODS:----------------------------------------------------------
    //
    
    @Override
    public void display(String data) 
    {
        //ignore the data
        display();
    }
    
    @Override
    public void display()
    {
        components.forEach((component) -> {
            component.display();
        });
    }
    
    @Override
    public void cleanUp()
    {
        components.forEach((component) ->{
            component.cleanUp();
        });
    }
    
    //
    //OBSERVERS:----------------------------------------------------------------
    //
    
    public void addObserver(ICanvasObserver ob)
    {
        this.canvObservers.add(ob);
    }
    
    public void notifyComponentUpdated(Component comp)
    {
        this.canvObservers.forEach((ob) -> {
            ob.componentUpdated(this, comp);
        });
    }


    public class Observer implements IComponentObserver
    {
        //
        // Handle Methods:
        //
        @Override
        public void componentUpdated(Component comp)
        {
            Canvas.this.notifyComponentUpdated(comp);
        }
    }
}
