package game;

import java.util.ArrayList;

import pieces.Horse;
import pieces.King;
import pieces.Pawn;
import pieces.Queen;
import pieces.Runner;
import pieces.Tower;



public class Json {
	public  Player whitePlayer = new Player(PlayerColor.WHITE);
	public Player blackPlayer = new Player(PlayerColor.BLACK);
	public PlayerColor playerTurn = PlayerColor.WHITE;

	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Horse> horses = new ArrayList<>();
	private ArrayList<Runner> runners = new ArrayList<>();
	private ArrayList<King> kings = new ArrayList<>();
	private ArrayList<Queen> queens = new ArrayList<>();
	private ArrayList<Pawn> pawns = new ArrayList<>();

	public Coordinate selectedCoord = null;
	public Coordinate clickedCoordinate = null;




	public void afterParsing() {
		Piece.pieces = new ArrayList<Piece>();

		for (Tower tower : towers) {
			Piece.pieces.add(tower);
		}
		for (Horse horse : horses) {
			Piece.pieces.add(horse);
		}
		for (Runner runner : runners) {
			Piece.pieces.add(runner);
		}
		for (King king : kings) {
			Piece.pieces.add(king);
		}
		for (Queen queen : queens) {
			Piece.pieces.add(queen);
		}
		for (Pawn pawn : pawns) {
			Piece.pieces.add(pawn);
		}

	}

	public void beforeParsing() {
		
		towers = new ArrayList<>();
		horses = new ArrayList<>();
		runners = new ArrayList<>();
		kings = new ArrayList<>();
		queens = new ArrayList<>();
		pawns = new ArrayList<>();

		for (Piece p : Piece.pieces) {
			if(p instanceof Tower) {
				towers.add((Tower)p);
			}
			if(p instanceof Horse) {
				horses.add((Horse)p);
			}
			if(p instanceof Runner) {
				runners.add((Runner)p);
			}
			if(p instanceof King) {
				kings.add((King)p);
			}
			if(p instanceof Queen) {
				queens.add((Queen)p);
			}
			if(p instanceof Pawn) {
				pawns.add((Pawn)p);
			}
		}

	}



	/*
	 * Creates all the pieces
	 */
	public void createPieces(){

		//White player
		towers.add(new Tower(0,0,PlayerColor.WHITE));
		towers.add(new Tower(7,0,PlayerColor.WHITE));
		horses.add(new Horse(1,0,PlayerColor.WHITE));
		horses.add(new Horse(6,0,PlayerColor.WHITE));
		runners.add(new Runner(2,0,PlayerColor.WHITE));
		runners.add(new Runner(5,0,PlayerColor.WHITE));
		kings.add(new King(4,0,PlayerColor.WHITE));
		queens.add(new Queen(3,0,PlayerColor.WHITE));

		for(int x = 0; x < 8; x++) {
			pawns.add(new Pawn(x,1,PlayerColor.WHITE));
		}

		//Black player
		towers.add(new Tower(0,7,PlayerColor.BLACK));
		towers.add(new Tower(7,7,PlayerColor.BLACK));
		horses.add(new Horse(1,7,PlayerColor.BLACK));
		horses.add(new Horse(6,7,PlayerColor.BLACK));
		runners.add(new Runner(2,7,PlayerColor.BLACK));
		runners.add(new Runner(5,7,PlayerColor.BLACK));
		kings.add(new King(3,7,PlayerColor.BLACK));
		queens.add(new Queen(4,7,PlayerColor.BLACK));

		for(int x = 0; x < 8; x++) {
			pawns.add(new Pawn(x,6,PlayerColor.BLACK));
		}

	}

}
