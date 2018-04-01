package server_pieces;


import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Tower extends Piece {

	public Tower(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public Tower(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}

	
	public String toString(){
		return "tower";
	}

	private void setDirections(){
		directions = new Direction[]{Direction.N, Direction.E, Direction.S, Direction.V};
		moveMax = 8;
	}

}
