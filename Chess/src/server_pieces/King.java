package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class King extends Piece {

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
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
	 * Set the possible move directions
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
