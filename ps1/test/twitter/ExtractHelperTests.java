package twitter;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtractHelperTests {

    /*
     * findAtCharIndex Tests:
     *      (1) " @" exists
     *      (2) " @" DNE
     *      (3) "@" exists with some other char preceeding
     *      
     * findEndIndex tests:
     *      (1) valid name 
     *      (2) invalid name (@ followed by invalid character)
     *      (3) name includes each of: hyphen, underscore, letter, digit.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //@Test
    
}
