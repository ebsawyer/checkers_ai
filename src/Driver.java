import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    private State initState;
    private int opponentChoice;
    private int gameSize;
    private State nextState;
    private ArrayList<State> nextMoves;

    public Driver(int gameSize) {
        this.gameSize = gameSize;
        this.initState = new State(gameSize);
        this.nextMoves = new ArrayList<State>();

    }

    public ArrayList<State> getNextMoves() {
        return nextMoves;
    }

    public State getInitState() {
        return initState;
    }

    public int getOpponentChoice() {
        return opponentChoice;
    }

    public void setOpponentChoice(int opponentChoice) {
        this.opponentChoice = opponentChoice;
    }

    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    //opponent with the Minimax algorithm
    public int minimax(State state, int turn, int depth) {

        if (depth == 1) {

            nextMoves.add(state);
        }

        state.getPossibleMoves().clear();
        state.fillActions(state, turn);

        if (state.terminalTest() == true) {

            state.setUtility(state.utility());
            return state.utility();
        }
        //for 4x4 minimax cutoff at 10 per Prof. Ferguson's email
        if (depth == 10) {

            state.setUtility(0);
            return 0;
        }

        //maximizing player
        if (turn % 2 == 0) {
            int bestUtil = Integer.MIN_VALUE;
            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimax(childState, turn + 1, depth + 1);

                bestUtil = Math.max(bestUtil, util);
                state.setUtility(bestUtil);

            }
            return bestUtil;
        }

        //minimizing player
        else {
            int bestUtil = Integer.MAX_VALUE;

            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimax(childState, turn + 1, depth + 1);

                bestUtil = Math.min(bestUtil, util);
                state.setUtility(bestUtil);
            }
            return bestUtil;
        }
    }

    //opponent using the H-Minimax algorithm withn alpha-beta pruning
    public int minimaxAB(State state, int alpha, int beta, int turn, int depth) {

        if (depth == 1) {
            nextMoves.add(state);
        }

        //heuristic function
        //f(n) = (# of white pieces + 2 * # white kings) - (# of black pieces + 2 * # black kings) = 2
        //estimates white will win if at this state
       if((state.getParentBoard().getNumWhitePieces() + (2 * state.getParentBoard().getNumWhiteKings())) - (state.getParentBoard().getNumBlackPieces() + (2*state.getParentBoard().getNumBlackPieces())) == 2){
           state.setUtility(1);
           return 1;
       }

        state.getPossibleMoves().clear();
        state.fillActions(state, turn);

        if (state.terminalTest() == true) {

            state.setUtility(state.utility());
            return state.utility();
        }

        //depth cutoff at 12
        if (depth == 12) {
            state.setUtility(0);
            return 0;
        }

        //maximizing player
        if (turn % 2 == 0) {
            int bestUtil = Integer.MIN_VALUE;
            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimaxAB(childState, alpha, beta, turn + 1, depth + 1);

                bestUtil = Math.max(bestUtil, util);
                alpha = Math.max(alpha, bestUtil);
                if (bestUtil >= beta) {
                    state.setUtility(bestUtil);
                    return bestUtil;
                }
                alpha = Math.max(alpha, bestUtil);
                state.setUtility(bestUtil);

            }
            return bestUtil;
        }

        //minimizing player
        else {
            int bestUtil = Integer.MAX_VALUE;

            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimaxAB(childState, alpha, beta, turn + 1, depth + 1);
                bestUtil = Math.min(bestUtil, util);
                beta = Math.min(beta, bestUtil);
                if (bestUtil <= alpha) {
                    state.setUtility(bestUtil);
                    return bestUtil;
                }
                beta = Math.min(bestUtil, beta);
                state.setUtility(bestUtil);
            }
            return bestUtil;
        }
    }

    //opponent using the H-Minimax algorithm
    public int hminimax(State state, int turn, int depth, int depthLimit) {

        if (depth == 1) {
            nextMoves.add(state);
        }

        state.getPossibleMoves().clear();
        state.fillActions(state, turn);

        if (state.terminalTest() == true) {

            state.setUtility(state.utility());
            return state.utility();
        }
        if (depth == depthLimit) {
            state.setUtility(0);
            return 0;
        }

        //maximizing player
        if (turn % 2 == 0) {
            int bestUtil = Integer.MIN_VALUE;
            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimax(childState, turn + 1, depth + 1);

                bestUtil = Math.max(bestUtil, util);
                state.setUtility(bestUtil);

            }
            return bestUtil;
        }

        //minimizing player
        else {
            int bestUtil = Integer.MAX_VALUE;

            for (int k = 0; k < state.getPossibleMoves().size(); k++) {

                State childState = new State(gameSize);
                childState.setParentBoard(state.duplicateBoard(state.getParentBoard()));
                childState.fillActions(state, turn);

                childState.getParentBoard().move(childState.getPossibleMoves().get(k)[0], state.getPossibleMoves().get(k)[1]);

                int util = minimax(childState, turn + 1, depth + 1);
                bestUtil = Math.min(bestUtil, util);
                state.setUtility(bestUtil);
            }
            return bestUtil;
        }

    }

    //random opponent
    public int random(State state) {
        int size = state.getPossibleMoves().size();
        int random = (int) (Math.random() * size);
        return random;

    }
}
