/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * Timespan Testing:
     * The input space is a list of tweets: 
     *      (1) The list can be empty (output not defined)
     *      (2) The start or end time could occur in the middle of the list
     *      (3) At least one of the start & end time could be the first or last element of the list
     *      (4) Start can occur before or after end time
     *      
     *      So I will use 3 test cases:
     *      (1) Start occurs before end, at first & last elements of the list
     *      (2) Start occurs after end, in the middle of the list
     *      (3) All tweets occur at the same time
     *      
     * Mentioned Users Testing:
     * The input space is a list of tweets:
     *      (1) There could be no mentions
     *      (2) At least one tweet contains text that looks like but is not a username
     *      (3) Multiple mentions of the same user
     *      (4) Empty List
     *      
     *      Test cases:
     *      (1) Multiple mentions of the same user (should return one mention)
     *      (2) One tweet with text that looks like a mention (should return no mentions)
     *      (3) Empty List
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T10:30:00Z");
    
    // 4 tweets, each with time-stamps and mentions appropriate to test both methods
    private static final Tweet time1Mention1 = new Tweet(1, "alyssa", "@barackObama talk about rivest so much?", d1);
    private static final Tweet time2Mention1 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes @barackObama #hype", d2);
    private static final Tweet time1LookAlikeMention = new Tweet(3, "barackObama", "this isn't an actual mention@alyssa.", d1);
    private static final Tweet time3 = new Tweet(4, "user4", "tweet tweet", d3);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /* getTimespan tests -----------------------------------------------------*/
    @Test //(1)
    public void testGetTimespanMinBeforeMax() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(time1Mention1, time2Mention1));
        
        // assertEquals(String message, expected value, actual value)
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test //(2) 
    public void testGetTimespanMinAfterMax() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(time3, time2Mention1, time1Mention1, time3));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test //(3)
    public void testGetTimespanSameTime() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(time3, time3));
        
        assertEquals("expected start", d3, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    }
    
    /* getMentionedUsers tests ------------------------------------------------*/
    @Test //(1)
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(time1Mention1, time2Mention1));
        
        // Expecting a set of one element
        assertEquals("expected one mention in set", new HashSet<String>(Arrays.asList("barackobama")), mentionedUsers);
    }
    
    @Test //(2) 
    public void testGetMentionedUsersLookAlike() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(time1LookAlikeMention));
        
        assertTrue("Expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test //(3)
    public void testGetMentionedUsersEmptyList() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList());
        
        assertTrue("Expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
