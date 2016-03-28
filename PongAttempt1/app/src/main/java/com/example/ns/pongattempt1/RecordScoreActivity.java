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
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RecordScoreActivity extends AppCompatActivity {

    public ArrayList<ScoreDetails> scoreList = new ArrayList<ScoreDetails>();
    DecimalFormat df = new DecimalFormat("#.00");

    /**
     *  Created by Sidharth on 22-11-2015
     *  When win = 1 - Display the success message and give options to save the score
     *  When win = 0 - Display message that user is not top scorer and just display the score
     *  When win = 2 - Error messgae and dont display anything
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        final int rsmin = i.getIntExtra("MIN", 0);
        final int rssec = i.getIntExtra("SEC", 0);
        int win = i.getIntExtra("WIN", 0);
        setContentView(R.layout.activity_record_score);
        Button saveBtn = (Button) findViewById(R.id.savebtn);
        if (win == 1) {
            TextView heading = (TextView) findViewById(R.id.heading);
            heading.setText("Congratulations! You are one among the top ten scorer");
            final double score = rsmin + (rssec / 100.00);
            String min1 = String.format("%02d", rsmin);
            String sec1 = String.format("%02d", rssec);
            final String curTime = min1 + ":" + sec1;
            TextView tvscore = (TextView) findViewById(R.id.scorenew);
            tvscore.setText(curTime);

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        readFile();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),
                                "Problems: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    EditText name = (EditText) findViewById(R.id.name);
                    scoreList.add(new ScoreDetails(name.getText().toString(), score));
                    Collections.sort(scoreList);
                    saveFile();
                }
            });
        } else if (win == 0) {
            TextView heading = (TextView) findViewById(R.id.heading);
            heading.setText("Sorry! You are NOT one among the top ten scorer. Better Luck next time.");
            TextView tv = (TextView) findViewById(R.id.ssnamelbl);
            tv.setVisibility(View.INVISIBLE);
             EditText name = (EditText) findViewById(R.id.name);
            name.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);

            String min1 = String.format("%02d", rsmin);
            String sec1 = String.format("%02d", rssec);
            final String curTime = min1 + ":" + sec1;
            TextView tvscore = (TextView) findViewById(R.id.scorenew);
            tvscore.setText(curTime);
        } else {
            TextView heading = (TextView) findViewById(R.id.heading);
            heading.setText("Out! You missed the paddle");
            TextView tv = (TextView) findViewById(R.id.ssnamelbl);
            tv.setVisibility(View.INVISIBLE);
            tv = (TextView) findViewById(R.id.ssscorelbl);
            tv.setVisibility(View.INVISIBLE);
            EditText name = (EditText) findViewById(R.id.name);
            name.setVisibility(View.INVISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);

        }
    }

    /**
     *  Created by Niranjana on 23-11-2015.
     *  Read the contents of the file and add it to the arraylist
     * @throws IOException
     */
    private void readFile() throws IOException {
        String str = "";
        int j = 0;
        String FILE_NAME = "nsHighscore.txt";
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, FILE_NAME);
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            while ((str = bReader.readLine()) != null) {
                String w[] = str.split("\t");
                double s = Double.parseDouble(w[1]);
                scoreList.add(new ScoreDetails(w[0], s));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Created by Sidharth on 23-11-2015
     *  Save the high scores back to the file
     */
    private void saveFile() {
        String FILE_NAME = "nsHighscore.txt";

        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, FILE_NAME);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            int k = 0;
            while (k < scoreList.size() && k < 10) {
                writer.write(scoreList.get(k).getName() + "\t" + scoreList.get(k).getScore() + "\n");
                k++;
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), pong1.class);
        startActivity(intent);
    }

    /**
     *  Created by Sidharth on 24-11-2015
     *  to add the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_score, menu);
        return true;
    }

    /**
     *  Created by Niranjana on 22-11-2015.
     *  When the action bar icons gets selected
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
