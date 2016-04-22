package eda397_group7.chalmers.se.eda397_2016_group7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Game.HostSession;
import TrelloInteraction.Board;

public class PHPActivity extends AppCompatActivity {

    private HostSession host;
    private Board primaryBoard;
    private final int NUMBER_OF_PLAYERS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_php);

        Intent i = getIntent();

        String boardId = i.getStringExtra("board");
        //final Board primaryBoard = (Board) i.getParcelableExtra("board");
        primaryBoard = new Board(boardId);

        primaryBoard.updateCards();

        host = new HostSession(primaryBoard.getId(), NUMBER_OF_PLAYERS);


        Button newSession = (Button) findViewById(R.id.newSession);
        Button selectCard = (Button) findViewById(R.id.selectCard);
        Button resetSession = (Button) findViewById(R.id.resetButton);

        if (newSession != null) {
            newSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        host.startSession(NUMBER_OF_PLAYERS);
                        TextView t = (TextView) findViewById(R.id.trello_text);
                        t.setText(primaryBoard.getCard());
                    } catch (Exception ex) { }
                }
            });
        }
        if (resetSession != null) {
            resetSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        host.resetGame();
                    } catch (Exception ex) { }
                }
            });

        }
    }
}
