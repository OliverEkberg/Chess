package pieces;
import java.util.ArrayList;

import game.Coordinate;
import game.Direction;
import game.Piece;
import game.PlayerColor;

public class Pawn extends Piece {

	/*
	 * Constructors
	 */
	public Pawn(Coordinate coord, PlayerColor color) {
		super(coord, color);
		setDirections();
	}
	public Pawn(int x, int y, PlayerColor color){
		this(new Coordinate(x,y), color);
	}



	@Override
	public void move(Coordinate coord){
		removePiece(coord);
		this.coord = coord;
		moveMax = 1;
		
	}

	@Override
	public String toString() {
		return "pawn";
	}
	private void setDirections(){
		if(color.equals(PlayerColor.WHITE))
			directions = new Direction[]{Direction.S};
		else
			directions = new Direction[]{Direction.N};
		moveMax = 2;
	}
	@Override
	public ArrayList<Coordinate> isMovableAll(){
		ArrayList<Coordinate> list = new ArrayList<>();
		if(color.equals(PlayerColor.WHITE)){
			if(!Piece.isEmpty(this.coord.getMove(Direction.SE)) && Piece.getPiece(this.coord.getMove(Direction.SE)).isEnemy(this)){
				list.add(this.coord.getMove(Direction.SE));
			}
			if(!Piece.isEmpty(this.coord.getMove(Direction.SV)) && Piece.getPiece(this.coord.getMove(Direction.SV)).isEnemy(this)){
				list.add(this.coord.getMove(Direction.SV));
			}
			
			
		}else{
			if(!Piece.isEmpty(this.coord.getMove(Direction.NE)) && Piece.getPiece(this.coord.getMove(Direction.NE)).isEnemy(this)){
				list.add(this.coord.getMove(Direction.NE));
			}
			if(!Piece.isEmpty(this.coord.getMove(Direction.NV)) && Piece.getPiece(this.coord.getMove(Direction.NV)).isEnemy(this)){
				list.add(this.coord.getMove(Direction.NV));
			}
		}
		
		
		
		for(int i = 0; i < moveMax; i++){
			if(Piece.isEmpty(this.coord.getMove(this.directions[0], i+1))){
				list.add(this.coord.getMove(this.directions[0], i+1));

			}else{
				break;
			}
		}	


		return list;
	}


}
