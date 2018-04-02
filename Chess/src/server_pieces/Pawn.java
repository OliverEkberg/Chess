package server_pieces;
import server_main.Piece;
import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * Pawn piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Pawn extends Piece {

	

	/**
	 * Sets the {@link Direction directions}
	 * 
	 * @param x		X-position
	 * @param y		Y-position
	 * @param color	Color of the piece
	 * @see 			#setDirections()
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
	 * Set the possible move {@link Direction directions}
	 */
	private void setDirections(){
		if(this.color.equals(PlayerColor.WHITE))
			directions = new Direction[]{Direction.S};
		else
			directions = new Direction[]{Direction.N};
		moveMax = 2;
	}


}
