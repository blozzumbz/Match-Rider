package com.example.blozzum.matchrider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Button startButton = (Button)findViewById(R.id.startGame);
        startButton.setOnClickListener(this);
        Button exitButton = (Button)findViewById(R.id.exit);
        exitButton.setOnClickListener(this);
        Button howToButton = (Button)findViewById(R.id.howToPlay);
        howToButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startGame:
                Intent startIntent = new Intent(this, GameActivity.class);
                startActivity(startIntent);
                break;
            case R.id.exit:
                warningBeforeExit();
                break;
            case R.id.howToPlay:
                Intent howToIntent = new Intent(this, HowToActivity.class);
                startActivity(howToIntent);
            default:
                break;
        }

    }

    //checking exit
    public void warningBeforeExit(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure to exit?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        warningBeforeExit();
    }
}
