package shared;

/**
 * Helper class to keep track of location in game
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Coordinate {

	public int x;
	public int y;

	/**
	 * @param x	X-Position
	 * @param y	Y-Position
	 */
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	

	/**
	 * Compares coordinates
	 * 
	 * @param coord  Coordinate to compare with this
	 * @return		 If it is the same coordinate or not
	 */
	public boolean equals(Coordinate coord){
		return (coord == null) ? false : this.x == coord.x && this.y == coord.y;
	}


	/**
	 * Gets a move
	 * 
	 * @param d			Direction of the move
	 * @param moveLen	Length of the move
	 * @return			Coordinate of the move
	 * @see 				Direction
	 */
	public Coordinate getMove(Direction d, int moveLen){

		switch(d) {

		case N:
			return new Coordinate(this.x, this.y-moveLen);
		case NE:
			return new Coordinate(this.x+moveLen, this.y-moveLen);
		case E:
			return new Coordinate(this.x+moveLen, this.y);
		case SE:
			return new Coordinate(this.x+moveLen, this.y+moveLen);
		case S:
			return new Coordinate(this.x, this.y+moveLen);
		case SV:
			return new Coordinate(this.x-moveLen, this.y+moveLen);
		case V:
			return new Coordinate(this.x-moveLen, this.y);
		case NV:
			return new Coordinate(this.x-moveLen, this.y-moveLen);	
		default:
			return this;

		}

	}

	
	/**
	 * Gets a move with length 1
	 * 
	 * @param d	Direction to move
	 * @return	Coordinate of move
	 * @see		Coordinate#getMove(Direction, int)
	 */
	public Coordinate getMove(Direction d){
		return getMove(d, 1);
	}

}
