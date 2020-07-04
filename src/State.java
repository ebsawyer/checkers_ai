import java.util.ArrayList;

public class State {

    private Board parentBoard;
    private ArrayList<State> children;
    private ArrayList<Coordinate[]> possibleMoves;
    //Even if it's the User's turn (white pieces) (max)
    //Odd if it's the CPU's turn (black pieces) (min)
    //private int turn;
    //keeps track of the depth of the tree
    //private int depth;
    private int utility;
    private int size;
    //true if it's a terminal state
    private Boolean terminal;
    private Coordinate bestPiece;
    private Coordinate bestMove;

    //init states constructor
    public State(int size) {
        this.size = size;
        this.parentBoard = new Board(size);
        this.utility = utility;
        //white always goes first
        //this.turn = turn;
        this.children = new ArrayList<State>();
        this.terminal = false;
        //this.depth = depth;
        this.possibleMoves = new ArrayList<Coordinate[]>();
        this.bestMove = new Coordinate();
        this.bestPiece = new Coordinate();
    }

    //Terminal function
    public boolean terminalTest() {
        //if the number of white or black pieces is 0, it's a terminal state
        if (parentBoard.getNumBlackPieces() == 0 || parentBoard.getNumBlackPieces() == 0) {
            return true;
        }

        //if there are no more possible moves for a particular color, it's a terminal state
        if (possibleMoves.size() == 0) {
            return true;
        }
        return false;
    }

    //Utility function
    public int utility() {
        if (getParentBoard().getNumWhitePieces() == 0) {
            return -1;
        }
        else if(getParentBoard().getNumBlackPieces() == 0){
            return 1;
        } else {
            return 0;
        }
    }

    public void setUtility(int utility) {
        this.utility = utility;
    }

    //sets utility if one of the teams wins
    public int getUtility() {

        if (getParentBoard().getNumWhitePieces() == 0) {
            this.utility = -1;
        } else if (getParentBoard().getNumBlackPieces() == 0) {
            this.utility = 1;
        }
        return utility;
    }

    //Results function
    //Returns the new state (takes state, original coordinate, new coordinate and outputs new state)
    public State results(State parentState, Coordinate origCord, Coordinate newCord) {

        State temp = new State(parentState.getSize());
        temp.setParentBoard(duplicateBoard(parentState.getParentBoard()));
        temp.getParentBoard().move(origCord, newCord);
        return temp;
    }

    public Coordinate getBestPiece() {
        return bestPiece;
    }

    public void setBestPiece(Coordinate bestPiece) {
        this.bestPiece = bestPiece;
    }

    public Coordinate getBestMove() {
        return bestMove;
    }

    public void setBestMove(Coordinate bestMove) {
        this.bestMove = bestMove;
    }

    //Action function (fills arrayList of possible actions)
    //Results function (returns the new state based on the action taken)
    //Each pairing includes in the arrayList has an an array with
    //the original piece coordinate and the new piece coordinate
    public void fillActions(State state, int turn) {

        //whites move
        if (turn % 2 == 0) {
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (state.getParentBoard().getBoardLayout()[r][c] != null) {
                        if (state.getParentBoard().getBoardLayout()[r][c].getColor() == true) {
                            for (int k = 0; k < state.getParentBoard().getBoardLayout()[r][c].getValidMoves().size(); k++) {
                                Coordinate[] temp = new Coordinate[2];
                                temp[0] = state.getParentBoard().getBoardLayout()[r][c].getCoordinate();

                                temp[1] = state.getParentBoard().getBoardLayout()[r][c].getValidMoves().get(k);
                                this.possibleMoves.add(temp);
                            }
                        }
                    }
                }
            }
        }

        //blacks move
        if (turn % 2 != 0) {
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (state.getParentBoard().getBoardLayout()[r][c] != null) {
                        if (state.getParentBoard().getBoardLayout()[r][c].getColor() == false) {
                            for (int k = 0; k < state.getParentBoard().getBoardLayout()[r][c].getValidMoves().size(); k++) {
                                Coordinate[] temp = new Coordinate[2];
                                temp[0] = state.getParentBoard().getBoardLayout()[r][c].getCoordinate();
                                temp[1] = state.getParentBoard().getBoardLayout()[r][c].getValidMoves().get(k);
                                this.possibleMoves.add(temp);
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Coordinate[]> getPossibleMoves() {
        return possibleMoves;
    }

    //prints the possible moves arrayList
    public void printPossibleMoves() {
        for (int i = 0; i < this.possibleMoves.size(); i++) {
            System.out.println("Piece: " + possibleMoves.get(i)[0].getRow() + ", " + possibleMoves.get(i)[0].getCol() + " Move: " + possibleMoves.get(i)[1].getRow() + ", " + possibleMoves.get(i)[1].getCol());
        }
    }

    public int getSize() {
        return size;
    }

    public void setParentBoard(Board parentBoard) {
        this.parentBoard = parentBoard;
    }

    public Board getParentBoard() {
        return parentBoard;
    }

    public void setInitialState(int size) {
        this.parentBoard.fillPieces();
        this.parentBoard.fillAllMoves();
        if(size == 4){
            this.getParentBoard().setNumBlackPieces(2);
            this.getParentBoard().setNumWhitePieces(2);
        }
        if(size == 8){
            this.getParentBoard().setNumBlackPieces(12);
            this.getParentBoard().setNumWhitePieces(12);
        }
    }

    //makes a copy of the coordinate
    public Coordinate duplicateCoordinate(Piece piece) {
        int tempRow = piece.getCoordinate().getRow();
        int tempCol = piece.getCoordinate().getCol();
        Coordinate temp = new Coordinate(tempRow, tempCol);

        return temp;
    }

    //helper function that makes a DEEP copy of the piece's valid moves
    public ArrayList<Coordinate> duplicateValidMove(Piece piece) {

        ArrayList<Coordinate> temp = new ArrayList<Coordinate>();

        for (int k = 0; k < piece.getValidMoves().size(); k++) {
            int tempRow = piece.getValidMoves().get(k).getRow();
            int tempCol = piece.getValidMoves().get(k).getCol();
            Coordinate tempCord = new Coordinate(tempRow, tempCol);
            temp.add(tempCord);
        }

        return temp;
    }

    //helper function that makes a DEEP copy of the piece
    public Piece duplicatePiece(Piece piece) {
        Boolean tempColor = piece.getColor();
        Boolean tempType = piece.getType();
        Coordinate tempCoordinate = duplicateCoordinate(piece);
        ArrayList<Coordinate> tempValidMoves = duplicateValidMove(piece);
        Boolean capture = piece.getCapture();

        Piece temp = new Piece(tempColor, tempType, tempCoordinate);
        temp.setCapture(capture);

        return temp;
    }

    //helper function that makes a DEEP copy of the board
    public Board duplicateBoard(Board board) {
        int tempSize = board.getSize();
        Board temp = new Board(tempSize);

        int blackPieces = board.getNumBlackPieces();
        int whitePieces = board.getNumWhitePieces();

        temp.setNumWhitePieces(whitePieces);
        temp.setNumBlackPieces(blackPieces);

        for (int r = 0; r < tempSize; r++) {
            for (int c = 0; c < tempSize; c++) {
                if (board.getBoardLayout()[r][c] != null) {
                    temp.getBoardLayout()[r][c] = duplicatePiece(board.getBoardLayout()[r][c]);
                    temp.getBoardLayout()[r][c].setValidMoves(duplicateValidMove(board.getBoardLayout()[r][c]));
                } else {
                    temp.getBoardLayout()[r][c] = null;
                }
            }
        }

        return temp;
    }

    // public static void main(String[] args) {
    //     State s1 = new State(4);
    //
    //     s1.getParentBoard().printBoard();
    //
    //     State s2 = s1.results(s1, new Coordinate(3, 0), new Coordinate(2, 1));
    //     System.out.println();
    //     s2.getParentBoard().printBoard();
    //     s2.fillActions(s2, 1);
    //     s2.printPossibleMoves();
    // }
}
