package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Pawn extends Piece {

	/*
	 * Constructors
	 */
	public Pawn(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public Pawn(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}



	@Override
	public void setCoordinate(Coordinate coord){
		this.coord = coord;
		moveMax = 1;
		
	}

	@Override
	public String toString() {
		return "pawn";
	}
	private void setDirections(){
		if(this.color.equals(PlayerColor.WHITE))
			directions = new Direction[]{Direction.S};
		else
			directions = new Direction[]{Direction.N};
		moveMax = 2;
	}


}
