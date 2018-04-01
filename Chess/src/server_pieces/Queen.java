package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;


public class Queen extends Piece {

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
	 */
	public Queen(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}


	@Override
	public String toString() {
		return "queen";
	}
	
	/**
	 * Sets possible moving directions
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SV, Direction.V, Direction.NV};
		moveMax = 8;
	}

}
