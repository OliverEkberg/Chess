package server_main;

import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

public abstract class Piece {


	private boolean active = true;
	protected Coordinate coord;
	protected PlayerColor color;
	protected int moveMax; //Max moving length
	protected Direction[] directions; //Move directions

	/**
	 * Constructor
	 * @param coord
	 * @param color
	 */
	public Piece(Coordinate coord, PlayerColor color){
		this.coord = coord;
		this.color = color;
	}
	

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setCoordinate(Coordinate coord){
		this.coord = coord;
	}
	public Coordinate getCoordinate(){
		return coord;
	}
	public PlayerColor getColor() {
		return color;
	}
	public void setColor(PlayerColor color) {
		this.color = color;
	}
	public int getMoveMax() {
		return moveMax;
	}
	public void setMoveMax(int moveMax) {
		this.moveMax = moveMax;
	}
	public Direction[] getDirections() {
		return directions;
	}
	public void setDirections(Direction[] directions) {
		this.directions = directions;
	}


	/**
	 * Checks if enemy
	 */
	public boolean isEnemy(Piece p){
		return !this.color.equals(p.color);
	}
	public boolean isEnemy(PlayerColor c){
		return !this.color.equals(c);
	}


	
	public abstract String toString();


	public boolean isKing() {
		return false;
	}



}
