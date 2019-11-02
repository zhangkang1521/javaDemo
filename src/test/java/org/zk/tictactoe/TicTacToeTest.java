package org.zk.tictactoe;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TicTacToeTest {

    TicTacToe ticTacToe;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        ticTacToe = new TicTacToe();
    }

    @Test
    public void whenXOutsideThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(5, 2);
    }

    @Test
    public void whenYOutsideThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(1, 0);
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 1);
    }

    @Test
    public void firstPlayerX() {
        assertEquals('X', ticTacToe.nextPlayer());
    }

    @Test
    public void nextPlayer2() {
        ticTacToe.play(1, 1);
        assertEquals('O', ticTacToe.nextPlayer());
        ticTacToe.play(1, 2);
        assertEquals('X', ticTacToe.nextPlayer());
        ticTacToe.play(1, 3);
        assertEquals('O', ticTacToe.nextPlayer());
    }

    @Test
    public void noWinner() {
        String actual = ticTacToe.play(1, 1);
        assertEquals("No Winner", actual);
    }

    @Test
    public void wholeHorizon() {
        ticTacToe.play(1, 1); // x
        ticTacToe.play(1, 2);
        ticTacToe.play(2, 1); // x
        ticTacToe.play(2, 2);
        String actual = ticTacToe.play(3, 1); // x
        assertEquals("X is Winner", actual);
    }

    @Test
    public void wholeVertical() {
        ticTacToe.play(1, 1); // x
        ticTacToe.play(2, 1);
        ticTacToe.play(1, 2); // x
        ticTacToe.play(2, 2);
        String actual = ticTacToe.play(1, 3); // x
        assertEquals("X is Winner", actual);
    }

    @Test
    public void diagonal1() {
        ticTacToe.play(1, 1); // x
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 2); // x
        ticTacToe.play(3, 1);
        String actual = ticTacToe.play(3, 3); // x
        assertEquals("X is Winner", actual);
    }

    @Test
    public void diagonal2() {
        ticTacToe.play(3, 1); // x
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 2); // x
        ticTacToe.play(1, 1);
        String actual = ticTacToe.play(1, 3); // x
        assertEquals("X is Winner", actual);
    }

    @Test
    public void draw() {
        ticTacToe.play(3, 1); // x
        ticTacToe.play(2, 1);
        ticTacToe.play(2, 2); // x
        ticTacToe.play(1, 1);
        ticTacToe.play(1, 2); // x
        ticTacToe.play(3, 2);
        ticTacToe.play(3, 3); // x
        ticTacToe.play(1, 3);
        String actual = ticTacToe.play(2, 3); // x
        assertEquals("draw", actual);
    }

    @Test
    public void test() {
        List<String> friends = Arrays.asList("Joe", "Daniel");
//        assertTrue(friends.containsAll(Arrays.asList("Joe", "Daniel")));
        assertThat("abc", startsWith("a"));
    }




}