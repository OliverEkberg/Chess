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


public class Server {
	private ServerSocket serverSocket = null;

	private HashMap<PlayerColor, Socket> sockets = new HashMap<>();
	private HashMap<PlayerColor, Client> clients = new HashMap<>();
	
	private ServerView sv;


	private Gson gson = new GsonBuilder().serializeNulls().create();

	private ChessLogic logic;

	public Server() {
		sv = new ServerView(this);
	}
	
	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		logic = new ChessLogic();
		waitForPlayers();
		logic.setGameRunning(true);
	}
	public void stop() {
		logic.setGameRunning(false);
		for (PlayerColor c : clients.keySet()) {
			clients.get(c).send("Error_Server not responding.");
		}
		System.exit(0);
	}

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
	 * @param json
	 * @param client
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
			 * If player owns clicked piece, it's possible moves will be collected
			 */
			if(logic.isOwnedBy(clickedCoordinate, client.getColor())) {

				client.setSelectedCoord(clickedCoordinate);

				Markers markers = new Markers();
				markers.setSelected(client.getSelectedCoord());


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
					logic.switchTurn(client.getColor());
					client.setSelectedCoord(clickedCoordinate);
					sendPositions(clients);

					if(!logic.canMakeMove(logic.getPlayerTurn())) {
						if(logic.isChess(logic.getPlayerTurn())) {
							for(PlayerColor key : clients.keySet()) {
								clients.get(key).send(Commands.GameOver +"_"+"Chess mate. " + logic.getPlayerTurn() + " player lost!");
							}
						}else {
							for(PlayerColor key : clients.keySet()) {
								clients.get(key).send(Commands.GameOver +"_"+"Patt. " + logic.getPlayerTurn() + " player lost!");
							}
						}
						stop();

					}

				}
			}


		}



	}


	/**
	 * Sends positions to client
	 * @param client
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
	 * @param clientMap
	 */
	private void sendPositions(HashMap<PlayerColor, Client> clientMap) {
		for (PlayerColor playerColor : clientMap.keySet()) {
			sendPositions(clientMap.get(playerColor));
		}
	}


	public String getIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Server();
	}

}

