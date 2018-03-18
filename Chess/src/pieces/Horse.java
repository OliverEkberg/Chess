package pieces;
import java.util.ArrayList;

import game.Coordinate;
import game.Piece;
import game.PlayerColor;


public class Horse extends Piece {

	public Horse(Coordinate coord, PlayerColor color) {
		super(coord, color);
	}
	public Horse(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}


	@Override
	public String toString() {
		return "horse";
	}
	
	@Override
	public ArrayList<Coordinate> isMovableAll(){
		ArrayList<Coordinate> list = new ArrayList<>();
		for(int y = 0; y < 8; y ++){
			for(int x = 0; x < 8; x++){
				Coordinate coord = new Coordinate(x,y);
				if((this.coord.y - 2 == coord.y && this.coord.x + 1 == coord.x) || (this.coord.y - 2 == coord.y && this.coord.x - 1 == coord.x) || (this.coord.y + 2 == coord.y && this.coord.x - 1 == coord.x) || (this.coord.y + 2 == coord.y && this.coord.x + 1 == coord.x) || (this.coord.y + 1 == coord.y && this.coord.x - 2 == coord.x) || (this.coord.y - 1 == coord.y && this.coord.x - 2 == coord.x) || (this.coord.y - 1 == coord.y && this.coord.x + 2 == coord.x) || (this.coord.y + 1 == coord.y && this.coord.x + 2 == coord.x)){
					if(Piece.isEmpty(coord) || this.isEnemy(coord))
						list.add(new Coordinate(x,y));
				}
			}
		}

		return list;
	}

}
