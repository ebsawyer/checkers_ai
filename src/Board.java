import java.util.ArrayList;

public class Board {

    private int size;
    private Piece[][] boardLayout;
    private int numWhitePieces;
    private int numBlackPieces;
    private int numWhiteKings;
    private int numBlackKings;


    public Board(int size) {
        this.size = size;
        boardLayout = new Piece[size][size];
        this.numBlackPieces = numBlackPieces;
        this.numWhitePieces = numWhitePieces;
        this.numWhiteKings = numWhiteKings;
        this.numBlackKings = numBlackKings;
    }

    public void setNumWhitePieces(int numWhitePieces) {
        this.numWhitePieces = numWhitePieces;
    }

    public void setNumBlackPieces(int numBlackPieces) {
        this.numBlackPieces = numBlackPieces;
    }

    public void setNumWhiteKings(int numWhiteKings) {
        this.numWhiteKings = numWhiteKings;
    }

    public void setNumBlackKings(int numBlackKings) {
        this.numBlackKings = numBlackKings;
    }

    public int getNumWhiteKings() {
        return numWhiteKings;
    }

    public int getNumBlackKings() {
        return numBlackKings;
    }

    public int getNumBlackPieces() {
        return numBlackPieces;
    }

    public int getNumWhitePieces() {
        return numWhitePieces;
    }

    public Piece[][] getBoardLayout() {
        return boardLayout;
    }

    public void printValidMoves() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (boardLayout[r][c] != null) {
                    System.out.println("Initial Coordinate: " + r + ", " + c);
                    for (int k = 0; k < boardLayout[r][c].getValidMoves().size(); k++) {
                        System.out.println("Moves: " + boardLayout[r][c].getValidMoves().get(k).getRow() + ", " + boardLayout[r][c].getValidMoves().get(k).getCol());
                    }
                }
            }
            System.out.println();
        }
    }

    public void setKing(Coordinate cord) {
        if (boardLayout[cord.getRow()][cord.getCol()].getColor() == true) {
            if (cord.getRow() == 0) {
                boardLayout[cord.getRow()][cord.getCol()].setType(false);
                this.numWhiteKings++;
            }
        } else if (boardLayout[cord.getRow()][cord.getCol()].getColor() == false) {
            if (cord.getRow() == size - 1) {
                boardLayout[cord.getRow()][cord.getCol()].setType(false);
                this.numWhiteKings++;
            }
        }
    }

    //fills the board with checkers
    public void fillPieces() {

        // 4x4 checkers
        if (size == 4) {

            //filling in the black checkers
            for (int c = 0; c < size; c++) {
                if (c % 2 != 0) {
                    boardLayout[0][c] = new Piece(false, true, new Coordinate(0, c));
                }
            }

            //filling in the white checkers
            for (int c = 0; c < size; c++) {
                if (c % 2 == 0) {
                    boardLayout[3][c] = new Piece(true, true, new Coordinate(3, c));
                }
            }
        }

        // 8x8 checkers
        if (size == 8) {

            //filling in the black checkers
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < size; c++) {
                    if (r == 0 && c % 2 != 0) {
                        boardLayout[r][c] = new Piece(false, true, new Coordinate(r, c));
                    }
                    if (r == 1 && c % 2 == 0) {
                        boardLayout[r][c] = new Piece(false, true, new Coordinate(r, c));
                    }
                    if (r == 2 && c % 2 != 0) {
                        boardLayout[r][c] = new Piece(false, true, new Coordinate(r, c));
                    }
                }
            }

            //filling in the while checkers
            for (int r = 5; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    if (r == 5 && c % 2 == 0) {
                        boardLayout[r][c] = new Piece(true, true, new Coordinate(r, c));
                    }
                    if (r == 6 && c % 2 != 0) {
                        boardLayout[r][c] = new Piece(true, true, new Coordinate(r, c));
                    }
                    if (r == 7 && c % 2 == 0) {
                        boardLayout[r][c] = new Piece(true, true, new Coordinate(r, c));
                    }
                }
            }
        }
    }

    //prints the board to the terminal
    public void printBoard() {

        for (int r = 0; r < size; r++) {

            for (int c = 0; c < size; c++) {
                if (boardLayout[r][c] != null) {
                    boardLayout[r][c].printPiece();
                } else {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }

    public int getSize() {
        return size;
    }


    public void fillAllMoves() {
        int size = getSize();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {

                Coordinate tempCord = new Coordinate(r, c);

                if (boardLayout[r][c] != null && boardLayout[r][c].getColor() == true) {
                    fillWhiteValidMoves(tempCord);
                } else if (boardLayout[r][c] != null && boardLayout[r][c].getColor() == false) {
                    fillBlackValidMoves(tempCord);
                }
            }
        }
    }

    //fills the arraylist of the current piece being moved with the valid moves
    //this only fills the valid moves for white pieces
    public void fillWhiteValidMoves(Coordinate currCord) {

        if (boardLayout[currCord.getRow()][currCord.getCol()].getValidMoves() != null) {
            //clears the arrayList for the piece
            boardLayout[currCord.getRow()][currCord.getCol()].getValidMoves().clear();
        }

        //when the piece isn't a king
        if (boardLayout[currCord.getRow()][currCord.getCol()].getType() == true) {
            //checks if move left is possible
            if (currCord.getCol() != 0 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] == null) {
                // adds move left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() - 1));
            }
            //checks if move right is possible
            if (currCord.getCol() != size - 1 && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1] == null) {
                // adds move right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() + 1));
            }
            //checks if capture right is possible
            if (currCord.getCol() < size - 2 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol()+1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1].getColor() == false && boardLayout[currCord.getRow() - 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() + 2));
            }
            //checks if capture left is possible
            if (currCord.getCol() > 1 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1].getColor() == false && boardLayout[currCord.getRow() - 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() - 2));
            }
        }

        //checks if king move is possible
        if (boardLayout[currCord.getRow()][currCord.getCol()].getType() == false) {
            //move down left
            if (currCord.getCol() != 0 && currCord.getRow() != size - 1 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] == null) {
                // adds move left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() - 1));
            }

            //move down right
            if (currCord.getCol() != size - 1 && currCord.getRow() != size - 1 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] == null) {
                // adds move right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() + 1));
            }
            //move up left
            if (currCord.getCol() != 0 && currCord.getRow() != 0 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() - 1));

            }
            //move up right
            if (currCord.getCol() != size - 1 && currCord.getRow() != 0 && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() + 1));
            }

            //capture left up
            if (currCord.getCol() > 1 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1].getColor() == false && boardLayout[currCord.getRow() - 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() - 2));
            }

            //capture right up
            if (currCord.getCol() < size - 2 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol()+1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1].getColor() == false && boardLayout[currCord.getRow() - 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() + 2));
            }

            //capture left down
            if (currCord.getCol() > 1 && currCord.getRow() < size -2 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] != null &&  boardLayout[currCord.getRow() + 1][currCord.getCol() - 1].getColor() == false && boardLayout[currCord.getRow() + 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() - 2));
            }

            //capture right down
            if (currCord.getCol() < size - 2 && currCord.getRow() < size - 2 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] != null && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1].getColor() == false && boardLayout[currCord.getRow() + 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() + 2));
            }
        }
    }

    public void fillBlackValidMoves(Coordinate currCord) {

        if (boardLayout[currCord.getRow()][currCord.getCol()].getValidMoves() != null) {
            //clears the arrayList for the piece
            boardLayout[currCord.getRow()][currCord.getCol()].getValidMoves().clear();
        }

        //moves when the piece isn't a king
        if (boardLayout[currCord.getRow()][currCord.getCol()].getType() == true) {

            //checks if move left is possible
            if (currCord.getCol() != 0 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] == null) {
                // adds move left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() - 1));
            }
            //checks if move right is possible
            if (currCord.getCol() != size - 1 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] == null) {
                // adds move right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() + 1));
            }

            //checks if capture right is possible
            if (currCord.getCol() < size - 2 && currCord.getRow() < size - 2 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] != null && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1].getColor() == true && boardLayout[currCord.getRow() + 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() + 2));
            }

            //checks if capture left is possible
            if (currCord.getCol() > 1 && currCord.getRow() < size -2 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] != null &&  boardLayout[currCord.getRow() + 1][currCord.getCol() - 1].getColor() == true && boardLayout[currCord.getRow() + 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() - 2));


            }
        }

        //checks if king move is possible
        if (boardLayout[currCord.getRow()][currCord.getCol()].getType() == false) {
            //move down left
            if (currCord.getCol() != 0 && currCord.getRow() != size - 1 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] == null) {
                // adds move left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() - 1));
            }

            //move down right
            if (currCord.getCol() != size - 1 && currCord.getRow() != size - 1 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] == null) {
                // adds move right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 1, currCord.getCol() + 1));
            }
            //move up left
            if (currCord.getCol() != 0 && currCord.getRow() != 0 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() - 1));

            }
            //move up right
            if (currCord.getCol() != size - 1 && currCord.getRow() != 0 && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 1, currCord.getCol() + 1));
            }

            //capture left up
            if (currCord.getCol() > 1 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() - 1].getColor() == true && boardLayout[currCord.getRow() - 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() - 2));

            }

            //capture right up
            if (currCord.getCol() < size - 2 && currCord.getRow() > 1 && boardLayout[currCord.getRow() - 1][currCord.getCol()+1] != null && boardLayout[currCord.getRow() - 1][currCord.getCol() + 1].getColor() == true && boardLayout[currCord.getRow() - 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() - 2, currCord.getCol() + 2));

            }

            //capture left down
            if (currCord.getCol() > 1 && currCord.getRow() < size -2 && boardLayout[currCord.getRow() + 1][currCord.getCol() - 1] != null &&  boardLayout[currCord.getRow() + 1][currCord.getCol() - 1].getColor() == true && boardLayout[currCord.getRow() + 2][currCord.getCol() - 2] == null) {
                // adds capture left as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() - 2));

            }

            //capture right down
            if (currCord.getCol() < size - 2 && currCord.getRow() < size - 2 && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1] != null && boardLayout[currCord.getRow() + 1][currCord.getCol() + 1].getColor() == true && boardLayout[currCord.getRow() + 2][currCord.getCol() + 2] == null) {
                // adds capture right as a valid move for the piece
                boardLayout[currCord.getRow()][currCord.getCol()].addValidMove(new Coordinate(currCord.getRow() + 2, currCord.getCol() + 2));

            }
        }
    }

    //basic move left of right
    //checks to see if the move is valid
    //moves the piece on the arraylist
    //changes the piece's coordinates accordingly
    public void move(Coordinate iCord, Coordinate nCord) {
        //white pieces
        if (boardLayout[iCord.getRow()][iCord.getCol()].getColor() == true) {

            //the piece is only moved if the move is a "valid move"

            if (boardLayout[iCord.getRow()][iCord.getCol()].isValidMove(nCord) == true) {

                Piece temp = boardLayout[iCord.getRow()][iCord.getCol()];
                //moves the piece
                boardLayout[nCord.getRow()][nCord.getCol()] = temp;
                //sets the updates coordinates of the piece
                boardLayout[nCord.getRow()][nCord.getCol()].setCoordinate(nCord.getRow(), nCord.getCol());
                //sets the old piece location to null on array
                boardLayout[iCord.getRow()][iCord.getCol()] = null;

                setKing(nCord);

                //single jump
                if (Math.abs(nCord.getRow() - iCord.getRow()) == 2 && Math.abs(nCord.getCol() - iCord.getCol()) == 2) {

                    //capture up left for white
                    if (nCord.getRow() < iCord.getRow() && nCord.getCol() < iCord.getCol()) {
                        boardLayout[iCord.getRow() - 1][iCord.getCol() - 1] = null;
                    }
                    //capture up right for white
                    if (nCord.getRow() < iCord.getRow() && nCord.getCol() > iCord.getCol()) {
                        boardLayout[iCord.getRow() - 1][iCord.getCol() + 1] = null;
                    }
                    //capture down left for white
                    if (nCord.getRow() > iCord.getRow() && nCord.getCol() < iCord.getCol()) {
                        boardLayout[iCord.getRow() + 1][iCord.getCol() - 1] = null;
                    }
                    //capture down right for white
                    if (nCord.getRow() > iCord.getRow() && nCord.getCol() > iCord.getCol()) {
                        boardLayout[iCord.getRow() + 1][iCord.getCol() + 1] = null;
                    }
                    numBlackPieces--;
                }

            } else {
                System.out.println("Not a valid move");
                System.out.println();
            }

        }

        //black pieces
        else if (boardLayout[iCord.getRow()][iCord.getCol()].getColor() == false) {

            //the piece is only moved if the move is a "valid move"
            if (boardLayout[iCord.getRow()][iCord.getCol()].isValidMove(nCord) == true) {


                Piece temp = boardLayout[iCord.getRow()][iCord.getCol()];
                //moves the piece
                boardLayout[nCord.getRow()][nCord.getCol()] = temp;
                //sets the updates coordinates of the piece
                boardLayout[nCord.getRow()][nCord.getCol()].setCoordinate(nCord.getRow(), nCord.getCol());
                //sets the old piece location to null on array
                boardLayout[iCord.getRow()][iCord.getCol()] = null;
                setKing(nCord);


                //single jump
                if (Math.abs(nCord.getRow() - iCord.getRow()) == 2 && Math.abs(nCord.getCol() - iCord.getCol()) == 2) {

                    //capture down left for black
                    if (nCord.getRow() > iCord.getRow() && nCord.getCol() < iCord.getCol()) {
                        boardLayout[iCord.getRow() + 1][iCord.getCol() - 1] = null;
                    }
                    //capture down right for black
                    if (nCord.getRow() > iCord.getRow() && nCord.getCol() > iCord.getCol()) {
                        boardLayout[iCord.getRow() + 1][iCord.getCol() + 1] = null;
                    }
                    //capture up left for black
                    if (nCord.getRow() < iCord.getRow() && nCord.getCol() < iCord.getCol()) {
                        boardLayout[iCord.getRow() - 1][iCord.getCol() - 1] = null;
                    }
                    //capture up right for black
                    if (nCord.getRow() < iCord.getRow() && nCord.getCol() > iCord.getCol()) {
                        boardLayout[iCord.getRow() - 1][iCord.getCol() + 1] = null;
                    }
                    numWhitePieces--;
                }


            } else {
                System.out.println("Not a valid move");
                System.out.println();
            }
        }

        fillAllMoves();
    }

    // public static void main(String[] args) {
    //     Board board = new Board(4);
    //     board.fillPieces();
    //     board.fillAllMoves();
    //     board.move(new Coordinate(3, 0), new Coordinate(2, 1));
    //
    //     board.move(new Coordinate(0, 3), new Coordinate(1, 2));
    //     board.move(new Coordinate(2, 1), new Coordinate(0, 3));
    //     //board.move(new Coordinate(0, 1), new Coordinate(1, 2));
    //     //board.move(new Coordinate(0, 3), new Coordinate(2, 1));
    //     //board.move(new Coordinate(1, 2), new Coordinate(3, 0));
    //     //board.move(new Coordinate(3, 0), new Coordinate(2, 1));
    //     //board.move(new Coordinate(3, 2), new Coordinate(2, 3));
    //     //board.move(new Coordinate(0, 3), new Coordinate(1, 2));
    //     //System.out.println(board.boardLayout[3][0].getType());
    //     System.out.println("B: " + board.numBlackPieces);
    //     System.out.println("W: " + board.numWhitePieces);
    //     board.printBoard();
    //     System.out.println();
    //     board.printValidMoves();
    // }
}
