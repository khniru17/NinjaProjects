/**
 Name: Niranjana Kandavel Net Id: nxk152730
 Name: Sidharth Udhayakumar Net Id: sxu140530
 Date: 11-29-2015
 Course Number: CS 6301.006
 Purpose: To create an Android Application that replicates the break out game.
 To win the game, user has to break the 3 levels of bricks.
 If the ball hits the wall or if it misses the paddle - the user lose the game.
 Time is used to calculate the high score.
 Top 10 high scores are recorded
 So, the app has 3 Activity:
 1. pong1 - This is main Activity used to play the pong game
 2. HighScoreActivity - To view the list of top ten scorers
 3. RecordScoreActivity - This is used to save the score when user wins, to display Out message
 classes: contactObject, CustomAdapter, CustomComparator, GlobalVariable, Item, nsMainActivity, nsSubActivity
 layout xml file:
 1. activity_high_core.xml - For high score activity
 2. activity_pong1.xml - To display the game and the control area
 3. activity_record_score.xml - To record score
 menu xml file:
 1. menu_high_score.xml - Actionbar for high sore activity
 2. menu_pong1.xml- Actionbar for pong game activity
 3. menu_record_score.xml - - Actionbar for score recording activity
 */
package com.example.ns.pongattempt1;

/**
 * Created by Niranjana on 27-11-2015.
 * This Class is to store the user name and their high score
 * It implements Comparable to sort the highscore which is the time details in ascending order
 */
public class ScoreDetails implements Comparable<ScoreDetails> {
    private String name;
    private double score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ScoreDetails(String name, double score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Created by Sidharth on 28-11-2015
     * @param another
     * @return
     */
    @Override
    public int compareTo(ScoreDetails another) {
        return new Double(score).compareTo( another.score);
    }

    /**
     * Created by Sidharth on 22-11-2015
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(score);
    }


}
