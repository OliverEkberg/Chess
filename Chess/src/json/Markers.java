package json;

import java.util.ArrayList;

import game.Coordinate;

public class Markers {
	private ArrayList<Coordinate> freeMoves = new ArrayList<>();
	private ArrayList<Coordinate> enemyMoves = new ArrayList<>();
	private Coordinate selected;
	
	public Coordinate getSelected() {
		return selected;
	}
	public void setSelected(Coordinate selected) {
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
