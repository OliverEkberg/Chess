package shared;

import java.util.ArrayList;

public class Markers {
	
	//Contains all free moves
	private ArrayList<Coordinate> freeMoves = new ArrayList<>();
	//Contains all enemy moves
	private ArrayList<Coordinate> enemyMoves = new ArrayList<>();
	
	private Coordinate selected;
	
	
	/*
	 * Setters and getters
	 */
	public Coordinate getSelectedCoordinate() {
		return selected;
	}
	public void setSelectedCoordinate(Coordinate selected) {
		this.selected = selected;
	}
	public ArrayList<Coordinate> getFreeMoves() {
		return freeMoves;
	}
	public void setFreeMove(Coordinate coord){
		freeMoves.add(coord);
	}
	public ArrayList<Coordinate> getEnemyMoves() {
		return enemyMoves;
	}
	public void setEnemyMove(Coordinate coord){
		enemyMoves.add(coord);
	}
	
	
	
	
	
	
	
	
}
