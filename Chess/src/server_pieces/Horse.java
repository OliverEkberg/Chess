package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.PlayerColor;


public class Horse extends Piece {

	public Horse(Coordinate coord, PlayerColor color) {
		super(coord, color);
	}
	public Horse(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}


	@Override
	public String toString() {
		return "horse";
	}


}
