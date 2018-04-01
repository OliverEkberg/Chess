package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Runner extends Piece {

	public Runner(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public Runner(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}

	@Override
	public String toString() {
		return "runner";
	}
	private void setDirections(){
		directions = new Direction[]{Direction.NE, Direction.SE, Direction.SV, Direction.NV};
		moveMax = 8;
	}

}
