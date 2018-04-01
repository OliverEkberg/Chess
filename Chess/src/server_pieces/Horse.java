package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.PlayerColor;


public class Horse extends Piece {

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
	 */
	public Horse(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
	}


	@Override
	public String toString() {
		return "horse";
	}


}
