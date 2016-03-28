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

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Niranjana on 18-11-2015.
 * This class is used to creste the bricks, paddle
 */
public class CanvasView extends View {
    boolean isBtnPressed = false;
    boolean paddleChange = false;
    int up = 0;
    int down = 0;
    int right = 0;
    int left = 0;
    int pleft = 0;
    int ptop = 0;
    int touchX = 0;
    int touchY = 0;
    int count = 0;
    int cWidth = 0;
    int cHeight = 0;
    int radius = 0;
    int row1[] = {2, 3, 4, 5, 2, 3, 1};
    int row2[] = {4, 2, 1, 5, 4, 3, 5, 3};
    int row3[] = {5, 4, 1, 3, 2, 1, 4, 2, 4};
    Random rand = new Random();
    float speedx = 5;
    float speedy = 6;
    int cvmin = 0;
    int cvsec = 0;
    double highscore = 0.0;
    float sensx = 0.0f;
    float xfactor = 0;
    float yfactor = 0;
    int record = 0;
    float x, y;
    private SparseArray<PointF> mActivePointers;

    /**
     * Constructor
     * @param context
     */
    public CanvasView(Context context) {
        super(context);
        mActivePointers = new SparseArray<PointF>();
    }

    /**
     * Created by Sidharth on 25-11-2015
     * To handle mouse touch Events
     * @param event - to get the X and Y position
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();


        switch (maskedAction) {

         case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (event.getY(pointerIndex) < (9 * getHeight() / 10)) {
                    isBtnPressed = true;

                    count++;
                    if (count > 1) {
                        paddleChange = true;
                        touchX = (int) event.getX(pointerIndex);
                        touchY = (int) event.getY(pointerIndex);
                        PointF f = new PointF();
                        f.x = event.getX(pointerIndex);
                        f.y = event.getY(pointerIndex);
                        mActivePointers.put(pointerId, f);
                    } else {
                        up = 1;
                    }
                    invalidate();
                }
                Log.i("TAG", "Pointer down");
                break;


            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                Log.i("tag", "Move");

                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = mActivePointers.get(event.getPointerId(i));
                    if (point != null) {
                        if (event.getY(i) < (9 * getHeight() / 10)) {
                            touchX = (int) event.getX(i);
                            touchY = (int) event.getY(i);
                        }
                    }
                }


                break;
            case MotionEvent.ACTION_CANCEL: {
                mActivePointers.remove(pointerId);
                //invalidate();;
                break;
            }
        }

        //invalidate();;
        return true;
    }

    /**
     * Created by Sidharth on 23-11-2015
     */
    private void setRandomNumber() {
      /*  List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            values.add(i);
        }*/
       /* row1[0] = 2;row1[1] = 4;row1[2] = 3;row1[3] = 5;row1[4] = 2;row1[5] = 1;row1[6] = 3;
        row2[0] = 4;row2[1] = 2;row2[2] = 5;row2[3] = 1;row2[4] = 3;row2[5] = 4;row2[6] = 5;row2[7] = 2;
        row3[0] = 5;row3[1] = 4;row3[2] = 1;row3[3] = 2;row3[4] = 3;row3[5] = 2;row3[6] = 4;row3[7] = 1;row3[8] = 4;*/
        int r1 = 0;
        Random rand = new Random();
        for(int i =0;i<7;i++){
            int r2 = rand.nextInt(5)+1;
            if((r2-r1)==0){
                if (r2 == 5){
                    r2--;
                }else {
                    r2++;
                }
            }
            row1[i] = r2;
            r1 = r2;
        }
        for(int i =0;i<7;i++){
            String val = String.valueOf(row1[i]);
            Log.i("info",val);
        }

        r1 = 0;
        rand = new Random();
        for(int i =0;i<8;i++){
            int r2 = rand.nextInt(5)+1;
            if((r2-r1)==0){
                if (r2 == 5){
                    r2--;
                }else {
                    r2++;
                }
            }
            row2[i] = r2;
            r1 = r2;
        }

        r1 = 0;
        rand = new Random();
        for(int i =0;i<9;i++){
            int r2 = rand.nextInt(5)+1;
            if((r2-r1)==0){
                if (r2 == 5){
                    r2--;
                }else {
                    r2++;
                }
            }
            row3[i] = r2;
            r1 = r2;
        }

      }

    /**
     * Created by Niranjana on 18-11-2015.
     * To draw the bricks, paddle
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (count == 0){
            setRandomNumber();

        }

        cWidth = canvas.getWidth();
        cHeight = canvas.getHeight();
        radius = cWidth / 24;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);
        canvas.drawPaint(paint);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
       if(sensx < -3.0){
           xfactor = (-1 * sensx) - 2;

       } else if (sensx > 3.0){
            xfactor = (-1 * sensx) + 2;
        }else{
            xfactor = 0;
        }

        //canvas.drawText(Float.toString(sensx), 800,800, paint);
        int b = 1;
        while (b < 7) {
            paint = getColor(row1[b - 1], paint);
            canvas.drawRect((((b - 1) * cWidth) / 7), 0, ((b * cWidth) / 7), cWidth / 12, paint);
            b++;
        }
        paint = getColor(row1[b - 1], paint);
        b--;
        canvas.drawRect(((b * cWidth) / 7), 0, cWidth, cWidth / 12, paint);

        b = 1;
        while (b < 8) {
            paint = getColor(row2[b - 1], paint);
            canvas.drawRect((((b - 1) * cWidth) / 8), cWidth / 12, ((b * cWidth) / 8), (2 * cWidth) / 12, paint);
            b++;
        }
        paint = getColor(row2[b - 1], paint);
        b--;
        canvas.drawRect((((b) * cWidth) / 8), cWidth / 12, cWidth, (2 * cWidth) / 12, paint);

        b = 1;
        while (b < 9) {
            paint = getColor(row3[b - 1], paint);
            canvas.drawRect((((b - 1) * cWidth) / 9), (2 * cWidth) / 12, ((b * cWidth) / 9), (3 * cWidth) / 12, paint);
            b++;
        }
        paint = getColor(row3[b - 1], paint);
        b--;
        canvas.drawRect((((b) * cWidth) / 9), (2 * cWidth) / 12, cWidth, (3 * cWidth) / 12, paint);


        paint.setColor(Color.WHITE);
        if (isBtnPressed) {
            if (up == 1) {

                if (x > cWidth) { //Hits the right wall
                    up = 0;
                    left = 1;
                   // reset();
                } else if ((y - radius) < 0) { //Hits the Top Wall
                    win();
                } else if ((y - radius) < ((cWidth) / 12)) { //Hits brick in row1
                    int bCnt = (int)(x) / (cWidth / 7);
                    if (bCnt > 5){
                        bCnt = 6;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row1[bCnt] > 0) {
                        row1[bCnt]--;
                        up = 0;
                        right = 1;
                    } else {
                        x = x + speedx + xfactor;
                        y = y - speedy + yfactor;
                    }
                } else if ((y - radius) < ((2 * cWidth) / 12)) { //Hits brick in row2
                    int bCnt = (int)(x) / (cWidth / 8);
                    if (bCnt > 6){
                        bCnt = 7;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row2[bCnt] > 0) {
                        row2[bCnt]--;
                        up = 0;
                        right = 1;
                    } else {
                        x = x + speedx + xfactor;
                        y = y - speedy + yfactor;
                    }
                } else if ((y - radius) < ((3 * cWidth) / 12)) { //Hits brick in row3
                    int bCnt = (int)(x) / (cWidth / 9);
                    if (bCnt > 7){
                        bCnt = 8;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row3[bCnt] > 0) {
                        row3[bCnt]--;
                        up = 0;
                        right = 1;
                    } else {
                        x = x + speedx + xfactor;
                        y = y - speedy + yfactor;
                    }
                } else { //Otherwise
                    x = x + speedx + xfactor;
                    y = y - speedy + yfactor;
                }
            } else if (left == 1) {
                if (x < 0) { //Hits the left wall
                    //reset();
                    left = 0;
                    up = 1;
                } else if ((y - radius) < 0) { //Hits the Top Wall
                    win();
                } else if ((y - radius) < ((cWidth) / 12)) { //Hits brick in row1
                    int bCnt = (int)(x) / (cWidth / 7);
                    if (bCnt > 5){
                        bCnt = 6;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row1[bCnt] > 0) {
                        row1[bCnt]--;
                        left = 0;
                        down = 1;
                    } else {
                        x = x - speedx + xfactor;
                        y = y - speedy + yfactor;
                    }
                } else if ((y - radius) < ((2 * cWidth) / 12)) { //Hits brick in row2
                    int bCnt = (int)(x) / (cWidth / 8);
                    if (bCnt > 6){
                        bCnt = 7;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row2[bCnt] > 0) {
                        row2[bCnt]--;
                        left = 0;
                        down = 1;
                    } else {
                        x = x - speedx + xfactor;
                        y = y - speedy + yfactor;

                    }
                } else if ((y - radius) < ((3 * cWidth) / 12)) { //Hits brick in row3
                    int bCnt = (int)(x) / (cWidth / 9);
                    if (bCnt > 7){
                        bCnt = 8;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row3[bCnt] > 0) {
                        row3[bCnt]--;
                        left = 0;
                        down = 1;
                    } else {
                        x = x - speedx + xfactor;
                        y = y - speedy + yfactor;

                    }
                } else { //Otherwise
                    x = x - speedx + xfactor;
                    y = y - speedy + yfactor;

                }
            } else if (down == 1) {
                if (x < 0){ //Hits the left wall
                    down = 0;
                    right = 1;
                } else if (y > ptop + speedy){   //misses the paddle
                    reset();

                } else if ((y - radius) < (cWidth) / 12) {
                    int bCnt = (int)(x) / (cWidth / 8);
                    if (bCnt > 6){
                        bCnt = 7;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row2[bCnt] > 0) {
                        row2[bCnt]--;
                    } else {
                        x = x - speedx + xfactor;
                        y = y + speedy + yfactor;
                    }
                } else if ((y - radius) < ((2 * cWidth) / 12)) {
                    int bCnt = (int)(x) / (cWidth / 8);
                    if (bCnt > 7){
                        bCnt = 8;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row3[bCnt] > 0) {
                        row3[bCnt]--;
                    } else {
                        x = x - speedx + xfactor;
                        y = y + speedy + yfactor;
                    }
                } else if (y > (ptop - radius) && ((x > pleft) && (x < (pleft + (cWidth / 7))))) {
                    down = 0;
                    if (pleft < cWidth / 2) {
                        up = 1;
                    } else {
                        left = 1;
                    }
                } else {
                    x = x - speedx + xfactor;
                    y = y + speedy + yfactor;
                }
            } else if (right == 1) {
                if (y > ptop + speedy){ //misses the paddle
                    reset();
                } else if (x > cWidth) {    //Hits the right wall
                    right = 0;
                    down = 1;
                } else if ((y - radius) < (cWidth) / 12) {
                    int bCnt = (int)(x) / (cWidth / 8);
                    if (bCnt > 6){
                        bCnt = 7;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row2[bCnt] > 0) {
                        row2[bCnt]--;
                    } else {
                        x = x + speedx + xfactor;
                        y = y + speedy + yfactor;
                    }
                } else if ((y - radius) < ((2 * cWidth) / 12)) {
                    int bCnt = (int)(x) / (cWidth / 9);
                    if (bCnt > 7){
                        bCnt = 8;
                    }else if (bCnt < 1){
                        bCnt = 0;
                    }
                    if (row3[bCnt] > 0) {
                        row3[bCnt]--;
                    } else {
                        x = x + speedx + xfactor;
                        y = y + speedy + yfactor;
                    }
                } else if (y > (ptop - radius) && ((x > pleft) && (x < (pleft + (cWidth / 7))))) {
                    right = 0;
                    if (pleft < cWidth / 2) {
                        up = 1;
                    } else {
                        left = 1;
                    }
                } else {
                    x = x + speedx + xfactor;
                    y = y + speedy + yfactor;
                }
            }
            if (paddleChange) {
                pleft = touchX;
                //ptop = touchY;
            }
        } else if (count == 0) {
            x = cWidth / 4;
            y = 3 * cHeight / 5;
            pleft = (cWidth / 4) - (2 * radius);
            ptop = (3 * cHeight / 5) + radius;
        }

        canvas.drawCircle(x, y, radius, paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(pleft, ptop, pleft + (cWidth / 7), ptop + (cWidth / 24), paint);
        if (isBtnPressed) invalidate();
    }

    /**
     * Created by Sidharth on 23-11-2015
     * To reset back when the user Hits the wall
     */
    void reset() {
        isBtnPressed = false;
        paddleChange = false;
        x = cWidth / 4;
        y = 3 * cHeight / 5;
        pleft = (cWidth / 4) - (2 * radius);
        ptop = (3 * cHeight / 5) + radius;
        up = 1;
        count = 0;
        Context context = getContext();
        Intent intent = new Intent(context, RecordScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int win = 2;
        intent.putExtra("WIN", win);
        context.startActivity(intent);
    }

    /**
     * Created by Sidharth on 24-11-2015
     * When the user wins, show the Score Save Screen
     */
    void win() {
        isBtnPressed = false;
        paddleChange = false;
        x = cWidth / 4;
        y = 3 * cHeight / 5;
        pleft = (cWidth / 4) - (2 * radius);
        ptop = (3 * cHeight / 5) + radius;
        up = 1;
        count = 0;
        Context context = getContext();
        Intent intent = new Intent(context, RecordScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int win = 1;
        double currScore = cvmin + (cvsec / 100.00);
        if (currScore < highscore || record <= 10) {
            win = 1;
        } else {
            win = 0;
        }
        intent.putExtra("MIN", cvmin);
        intent.putExtra("SEC", cvsec);
        intent.putExtra("WIN", win);
        context.startActivity(intent);

    }


    /**
     * Created by Niranjana on 25-11-2015.
     * to Pause when user hits the pause button
     */
    void pause() {
        if (isBtnPressed) {
            isBtnPressed = false;
        } else {
            isBtnPressed = true;
            count++;
            if (count > 1) {
                paddleChange = true;
            } else {
                up = 1;
            }
            invalidate();
        }
    }

    /**
     * Created by Niranjana on 22-11-2015.
     * To find the colour to be painted. It is based on the brick number.
     * @param b - brick number
     * @param paint - colour to paint
     * @return - colour to be painted
     */
    Paint getColor(int b, Paint paint) {
        int c = b;
        switch (c) {
            case 0:
                paint.setColor(Color.CYAN);
                break;
            case 1:
                paint.setColor(Color.WHITE);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                break;
            case 3:
                paint.setColor(Color.GREEN);
                break;
            case 4:
                paint.setColor(Color.RED);
                break;
            case 5:
                paint.setColor(Color.BLACK);
                break;
        }
        return paint;
    }

    /**
     * Created by Sidharth on 22-11-2015
     * To save the updated speed from the slider
     * @param val
     */
    void changeSpeed(int val) {
        speedx = val;
        speedy = val + 1;
    }

    /**
     * Created by Sidharth on 18-11-2015
     * To get the latest time from the timer
     * @param min
     * @param sec
     */
    void updateTime(int min, int sec) {
        cvmin = min;
        cvsec = sec;
    }

    /**
     * Created by Niranjana on 21-11-2015.
     * To communicate if the button is pressed or not
     * @return
     */
    boolean sendButtonInfo() {
        return isBtnPressed;
    }

    /**
     * Created by Niranjana on 21-11-2015.
     * To store the value of the high score
     * @param hs
     * @param rec
     */
    void setHighscore(double hs, int rec) {
        highscore = hs;
        record = rec;
    }

    /**
     * Created by Sidharth on 21-11-2015
     * get the sensor value
     * @param s
     */
    void setsensor(float s) {
        sensx = s;
    }

}

