package server_main;

import java.util.ArrayList;

import javafx.util.Pair;
import shared.Coordinate;


/**
 * Holds all games and all their data
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class SavedGames {
	
	//Holds all games
	private ArrayList<ArrayList<Pair<Coordinate, Coordinate>>> games = new ArrayList<>();
	
	
	/**
	 * @param game	Game to add to {@link #games games}
	 */
	public void addGame(ArrayList<Pair<Coordinate, Coordinate>> game) {
		games.add(game);
	}
	
	/**
	 * Gets a game by index
	 * 
	 * @param pos 	Index of the game
	 * @return		The list with game details
	 */
	public ArrayList<Pair<Coordinate, Coordinate>> getGame(int pos) {
		return games.get(pos);
	}
 
}
