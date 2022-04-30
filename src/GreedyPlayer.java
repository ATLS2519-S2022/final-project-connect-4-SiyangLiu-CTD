/**
 * A Connect-4 player that use greedy ai.
 * 
 * @author Siyang Liu
 *
 */
public class GreedyPlayer implements Player {
	private int id;
	private int oppId;
	private int cols;
	
	private Move[] routes;
	/**
	 * sets name of the player
	 * 
	 * @return string the players name
	 */
	public String name() {
		return "Greedo";
	}
	
    /**
     * Initialize the player. The game calls this method once,
     * before any calls to calcMove().
     * 
     * @param id integer identifier for the player (can get opponent's id via 3-id);
     * @param msecPerMove time allowed for each move
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
	public void init(int id, int msecPerMove, int rows, int cols) {
		this.id = id;
		this.oppId = 3 - id;
		this.cols = cols;
	}
	
    /**
     * Called by driver program to calculate the next move.
     *  
     * @param board current connect 4 board
     * @param oppMoveCol column of opponent's most recent move; -1 if this is the first move 
     * 		  of the game; note that the board may not be empty on the first move of the game!
     * @param arb handles communication between game and player
     * @throws TimeUpException If the game determines the player has run out of time
     */
	public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException{
		if(board.isFull()) 
			throw new Error("Error: The board is full!");
		routes = new Move[cols];
		for(int c = 0; c < cols; c++) {
			if(board.isValidMove(c)) {
				board.move(c, id);
				int moveValue = evaluateBoard(board, id, oppId);
				routes[c]= new Move(c, moveValue);
				board.unmove(c, id);
				}
		}
		Move bestMove = null;
		for(int i = 0; i < routes.length; i++) {
			if(bestMove == null) {
				bestMove = routes[i];
			}
			else if(routes[i]!= null && bestMove.compareTo(routes[i]) < 0) {
				bestMove = routes[i];
			}
		}
		arb.setMove(bestMove.colunm);
		
	}
	
	/**
	 * This method determines how well the player is doing against the opponent 
	 * by getting the score if you played that particular move and your enemy's score if you
	 * played that move. it then compares the scores by subtracting your score from the enemy's score
	 * and returns the difference.
	 * 
	 * @param board a Connect4 object the board configuration of the connect4 board
	 * @param myid an int that determines which person is playing
	 * @param enemyid an int that determines which person is playing
	 */
	
	private int evaluateBoard(Connect4Board board, int myid, int enemyid) {
		int myScore = calcScore(board, myid);
		int oppScore = calcScore(board,enemyid);
		
		return myScore - oppScore;
	}
	
	/**
	 * This method figures out if you scored a point by finding how many times 
	 * there are 4 player moves that are right next to each other in a line, either horizontally
	 * Vertically or diagonally. it then adds up all the occurrences and returns the total score
	 *   
	 * 
	 * @param board a Connect4Board object that shows the configuration of the board
	 * @param id an integer that determines who the player is
	 * @return score an int that is the total number of times the player has 4 tiles in a row
	 */
	public int calcScore(Connect4Board board, int id) {
		
		
		final int rows = board.numRows();
		final int cols = board.numCols();
				
		int score = 0;
		
		//horizontal
		for(int r=0; r<rows; r++) {
			for(int c=0; c<=cols - 4; c++) {
				if(board.get(r, c+0) != id) continue;
				if(board.get(r, c+1)!= id) continue;
				if(board.get(r, c+2)!= id) continue;
				if(board.get(r, c+3)!= id) continue;
				score++;
		
			}
		}
		//vertical 
		for(int c = 0; c<cols;c++) {
			for(int r=0;r<=rows-4;r++) {
				if(board.get(r+0, c)!= id)continue;
				if(board.get(r+1, c)!= id)continue;
				if(board.get(r+2, c)!= id)continue;
				if(board.get(r+3, c)!= id)continue;
				score++;
				
				
			}
		}
		//diagonal 
		for(int c = 0; c<=cols-4;c++) {
			for(int r=0;r<=rows-4;r++) {
				if(board.get(r+0, c+0)!= id)continue;
				if(board.get(r+1, c+1)!= id)continue;
				if(board.get(r+2, c+2)!= id)continue;
				if(board.get(r+3, c+3)!= id)continue;
				score++;
				
			}
		}
		for(int c = 0; c<=cols-4;c++) {
			for(int r=rows-1;r>= 3;r--) {
				if(board.get(r-0, c+0)!= id)continue;
				if(board.get(r-1, c+1)!= id)continue;
				if(board.get(r-2, c+2)!= id)continue;
				if(board.get(r-3, c+3)!= id)continue;
				score++;
				
			}
		}
		return score;
	}
	
	private class Move implements Comparable<Move>{
		private int colunm;
		private int value;
		
		/**
		 * Move constructor. creates a move based on the column and value of the position
		 * 
		 * @param column : the column of the position
		 * @param value : the value of the position
		 */
		public Move(int colunm, int value) {
			this.colunm = colunm;
			this.value = value;
			
		}
		
		/**
		 * compares two values
		 * 
		 * @return value of the integer that is greater
		 */
		public int compareTo(Move other) {
			return Integer.compare(this.value, other.value);
			
		}
	}
}     