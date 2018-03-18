package pieces;
import game.Coordinate;
import game.Direction;
import game.Piece;
import game.PlayerColor;


public class Queen extends Piece {

	public Queen(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public Queen(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}


	@Override
	public String toString() {
		return "queen";
	}
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SV, Direction.V, Direction.NV};
		moveMax = 8;
	}

}
