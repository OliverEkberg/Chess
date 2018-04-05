package server_main;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import shared.Commands;
import shared.Coordinate;
import shared.DrawPiece;
import shared.Markers;
import shared.PlayerColor;
import shared.Positions;

/**
 * Chess server. Handles clients and direct IO. 
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Server {
	private ServerSocket serverSocket = null;

	private HashMap<PlayerColor, Socket> sockets = new HashMap<>();
	private HashMap<PlayerColor, Client> clients = new HashMap<>();
	
	private ServerView sv;


	private Gson gson = new GsonBuilder().serializeNulls().create();

	private ChessLogic logic;

	/**
	 * Creates a new {@link ServerView}
	 */
	public Server() {
		sv = new ServerView(this);
	}
	
	
	/**
	 * Starts the server at port
	 * 
	 * @param port			Requested port of the new server
	 * @throws IOException
	 * @see					Server#waitForPlayers()	
	 * @see					ChessLogic#setGameRunning(boolean)			
	 */
	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		logic = new ChessLogic();
		waitForPlayers();
		logic.setGameRunning(true);
	}
	
	
	/**
	 * Sends a message to all {@link Client clients} and stops the server
	 * 
	 * @see	ChessLogic#setGameRunning(boolean)
	 */
	public void stop() {
		logic.setGameRunning(false);
		for (PlayerColor c : clients.keySet()) {
			clients.get(c).send("Error_Server not responding.");
		}
		System.exit(0);
	}

	
	/**
	 * A thread that waits for new players to connect
	 * 
	 * @throws IOException
	 * @see	Server#sendPositions(PlayerColor)
	 * @see ServerView#showError(String)
	 */
	private void waitForPlayers() throws IOException {
		new Thread()
		{
			public void run() {

				try {
					/*
					 * First player is black
					 */
					PlayerColor color = PlayerColor.BLACK;
					sockets.put(color, serverSocket.accept());
					clients.put(color, new Client(color, sockets.get(color), Server.this));
					sendPositions(clients.get(color));


					/*
					 * Second player is white
					 */
					color = PlayerColor.WHITE;

					sockets.put(color, serverSocket.accept());
					clients.put(color, new Client(color, sockets.get(color), Server.this));
					sendPositions(clients.get(color));
				} catch (IOException e) {
					sv.showError("Could not connect both players.");
				}

			}
		}.start();

	}

	/**
	 * Handles incoming click from a client
	 * 
	 * @param json		Clock in json-format
	 * @param client		The client sending the data
	 * @see				ChessLogic
	 */
	public synchronized void fromClient(String json, Client client) {

		if(!logic.isGameRunning()) {
			return;
		}
		


		/*
		 * Player needs to have turn
		 */
		if(logic.getPlayerTurn().equals(client.getColor())) {


			//Parsing JSON
			Coordinate clickedCoordinate = gson.fromJson(json, Coordinate.class);
			
			
			/*
			 * Clicking the selected coordinate will deselect it
			 */
			if(client.getSelectedCoord() != null && client.getSelectedCoord().equals(clickedCoordinate)) {
				client.setSelectedCoord(null);
				sendPositions(clients);
				return;
			}
			

			/*
			 * If player owns clicked piece, it's possible moves will be collected
			 */
			if(logic.isOwnedBy(clickedCoordinate, client.getColor())) {
				
			
				client.setSelectedCoord(clickedCoordinate);

				Markers markers = new Markers();
				markers.setSelectedCoordinate(client.getSelectedCoord());


				ArrayList<Coordinate> allMoves = logic.isMovableAll(logic.getPiece(client.getSelectedCoord()));

				for(Coordinate c : allMoves){
					if(logic.isEmpty(c)){
						markers.getFreeMoves().add(c);
					}else if(logic.getPiece(c).isEnemy(client.getColor())){
						markers.getEnemyMoves().add(c);
					}
				}

				String returnJson = Commands.Markers.toString()+"_";
				returnJson += gson.toJson(markers);
				client.send(returnJson);
				return;
			}


			/*
			 * The selected piece needs to be owned by the player
			 */
			if (logic.isOwnedBy(client.getSelectedCoord(), client.getColor())) {
				Piece selectedPiece = logic.getPiece(client.getSelectedCoord());

				/*
				 * Moves selected piece if possible
				 */
				if(logic.isMovable(clickedCoordinate,selectedPiece) && !logic.isChess(clickedCoordinate,selectedPiece)){
					logic.move(selectedPiece,clickedCoordinate);
					logic.switchTurn();
					client.setSelectedCoord(null);
					sendPositions(clients);

					//If the new playerturn can't make move
					if(!logic.canMakeMove(logic.getPlayerTurn())) {
						if(logic.isChess(logic.getPlayerTurn())) {
							
							//Send chess to both clients
							for(PlayerColor key : clients.keySet()) {
								clients.get(key).send(Commands.GameOver +"_"+"Chess mate. " + logic.getPlayerTurn() + " player lost!");
							}
						}else {
							
							//Send patt to both clients
							for(PlayerColor key : clients.keySet()) {
								clients.get(key).send(Commands.GameOver +"_"+"Patt. " + logic.getPlayerTurn() + " player lost!");
							}
						}
						
						//Saves and stops the game
						logic.saveGame();
						stop();

					}

				}
			}


		}



	}


	/**
	 * Sends positions
	 * 
	 * @param client		Whom to send the positions
	 * @see 				Positions
	 */
	private void sendPositions(Client client) {
		Positions p = new Positions();

		for (Piece piece : logic.getPieces()) {
			p.addPosition(new DrawPiece(piece.getCoordinate(), piece.getColor(), piece.toString()));
		}

		String returnJson = Commands.Positions.toString()+"_";
		returnJson += gson.toJson(p);

		client.send(returnJson);
	}

	
	/**
	 * Sends positions to all clients
	 * 
	 * @param clientMap		Map of all clients
	 * @see 					#sendPositions(Client)
	 */
	private void sendPositions(HashMap<PlayerColor, Client> clientMap) {
		for (PlayerColor playerColor : clientMap.keySet()) {
			sendPositions(clientMap.get(playerColor));
		}
	}


	/**
	 * @return	The IP-address of the server
	 */
	public String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {}
		return null;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Server();
	}

}

