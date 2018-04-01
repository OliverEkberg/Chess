package server_pieces;


import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Tower extends Piece {

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
	 */
	public Tower(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}
	
	public String toString(){
		return "tower";
	}

	/**
	 * Sets possible moving directions
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.E, Direction.S, Direction.V};
		moveMax = 8;
	}

}
