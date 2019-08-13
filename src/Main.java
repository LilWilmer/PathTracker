/*****************************************************************************
* AUTH: William Payne
* FILE: Main.java
* LAST MOD: 21/05/19
* PURPOSE: Handles setting up the object structures and starting the program
*****************************************************************************/
import view.*;
import view.UIUtils;
import controller.handler.*;
import controller.handler.states.*;
import controller.menu.SubMenu;
import controller.geo.GeoUtils;
import controller.geo.GpsReceiver;
import controller.geo.GpsUpdater;
import model.coordinate.factory.*;
import model.route.RouteReader;
import model.trekker.*;
import view.canvas.Canvas;
import view.canvas.component.*;
import view.canvas.component.modifiers.*;

public class Main
{
    public static void main(String[] args)
    {
        //INIT:
        UIUtils.initDefautColours();
        
        CoordinateFactory cFactory = new CartesianCoordinateFactory();
        TrekkerFactory tFactory = new TrekkerFactory(cFactory);
        GeoUtils util = new GeoUtils();
        RouteReader reader = new RouteReader(cFactory);
        ProgramHandler handler = new ProgramHandler();
        TerminalUI ui = new TerminalUI(handler, new UserInput());
        TerminalDisplay display = new TerminalDisplay();
        
        GpsReceiver receiver = new GpsReceiver(handler, cFactory);
        GpsUpdater gps = new GpsUpdater(handler, receiver);
        Thread sample = new Thread(gps);
        
        //CANVAS SETUP:
        display.addCanvas("SelectionState", buildSelectionState(handler));
        display.addCanvas("DisplayState",   buildDisplayState(  handler));
        display.addCanvas("TrackingState",  
                    buildTrackingState( handler, tFactory));
        buildLoadingState(handler,util,reader);

        //OBSERVER LINKING:
        handler.addErrorHandler(ui);
        handler.addStateObserver(ui);
        handler.addStateObserver(display.getStateObserver());

        //START:
        UIUtils.clearScreen();
        
        handler.start();
        sample.start();

        ui.run();
        
        //END:
        UIUtils.clearScreen();
    }
    
    //SELECTION STATE:
    /**
     * 
     * @param handler
     * @return 
     */
    public static Canvas buildSelectionState(ProgramHandler handler)
    {
        
        SubMenu menu = new SubMenu("Main Menu");
        SelectionState state = new SelectionState(handler, menu);
        handler.addState(state.getStateID(), state);

        Canvas canvas = new Canvas();
        WindowComponent window = new WindowComponent(1,1,120,15);
        menu.addObserver(new MenuModifier(canvas, window));
        return canvas;
    }
    
    //LOADING STATE:
    /**
     * 
     * @param handler
     * @param util
     * @param reader 
     */
    public static void buildLoadingState(ProgramHandler handler,
                                         GeoUtils util,
                                         RouteReader reader)
    {
        LoadingState state = new LoadingState(handler, util, reader);
        handler.addState(state.getStateID(), state);
    }
    
    //DISPLAY STATE:
    /**
     * 
     * @param handler
     * @return 
     */
    public static Canvas buildDisplayState(ProgramHandler handler)
    {
        DisplayState state = new DisplayState(handler);
        handler.addState(state.getStateID(), state);
        
        Canvas canvas = new Canvas();
        WindowComponent window = new WindowComponent(1,1,120,35);
        handler.addHandlerObserver(new DisplayModifier(canvas,window));
        
        return canvas;
    }
    
    //TRACKING STATE:
    /**
     * 
     * @param handler
     * @param tFactory
     * @return 
     */
    public static Canvas buildTrackingState(ProgramHandler handler,
                                            TrekkerFactory tFactory)
    {
        //add new tracking state to the program handler:
        TrackingState state = new TrackingState(handler, tFactory);
        handler.addState(state.getStateID(), state);
        
        //DECLARTION AND INIT
        Canvas canvas = new Canvas();
        Component window = new WindowComponent(1,1,70,15);
        TextComponent gpsField = new TextComponent(3,2);
        TextComponent lastField = new TextComponent(3,3);
        TextComponent targetField = new TextComponent(3,4);
        TextComponent descriptionField = new TextComponent(3,5);
        TextComponent distField = new TextComponent(3,6);
        TextComponent climbField = new TextComponent(3,7);
        TextComponent descentField = new TextComponent(3,8);
        TextComponent backField = new TextComponent("[1].back",3,13);
        TextComponent skipField = new TextComponent("[2].skip",3,14);
        
        //ADDING COMPONENTS TO CANVAS
        canvas.addComponent(window);
        canvas.addComponent(gpsField);
        canvas.addComponent(lastField);
        canvas.addComponent(targetField);
        canvas.addComponent(descriptionField);
        canvas.addComponent(distField);
        canvas.addComponent(climbField);
        canvas.addComponent(descentField);
        canvas.addComponent(backField);
        canvas.addComponent(skipField);
        
        //FINAL:
        tFactory.addTrekkerObserver(
                new TrekkerModifier(gpsField,
                                    descriptionField,
                                    lastField,
                                    targetField,
                                    distField,
                                    climbField,
                                    descentField,
                                    skipField
                )
        );
        
        return canvas;
    }
}
