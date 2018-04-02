package server_pieces;

import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * Tower piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Tower extends Piece {

	/**
	 * Sets the {@link Direction directions}
	 * 
	 * @param x		X-position
	 * @param y		Y-position
	 * @param color	Color of the piece
	 * @see 			#setDirections()
	 */
	public Tower(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}
	
	public String toString(){
		return "tower";
	}

	/**
	 * Set the possible move {@link Direction directions}
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.E, Direction.S, Direction.V};
		moveMax = 8;
	}

}
