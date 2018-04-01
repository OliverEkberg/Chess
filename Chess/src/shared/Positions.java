package shared;

import java.util.ArrayList;


public class Positions {
	private ArrayList<DrawPiece> positionList = new ArrayList<>();
	
	public ArrayList<DrawPiece> getPositionList(){
		return positionList;
	}
	
	public void addPosition(DrawPiece drawPiece) {
		positionList.add(drawPiece);
	}
	
}
