package game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import images.ResourceLoader;


public abstract class Piece {

	public static ArrayList<Piece> pieces = new ArrayList<>(); //Holds all the pieces
	protected Coordinate coord;
	public PlayerColor color;
	protected int moveMax;
	protected Direction[] directions;
	public static HashMap<String, Image> images = new HashMap<>();

	/*
	 * Constructor
	 */
	public Piece(Coordinate coord, PlayerColor color){
		this.coord = coord;
		this.color = color;
		
	}
	public static void loadImages() {
		images.put("BLACK_horse", ResourceLoader.getImage("BLACK_horse.png"));
		images.put("BLACK_king", ResourceLoader.getImage("BLACK_king.png"));
		images.put("BLACK_pawn", ResourceLoader.getImage("BLACK_pawn.png"));
		images.put("BLACK_queen", ResourceLoader.getImage("BLACK_queen.png"));
		images.put("BLACK_runner", ResourceLoader.getImage("BLACK_runner.png"));
		images.put("BLACK_tower", ResourceLoader.getImage("BLACK_tower.png"));
		
		images.put("WHITE_horse", ResourceLoader.getImage("WHITE_horse.png"));
		images.put("WHITE_king", ResourceLoader.getImage("WHITE_king.png"));
		images.put("WHITE_pawn", ResourceLoader.getImage("WHITE_pawn.png"));
		images.put("WHITE_queen", ResourceLoader.getImage("WHITE_queen.png"));
		images.put("WHITE_runner", ResourceLoader.getImage("WHITE_runner.png"));
		images.put("WHITE_tower", ResourceLoader.getImage("WHITE_tower.png"));
	}


	public static Piece getPiece(Coordinate coord){
		for(Piece piece : pieces)
			if(piece.getCoordinate().equals(coord))
				return piece;
			
		return null;
	}

	/*
	 * Check all the positions on the board for availability
	 */
	public ArrayList<Coordinate> isMovableAll(){
		ArrayList<Coordinate> list = new ArrayList<>();

		for(Direction d : directions){
			for(int i = 0; i < moveMax; i++){
				Coordinate movedCoordinate = this.coord.getMove(d, i+1);
				
				if(Piece.isEmpty(movedCoordinate)){
					list.add(movedCoordinate);
				}else if(getPiece(movedCoordinate).isEnemy(this)){
					list.add(movedCoordinate);
					break;	
				}
				else{
					break;
				}
			}	

		}
		return list;
	}

	public void removePiece(Coordinate coord){
		pieces.remove(getPiece(coord));
	}

	public boolean isMovable(Coordinate coord){
		ArrayList<Coordinate> possibleMoves = isMovableAll();
		for(Coordinate coordd : possibleMoves)
			if(coordd.equals(coord))
				return true;
		return false;
	}

	/*
	 * Moves the piece
	 */
	public void move(Coordinate coord){
		removePiece(coord);
		this.coord = coord;
	}

	public Coordinate getCoordinate(){
		return coord;
	}

	public boolean isEnemy(Piece p){
		return !this.color.equals(p.color);
	}
	public boolean isEnemy(PlayerColor c){
		return !this.color.equals(c);
	}
	public boolean isEnemy(Coordinate coord){
		return isEnemy(getPiece(coord));
	}



	public static boolean isEmpty(Coordinate coord){
		for(Piece piece : pieces)
			if(piece.getCoordinate().equals(coord))
				return false;
		return true;	
	}

	private void draw(Graphics g, int scale){
//		Image image = new ImageIcon("images/"+color+"_"+this+".png").getImage();
		Image image = images.get(color + "_" + this);
//		Image image = new ImageIcon(Piece.class.getResource("imgs/"+color+"_"+this+".png")).getImage();

		
		g.drawImage(image, coord.x*scale, coord.y*scale, null);
	}

	public static void drawAll(Graphics g){
		for(Piece piece : pieces)
			piece.draw(g, 100);
	}
	
	
	public abstract String toString();


	public static boolean isOwnedBy(Coordinate c, PlayerColor playerTurn) {
		return getPiece(c).color.equals(playerTurn);
	}

}
