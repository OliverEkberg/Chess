package shared;

import java.util.ArrayList;

/**
 * Helper class to keep track of markers sent from the server
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Markers {
	
	//Contains all free moves
	private ArrayList<Coordinate> freeMoves = new ArrayList<>();
	//Contains all enemy moves
	private ArrayList<Coordinate> enemyMoves = new ArrayList<>();
	//Contains selected coordinate
	private Coordinate selected;
	

	/**
	 * @return	Selected {@link Coordinate coordinate}
	 */
	public Coordinate getSelectedCoordinate() {
		return selected;
	}
	
	
	/**
	 * @param selected	New selected coordinate
	 */
	public void setSelectedCoordinate(Coordinate selected) {
		this.selected = selected;
	}
	
	/**
	 * @return	List with all the free moves
	 */
	public ArrayList<Coordinate> getFreeMoves() {
		return freeMoves;
	}
	
	
	/**
	 * @param coord		New free move to add
	 */
	public void addFreeMove(Coordinate coord){
		freeMoves.add(coord);
	}
	
	
	/**
	 * @return	List with all possible moves that contains enemies
	 */
	public ArrayList<Coordinate> getEnemyMoves() {
		return enemyMoves;
	}
	
	
	/**
	 * @param coord		New enemy move to add
	 */
	public void addEnemyMove(Coordinate coord){
		enemyMoves.add(coord);
	}
	
}
