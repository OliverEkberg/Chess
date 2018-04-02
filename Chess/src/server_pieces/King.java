package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * King piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class King extends Piece {


	/**
	 * Sets the {@link Direction directions}
	 * 
	 * @param x		X-position
	 * @param y		Y-position
	 * @param color	Color of the piece
	 * @see 			#setDirections()
	 */
	public King(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}

	
	@Override
	public String toString() {
		return "king";
	}
	
	/**
	 * Set the possible move {@link Direction directions}
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SV, Direction.V, Direction.NV};
		moveMax = 1;
	}
	
	
	@Override
	public boolean isKing() {
		return true;
	}

}
