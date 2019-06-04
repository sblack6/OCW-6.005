/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty())
            return null;
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().compareTo(start) < 0)
                start = tweet.getTimestamp();
            else if (tweet.getTimestamp().compareTo(end) > 0)
                end = tweet.getTimestamp();
        }
        return new Timespan(start, end);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    // Search for " @" w/ findCharAtIndices
    // For each " @" occurrence: use findEndIndex to find the full word
    // If non-null, scrub and add to set (check if in set 1st)
    // scrub before putting in the set (make all lowercase)
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<String>();
        for (Tweet tweet : tweets) {
            List<Integer> indices = findAtCharIndices(tweet.getText());
            for (int startIndex : indices) {
                int endIndex = findEndIndex(tweet.getText(), startIndex+1);
                if (endIndex == -1)
                    continue;
                else {
                    String username = tweet.getText().substring(startIndex+2, endIndex+1);
                    mentionedUsers.add(username.toLowerCase());
                }
            }
        }
        return mentionedUsers;
    }
    
    /** 
     * Helper method for getMentionedUsers: Get possible mention indices
     * @param text : the text of a tweet
     * @return the indices where " @" occurs
     */
    private static List<Integer> findAtCharIndices(String text) {
        List<Integer> indices = new LinkedList<>();
        int index = 0;
        while (index >= 0) {
            index = text.indexOf(" @", index);
            if (index != -1)
                indices.add(index++);
        }
        return indices;
    }
    
    /**
     * Helper method for getMentionedUsers: Find the end of a username
     * @param text : the text of a tweet
     * @param startIndex : the starting index of the username
     * @return the ending index of the username
     *          return -1 if there are no valid characters
     */
    // Another case to worry abt: what if we have something like " @."?
    private static int findEndIndex(String text, int startIndex) {
        int endIndex = startIndex;
        while (endIndex+1 < text.length()) {
            char ch = text.charAt(endIndex+1);
            if (!Character.isLetterOrDigit(ch))
                if (ch != '-' && ch != '_')
                    break;
            endIndex++;
        }
        if (endIndex == startIndex)
            return -1;
        return endIndex;
    }
}
