package json;

import game.Coordinate;
import game.PlayerColor;

public class DrawPiece{
	public Coordinate c;
	public PlayerColor p;
	public String type;

	public DrawPiece(Coordinate c, PlayerColor p, String type) {
		this.c = c;
		this.p = p;
		this.type = type;
	}
}
