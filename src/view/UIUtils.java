/*****************************************************************************
* AUTH: William Payne
* FILE: UIUtils.java
* LAST MOD: 16/05/19
* PURPOSE: Provides 
*****************************************************************************/
package view;

import java.util.HashMap;

public class UIUtils 
{
    //
    // FIELDS:------------------------------------------------------------------
    //
    public static HashMap<String,String> COLOURS = new HashMap<>();
    public UIUtils(){}

    public static String colour = "\033[0m";
    public static String def    = "\033[0m";
    //
    // INIT:
    //
    public static void initDefautColours()
    {
        COLOURS.put("blue", "\033[34m");
        COLOURS.put("red", "\033[31m");
        COLOURS.put("green", "\033[32m");
        COLOURS.put("yellow", "\033[33m");
        COLOURS.put("default","\033[0m");
    }
    public static String getColour(String c)
    {
        colour = COLOURS.get(c);
        return colour;
    }
    
    //
    // Base System.out methods:-------------------------------------------------
    //
    
    public static void p(String line)
    {
        System.out.print(line);
    }

    public static void pAt(String s, int x, int y)
    {
        p("\033["+ y +";"+ x +"f"+colour+s+def);
    }

    public static void pAt(String s, int[] pos)
    {
        if(pos.length == 2)
        {
            pAt(s,pos[0],pos[1]);
        }
    }


    //
    // DRAWING METHODS:---------------------------------------------------------
    //

    /**
     * FUNC: Draw line
     * @param p String: the pattern to be drawn
     * @param l int:    the length of the line
     * @param x int:    the start x coordinate
     * @param y int:    the start y coordinate
     */
    public static void drawLineAt(String p, int l, int x, int y)
    {
        String line = "";
        for(int ii = 0; ii < l; ii++)
        {
                line+=p;
        }
        pAt(line,x+1,y);
    }

    /**
     * FUNC: Draw vertical line
     * @param p String: the pattern to be drawn
     * @param l int:    the length of the line
     * @param x int:    the start x coordinate
     * @param y int:    the start y coordinate
     */
    public static void drawVertAt(String p, int l, int x, int y)
    {
        for(int ii = y; ii < l+y; ii++)
        {
            pAt(p,x,ii);
        }
    }

    //Draw a character in the specified box size
    public static void drawBox(char p, int x, int y, int length, int height)
    {
        for(int ii = y; ii < y+height; ii++)
        {
            drawLineAt(p+"", length, x, ii);
        }
    }
    
    //Print Window at a coordinate
    public static void drawWindowAt(int x, int y, int startx, int starty)
    {
        drawLineAt("=",x,startx, starty);
        drawLineAt("=",x,startx, y+starty);

        drawVertAt("||",y-1,startx, starty+1);
        drawVertAt("||",y-1,startx+x,starty+1);
    }

    //
    // CLEARING METHODS:--------------------------------------------------------
    //
    
    /**
     * METHOD: clearLine()
     * @param y the Coordinate of the line to be cleared
     */
    public static void clearLine(int y)
    {
        drawLineAt(" ",200,0,y);
    }

    /**
     * METHOD: clearScreen()
     * PURP: Clears the screen without moving the screen down.
     */
    public static void clearScreen()
    {
        for(int ii = 0; ii < 100; ii++)
        {
            clearLine(ii);
        }
    }

    /**
     * FUNC: clearConsole()
     * PURP: Clears the screen by paging down in the console
     */
    public static void clearConsole()
    {
        System.out.println("\033[H\033[2J");
    }


    //
    // CURSOR MOVERS:-----------------------------------------------------------
    //

    public static void cursorTo(int x, int y)
    {
        p("\033["+ y +";"+ x +"f");
    }

    public static void mark()
    {
            p("\033[s");
    }

    public static void recall()
    {
        p("\033[u");
    }

    public static void penDown()
    {
        p("\033[10000;1H");
    }

}
