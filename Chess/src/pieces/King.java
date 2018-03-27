package pieces;
import game.Coordinate;
import game.Direction;
import game.Piece;
import game.PlayerColor;

public class King extends Piece {

	public King(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public King(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}

	
	@Override
	public String toString() {
		return "king";
	}
	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.NE, Direction.E, Direction.SE, Direction.S, Direction.SV, Direction.V, Direction.NV};
		moveMax = 1;
	}
	
	@Override
	public boolean isKing() {
		return true;
	}

}
