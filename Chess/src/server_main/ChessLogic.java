package server_main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.util.Pair;
import server_pieces.Horse;
import server_pieces.King;
import server_pieces.Pawn;
import server_pieces.Queen;
import server_pieces.Runner;
import server_pieces.Tower;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * Handles all the chess logic.
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class ChessLogic {

	private ArrayList<Piece> pieces = new ArrayList<>();
	private boolean gameRunning = false;
	private ArrayList<Pair<Coordinate, Coordinate>> gameHistory = new ArrayList<>();
	private PlayerColor playerTurn = PlayerColor.WHITE;

	private Gson gson = new GsonBuilder().serializeNulls().create();

	/**
	 * @see ChessLogic#createPieces()
	 */
	public ChessLogic() {
		createPieces();
	}

	/**
	 * Creates all the pieces
	 */
	private void createPieces(){

		//White player
		pieces.add(new Tower(0,0,PlayerColor.WHITE));
		pieces.add(new Tower(7,0,PlayerColor.WHITE));
		pieces.add(new Horse(1,0,PlayerColor.WHITE));
		pieces.add(new Horse(6,0,PlayerColor.WHITE));
		pieces.add(new Runner(2,0,PlayerColor.WHITE));
		pieces.add(new Runner(5,0,PlayerColor.WHITE));
		pieces.add(new King(4,0,PlayerColor.WHITE));
		pieces.add(new Queen(3,0,PlayerColor.WHITE));

		for(int x = 0; x < 8; x++) {
			pieces.add(new Pawn(x,1,PlayerColor.WHITE));
		}

		//Black player
		pieces.add(new Tower(0,7,PlayerColor.BLACK));
		pieces.add(new Tower(7,7,PlayerColor.BLACK));
		pieces.add(new Horse(1,7,PlayerColor.BLACK));
		pieces.add(new Horse(6,7,PlayerColor.BLACK));
		pieces.add(new Runner(2,7,PlayerColor.BLACK));
		pieces.add(new Runner(5,7,PlayerColor.BLACK));
		pieces.add(new King(4,7,PlayerColor.BLACK));
		pieces.add(new Queen(3,7,PlayerColor.BLACK));

		for(int x = 0; x < 8; x++) {
			pieces.add(new Pawn(x,6,PlayerColor.BLACK));
		}

	}


	/**
	 * Switches playerTurn
	 * 
	 * @see shared.PlayerColor
	 */
	public void switchTurn() {
		playerTurn = (playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}


	/**
	 * @return	The player that has turn
	 */
	public PlayerColor getPlayerTurn() {
		return playerTurn;
	}


	/**
	 * @return If game is running or not
	 */
	public boolean isGameRunning() {
		return gameRunning;
	}


	/**
	 * @param gameRunning	Boolean of game running
	 */
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}


	/**
	 * @return	A list with all the pieces
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}


	/**
	 * Finds the requested {@link Piece} and returns it
	 * 
	 * @param coord 		Matching coordinate.
	 * @return 			Matching piece or null
	 * @see 				Piece#getCoordinate()
	 */
	public Piece getPiece(Coordinate coord){
		for(Piece piece : pieces) {
			if(piece.getCoordinate().equals(coord)) {
				return piece;
			}
		}
		return null;
	}


	/**
	 * @param coord 		Piece at {@link Coordinate} to remove
	 */
	public void removePiece(Coordinate coord){
		pieces.remove(getPiece(coord));
	}


	/**
	 * Checks if a piece at a {@link shared.Coordinate coordinate} is owned by a specific player
	 * 
	 * @param c			Coordinate of the piece
	 * @param player		Color of the player
	 * @return			If the piece is owned by the player with the {@link shared.PlayerColor color} or not
	 * @see 				ChessLogic#isEmpty(Coordinate)
	 */
	public  boolean isOwnedBy(Coordinate c, PlayerColor player){
		return (c == null || isEmpty(c)) ? false : getPiece(c).getColor().equals(player); 
	}


	/**
	 * Checks if coordinate is empty
	 * 
	 * @param coord		Coordinate to check if empty
	 * @return			Coordinate empty or not
	 * @see 				Piece#getCoordinate()
	 */
	public  boolean isEmpty(Coordinate coord){
		for(Piece piece : pieces) {
			if(piece.getCoordinate().equals(coord)) {
				return false;
			}
		}
		return true;	
	}


	/**
	 * Moves a {@link Piece piece} and saves the move
	 * 
	 * @param p		Piece to move
	 * @param c		Where to move the piece
	 * @see 			ChessLogic#saveGame()
	 * @see 			ChessLogic#removePiece(Coordinate)
	 * @see 			Piece#setCoordinate(Coordinate)
	 */
	public void move(Piece p, Coordinate c) {
		addDraw(p.getCoordinate(), c);
		removePiece(c);
		p.setCoordinate(c);
		
		/*
		 * If the piece is a pawn and have moved to the other side, transform it to a queen
		 */
		if(p instanceof Pawn && ((Pawn) p).isOnOtherSide()) {
			PlayerColor pieceColor = p.getColor();
			removePiece(c);
			pieces.add(new Queen(c.x, c.y, pieceColor));
			
		}
	}


	/**
	 * Checks if the player is in chess
	 * 
	 * @param playerColor	Color of the player to check
	 * @return 				If the player is in chess or not
	 * @see 					Piece
	 * @see					ChessLogic#isMovable(Coordinate, Piece)
	 */
	public boolean isChess(PlayerColor playerColor) {

		Piece king = null;
		for(Piece p : pieces) {
			if(p.isKing() && p.getColor() == playerColor) {
				king = p;
			}
		}

		for (Piece p : pieces) {
			//Only enemies is used
			if(p.getColor() != playerColor) {
				//If anyone can move to the king it's chess
				if(isMovable(king.getCoordinate(), p)) {	
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * Checks if a given move will put the player in chess
	 * 
	 * @param move	Where the piece want to be moved
	 * @param p		Which piece
	 * @return		If it will put the player in chess or not
	 * @see			Coordinate
	 * @see			ChessLogic#isChess(PlayerColor)
	 */
	public boolean isChess(Coordinate move, Piece p) {

		Coordinate saved = p.getCoordinate();


		Piece pieceAtMove = getPiece(move);

		if(pieceAtMove != null) {
			pieceAtMove.setActive(false);
		}

		p.setCoordinate(move);

		boolean ret = isChess(p.getColor());
		p.setCoordinate(saved);
		if(pieceAtMove != null) {
			pieceAtMove.setActive(true);
		}

		return ret;
	}


	/**
	 * Checks if the player can make any moves
	 * 
	 * @param player		Which player
	 * @return			If the player can make any moves at all
	 * @see 				ChessLogic#isMovable(Coordinate, Piece)
	 * @see				ChessLogic#isMovable(Coordinate, Piece)
	 */
	public  boolean canMakeMove(PlayerColor player) {

		for (Piece piece : pieces) {
			if(piece.getColor() == player) {
				ArrayList<Coordinate> possibleMoves = isMovableAll(piece);
				for (Coordinate coordinate : possibleMoves) {
					if(isMovable(coordinate, piece) && !isChess(coordinate, piece)) {
						return true;
					}
				}

			}
		}
		return false;
	}


	/**
	 * @param p	The piece that is getting checked
	 * @return	All the possible moves for a {@link Piece piece}
	 */
	public ArrayList<Coordinate> isMovableAll(Piece p){
		ArrayList<Coordinate> list = new ArrayList<>();

		//Inactive piece can not move
		if(!p.isActive()) {
			return list;
		}


		//Pawn specific rules
		if(p instanceof Pawn) {

			if(p.getColor().equals(PlayerColor.WHITE)){
				if(!isEmpty(p.getCoordinate().getMove(Direction.SE)) && getPiece(p.getCoordinate().getMove(Direction.SE)).isEnemy(p)){
					list.add(p.getCoordinate().getMove(Direction.SE));
				}
				if(!isEmpty(p.getCoordinate().getMove(Direction.SV)) && getPiece(p.getCoordinate().getMove(Direction.SV)).isEnemy(p)){
					list.add(p.getCoordinate().getMove(Direction.SV));
				}


			}else{
				if(!isEmpty(p.getCoordinate().getMove(Direction.NE)) && getPiece(p.getCoordinate().getMove(Direction.NE)).isEnemy(p)){
					list.add(p.getCoordinate().getMove(Direction.NE));
				}
				if(!isEmpty(p.getCoordinate().getMove(Direction.NV)) && getPiece(p.getCoordinate().getMove(Direction.NV)).isEnemy(p)){
					list.add(p.getCoordinate().getMove(Direction.NV));
				}
			}



			for(int i = 0; i < p.getMoveMax(); i++){
				if(isEmpty(p.getCoordinate().getMove(p.getDirections()[0], i+1))){
					list.add(p.getCoordinate().getMove(p.getDirections()[0], i+1));
				}else{
					break;
				}
			}	




			//Horse specific rules
		}else if(p instanceof Horse) {

			for(int y = 0; y < 8; y ++){
				for(int x = 0; x < 8; x++){
					Coordinate coord = new Coordinate(x,y);
					if((p.getCoordinate().y - 2 == coord.y && p.getCoordinate().x + 1 == coord.x) || (p.getCoordinate().y - 2 == coord.y && p.getCoordinate().x - 1 == coord.x) || (p.getCoordinate().y + 2 == coord.y && p.getCoordinate().x - 1 == coord.x) || (p.getCoordinate().y + 2 == coord.y && p.getCoordinate().x + 1 == coord.x) || (p.getCoordinate().y + 1 == coord.y && p.getCoordinate().x - 2 == coord.x) || (p.getCoordinate().y - 1 == coord.y && p.getCoordinate().x - 2 == coord.x) || (p.getCoordinate().y - 1 == coord.y && p.getCoordinate().x + 2 == coord.x) || (p.getCoordinate().y + 1 == coord.y && p.getCoordinate().x + 2 == coord.x)){
						if(isEmpty(coord) || p.isEnemy(getPiece(coord)))
							list.add(new Coordinate(x,y));
					}
				}
			}

			//Every other kind of piece
		}else {

			for(Direction d : p.getDirections()){
				for(int i = 0; i < p.getMoveMax(); i++){
					Coordinate movedCoordinate = p.getCoordinate().getMove(d, i+1);
					if(movedCoordinate.x < 0 || movedCoordinate.y < 0 || movedCoordinate.x > 7 || movedCoordinate.y > 7) {
						break;
					}


					if(isEmpty(movedCoordinate)){
						list.add(movedCoordinate);
					}else if(getPiece(movedCoordinate).isEnemy(p)){
						list.add(movedCoordinate);
						break;	
					}
					else{
						break;
					}
				}	

			}

		}
		return list;
	}

	
	/**
	 * Checks if a {@link Piece piece} can move to a specific {@link Coordinate coordinate}
	 * 
	 * @param coord		Coordinate to move to
	 * @param p			Piece to move
	 * @return			If the piece can move to that coordinate or not
	 * @see				ChessLogic#isMovableAll(Piece)
	 */
	public boolean isMovable(Coordinate coord, Piece p){
		ArrayList<Coordinate> possibleMoves = isMovableAll(p);
		for(Coordinate coordd : possibleMoves)
			if(coordd.equals(coord))
				return true;
		return false;
	}

	
	/**
	 * Adds a draw to a list
	 * 
	 * @param pieceCoordinate	Where the piece was
	 * @param moveCoordinate		Where the piece moved
	 * @see						ChessLogic#gameHistory
	 */
	public void addDraw(Coordinate pieceCoordinate, Coordinate moveCoordinate) {
		gameHistory.add(new Pair<Coordinate, Coordinate>(pieceCoordinate, moveCoordinate));
	}


	/**
	 * Converts {@link ChessLogic#gameHistory the game history} to json and saves it in a file
	 * @see		SavedGames#addGame(ArrayList)
	 */
	public void saveGame() {
		SavedGames games = new SavedGames();
		File file = new File("games.json");


		/*
		 * Gets previous games if file exists, otherwise uses new list
		 */
		if(file.exists()) {
			byte[] encoded = null;
			try {
				encoded = Files.readAllBytes(Paths.get("games.json"));
			} catch (IOException e) {}
			games = gson.fromJson(new String(encoded), SavedGames.class);
		}

		games.addGame(gameHistory);


		if (file.exists()){
			file.delete();
		}  

		/*
		 * Writes the file
		 */
		FileWriter fw = null;
		try {
			fw = new FileWriter(file,true);
		} catch (IOException e) {}
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println(gson.toJson(games));

		pw.flush();
		pw.close();

	}

}
