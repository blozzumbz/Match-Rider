package com.example.blozzum.matchrider;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends Activity implements View.OnClickListener{
    TextView timerTextView;
    long startTime, countUp;
    Chronometer stopWatch;
    ArrayList<Integer> positionChoosed = new ArrayList<>();
    ArrayList<String> picNameChoosed = new ArrayList<String>(), picChoice = new ArrayList<String>(Arrays.asList(""));
    ArrayList<String> dataList = new ArrayList<String>(Arrays.asList("kuuga", "agito", "ryuki","blade","faiz","hibiki","kabuto","kiva","deno","decade","w","ooo","fourze","wizard","gaim","ghost","v1","v2","v3","v4","v5","v6","v7","v8","v9","j","zx","black"));
    int roundCounter = 1, matchedCounter = 0,setupToggle = 0,picResouceID, timeAdded = 0;
    ImageButton pic1,pic2,pic3,pic4,pic5,pic6,pic7,pic8;
    ImageButton[] listImageButton = {pic1,pic2,pic3,pic4,pic5,pic6,pic7,pic8};
    String backCardName = "backcard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        setupTimer();
        setupLayout();

    }

    //setup new stopwatch
    private void setupTimer(){
        stopWatch = (Chronometer) findViewById(R.id.chrono);
        startTime = SystemClock.elapsedRealtime();

        timerTextView = (TextView) findViewById(R.id.timer);
        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

            @Override
            public void onChronometerTick(Chronometer arg0) {
                countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
                String minute = Integer.toString((int) countUp / 60);
                String second = Integer.toString((int) countUp % 60);
                if (countUp / 60 < 10) {
                    minute = "0" + minute;
                }
                if (countUp % 60 < 10) {
                    second = "0" + second;
                }
                String asText = getString(R.string.timer) + " " + minute + " : " + second + " +" + timeAdded;
                timerTextView.setText(asText);

            }

        });
        stopWatch.start();
    }

    //setup new game
    private void setupLayout(){
        setupToggle = 0;
        //clear choice layout.
        picChoice.clear();
        flipCardDown();
        setupChoice();

    }

    //if 2 cards are opened, it return true
    private boolean checkOpenedCard(){
        return picNameChoosed.size() == 2;
    }

    //if choosed picture are matched, it will gone. But not, it stay hide.
    private void checkAnswer(){
        if(picNameChoosed.get(0).equals(picNameChoosed.get(1))){
            removeCard();
            matchedCounter++;
        }else{
            timeAdded += 5;
            flipCardDown();
        }
        picNameChoosed.clear();
        positionChoosed.clear();
    }

    //if third round of game is finished, The game is end.
    private void checkEndGame(){
        if (roundCounter > 3){
            //start intent to result page.
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("minute", Integer.toString((int) (countUp + timeAdded) / 60));
            intent.putExtra("second", Integer.toString((int) (countUp + timeAdded) % 60));
            startActivity(intent);
            finish();

        }
    }

    //if all card are invisible, set new round.
    private void checkChangeRound(){
        //count up round
        if(matchedCounter == 4){
            roundCounter++;
            matchedCounter = 0;
            setupLayout();
        }
    }

    //calculate game
    private void gameCalculate(){
        if(checkOpenedCard()){
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkAnswer();
                    checkChangeRound();
                    checkEndGame();
                }
            }, 250);
        }
    }

    //set imageButton to back card
    private void flipCardDown(){
        for (int i=0; i<listImageButton.length;i++){
            listImageButton[i] = (ImageButton) findViewById(getResources().getIdentifier(("pic"+Integer.toString(i+1)),"id",getPackageName()));
            picResouceID = getResources().getIdentifier(backCardName,"drawable",getPackageName());
            listImageButton[i].setImageResource(picResouceID);
            if(setupToggle == 0){
                listImageButton[i].setOnClickListener(this);
                listImageButton[i].setVisibility(View.VISIBLE);
            }
        }
        setupToggle = 1;

    }

    //setup all card in play
    private void setupChoice(){
        int randomIndexDatalist;
        for(int i=0; i < 4; i++){
            randomIndexDatalist = new Random().nextInt(dataList.size());
            picChoice.add(dataList.get(randomIndexDatalist));
            picChoice.add(dataList.get(randomIndexDatalist));
            dataList.remove(randomIndexDatalist);
        }
        Collections.shuffle(picChoice, new Random(System.nanoTime()));
    }

    //set imageButton to front card
    private void flipCardUp(int indexClicked){
        listImageButton[indexClicked-1] = (ImageButton) findViewById(getResources().getIdentifier(("pic"+Integer.toString(indexClicked)),"id",getPackageName()));
        picResouceID = getResources().getIdentifier(picChoice.get(indexClicked-1),"drawable",getPackageName());
        listImageButton[indexClicked-1].setImageResource(picResouceID);
        picNameChoosed.add(picChoice.get(indexClicked - 1));
        positionChoosed.add(indexClicked-1);
    }

    //make card to invisible
    private void removeCard(){
        int indexToRemove;
        for(int i = 0; i < 2;i++){
            indexToRemove = positionChoosed.get(i);
            listImageButton[indexToRemove].setVisibility(View.INVISIBLE);
        }

    }

    //clicking handler
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pic1:
                flipCardUp(1);
                gameCalculate();
                break;
            case R.id.pic2:
                flipCardUp(2);
                gameCalculate();
                break;
            case R.id.pic3:
                flipCardUp(3);
                gameCalculate();
                break;
            case R.id.pic4:
                flipCardUp(4);
                gameCalculate();
                break;
            case R.id.pic5:
                flipCardUp(5);
                gameCalculate();
                break;
            case R.id.pic6:
                flipCardUp(6);
                gameCalculate();
                break;
            case R.id.pic7:
                flipCardUp(7);
                gameCalculate();
                break;
            case R.id.pic8:
                flipCardUp(8);
                gameCalculate();
                break;
            default:
                break;
        }

    }
}
