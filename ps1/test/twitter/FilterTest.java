/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * writtenBy() Tests:
     * a) Input possibilities:
     *      An empty list
     *      List doesn't contain any tweets by the author
     *      Tweets by the author in the middle of the list
     *      Tweets by the author appear at first or last element of the list
     *      
     *  b) Test Cases:
     *      1) Empty list (no tweets by author)
     *      2) Tweets by the author appear at first, last and middle of list, with tweets NOT by the author also in the middle
     *      
     * inTimespan() Tests:
     *  a) Input Possibilities:
     *      Empty list
     *      Tweets in the timespan appear in the middle of the list
     *      Tweets in the timespan appear as first or last elements of the list
     *      The list contains tweets at the boundaries of the timespan
     *      No tweets in the timespan
     *      The list contains tweets occuring before and/or after the timespan
     *      
     *  b) Test Cases:
     *      1) Empty list (no tweets in the timespan)
     *      2) Tweets in the timespan, appear as first, last, and middle elements of the list
     *              Those tweets occur at instants on the boundaries and in the middle of the timespan
     *              The list also contains at least one tweet before and one after the timespan
     *  
     * containing() Tests:
     *  a) Input possibilities:
     *      (i) List of tweets:
     *          Empty List
     *          None of the tweets contain a valid word
     *          Tweet containing a word appears as first, last, or middle element of the list
     *      (ii) List of words:
     *          Empty word list
     *          None of the words in the list are contained in a tweet
     *          Words in the list that are in a tweet appear as first, last, or middle elements of the word list.
     *          
     *  b) Test Cases:
     *      1) Empty word & tweet lists  (No overlapping words)
     *      2) Non-empty lists with no overlapping words
     *      3) Overlapping words - appear as first last & middle elements of the word list (with non-overlapping in middle)
     *              Where the tweets containing those words appear as first, last, & middle, 
     *              with additional tweets not containing overlapping words (in middle)
     */
    
    private static final Instant beginInstant = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant endInstant = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant middleInstant = Instant.parse("2016-02-17T10:30:00Z");
    private static final Instant beforeInstant = Instant.parse("2016-02-17T09:00:00Z");
    private static final Instant afterInstant = Instant.parse("2016-02-17T12:00:00Z");
    private static final Timespan timespan = new Timespan(beginInstant, endInstant);
    
    private static final String validAuthor = "Joe";
    private static final List<String> words = Arrays.asList("Barack", "Obama", "president");
    
    // These 3 tweets will be "valid" for each of the 3 methods
    private static final Tweet validTweet1 = new Tweet(1, validAuthor, "barack is my best friend", beginInstant);
    private static final Tweet validTweet2 = new Tweet(2, "joe", "ObamA was at the NBA finals game!", endInstant);
    private static final Tweet validTweet3 = new Tweet(3, validAuthor, "Running for prEsident is harder than I thought!", middleInstant);
    // 2 Invalid tweets
    private static final Tweet invalidBeforeTweet = new Tweet(4, "Don", "This is harder than my last job", beforeInstant);
    private static final Tweet invalidAfterTweet = new Tweet(5, "Mike", "Text that looks like el presidente Barrak Obamma", afterInstant);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /* writtenBy() Tests -----------------------------------------------------------------*/
    @Test
    public void testWrittenByNoValidTweets() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(invalidBeforeTweet, invalidAfterTweet), validAuthor);
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    
    @Test
    public void testWrittenByValidTweets() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(validTweet1, invalidAfterTweet, validTweet2, 
                invalidBeforeTweet, validTweet3), validAuthor);
        
        assertEquals("expected 3-tweet list", 3, writtenBy.size());
        assertTrue(writtenBy.equals(Arrays.asList(validTweet1, validTweet2, validTweet3)));
    }
    
    /* inTimespan() Tests -----------------------------------------------------------------*/
    @Test
    public void testInTimespanNoValidTweets() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(invalidBeforeTweet, invalidAfterTweet), timespan);
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    @Test
    public void testInTimespanValidTweets() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(validTweet1, invalidAfterTweet, validTweet2, 
                invalidBeforeTweet, validTweet3), timespan);
        
        assertEquals("expected 3-tweet list", 3, inTimespan.size());
        assertTrue(inTimespan.equals(Arrays.asList(validTweet1, validTweet2, validTweet3)));
    }
    
    /* containing() Tests -----------------------------------------------------------------*/
    @Test
    public void testContainingNoValidTweets() {
        List<Tweet> containing = Filter.containing(Arrays.asList(invalidBeforeTweet, invalidAfterTweet), words);
        
        assertTrue("expected empty list", containing.isEmpty());
    }
    
    @Test
    public void testContainingValidTweets() {
        List<Tweet> containing = Filter.containing(Arrays.asList(validTweet1, invalidAfterTweet, validTweet2, 
                invalidBeforeTweet, validTweet3), words);
        
        assertEquals("expected 3-tweet list", 3, containing.size());
        assertTrue(containing.equals(Arrays.asList(validTweet1, validTweet2, validTweet3)));
    }
    
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
