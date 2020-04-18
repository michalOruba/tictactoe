package pl.michaloruba.tictactoe;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TicTacToe {
    private int player1Score;
    private int player2Score;
    private int currentGame;
    private int movesMade;
    private int currentPlayer;
    private Context context;
    private TableLayout table;

    public TicTacToe(Context current) {
        player1Score = 0;
        player2Score = 0;
        currentGame = 1;
        movesMade = 0;
        currentPlayer = 1;
        context = current;
    }

    public void setImage(ImageView v) {
        int imageId = getCurrentPlayerImageId();
        v.setImageResource(imageId);
        v.setTag(imageId);
        movesMade++;
    }

    public int getCurrentPlayerImageId() {
        return currentPlayer == 1 ? R.drawable.circle : R.drawable.cross;
    }

    public boolean hasPlayerWon(ImageView clickedImage, TableLayout table) {
        if (movesMade > 4){
            this.table = table;
            boolean checkCrossStatus = false;
            String clickedImageIdName = context.getResources().getResourceName(clickedImage.getId());
            int clickedImageRow = getIndexFromImageViewName(clickedImageIdName, 3);
            int clickedImageColumn = getIndexFromImageViewName(clickedImageIdName, 1);
            int rowColumnSubtraction = Math.abs(clickedImageRow - clickedImageColumn);

            if (isCornerOrMiddleClicked(rowColumnSubtraction)) {
                checkCrossStatus = checkCross(clickedImageRow, clickedImageColumn);
            }
            return checkRow(clickedImageRow) || checkColumn(clickedImageColumn) || checkCrossStatus;
        }
        return false;
    }

    private int getIndexFromImageViewName(String clickedImageIdName, int index){
        return Character.getNumericValue(clickedImageIdName.charAt(clickedImageIdName.length() - index)) - 1;
    }

    private boolean isCornerOrMiddleClicked(int rowColumnSubtraction) {
        return rowColumnSubtraction == 0 || rowColumnSubtraction == 2;
    }

    private boolean checkCross(int clickedImageRow, int clickedImageColumn) {
        if (centerFieldClicked(clickedImageColumn)){
            return checkLeftToRightCross() || checkRightToLeftCross();
        }
        else if (clickedImageRow == clickedImageColumn)
        {
            return checkLeftToRightCross();
        }
        else {
            return checkRightToLeftCross();
        }
    }

    private boolean centerFieldClicked(int clickedImageColumn) {
        return clickedImageColumn == 1;
    }

    private boolean checkRightToLeftCross() {
        for (int i = 0; i < 3; i++) {
            TableRow clickedRow = (TableRow) table.getChildAt(i);
            ImageView currentImage = (ImageView) clickedRow.getChildAt(2 - i);
            if (isNotCurrentPlayersImage(currentImage)) return false;
        }
        return true;
    }

    private boolean checkLeftToRightCross() {
        for (int i = 0; i < 3; i++) {
            TableRow clickedRow = (TableRow) table.getChildAt(i);
            ImageView currentImage = (ImageView) clickedRow.getChildAt(i);
            if (isNotCurrentPlayersImage(currentImage)) return false;
        }
        return true;
    }

    private boolean checkRow(int clickedImageRow) {
        TableRow clickedRow = (TableRow) table.getChildAt(clickedImageRow);
        for (int i = 0; i < 3; i++){
            ImageView currentImage = (ImageView) clickedRow.getChildAt(i);
            if (isNotCurrentPlayersImage(currentImage)) return false;
        }
        return true;
    }

    private boolean checkColumn(int clickedImageColumn) {
        for (int i = 0; i < 3; i++){
            TableRow clickedRow = (TableRow) table.getChildAt(i);
            ImageView currentImage = (ImageView) clickedRow.getChildAt(clickedImageColumn);
            if (isNotCurrentPlayersImage(currentImage)) return false;
        }
        return true;
    }

    private boolean isNotCurrentPlayersImage(ImageView currentImage) {
        int imageID = -1;
        if (currentImage.getTag() != null) {
            imageID = (Integer) currentImage.getTag();
        }
        return imageID != getCurrentPlayerImageId();
    }

    public boolean areMovesLeft() {
        return movesMade >= 9;
    }

    public void updateScore(TextView player1ScoreTextView, TextView player2ScoreTextView) {
        if (currentPlayer == 1) {
            player1ScoreTextView.setText(String.valueOf(++player1Score));
        } else {
            player2ScoreTextView.setText(String.valueOf(++player2Score));
        }
    }

    public void resetBoard() {
        currentGame++;
        currentPlayer = (currentGame % 2) + 1;
        movesMade = 0;
    }

    public void changePlayer(ImageView player1Image, ImageView player2Image) {
        if (currentPlayer == 1){
            currentPlayer = 2;
            player2Image.setImageResource(R.drawable.blue_triangle);
            player1Image.setImageResource(android.R.color.transparent);
        } else {
            currentPlayer = 1;
            player1Image.setImageResource(R.drawable.blue_triangle);
            player2Image.setImageResource(android.R.color.transparent);
        }
    }
}