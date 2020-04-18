package pl.michaloruba.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    TextView player1ScoreTextView;
    TextView player2ScoreTextView;
    ImageView player1Image;
    ImageView player2Image;
    TableLayout table;
    TicTacToe game = new TicTacToe(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1ScoreTextView = findViewById(R.id.player1Score);
        player2ScoreTextView = findViewById(R.id.player2Score);
        player1Image = findViewById(R.id.player1Image);
        player2Image = findViewById(R.id.player2Image);
        table = findViewById(R.id.table);

        setUpListeners();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageView) {
            ImageView clickedCell = (ImageView) v;

            if (isFieldEmpty(clickedCell)) {
                game.setImage(clickedCell);
                if (!hasGameEnd(clickedCell)) {
                    game.changePlayer(player1Image, player2Image);
                }
                else {
                    clearImages();
                    game.resetBoard();
                    game.changePlayer(player1Image, player2Image);
                }
            }
        }
    }

    private boolean isFieldEmpty(ImageView v) {
        return v.getTag() == null;
    }

    private boolean hasGameEnd(ImageView clickedImage) {
        if (game.hasPlayerWon(clickedImage, table)){
            game.updateScore(player1ScoreTextView, player2ScoreTextView);
            return true;
        }
        return game.areMovesLeft();
    }

    private void clearImages() {
        makeActionOnAllImageViewsInTableLayout(clearImage);
    }

    private void setUpListeners() {
        makeActionOnAllImageViewsInTableLayout(setOnclickListener);
    }

    private void makeActionOnAllImageViewsInTableLayout(ImageViewActions imageViewActions) {
        int rowsCount = table.getChildCount();
        for(int i = 0; i < rowsCount; i++) {
            View v = table.getChildAt(i);
            if (v instanceof TableRow) {
                TableRow row = (TableRow) v;
                int cellsCount = row.getChildCount();
                for (int r = 0; r < cellsCount; r++) {
                    View v2 = row.getChildAt(r);
                    if (v2 instanceof ImageView) {
                        ImageView imageView = (ImageView) v2;
                        imageViewActions.makeAction(imageView);
                    }
                }
            }
        }
    }

    public ImageViewActions setOnclickListener = (imageView) -> imageView.setOnClickListener(this);

    public ImageViewActions clearImage = (imageView) -> {
        imageView.setImageResource(0);
        imageView.setTag(null);
    };
}
