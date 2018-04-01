package server_main;

import java.util.ArrayList;

import server_pieces.Horse;
import server_pieces.King;
import server_pieces.Pawn;
import server_pieces.Queen;
import server_pieces.Runner;
import server_pieces.Tower;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class ChessLogic {

	private ArrayList<Piece> pieces = new ArrayList<>();
	private boolean gameRunning = false;
	private PlayerColor playerTurn = PlayerColor.WHITE;

	/**
	 * Constructor
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

	public boolean isGameRunning() {
		return gameRunning;
	}
	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}
	public ArrayList<Piece> getPieces() {
		return pieces;
	}


	/**
	 * Finds the requested piece and returns it
	 * @param coord
	 * @return
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
	 * Removes piece by coordinate
	 * @param coord
	 */
	public void removePiece(Coordinate coord){
		pieces.remove(getPiece(coord));
	}



	/**
	 * Checks if piece at c is owned by player
	 * @param c
	 * @param playerTurn
	 * @return
	 */
	public  boolean isOwnedBy(Coordinate c, PlayerColor player){
		if(c == null || isEmpty(c)) {
			return false;
		}
		return getPiece(c).getColor().equals(player);
	}

	/**
	 * Checks if coordinate is empty
	 * @param coord
	 * @return
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
	 * Makes a move
	 * @param p
	 * @param c
	 */
	public void move(Piece p, Coordinate c) {
		removePiece(c);
		p.setCoordinate(c);
	}

	/**
	 * Checks if the player is in chess
	 * @param playerColor
	 * @return
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
	 * @param to
	 * @return
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
	 * @param playerTurn
	 * @return
	 */
	public  boolean canMakeMove(PlayerColor playerTurn) {

		for (Piece piece : pieces) {
			if(piece.getColor() == playerTurn) {
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
	 * Returns all the possible moves
	 * @return
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
	 * Checks if piece can move to coordinate
	 * @param coord
	 * @return
	 */
	public boolean isMovable(Coordinate coord, Piece p){
		ArrayList<Coordinate> possibleMoves = isMovableAll(p);
		for(Coordinate coordd : possibleMoves)
			if(coordd.equals(coord))
				return true;
		return false;
	}
	
	/**
	 * Switches playerTurn
	 * @param playerColor
	 */
	public void switchTurn(PlayerColor playerColor) {
		playerTurn = (playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}
	public PlayerColor getPlayerTurn() {
		return playerTurn;
	}

}
