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

import android.app.ActionBar;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {

    public ArrayList<ScoreDetails> scoreList = new ArrayList<ScoreDetails>();

    /**
     * Created by Niranjana on 18-11-2015.
     * Read the file contents and store it array list
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        try {
            readFile();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Created by Sidharth on 22-11-2015
     * Read file and display it in the table
     * @throws IOException
     */
    private void readFile() throws IOException {
        String str = "";
        int j = 0;
        String FILE_NAME = "nsHighscore.txt";
        int b = 0;
        String VIEW_NAME;

        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, FILE_NAME);
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            //int b = 0;
            while ((str = bReader.readLine()) != null) {
                String w[] = str.split("\t");
                VIEW_NAME = "textView" + b;
                TextView ntv = (TextView) findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
                ntv.setText(w[0]);
                VIEW_NAME = "scoreView" + b;
                TextView stv = (TextView) findViewById(getResources().getIdentifier(VIEW_NAME, "id", getPackageName()));
                stv.setText(w[1]);
                b++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created by Sidharth on 22-11-2015
     *  To display the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
        return true;
    }


    /**
     *  Created by Niranjana on 21-11-2015.
     *  When items on the action bar gets selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
