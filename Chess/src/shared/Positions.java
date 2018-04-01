package shared;

import java.util.ArrayList;


public class Positions {
	
	//Contains all piece positions
	private ArrayList<DrawPiece> positionList = new ArrayList<>();
	
	/*
	 * Setters and getters
	 */
	public ArrayList<DrawPiece> getPositionList(){
		return positionList;
	}
	public void addPosition(DrawPiece drawPiece) {
		positionList.add(drawPiece);
	}
	
}
