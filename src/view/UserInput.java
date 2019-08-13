/*The following code is my own work and has been used throughout all my 
  practicals*/
package view;
import java.util.*;
import controller.handler.IErrorObserver;
public class UserInput
{
    public static final String BORDER = ""
    +"======================================================================";
    public static final String INVALID = "Invalid input.";

    //
    //CLASS FIELDS:
    //
    private HashSet<IErrorObserver> observers;

    //
    //CONSTRUCTOR:
    //
    public UserInput(){this.observers = new HashSet<>();}
    
    
    //
    //INPUT METHODS:
    //
    
    /*************************************************************************
    * Submodule: readInt
    *   Purpose: get positive integer from user
    *   Imports: prompt(String)
    *   Exports: numOut
    *   Assertion: Prompts user for integer and scans in as String.
                   Parses String to an integer and returns value if positive.
                   Input is invalid if it cannot parse or if it is negative.
                   Loop till input is valid.
     * @param prompt
     * @param lower
     * @param upper
     * @param pos
     * @return 
    *************************************************************************/
    public int readInt ( String prompt, int lower, int upper, int[] pos )
    {
        String numIn;
        int numOut;
        boolean loop;
        Scanner sc = new Scanner(System.in);


        prompt += ":";

        numOut = 0;
        do
        {
            loop = false;
            UIUtils.pAt( prompt, pos[0], pos[1] );
            try
            {
                numIn = sc.nextLine(); //same here *refer to previous submodules
                numOut = Integer.parseInt( numIn );
                if(numOut < lower || numOut > upper)
                {
                    throw new IllegalArgumentException("Invalid input");
                }
            }
            catch ( NumberFormatException e )
            {
                notifyError(e);
                loop = true;
            }
            catch ( IllegalArgumentException e )
            {
                notifyError(e);
                loop = true;
            }
        }
        while ( loop == true );

        return numOut;
    }
    //
    // Observer Methods:
    //
    
    public void notifyError(Throwable e)
    {
        observers.forEach((ob) -> {
            ob.errorOccured(e);
        });
    }
}
