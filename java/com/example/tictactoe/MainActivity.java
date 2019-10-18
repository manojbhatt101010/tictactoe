package com.example.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount = 0, player1Points = 0, player2Points = 0;
    private TextView P1Points, P2Points;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        P1Points = findViewById(R.id.textViewP1);
        P2Points = findViewById(R.id.textViewP2);
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
                int buttonId = getResources().getIdentifier("button" + i + j, "id", getPackageName());
                buttons[i][j] = findViewById(buttonId);
                buttons[i][j].setOnClickListener(this);
            }

        Button reset = findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearBoard();
                player1Points = 0;
                player2Points = 0;
                updatePoints();
            }
        });
    }

    protected boolean someoneWon()
    {
        String[][] arr = new String[3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                arr[i][j] = buttons[i][j].getText().toString();

        int i = 0;
        for(int j = 0; j < 3; j++)
        {
            if(arr[i][j].equals(""))
                continue;

            if(arr[i][j].equals(arr[i+1][j]) && arr[i][j].equals(arr[i+2][j]))
                return true;

            if(j == 0)
                if(arr[i][j].equals(arr[i+1][j+1]) && arr[i][j].equals(arr[i+2][j+2]))
                    return true;

            if(j == 2)
                if(arr[i][j].equals(arr[i+1][j-1]) && arr[i][j].equals(arr[i+2][j-2]))
                    return true;

            if(arr[j][i].equals(""))
                continue;

            if(arr[j][i].equals(arr[j][i+1]) && arr[j][i].equals(arr[j][i+2]))
                return true;
        }
        return false;
    }

    protected void player1Won()
    {
        player1Points++;
        Toast.makeText(this, "Player 1 Won", Toast.LENGTH_SHORT).show();
        updatePoints();
        clearBoard();
    }

    protected void player2Won()
    {
        player2Points++;
        Toast.makeText(this, "Player 2 Won", Toast.LENGTH_SHORT).show();
        updatePoints();
        clearBoard();
    }

    protected void updatePoints()
    {
        String s = "Player 1: " + player1Points;
        P1Points.setText(s);
        s = "Player 2: " + player2Points;
        P2Points.setText(s);
    }

    protected void clearBoard()
    {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                buttons[i][j].setText("");

        roundCount = 0;
        player1Turn = true;
    }

    @Override
    public void onClick(View v)
    {
        if(!((Button)v).getText().toString().equals(""))
            return;

        if(player1Turn)
        {
            ((Button) v).setTextColor(Color.parseColor("#0000dd"));
            ((Button) v).setTextSize(70);
            ((Button) v).setText("O");
        }

        else {
            ((Button) v).setTextColor(Color.parseColor("#00aa00"));
            ((Button) v).setTextSize(70);
            ((Button) v).setText("X");
        }

        roundCount++;
        if(someoneWon())
        {
            if (player1Turn)
                player1Won();
            else
                player2Won();
        }


        else if(roundCount == 9)
        {
            Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
            clearBoard();
        }

        else
            player1Turn = !player1Turn;

    }
}
