package view.canvas.component.modifiers;

import model.trekker.ITrekkerObserver;
import model.trekker.Trekker;
import view.canvas.component.TextComponent;

public class TrekkerModifier implements ITrekkerObserver
{
    //
    // FIELDS:
    //
    private String lastPoint = "-";
    private TextComponent gpsField;
    private TextComponent descriptionField;
    private TextComponent targetField;
    private TextComponent lastField;
    private TextComponent distField;
    private TextComponent climbField;
    private TextComponent descentField;
    private TextComponent skipField;


    //
    // CONSTRUCTOR:
    //
    
    public TrekkerModifier( TextComponent gps,
                            TextComponent descr,
                            TextComponent last,
                            TextComponent target,
                            TextComponent dist,
                            TextComponent climb,
                            TextComponent descent,
                            TextComponent skipField
                            )
    {
        this.gpsField = gps;
        this.descriptionField = descr;
        this.lastField = last;
        this.targetField = target;
        this.distField = dist;
        this.climbField = climb;
        this.descentField = descent;
        this.skipField = skipField;
    }
    
    //
    // ComponentModifer METHODS:
    //
    
    //
    //  ITrekkerObserver METHODS:
    //
    
    @Override
    public void positionUpdated(Trekker t)
    {
        this.gpsField.setText("GPS: "+t.getPosition().toString());
        updateCommon(t);
    }

    @Override
    public void targetReached(Trekker t) 
    {
        this.descriptionField.setText("Description: "+t.getTarget().getDescription());
        this.lastField.setText("Last Point: "+ lastPoint);
        this.targetField.setText("Next Point: "+ t.getTarget().getLabel());
        this.lastPoint = t.getTarget().getLabel();
        updateCommon(t);
    }
    
    public void updateCommon(Trekker t)
    {
        String[] fields = {
            String.format("Remaining Distance: %7.2f",t.getRemainingDist())   ,
            String.format("Remaining Climb:    %7.2f",t.getRemainingClimb())  ,
            String.format("Remaining Descent:  %7.2f",t.getRemainingDescent())
        };
        
        this.distField.setText(fields[0]);
        this.climbField.setText(fields[1]);
        this.descentField.setText(fields[2]);
    }

    @Override
    public void routeEnded(Trekker t) 
    {
        this.lastPoint = "-";
        this.lastField.setText(this.lastPoint);
    }

    @Override
    public void signalUpdated(boolean isTracking)
    {
        if(isTracking)
        {
            //this.canvas.remove(skipField);
            this.gpsField.setColour("default");
            this.skipField.cleanUp();

        }
        else
        {
            //this.canvas.addComponent(skipField);
            this.gpsField.setColour("red");
            this.skipField.touch();
        }
    }
}
