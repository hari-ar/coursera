package week4;

import edu.princeton.cs.algs4.*;

/**
 * Created by hari.l on 23/11/16.
 */

public class Solver {


    private class Node implements Comparable<Node> {
        final Board board;
        final int moves;
        final Node previous;
        final int priority;
        Node(Board board, int moves, Node previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = moves + board.manhattan();
        }


        public int compareTo(Node that) {
            if (this.priority > that.priority) {
                return 1;
            }
            if (that.priority > this.priority) {
                return -1;
            }
            return 0;
        }
    }

    private boolean isValidBoard;
    private int moves;
    private MinPQ<Node> searchPriorityQueue;
    private MinPQ<Node> twinPriorityQueue;
    public Solver(Board initial) {
        searchPriorityQueue = new MinPQ<Node>();
        twinPriorityQueue = new MinPQ<Node>();
        searchPriorityQueue.insert(new Node(initial, 0, null));
        twinPriorityQueue.insert(new Node(initial.twin(), 0, null));

        while (!searchPriorityQueue.min().board.isGoal()
                && !twinPriorityQueue.min().board.isGoal()) {
            Node min = searchPriorityQueue.delMin();
            Node twinMin = twinPriorityQueue.delMin();
            Iterable<Board> neighbors = min.board.neighbors();
            Iterable<Board> twinNeighbors = twinMin.board.neighbors();
            for (Board neighbor : neighbors) {
                if (min.previous != null && neighbor.equals(min.previous.board)) {
                    continue;
                }
                Node next = new Node(neighbor, min.moves + 1, min);
                searchPriorityQueue.insert(next);
            }
            for (Board twinNeighbor : twinNeighbors) {
                if (twinMin.previous != null && twinNeighbor.equals(twinMin.previous.board)) {
                    continue;
                }
                Node twinNext = new Node(twinNeighbor, twinMin.moves + 1, twinMin);
                twinPriorityQueue.insert(twinNext);
            }
        }

        if (searchPriorityQueue.min().board.isGoal()) {
            moves = searchPriorityQueue.min().moves;
            isValidBoard = true;

        }
        if (twinPriorityQueue.min().board.isGoal()) {
            moves = -1;
            isValidBoard = false;

        }
    }


    public boolean isValidBoard() {
        return isValidBoard;
    }

    public int moves() {
        return moves;
    }


    public Iterable<Board> solution() {
        if (!isValidBoard) return null;
        Stack<Board> solution = new Stack<Board>();
        Node node = searchPriorityQueue.min();
        while (node != null) {
            solution.push(node.board);
            node = node.previous;
        }
        return solution;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        In in = new In(fileName);
        int N = in.readInt();
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j=0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board board = new Board(blocks);
        Solver solver = new Solver(board);

        if (!solver.isValidBoard())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board eachBoard : solver.solution())
                StdOut.println(eachBoard);
        }
    }
}