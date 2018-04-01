package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public class Pawn extends Piece {

	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param color
	 */
	public Pawn(int x, int y, PlayerColor color){
		super(new Coordinate(x,y), color);
		setDirections();
	}



	@Override
	public void setCoordinate(Coordinate coord){
		this.coord = coord;
		moveMax = 1; //Pawn can only move one step after first move
	}

	@Override
	public String toString() {
		return "pawn";
	}
	
	/**
	 * Sets the possible move directions
	 */
	private void setDirections(){
		if(this.color.equals(PlayerColor.WHITE))
			directions = new Direction[]{Direction.S};
		else
			directions = new Direction[]{Direction.N};
		moveMax = 2;
	}


}
