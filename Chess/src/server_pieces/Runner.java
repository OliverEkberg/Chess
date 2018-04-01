package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Runner extends Piece {

	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
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
	 * Sets possible moves
	 */
	private void setDirections(){
		directions = new Direction[]{Direction.NE, Direction.SE, Direction.SV, Direction.NV};
		moveMax = 8;
	}

}
