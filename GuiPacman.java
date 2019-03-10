/** This file contains a class for GUI pacman game
* @author Yuan Gao
**/
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.io.*;
import java.util.Random;

/** This class is for GUI pacman game, it has two inner classes.
* @author Yuan Gao
**/
public class GuiPacman extends Application
{
	private String outputBoard; // The filename for where to save the Board
	private Board board ; // The Game Board
	private Board previousBoard = board;
	// Fill colors to choose
	private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);
	private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
	private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
	private double degree = 0;
	private double previousDegree = degree;
	private GridPane boardpane;
	private StackPane rootPane;
	private Board backup = board;
	//private Board backup = board.boardClone();
	/*
	* Name:   start
	* Purpose:  Start and keep the game running.
	* Parameter:
	* Return:
	*/
	@Override
	public void start(Stage primaryStage)
	{
		// Process Arguments and Initialize the Game Board
		processArgs(getParameters().getRaw().toArray(new String[0]));
		rootPane = new StackPane();
		boardpane = new GridPane();
		rootPane.getChildren().add(boardpane);
		if (board.isGameOver()) {
			updatePane();
			GridPane deadpane = new GridPane();
			deadpane.setAlignment(Pos.CENTER);
			deadpane.setPadding(new Insets(11.5,12.5,13.5,14.5));
			// Set the padding of the pane.
			deadpane.setStyle("-fx-background-color: rgba(238, 228, 218, 0.73)");
			//deadpane.setOpacity(0.5);
			Text gameOver = new Text();
			gameOver.setOpacity(1);
			gameOver.setText("Gameover");
			gameOver.setFill(Color.BLACK);
			gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 60));
			deadpane.add(gameOver, 0,0,4, 1);
			rootPane.getChildren().clear();
			rootPane.getChildren().addAll(boardpane,deadpane);
			Scene scene = new Scene(rootPane, 520, 520);
			primaryStage.setTitle("GuiPacman"); //title of the window(primary stage)
			primaryStage.setScene(scene);
			// set what scene to show inside the window
			primaryStage.show();
		}
		else{
			updatePane();
			Scene scene = new Scene(rootPane, 520, 520);
			//scene that needs to be displayed
			scene.setOnKeyPressed(new myKeyHandler());
			primaryStage.setTitle("GuiPacman"); //title of the window(primary stage)
			primaryStage.setScene(scene); // set what scene to show inside the window
			primaryStage.show();
		}
	}

	/*
	* Update a pane acccording to board
	*
	*
	* @param: null
	*
	* @return: null
	*/
	public void updatePane() {
		backup = board;
		try{
			boardpane.setGridLinesVisible(true);
			boardpane.getChildren().clear();
			boardpane.setAlignment(Pos.CENTER);
			boardpane.setPadding(new Insets(11.5,12.5,13.5,14.5));
			// Set the padding of the pane.
			boardpane.setHgap(5.5);
			boardpane.setVgap(5.5);
			boardpane.setStyle("-fx-background-color: rgb(0, 0, 0)");
			Text Pacman = new Text(); //create an object of the Text
			Pacman.setText("  Pac-Man");
			Pacman.setFill(Color.WHITE);
			Pacman.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			//Now we need to add the element
			//onto one of the rows and columns of the GridPane
			Text Score = new Text(); //create an object of the Text
			Score.setText("   Score: "+Integer.toString(board.getScore()));
			Score.setFill(Color.WHITE);
			Score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			//Now we need to add the element onto
			//one of the rows and columns of the GridPane
			boardpane.add(Pacman, 0, 0, 4, 1); //read about colspan and rowspan
			boardpane.add(Score, 3, 0, 4, 1);
			for (int i=0;i<this.board.GRID_SIZE;i++) {
				for (int j=0;j<this.board.GRID_SIZE;j++) {
					Tile toAdd = new Tile(board.getGrid()[i][j]);
					//make sure your getGrid() method from Board class is working as expected
					ImageView image = toAdd.getNode(i, j);
					if (toAdd.character == 'P') {
          //set the facing of pacman
						image.setRotate(degree);
					}
					boardpane.add(image, j , i+1);
					//add the appropriate row and column in the pane
				}
			}
		}
		catch(Exception exp){
			//what if Quit triggers an exeption
			this.board = backup.boardClone();
			System.out.println("noupdate");
			boardpane.setGridLinesVisible(true);
			boardpane.getChildren().clear();
			boardpane.setAlignment(Pos.CENTER);
			boardpane.setPadding(new Insets(11.5,12.5,13.5,14.5));
			// Set the padding of the pane.
			boardpane.setHgap(5.5);
			boardpane.setVgap(5.5);
			boardpane.setStyle("-fx-background-color: rgb(0, 0, 0)");
			Text Pacman = new Text(); //create an object of the Text
			Pacman.setText("  Pac-Man");
			Pacman.setFill(Color.WHITE);
			Pacman.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			//Now we need to add the element
			//onto one of the rows and columns of the GridPane
			Text Score = new Text(); //create an object of the Text
			Score.setText("   Score: "+Integer.toString(board.getScore()));
			Score.setFill(Color.WHITE);
			Score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
			//Now we need to add the element
			//onto one of the rows and columns of the GridPane
			boardpane.add(Pacman, 0, 0, 4, 1); //read about colspan and rowspan
			boardpane.add(Score, 3, 0, 4, 1);
			for (int i=0;i<this.board.GRID_SIZE;i++) {
				for (int j=0;j<this.board.GRID_SIZE;j++) {
					Tile toAdd = new Tile(board.getGrid()[i][j]);
					//make sure your getGrid() method
					//from Board class is working as expected
					ImageView image = toAdd.getNode(i, j);
					if (toAdd.character == 'P') {
						image.setRotate(degree);
					}
					boardpane.add(image, j , i+1);
					//add the appropriate row and column in the pane
				}
			}
		}
	}

	/*
	* Name:  myKeyHandler
	*
	* Purpose:Handle the keypress event
	*
	*
	*/
	private class myKeyHandler implements EventHandler<KeyEvent> {

		/*
		* Name:   handle
		* Purpose:  handle the KeyEvent of user's input.
		* Parameter: keyEvent e
		* Return:
		*/
		@Override
		public void handle (KeyEvent e) {
			try{
				if (!board.isGameOver()) {
					if(e.getCode().equals(KeyCode.UP)) {
						if (board.canMove(Direction.UP)) {
							previousBoard = board.boardClone();
							previousDegree = degree;
							board.move(Direction.UP);
							degree = 270;
							System.out.println("Moving up");
						}
					}
					else if(e.getCode().equals(KeyCode.DOWN)) {
						if (board.canMove(Direction.DOWN)) {
							previousBoard = board.boardClone();
							previousDegree = degree;
							board.move(Direction.DOWN);
							degree = 90;
							System.out.println("Moving down");

						}
					}
					else if(e.getCode().equals(KeyCode.LEFT)) {
						if (board.canMove(Direction.LEFT)) {
							previousBoard = board.boardClone();
							previousDegree = degree;
							board.move(Direction.LEFT);
							degree = 180;
							System.out.println("Moving left");

						}
					}
					else if(e.getCode().equals(KeyCode.RIGHT)) {
						if (board.canMove(Direction.RIGHT)) {
							previousBoard = board.boardClone();
							previousDegree = degree;
							board.move(Direction.RIGHT);
							System.out.println("Moving right");
							degree = 360;
						}
					}
					else if(e.getCode().equals(KeyCode.S)) {
						//saving the board
						try {
							board.saveBoard(outputBoard);
							System.out.println("Saving Board to " + outputBoard);
						}
						catch (IOException ex) {
							System.out.println("saveBoard threw an Exception");
						}

					}
					else if(e.getCode().equals(KeyCode.Q)) {
						//for ec, go back a step
						try{
							board = previousBoard;
							degree = previousDegree;
						}
						catch (Exception exp){
							System.out.println("Cannot undo the step");
						}
					}
					if (board.isGameOver()) {
						try {
							board.saveBoard(outputBoard);
							System.out.println("Saving Board to "+outputBoard);
						}
						catch (IOException ex) {
							System.out.println("saveBoard threw an Exception");
						}
						gameIsOver();
					}
					updatePane();
				}}
				catch (Exception ex) {
					System.out.println("Cannot undo a step, please go somewhere first");
					board = backup.boardClone();
					updatePane();
				}

			}


			/*
			* Name:   gameIsOver
			* Purpose:  Check if the game is over and show the gameover board.
			* Parameter:
			* Return:
			*/
			private void gameIsOver() {
				updatePane();
				GridPane deadpane = new GridPane();
				deadpane.setAlignment(Pos.CENTER);
				deadpane.setPadding(new Insets(11.5,12.5,13.5,14.5));
				// Set the padding of the pane.
				//set color of background
				deadpane.setStyle("-fx-background-color: rgba(238, 228, 218, 0.73)");
				Text gameOver = new Text();
				gameOver.setOpacity(1);
				gameOver.setText("Gameover");
				gameOver.setFill(Color.BLACK);
				gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 60));
				deadpane.add(gameOver, 0,0,4, 1);
				rootPane.getChildren().clear();
				rootPane.getChildren().addAll(boardpane,deadpane);

			}
		} // End of Inner Class myKeyHandler.



		/*
		* Name:  Tile
		*
		* Purpose:   This class tile helps to make the tiles in the board
		*     presented using JavaFX. Whenever a tile is needed,
		*     the constructor taking one char parameter is called
		*     and create certain ImageView fit to the char representation
		*     of the tile.
		*
		*
		*/
		private class Tile {
			private ImageView repr;  // This field is for the Rectangle of tile.
			private char character;
			/*
			* Constructor
			*
			* Purpose: create a new tile obj
			* Parameter: a char
			*
			*/
			public Tile(char tileAppearance) {
				this.character = tileAppearance;
			}

			public ImageView getNode(int i, int j) {

				if (this.character == 'P') {
					this.repr = new ImageView(new Image("image/pacman_right.png"));

				}
				else if (this.character == 'G') {
					if (board.getGhosts()[0].getRow()==i
					  && board.getGhosts()[0].getCol()==j) {
						this.repr = new ImageView(new Image("image/blinky_left.png"));
					}
					else if (board.getGhosts()[1].getRow()==i
					  && board.getGhosts()[1].getCol()==j) {
						this.repr = new ImageView(new Image("image/clyde_up.png"));
					}
					else if (board.getGhosts()[2].getRow()==i
					  && board.getGhosts()[2].getCol()==j) {
						this.repr = new ImageView(new Image("image/inky_down.png"));
					}
					else if (board.getGhosts()[3].getRow()==i
					  && board.getGhosts()[3].getCol()==j) {
						this.repr = new ImageView(new Image("image/pinky_left.png"));
					}

				}
				else if (this.character == ' ') {
					this.repr = new ImageView(new Image("image/dot_eaten.png"));
				}
				else if (this.character == '*') {
					this.repr = new ImageView(new Image("image/dot_uneaten.png"));
				}
				else if (this.character == 'X') {
					this.repr = new ImageView(new Image("image/pacman_dead.png"));
				}

				this.repr.setFitHeight(42);
				this.repr.setFitWidth(42);
				return this.repr;
			}

		} // End of Inner class Tile




		/** DO NOT EDIT BELOW */

		// The method used to process the command line arguments
		private void processArgs(String[] args)
		{
			String inputBoard = null;  // The filename for where to load the Board
			int boardSize = 0;   // The Size of the Board

			// Arguments must come in pairs
			if((args.length % 2) != 0)
			{
				printUsage();
				System.exit(-1);
			}

			// Process all the arguments
			for(int i = 0; i < args.length; i += 2)
			{
				if(args[i].equals("-i"))
				{  // We are processing the argument that specifies
					// the input file to be used to set the board
					inputBoard = args[i + 1];
				}
				else if(args[i].equals("-o"))
				{  // We are processing the argument that specifies
					// the output file to be used to save the board
					outputBoard = args[i + 1];
				}
				else if(args[i].equals("-s"))
				{  // We are processing the argument that specifies
					// the size of the Board
					boardSize = Integer.parseInt(args[i + 1]);
				}
				else
				{  // Incorrect Argument
					printUsage();
					System.exit(-1);
				}
			}

			// Set the default output file if none specified
			if(outputBoard == null)
			outputBoard = "Pac-Man.board";
			// Set the default Board size if none specified or less than 2
			if(boardSize < 3)
			boardSize = 10;

			// Initialize the Game Board
			try{
				if(inputBoard != null)
				board = new Board(inputBoard);
				else
				board = new Board(boardSize);
			}
			catch (Exception e)
			{
				System.out.println(e.getClass().getName() + " was thrown while creating a " +
				"Board from file " + inputBoard);
				System.out.println("Either your Board(String, Random) " +
				"Constructor is broken or the file isn't " +
				"formated correctly");
				System.exit(-1);
			}
		}

		// Print the Usage Message
		private static void printUsage()
		{
			System.out.println("GuiPacman");
			System.out.println("Usage: GuiPacman [-i|o file ...]");
			System.out.println();
			System.out.println(" Command line arguments come in pairs of the form: <command> <argument>");
			System.out.println();
			System.out.println(" -i [file] -> Specifies a Pacman board that should be loaded");
			System.out.println();
			System.out.println(" -o [file] -> Specifies a file that should be used to save the Pac-Man board");
			System.out.println("    If none specified then the default \"Pac-Man.board\" file will be used");
			System.out.println(" -s [size] -> Specifies the size of the Pac-Man board if an input file hasn't been");
			System.out.println("    specified. If both -s and -i are used, then the size of the board");
			System.out.println("    will be determined by the input file. The default size is 10.");
		}

		public static void main(String[] args){

			Application.launch(args);

		}
	}
