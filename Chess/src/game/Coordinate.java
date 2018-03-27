package game;

public class Coordinate {
	
	public int x;
	public int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Compares coordinates
	 * @param coord
	 * @return
	 */
	public boolean equals(Coordinate coord){
		return this.x == coord.x && this.y == coord.y;
	}
	
	public String toString() {
		return "["+x+","+y+"]";
	}
	
	
	/**
	 * Gets move in direction d with length moveLen
	 * @param d
	 * @param moveLen
	 * @return
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
	 * Gets move in direction d with length 1
	 * @param d
	 * @return
	 */
	public Coordinate getMove(Direction d){
		return getMove(d, 1);
	}

}
