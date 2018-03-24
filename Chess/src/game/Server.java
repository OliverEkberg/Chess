package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import json.Commands;
import json.DrawPiece;
import json.Markers;
import json.Positions;


public class Server {
	private int port = 9001;
	private ServerSocket serverSocket = null;

	private HashMap<PlayerColor, Socket> sockets = new HashMap<>();
	private HashMap<PlayerColor, Client> clients = new HashMap<>();

	private PlayerColor playerTurn = PlayerColor.WHITE;

	private Gson gson = new GsonBuilder().serializeNulls().create();

	public Server() throws IOException {
		System.out.println("Up and running ...");		
		Piece.createPieces();
		serverSocket = new ServerSocket(port);

		/*
		 * First player is black
		 */
		PlayerColor color = PlayerColor.BLACK;

		sockets.put(color, serverSocket.accept());
		clients.put(color, new Client(color, sockets.get(color), this));
		sendPositions(clients.get(color));



		/*
		 * Second player is white
		 */
		color = PlayerColor.WHITE;

		sockets.put(color, serverSocket.accept());
		clients.put(color, new Client(color, sockets.get(color), this));
		sendPositions(clients.get(color));
	}

	/**
	 * Handles incoming click from a client
	 * @param json
	 * @param client
	 */
	public synchronized void fromClient(String json, Client client) {


		/*
		 * Player needs to have turn
		 */
		if(playerTurn.equals(client.getColor())) {

			//Parsing JSON
			Coordinate clickedCoordinate = gson.fromJson(json, Coordinate.class);

		
			/*
			 * If player owns clicked piece, it's possible moves will be collected
			 */
			if(Piece.isOwnedBy(clickedCoordinate, client.getColor())) {
				
				client.setSelectedCoord(clickedCoordinate);
				
				Markers markers = new Markers();
				markers.setSelected(client.getSelectedCoord());


				ArrayList<Coordinate> allMoves = Piece.getPiece(client.getSelectedCoord()).isMovableAll();

				for(Coordinate c : allMoves){
					if(Piece.isEmpty(c)){
						markers.getFreeMoves().add(c);
					}else if(Piece.getPiece(c).isEnemy(client.getColor())){
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
			if (Piece.isOwnedBy(client.getSelectedCoord(), client.getColor())) {
				Piece selectedPiece = Piece.getPiece(client.getSelectedCoord());
				
				/*
				 * Moves selected piece if possible
				 */
				if(selectedPiece.isMovable(clickedCoordinate)){
					selectedPiece.move(clickedCoordinate);
					switchTurn(client.getColor());
					client.setSelectedCoord(clickedCoordinate);
					sendPositions(clients);
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

		for (Piece piece : Piece.pieces) {
			p.addPosition(new DrawPiece(piece.coord, piece.color, piece.toString()));
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

	
	/**
	 * Switches playerTurn
	 * @param playerColor
	 */
	private void switchTurn(PlayerColor playerColor) {
		playerTurn = (playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();
	}

}

