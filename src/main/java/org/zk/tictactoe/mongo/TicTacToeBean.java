package org.zk.tictactoe.mongo;

import org.jongo.marshall.jackson.oid.Id;

import java.util.Objects;

public class TicTacToeBean {
    @Id
    private int turn;
    private int x;
    private int y;
    private char player;

    public TicTacToeBean(int turn, int x, int y, char player) {
        this.turn = turn;
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getPlayer() {
        return player;
    }

    public void setPlayer(char player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "TicTacToeBean{" +
                "turn=" + turn +
                ", x=" + x +
                ", y=" + y +
                ", player=" + player +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToeBean that = (TicTacToeBean) o;
        return turn == that.turn &&
                x == that.x &&
                y == that.y &&
                player == that.player;
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, x, y, player);
    }
}
