package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.PlayerColor;

/**
 * Horse piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Horse extends Piece {

	/**
	 * @param x		X-position
	 * @param y		Y-position
	 * @param color	Color of the piece
	 */
	public Horse(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
	}

	
	@Override
	public String toString() {
		return "horse";
	}


}
