package game;
import java.awt.Color;

public class Tile{
	private Coordinate coord;
 	private Color color;

 	/*
 	 * Constructor
 	 */
 	public Tile(Coordinate coord, Color color){
 		this.coord = coord;
      	this.color = color;
 	}
 	
 	public Coordinate getCoordinate(){
 		return coord;
 	}
 	
	public Color getColor(){
		return color;
	}
 	
}