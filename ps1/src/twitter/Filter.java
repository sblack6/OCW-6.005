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
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> writtenBy = new LinkedList<>();
        for (Tweet tweet : tweets) {
            if(tweet.getAuthor().toLowerCase().equals(username.toLowerCase())) 
                writtenBy.add(tweet);
        }
        return writtenBy;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> inTimespan = new LinkedList<>();
        for (Tweet tweet : tweets) {
            Instant time = tweet.getTimestamp();
            if (time.isAfter(timespan.getStart()) && time.isBefore(timespan.getEnd()))
                inTimespan.add(tweet);
            else if (time.equals(timespan.getStart()) || time.equals(timespan.getEnd()))
                    inTimespan.add(tweet);
        }
        return inTimespan;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        Set<String> wordSet = new HashSet<String>();
        List<Tweet> containing = new LinkedList<>();
        for (String word : words) {
            wordSet.add(word.toLowerCase());
        }
        for (Tweet tweet : tweets) {
            String[] tweetWords = parseWords(tweet.getText());
            for (String tweetWord : tweetWords) {
                if (wordSet.contains(tweetWord.toLowerCase())) {
                    containing.add(tweet);
                    break;
                }
            }
        }
        return containing;
    }
    
    /**
     * Parse the text of a tweet into a list of words
     * @param text : the text of a tweet
     * @return an array of the words contained in the text
     */
    private static String[] parseWords(String text) {
        return text.split("\\s");
    }

}
