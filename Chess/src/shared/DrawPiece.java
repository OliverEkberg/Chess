package shared;

/**
 * Helper class to keep track of information of a piece.
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class DrawPiece{
	private Coordinate coord;
	private PlayerColor playerColor;
	private String type;

	
	/**
	 * @param coord			Position of the piece
	 * @param playerColor	Color of the piece
	 * @param type			The type of the piece
	 */
	public DrawPiece(Coordinate coord, PlayerColor playerColor, String type) {
		this.coord = coord;
		this.playerColor = playerColor;
		this.type = type;
	}


	/**
	 * @return	The coordinate of the piece
	 */
	public Coordinate getCoord() {
		return coord;
	}
	

	/**	
	 * @return	The color of the piece
	 */
	public PlayerColor getPlayerColor() {
		return playerColor;
	}


	/**
	 * @return	The type of the piece
	 */
	public String getType() {
		return type;
	}

	
}
