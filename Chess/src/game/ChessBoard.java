package game;
import java.awt.Color;
import java.awt.Graphics;

public class ChessBoard {
	private Tile[][] tileList = new Tile[8][8]; 
	private int tileWidth; 

	/**
	 * Constructor
	 * @param tileWidth 		The width of the tiles
	 */
	public ChessBoard(int tileWidth){
		this.tileWidth = tileWidth;
		createTiles();
	}


	/*
	 * Creates all the tiles and adds them to the list
	 */
	private void createTiles(){
		Color c = Color.DARK_GRAY;
		for(int y = 0; y < 8; y++){
			c = (c == Color.DARK_GRAY) ? Color.GRAY: Color.DARK_GRAY;
			for(int x = 0; x < 8; x++){
				c = (c == Color.DARK_GRAY) ? Color.GRAY: Color.DARK_GRAY;
				tileList[x][y] = new Tile(new Coordinate(x,y), c);
			}
		}
	}


	/*
	 * Draws all the tiles on the graphics object
	 */
	public void drawTiles(Graphics g){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){

				Tile tile = tileList[x][y];
				Coordinate temp = tile.getCoordinate();

				g.setColor(tile.getColor());
				g.fillRect(temp.x*tileWidth, temp.y*tileWidth, tileWidth, tileWidth);

			}

		}
	}




}