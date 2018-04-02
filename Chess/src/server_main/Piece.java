package server_main;

import shared.Coordinate;
import shared.Direction;
import shared.PlayerColor;

/**
 * Abstract model of a piece
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public abstract class Piece {


	private boolean active = true;
	protected Coordinate coord;
	protected PlayerColor color;
	protected int moveMax; //Max moving length
	protected Direction[] directions; //Move directions

	/**
	 * @param coord		Coordiante of the piece
	 * @param color		Color of the piece
	 */
	public Piece(Coordinate coord, PlayerColor color){
		this.coord = coord;
		this.color = color;
	}


	/**
	 * @return 	Piece active or not
	 */
	public boolean isActive() {
		return active;
	}


	/**
	 * @param active		The new value of active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}


	/**
	 * @param coord		The new coordinate for the piece
	 */
	public void setCoordinate(Coordinate coord){
		this.coord = coord;
	}


	/**
	 * @return	The coordinate of the piece
	 */
	public Coordinate getCoordinate(){
		return coord;
	}


	/**
	 * @return	The {@link PlayerColor color} of the piece
	 */
	public PlayerColor getColor() {
		return color;
	}


	/**
	 * @return	The maximum moving length for the piece
	 */
	public int getMoveMax() {
		return moveMax;
	}


	/**
	 * @param moveMax	The new value of {@link #moveMax}
	 */
	public void setMoveMax(int moveMax) {
		this.moveMax = moveMax;
	}


	/**
	 * @return	The directions the piece can walk
	 */
	public Direction[] getDirections() {
		return directions;
	}


	/**
	 * Checks if p is enemy of this
	 * 
	 * @param p		Piece to check if enemy
	 * @return		Piece is enemy or not
	 */
	public boolean isEnemy(Piece p){
		return !this.color.equals(p.color);
	}
	
	
	/**
	 * Checks if this is enemy to a {@link PlayerColor color}
	 * 
	 * @param c		Possible enemy color to check
	 * @return		If enemy or not
	 */
	public boolean isEnemy(PlayerColor c){
		return !this.color.equals(c);
	}


	/**
	 * @return	If the piece is of the type king or not
	 */
	public boolean isKing() {
		return false;
	}
	
	public abstract String toString();

}
