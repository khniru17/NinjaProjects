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
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class pong1 extends AppCompatActivity {

    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity

    AtomicBoolean ContinueThread = new AtomicBoolean(false);
    TextView Watch;
    public int min = 0;
    public int sec = 0;
    CanvasView cvobj;
    double highscore;
    boolean mInitialized = false;
    float lastx = 0.0f;
    boolean resetTimer = true;

    /**
     *  Created by Sidharth on 22-11-2015
     *  Handler for timer thread
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

                displayCurrentTime();

            return false;
        }
    });

    /**
     *  Created by Niranjana on 22-11-2015.
     *  Event listener for sensor
     */
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];

        //    TextView sens = (TextView) findViewById(R.id.sens);
        //   sens.setText(Float.toString(event.values[0]));
            cvobj.setsensor(event.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     *  Created by Niranjana on 24-11-2015.
     *  Get the high score from file
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        cvobj = new CanvasView(this);
        super.onCreate(savedInstanceState);
        setContentView(cvobj);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        

        String str = "";
        int j = 0;
        String FILE_NAME = "nsHighscore.txt";



        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(baseDir, FILE_NAME);
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            String w[] = new String[2];
            int r = 0;
            while ((str = bReader.readLine()) != null) {
                w = str.split("\t");
                r++;
            }
            if (w[1] != null) {
                highscore = Double.parseDouble(w[1]);
                cvobj.setHighscore(highscore, r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        LayoutInflater inflater = getLayoutInflater();
        View tmpView;
        tmpView = inflater.inflate(R.layout.activity_pong1, null);
        getWindow().addContentView(tmpView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));

        final Button stop = (Button) findViewById(R.id.stop);
        stop.setEnabled(true);
        stop.setText("Start");
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvobj.pause();
                if (stop.getText().equals("Start")) {
                    stop.setText("Pause");
                    resetTimer = true;
                } else {
                    stop.setText("Start");
                    resetTimer = false;

                }
            }
        });

        SeekBar sb = (SeekBar) findViewById(R.id.seekbar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cvobj.changeSpeed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    /**
     * Created by Sidharth on 24-11-2015
     *  On Pause - register sensor
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
        ContinueThread.set(false);
    }

    /**
     *  Created by Sidharth on 24-11-2015
     *  On Resume - rest the timer, sensor and pause/start button
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        //cvobj.pause();
        Button stop = (Button) findViewById(R.id.stop);
        if (stop.getText().equals("Pause")) {
            stop.setText("Start");

        }
        resetTimer = true;
        ContinueThread.set(true);
    }

    /**
     * Created by Niranjana on 23-11-2015.
     * Start the thread to display timer
     */
    @Override
    protected void onStop() {

        super.onStop();
        ContinueThread.set(false);
    }

    /**
     * Created by Niranjana on 23-11-2015.
     * Start the thread to display timer
     */
    @Override
    protected void onStart() {
        super.onStart();
        Thread background = new Thread(new Runnable() {
            public void run() {
                try {
                    while (ContinueThread.get()) {
                        Thread.sleep(1000);
                            if(cvobj.sendButtonInfo()){
                                handler.sendMessage(handler.obtainMessage());
                            }
                    }
                } catch (Throwable t) {
                }
            }
        });
        ContinueThread.set(true);
        background.start();
    }

    /**
     * Created by Niranjana on 23-11-2015.
     * To add the action bar
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pong1, menu);
        return true;
    }

    /**
     * Created by Sidharth on 20-11-2015
     * When items on the action bar are selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //When insert button is clicked, it opens another activity
        if (id == R.id.highscoreab) {
            Intent intent = new Intent(getApplicationContext(), HighScoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }else if(id == R.id.home){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Created by Sidharth on 23-11-2015
     * To reset the timer
     */
    public  void resetTimer(){
        min = 0;
        sec = 0;
        resetTimer = false;
        cvobj.updateTime(min, sec);
        String min1 = String.format("%02d", min);
        String sec1 = String.format("%02d", sec);
        String curTime = min1 + ":" + sec1;

        //set the timer value
        Watch = (TextView) findViewById(R.id.timer);
        Watch.setText(curTime);
    }

    /**
     * Created by Niranjana on 22-11-2015.
     * To display current time
     */
    public void displayCurrentTime()
    {
        //calculate timer value
        sec++;
        if (sec == 60) {
            min++;
            sec = 0;
        }

        cvobj.updateTime(min, sec);
        String min1 = String.format("%02d", min);
        String sec1 = String.format("%02d", sec);
        String curTime = min1 + ":" + sec1;

        //set the timer value
        Watch = (TextView) findViewById(R.id.timer);
        Watch.setText(curTime);
        Button stop = (Button) findViewById(R.id.stop);
        stop.setEnabled(true);
        stop.setText("Pause");

    }


}
