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
	
	Gson gson = new GsonBuilder().serializeNulls().create();

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
	
	public void fromClient(String json, Client client) {
		
		
		
		
		//Player needs to have turn
		if(playerTurn.equals(client.getColor())) {

			//Parsing JSON
			Coordinate clickedCoordinate = gson.fromJson(json, Coordinate.class);

			boolean moved = false;

			if(client.getSelectedCoord() != null && !Piece.isEmpty(client.getSelectedCoord())){

				//Piece needs to be owned by player with turn
				if(Piece.isOwnedBy(client.getSelectedCoord(),playerTurn)){

					Piece selectedPiece = Piece.getPiece(client.getSelectedCoord());

					//Piece needs to have possible moves
					if(selectedPiece.isMovable(clickedCoordinate)){

						selectedPiece.move(clickedCoordinate);
						playerTurn = (playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE; //Switch turn
						moved = true;

					}
				}
			}

			client.setSelectedCoord(moved ? null : clickedCoordinate); //If moved none will be selected

			if(moved) {
				sendPositions(clients);
			}

		}
		if(playerTurn.equals(client.getColor()) && client.getSelectedCoord() != null && !Piece.isEmpty(client.getSelectedCoord()) && Piece.isOwnedBy(client.getSelectedCoord(), client.getColor())) {

					Markers markers = new Markers();
					markers.selected = client.getSelectedCoord();
					

					ArrayList<Coordinate> allMoves = Piece.getPiece(client.getSelectedCoord()).isMovableAll();

					for(Coordinate c : allMoves){
						if(Piece.isEmpty(c)){
							markers.freeMoves.add(c);
						}else if(Piece.getPiece(c).isEnemy(client.getColor())){
							markers.enemyMoves.add(c);
						}
					}
					
					String returnJson = Commands.Markers.toString()+"_";
					returnJson += gson.toJson(markers);
					client.send(returnJson);

			
		}
		
	}
	
	
	
	/**
	 * Sends positions to client
	 * @param client
	 */
	private void sendPositions(Client client) {
		Positions p = new Positions();

		for (Piece piece : Piece.pieces) {
			p.positionList.add(new DrawPiece(piece.coord, piece.color, piece.toString()));
		}

		String returnJson = Commands.Positions.toString()+"_";
		returnJson += gson.toJson(p);
		
		client.send(returnJson);
		
	}
	
	private void sendPositions(HashMap<PlayerColor, Client> clientMap) {
		for (PlayerColor playerColor : clientMap.keySet()) {
			sendPositions(clientMap.get(playerColor));
		}
	}


	public static void main(String[] args) throws IOException {
		new Server();
	}
	
}

	