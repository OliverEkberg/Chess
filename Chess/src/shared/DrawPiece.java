package shared;

public class DrawPiece{
	private Coordinate coord;
	private PlayerColor playerColor;
	private String type;

	/**
	 * Constructor
	 * @param coord
	 * @param playerColor
	 * @param type
	 */
	public DrawPiece(Coordinate coord, PlayerColor playerColor, String type) {
		this.coord = coord;
		this.playerColor = playerColor;
		this.type = type;
	}

	/*
	 * Setters and getters
	 */
	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public PlayerColor getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(PlayerColor playerColor) {
		this.playerColor = playerColor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
