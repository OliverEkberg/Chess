package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * Runner piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Runner extends Piece {

	/**
	 * Sets the {@link Direction directions}
	 * 
	 * @param x		X-position
	 * @param y		Y-position
	 * @param color	Color of the piece
	 * @see 			#setDirections()
	 */
	public Runner(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}

	@Override
	public String toString() {
		return "runner";
	}
	
	/**
	 * Set the possible move {@link Direction directions}
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.NE, Direction.SE, Direction.SV, Direction.NV};
		moveMax = 8;
	}

}
