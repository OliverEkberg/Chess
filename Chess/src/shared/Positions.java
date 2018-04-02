package shared;

import java.util.ArrayList;

/**
 * Helper class. Contains all information about pieces. Gets sent by server to client for drawing.
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Positions {
	
	//Contains all piece positions
	private ArrayList<DrawPiece> positionList = new ArrayList<>();
	

	/**
	 * @return	A list with all the pieces and their data
	 */
	public ArrayList<DrawPiece> getPositionList(){
		return positionList;
	}
	
	
	/**
	 * @param drawPiece		Piece-data to add to the list
	 */
	public void addPosition(DrawPiece drawPiece) {
		positionList.add(drawPiece);
	}
	
}
