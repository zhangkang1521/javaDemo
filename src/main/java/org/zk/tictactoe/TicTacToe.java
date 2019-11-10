package org.zk.tictactoe;

import org.zk.tictactoe.mongo.TicTacToeBean;
import org.zk.tictactoe.mongo.TicTacToeCollection;

/**
 * 井字棋，英文名叫Tic-Tac-Toe，是一种在3*3格子上进行的连珠游戏，和五子棋类似，由于棋盘一般不画边框，格线排成井字故得名。
 * 游戏需要的工具仅为纸和笔，然后由分别代表O和X的两个游戏者轮流在格子里留下标记（一般来说先手者为X），任意三个标记形成一条直线，则为获胜。
 */
public class TicTacToe {

    private TicTacToeCollection collection;
    private int turn = 1;

    private Character[][] board = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };

    private char lastPlayer = '\0';

    private static final int SIZE = 3;

    public TicTacToe() throws Exception{
        this(new TicTacToeCollection());
    }

    public TicTacToe(TicTacToeCollection collection) {
        this.collection = collection;
        this.collection.drop();
    }

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
        setBox(x, y);
        if(isWin(x, y)) {
            return lastPlayer + " is Winner";
        } else if (allBoxFilled()){
            return "draw";
        }else {
            return "No Winner";
        }
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3) {
            throw new RuntimeException("x is outside board");
        }
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        } else {
            board[x - 1][y - 1] = lastPlayer;
            getTicTacToeCollection().saveMove(new TicTacToeBean(turn++, x, y, lastPlayer));
        }

    }

    public char nextPlayer() {
        if (lastPlayer == 'X') {
            return 'O';
        }
        return 'X';
    }

    private boolean isWin(int x, int y) {
        int playerTotal = lastPlayer * SIZE;
        int horizon = 0, vertical = 0, diagonal1 = 0, diagonal2 = 0;
        for (int i = 0; i < SIZE; i++) {
            horizon += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[SIZE - i - 1][i];
        }
        if (horizon == playerTotal
                || vertical == playerTotal
                || diagonal1 == playerTotal
                || diagonal2 == playerTotal) {
            return true;
        }
        return false;
    }

    private boolean allBoxFilled() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    public TicTacToeCollection getTicTacToeCollection() {
        return collection;
    }
}
