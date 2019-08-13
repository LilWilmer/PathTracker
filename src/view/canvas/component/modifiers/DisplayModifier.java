package view.canvas.component.modifiers;

import controller.handler.IHandlerObserver;
import java.util.LinkedList;
import model.point.WayPoint;
import model.route.Route;
import view.canvas.Canvas;
import view.canvas.component.TextComponent;
import view.canvas.component.WindowComponent;

public class DisplayModifier implements IHandlerObserver
{
    //
    // FIELDS:
    //
    
    private Canvas canvas;
    private WindowComponent window;
    
    //
    // CONSTRUCTOR:
    //
    
    public DisplayModifier(Canvas canvas, WindowComponent window)
    {
        this.canvas = canvas;
        this.window = window;
    }

    @Override
    public void routeUpdated(Route r) 
    {
        int ii = 1;
        double[] dist = new double[3];
        int[] xy = this.window.getPos();
        r.calcDistance(dist);

        String[] fields = { 
                        String.format("Route Name:  %-50s",r.getName()),
                        String.format("Description: %-50s",r.getDescription()),
                        String.format("Distance: %-6.2fm",dist[0]),
                        String.format("Ascent:   %-6.2fm",dist[1]),
                        String.format("Descent:  %-6.2fm",dist[2]),
                        String.format("---Way Points---"),
                        String.format("Label %8s %9s %7s %5s","lat", "long", "alt", "Desc")
        };
        
        this.canvas.clearComponenets();
        this.canvas.addComponent(this.window);

        for(String s : fields)
        {
            this.canvas.addComponent(
            new TextComponent(s, xy[0]+2, xy[1]+ii));
            ii++;
        }

        //TODO: GET LENGTH
        LinkedList<WayPoint> points = new LinkedList<>();
        r.getPoints(points);
        for(WayPoint n : points)
        {
            this.canvas.addComponent(
                    new TextComponent(n.toString(), xy[0]+2, xy[1]+ii)
            );
            ii++;
        }
        ii++;
        this.canvas.addComponent(
        new TextComponent("[1].Return",xy[0]+2,xy[1]+ii));
        ii++;
        
        this.canvas.addComponent(
        new TextComponent("[2].Go",xy[0]+2,xy[1]+ii));
        ii++;
        
    }
}
