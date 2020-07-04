import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //this class is where the game is run.
        //it asks the user to input the board size as well as the opponents algorithm
        //based off that it runs the game
        //for h-minimax the user inputs the depth cutoff

        Scanner scan = new Scanner(System.in);

        System.out.print("Welcome to checkers! You're the white pieces.\n" +
                "By Ethan Sawyer \n\n" +
                "Choose your game: \n" +
                " 1. 4 x 4 Game\n" +
                " 2. 8 x 8 Game\n\n" +
                "" +
                "Game Choice: ");

        int gameSize = scan.nextInt();

        if (gameSize == 1) {
            gameSize = 4;
        } else {
            gameSize = 8;
        }

        System.out.print("\nChoose your opponent: \n " +
                "1. Random \n " +
                "2. MINIMAX\n " +
                "3. MINIMAX with alpha-beta pruning\n " +
                "4. H-MINIMAX with a fixed depth cutoff\n\n" +
                "Opponent Choice: ");

        int opponentChoice = scan.nextInt();

        Driver driver = new Driver(gameSize);

        //when the user selects a random move
        if (opponentChoice == 1) {

            Boolean endgame = false;

            System.out.println();
            driver.getInitState().setInitialState(gameSize);
            System.out.println("Begin game:");
            System.out.println();
            driver.getInitState().getParentBoard().printBoard();
            System.out.println();
            Board tempBoard = driver.getInitState().duplicateBoard(driver.getInitState().getParentBoard());
            State tempState = new State(gameSize);
            tempState.setParentBoard(tempBoard);

            while (endgame == false) {

                System.out.println("Your turn!");
                System.out.println();
                System.out.print("Enter the row and column of the piece you want to move: ");
                String origLoc = scan.next();
                System.out.print("Enter the row and column of where you want to move it: ");
                String newLoc = scan.next();

                int initRow = Integer.parseInt(origLoc.substring(0, 1));
                int initCol = Integer.parseInt(origLoc.substring(1, 2));
                int newRow = Integer.parseInt(newLoc.substring(0, 1));
                int newCol = Integer.parseInt(newLoc.substring(1, 2));

                System.out.println();
                System.out.println("Move " + initRow + initCol + " to " + newRow + newCol);
                System.out.println();

                tempState.getParentBoard().move(new Coordinate(initRow, initCol), new Coordinate(newRow, newCol));
                tempState.getParentBoard().printBoard();

                if (tempState.getParentBoard().getNumBlackPieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("White team wins!");
                    break;
                }

                tempState.getPossibleMoves().clear();
                tempState.fillActions(tempState, 1);
                int random = driver.random(tempState);

                int initRowNew = tempState.getPossibleMoves().get(random)[0].getRow();
                int initColNew = tempState.getPossibleMoves().get(random)[0].getCol();
                int newRowNew = tempState.getPossibleMoves().get(random)[1].getRow();
                int newColNew = tempState.getPossibleMoves().get(random)[1].getCol();

                System.out.println();
                System.out.println("Black's turn:");

                //tempState.printPossibleMoves();

                tempState.getParentBoard().move(new Coordinate(initRowNew, initColNew), new Coordinate(newRowNew, newColNew));
                tempState.getParentBoard().printBoard();
                //System.out.println("White Pieces: " + tempState.getParentBoard().getNumWhitePieces());
                //System.out.println("Black Pieces: " + tempState.getParentBoard().getNumBlackPieces());

                if (tempState.getParentBoard().getNumWhitePieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("Black team wins!");
                    break;
                }

                tempState.getPossibleMoves().clear();
                tempState.fillActions(tempState, 1);

                System.out.println();

            }
        }
        //when the user selects minimax game
        if (opponentChoice == 2) {

            Boolean endgame = false;

            System.out.println();
            driver.getInitState().setInitialState(gameSize);
            System.out.println("Begin game:");
            System.out.println();
            driver.getInitState().getParentBoard().printBoard();
            System.out.println();
            Board tempBoard = driver.getInitState().duplicateBoard(driver.getInitState().getParentBoard());
            State tempState = new State(gameSize);
            tempState.setParentBoard(tempBoard);

            while (endgame == false) {

                System.out.println("Your turn!");
                System.out.println();
                System.out.print("Enter the row and column of the piece you want to move: ");
                String origLoc = scan.next();
                System.out.print("Enter the row and column of where you want to move it: ");
                String newLoc = scan.next();

                int initRow = Integer.parseInt(origLoc.substring(0, 1));
                int initCol = Integer.parseInt(origLoc.substring(1, 2));
                int newRow = Integer.parseInt(newLoc.substring(0, 1));
                int newCol = Integer.parseInt(newLoc.substring(1, 2));

                System.out.println();
                System.out.println("Move " + initRow + initCol + " to " + newRow + newCol);
                System.out.println();

                tempState.getParentBoard().move(new Coordinate(initRow, initCol), new Coordinate(newRow, newCol));
                tempState.getParentBoard().printBoard();

                if (tempState.getParentBoard().getNumBlackPieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("White team wins!");
                    break;
                }


                driver.getNextMoves().clear();

                System.out.println();
                int move = driver.minimax(tempState, 1, 0);

                System.out.println("Black's turn:");

                for (int k = 0; k < driver.getNextMoves().size(); k++) {


                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }
                    if (driver.getNextMoves().get(k).getUtility() == move) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }
                }
                //accounts for a capture that might have the same utility as a move
                for (int k = 0; k < driver.getNextMoves().size(); k++) {

                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }

                }


                tempState.getParentBoard().printBoard();

                if (tempState.getParentBoard().getNumWhitePieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("Black team wins!");
                    break;
                }
                System.out.println();


            }
        }


        //when the user selects alpha-beta pruning minimax
        if (opponentChoice == 3) {

            Boolean endgame = false;

            System.out.println();
            driver.getInitState().setInitialState(gameSize);
            System.out.println("Begin game:");
            System.out.println();
            driver.getInitState().getParentBoard().printBoard();
            System.out.println();
            Board tempBoard = driver.getInitState().duplicateBoard(driver.getInitState().getParentBoard());
            State tempState = new State(gameSize);
            tempState.setParentBoard(tempBoard);

            while (endgame == false) {

                System.out.println("Your turn!");
                System.out.println();
                System.out.print("Enter the row and column of the piece you want to move: ");
                String origLoc = scan.next();
                System.out.print("Enter the row and column of where you want to move it: ");
                String newLoc = scan.next();

                int initRow = Integer.parseInt(origLoc.substring(0, 1));
                int initCol = Integer.parseInt(origLoc.substring(1, 2));
                int newRow = Integer.parseInt(newLoc.substring(0, 1));
                int newCol = Integer.parseInt(newLoc.substring(1, 2));

                System.out.println();
                System.out.println("Move " + initRow + initCol + " to " + newRow + newCol);
                System.out.println();


                tempState.getParentBoard().move(new Coordinate(initRow, initCol), new Coordinate(newRow, newCol));
                tempState.getParentBoard().printBoard();


                if (tempState.getParentBoard().getNumBlackPieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("White team wins!");
                    break;
                }

                driver.getNextMoves().clear();

                System.out.println();
                int move = driver.minimaxAB(tempState, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, 0);

                System.out.println("Black's turn:");

                for (int k = 0; k < driver.getNextMoves().size(); k++) {

                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }
                    if (driver.getNextMoves().get(k).getUtility() == move) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }

                }
                //accounts for a capture that might have the same utility as a move
                for (int k = 0; k < driver.getNextMoves().size(); k++) {

                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }

                }
                tempState.getParentBoard().printBoard();
                System.out.println();

                if (tempState.getParentBoard().getNumWhitePieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("Black team wins!");
                    break;
                }

            }
        }

        //when the user selects h-minimax
        if (opponentChoice == 4) {

            Boolean endgame = false;

            System.out.println();
            driver.getInitState().setInitialState(gameSize);
            System.out.println("Begin game:");
            System.out.println();
            System.out.print("Enter the depth to cutoff at: ");
            int depthCutoff = scan.nextInt();
            System.out.println();
            driver.getInitState().getParentBoard().printBoard();
            System.out.println();
            Board tempBoard = driver.getInitState().duplicateBoard(driver.getInitState().getParentBoard());
            State tempState = new State(gameSize);
            tempState.setParentBoard(tempBoard);

            while (endgame == false) {


                System.out.println("Your turn!");
                System.out.println();
                System.out.print("Enter the row and column of the piece you want to move: ");
                String origLoc = scan.next();
                System.out.print("Enter the row and column of where you want to move it: ");
                String newLoc = scan.next();

                int initRow = Integer.parseInt(origLoc.substring(0, 1));
                int initCol = Integer.parseInt(origLoc.substring(1, 2));
                int newRow = Integer.parseInt(newLoc.substring(0, 1));
                int newCol = Integer.parseInt(newLoc.substring(1, 2));

                System.out.println();
                System.out.println("Move " + initRow + initCol + " to " + newRow + newCol);
                System.out.println();


                tempState.getParentBoard().move(new Coordinate(initRow, initCol), new Coordinate(newRow, newCol));
                tempState.getParentBoard().printBoard();

                if (tempState.getParentBoard().getNumBlackPieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("White team wins!");
                    break;
                }

                driver.getNextMoves().clear();


                System.out.println();
                int move = driver.hminimax(tempState, 1, 0, depthCutoff);

                System.out.println("Black's turn:");

                for (int k = 0; k < driver.getNextMoves().size(); k++) {

                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }
                    if (driver.getNextMoves().get(k).getUtility() == move) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }

                }
                //accounts for a capture that might have the same utility as a move
                for (int k = 0; k < driver.getNextMoves().size(); k++) {

                    if (driver.getNextMoves().get(k).getParentBoard().getNumWhitePieces() < tempState.getParentBoard().getNumWhitePieces()) {
                        tempState.setParentBoard(driver.getNextMoves().get(k).getParentBoard());
                    }

                }
                tempState.getParentBoard().printBoard();
                System.out.println();

                if (tempState.getParentBoard().getNumWhitePieces() == 0) {
                    endgame = true;
                    System.out.println("Game ends!");
                    System.out.println("Black team wins!");
                    break;
                }

            }
        }
    }
}
