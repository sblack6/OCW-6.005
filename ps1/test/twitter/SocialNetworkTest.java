/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * guessFollowsGraph Tests:
     *  a) Input Possibilities:
     *      Empty list 
     *      List with no mentions
     *      1+ tweet contains mention of the author (can't follow self)
     *      A tweet mentions multiple other users
     *      Multiple mentions across the list of tweets (as 1st, last, & middle elements)
     *      Multiple tweets with mentions by the same author
     *  
     *  b) Test Cases:
     *      1) Empty list / no mentions
     *      2) A list with mentions occurring as 1st, middle, & last elements
     *          With other middle tweets that contain no mentions
     *          One of the "no mention" tweets mentions the author
     *          One of the "mention" tweets contains multiple mentions
     *          One author has multiple tweets mentioning different users
     *          
     * influencers tests:
     *  a) Input possibilities:
     *      Empty map
     *      Map out of order
     *      Map already in order
     */
    
    private static final Instant instant = Instant.parse("2016-02-17T10:00:00Z");
    private static final String author1 = "Joe";
    private static final String author2 = "Jim";
    private static final String author3 = "Julie";
    // 5 tweets for guessFollowGraph tests
    private static final Tweet mentionTweet1 = new Tweet(1, author1, "Hey @" + author3, instant);
    private static final Tweet mentionTweet2 = new Tweet(2, author2, "My favorite people are @" + author1 + "and @" + author3, instant);
    private static final Tweet mentionTweet3 = new Tweet(3, author3, "Thank you @" + author2 + "!", instant);
    private static final Tweet selfMention = new Tweet(4, author1, "Actually @" + author1 + " is better.", instant);
    private static final Tweet noMentionTweet = new Tweet(5, author2, "I always have premium content", instant);
    private static final Tweet mentionTweet4 = new Tweet(6, author1, "Actually, my tweets are better @" + author2 + "!", instant);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    @Test
    public void testGuessFollowsGraphMultipleMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(mentionTweet1, selfMention, 
                mentionTweet2, noMentionTweet, mentionTweet3, mentionTweet4));
        assertEquals("expected graph to have 3 mappings", 3, followsGraph.size());
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    @Test
    public void testInfluencersWithResults() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(mentionTweet1, 
                mentionTweet2, mentionTweet3, mentionTweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertEquals("expected list of size 3", 3, influencers.size());
        assertEquals("expect author3 to be first influencer", author3.toLowerCase(), influencers.get(0));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
