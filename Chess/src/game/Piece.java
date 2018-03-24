package game;

import java.util.ArrayList;

import pieces.Horse;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;
import pieces.Runner;
import pieces.Tower;


public abstract class Piece {

	public static ArrayList<Piece> pieces = new ArrayList<>(); //Holds all pieces

	protected Coordinate coord;
	public PlayerColor color;
	protected int moveMax; //Max moving length
	protected Direction[] directions; //Move directions


	public Piece(Coordinate coord, PlayerColor color){
		this.coord = coord;
		this.color = color;
		pieces.add(this);

	}

	/**
	 * Finds the requested piece and returns it
	 * @param coord
	 * @return
	 */
	public static Piece getPiece(Coordinate coord){
		for(Piece piece : pieces) {
			if(piece.getCoordinate().equals(coord)) {
				return piece;
			}
		}
		return null;
	}

	/**
	 * Returns all the possible moves for selected piece
	 * @return
	 */
	public ArrayList<Coordinate> isMovableAll(){
		ArrayList<Coordinate> list = new ArrayList<>();

		for(Direction d : directions){
			for(int i = 0; i < moveMax; i++){
				Coordinate movedCoordinate = this.coord.getMove(d, i+1);

				if(Piece.isEmpty(movedCoordinate)){
					list.add(movedCoordinate);
				}else if(getPiece(movedCoordinate).isEnemy(this)){
					list.add(movedCoordinate);
					break;	
				}
				else{
					break;
				}
			}	

		}
		return list;
	}

	/**
	 * Removes piece by coordinate
	 * @param coord
	 */
	public void removePiece(Coordinate coord){
		pieces.remove(getPiece(coord));
	}
	
	/**
	 * Checks if piece can move to coordinate
	 * @param coord
	 * @return
	 */
	public boolean isMovable(Coordinate coord){
		ArrayList<Coordinate> possibleMoves = isMovableAll();
		for(Coordinate coordd : possibleMoves)
			if(coordd.equals(coord))
				return true;
		return false;
	}

	/**
	 * Moves the piece
	 */
	public void move(Coordinate coord){
		removePiece(coord);
		this.coord = coord;
	}

	
	public Coordinate getCoordinate(){
		return coord;
	}
	
	/*
	 * Checks if enemy
	 */
	public boolean isEnemy(Piece p){
		return !this.color.equals(p.color);
	}
	public boolean isEnemy(PlayerColor c){
		return !this.color.equals(c);
	}
	public boolean isEnemy(Coordinate coord){
		return isEnemy(getPiece(coord));
	}

	/**
	 * Checks if coordinate is empty
	 * @param coord
	 * @return
	 */
	public static boolean isEmpty(Coordinate coord){
		
		
		for(Piece piece : pieces)
			if(piece.getCoordinate().equals(coord))
				return false;
		return true;	
	}


	public abstract String toString();

	/**
	 * Checks if piece at c is owned by player
	 * @param c
	 * @param playerTurn
	 * @return
	 */
	public static boolean isOwnedBy(Coordinate c, PlayerColor player){
		if(c == null || isEmpty(c)) {
			return false;
		}
		return getPiece(c).color.equals(player);
	}

	/**
	 * Creates all the pieces
	 */
	public static void createPieces(){

		//White player
		new Tower(0,0,PlayerColor.WHITE);
		new Tower(7,0,PlayerColor.WHITE);
		new Horse(1,0,PlayerColor.WHITE);
		new Horse(6,0,PlayerColor.WHITE);
		new Runner(2,0,PlayerColor.WHITE);
		new Runner(5,0,PlayerColor.WHITE);
		new King(4,0,PlayerColor.WHITE);
		new Queen(3,0,PlayerColor.WHITE);

		for(int x = 0; x < 8; x++) {
			new Pawn(x,1,PlayerColor.WHITE);
		}

		//Black player
		new Tower(0,7,PlayerColor.BLACK);
		new Tower(7,7,PlayerColor.BLACK);
		new Horse(1,7,PlayerColor.BLACK);
		new Horse(6,7,PlayerColor.BLACK);
		new Runner(2,7,PlayerColor.BLACK);
		new Runner(5,7,PlayerColor.BLACK);
		new King(3,7,PlayerColor.BLACK);
		new Queen(4,7,PlayerColor.BLACK);

		for(int x = 0; x < 8; x++) {
			new Pawn(x,6,PlayerColor.BLACK);
		}

	}




}
