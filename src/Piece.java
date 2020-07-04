import java.util.ArrayList;

public class Piece {

    private Boolean color;
    private Boolean type;
    private Coordinate coordinate;
    private ArrayList<Coordinate> validMoves;
    //it's capture if it's true
    private Boolean capture;

    /*
    T corresponds to white for color
    F corresponds to black for color

    T corresponds to pawn for type
    F corresponds to king for type
     */

    public ArrayList<Coordinate> getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(ArrayList<Coordinate> validMoves) {
        this.validMoves = validMoves;
    }

    public Piece(Boolean color, Boolean type, Coordinate coordinate) {
        this.color = color;
        this.type = type;
        this.coordinate = coordinate;
        this.validMoves = new ArrayList<Coordinate>();
        this.capture = false;
    }

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    public Boolean getCapture() {
        return capture;
    }

    // Returns true if the piece is white
    // Returns false if the piece is black
    public Boolean getType() {
        return type;
    }

    public void addValidMove(Coordinate nCord) {
        validMoves.add(nCord);
    }

    public void setType(Boolean type) {

        this.type = type;
    }

    public ArrayList<Coordinate> getValidMoves(Piece piece) {
       return validMoves;
    }

    public Boolean getColor() {
        return color;
    }

    //helper function for printBoard
    public void printPiece() {
        if (color == true) {
            System.out.print("W ");
        } else {
            System.out.print("B ");
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int row, int col) {
        coordinate.setCol(col);
        coordinate.setRow(row);

    }


    //checks to see if a move is valid (meaning its in the valid moves arraylist
    public boolean isValidMove(Coordinate coordinate) {
        for (int i = 0; i < validMoves.size(); i++) {
            if (coordinate.getRow() == validMoves.get(i).getRow() && coordinate.getCol() == validMoves.get(i).getCol()) {
                return true;
            }
        }
        return false;
    }




}
