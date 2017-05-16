package week4;


import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

/**
 * Created by hari.l on 23/11/16.
 */

public class Board {
    
    //C
    private class Coordinates {
        int x;
        int y;
    }

    private int manhattanValue;
    private final int[][] board;
    private final int N;

    private Coordinates blankSquare;

    public Board(int[][] blocks) {
        N = blocks.length;
        board = new int[N][N];
        manhattanValue = -1;
        blankSquare = new Coordinates();


        for (int x = 0; x < N; x++) {
            for (int y = 0; y < N; y++) {
                board[x][y] = (char)blocks[x][y];
                if (blocks[x][y] == 0) {
                    blankSquare.x = x;
                    blankSquare.y = y;
                }
            }
        }
    }

    public int dimension() {
        return N;
    }

    private char goal(char i, char j) {
        if (i == N - 1 && j == N - 1) {
            return 0;
        } else {
            return (char)((i * N) + j + 1);
        }
    }
    // number of misplaced blocks
    public int hamming() {

        int count = 0;

        for (char i = 0; i < N; i++) {
            for (char j = 0; j < N; j++) {
                if (board[i][j] != 0 && board[i][j] != goal(i,j)) {
                    count++;
                }
            }
        }
        return count;
    }

    // get manhattanValues
    private Coordinates coordinatesForGoalValue(int value) {
        Coordinates coordinates = new Coordinates();
        if (value == 0) {
            coordinates.x = N - 1;
            coordinates.y = N - 1;
        } else {
            coordinates.x = (value - 1) / N;
            coordinates.y = (value - 1) % N;
        }
        return coordinates;
    }
    // sum of Manhattan distances
    public int manhattan() {
        if (manhattanValue != -1) {
            return manhattanValue;
        }
        int distance = 0;
        for (char i = 0; i < N; i ++) {
            for (char j = 0; j < N; j++) {
                int boardValue = board[i][j];
                if (boardValue == 0) { continue; }
                Coordinates goalCoordinates = coordinatesForGoalValue(boardValue);
                distance += Math.abs(goalCoordinates.x - i) + Math.abs(goalCoordinates.y - j);
            }
        }
        manhattanValue = distance;
        return distance;
    }

    // is this board solved
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    // (BLANK DOES NOT COUNT)
    public Board twin() {
        // copy our board
        int[][] blocks = copyBoard();
        // if neither of the first two blocks are blank,
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            // switch first two blocks
            blocks[0][0] = board[0][1];
            blocks[0][1] = board[0][0];
        } else {
            // otherwise, switch first two blocks on second row
            blocks[1][0] = board[1][1];
            blocks[1][1] = board[1][0];
        }
        return new Board(blocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass())  return false;
        Board that = (Board)y;
        return Arrays.deepEquals(this.board, that.board);
    }

    private int[][] copyBoard() {
        int[][] boardCopy = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        return boardCopy;
    }
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<Board>();
        // copy our board as int arrays

        // blank square not left edge
        if (blankSquare.y > 0) {
            // create board with blank switched with left neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.x][blankSquare.y] = board[blankSquare.x][blankSquare.y -1];
            blocks[blankSquare.x][blankSquare.y -1] = board[blankSquare.x][blankSquare.y];
            stack.push(new Board(blocks));
        }
        // blank square not right edge
        if (blankSquare.y < N-1) {
            // create board with blank switched with right neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.x][blankSquare.y] = board[blankSquare.x][blankSquare.y +1];
            blocks[blankSquare.x][blankSquare.y +1] = board[blankSquare.x][blankSquare.y];
            stack.push(new Board(blocks));
        }
        // blank square not top edge
        if (blankSquare.x > 0) {
            // create board with blank switched with top neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.x][blankSquare.y] = board[blankSquare.x -1][blankSquare.y];
            blocks[blankSquare.x -1][blankSquare.y] = board[blankSquare.x][blankSquare.y];
            stack.push(new Board(blocks));
        }
        // blank square not bottom edge
        if (blankSquare.x < N-1) {
            // create board with blank switched with bottom neighbor; push to stack
            int[][] blocks = copyBoard();
            blocks[blankSquare.x][blankSquare.y] = board[blankSquare.x +1][blankSquare.y];
            blocks[blankSquare.x +1][blankSquare.y] = board[blankSquare.x][blankSquare.y];
            stack.push(new Board(blocks));
        }

        return stack;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((int)N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                stringBuilder.append(String.format("%d ", (int)board[i][j]));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}