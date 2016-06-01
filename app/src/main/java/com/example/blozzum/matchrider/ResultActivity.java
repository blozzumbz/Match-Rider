package com.example.blozzum.matchrider;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);

        Button tryAgainButton = (Button)findViewById(R.id.tryAgain);
        Button mainMenuButton = (Button)findViewById(R.id.mainMenu);
        tryAgainButton.setOnClickListener(this);
        mainMenuButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        String minute = bundle.getString("minute");
        String second = bundle.getString("second");

        //set time score
        TextView timeScoreTextView = (TextView)findViewById(R.id.timeScore);
        timeScoreTextView.setText("Your Time\n" + minute + " minute " + second + " second");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tryAgain:
                Intent tryAgainIntent = new Intent(this, GameActivity.class);
                startActivity(tryAgainIntent);
                finish();
                break;
            case R.id.mainMenu:
                finish();
        }
    }
}
