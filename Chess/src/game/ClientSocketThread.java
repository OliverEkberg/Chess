package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import json.Commands;
import json.DrawPiece;
import json.Positions;

public class ClientSocketThread extends Thread{

	public static HashMap<PlayerColor, ClientSocketThread> clientList = new HashMap<>(2);
	public static PlayerColor playerTurn = PlayerColor.WHITE;

	/*
	 * Defining IO
	 */
	private BufferedReader in;
	private PrintWriter out;


	Gson gson = new GsonBuilder().serializeNulls().create();


	public PlayerColor playerColor;
	public Coordinate selectedCoord = null;






	public ClientSocketThread(Socket socket, PlayerColor playerColor) throws IOException {

		//Create IO
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);


		this.playerColor = playerColor;

		sendPositions(this);


	} 

	public void run(){
		String json = null;

		while(true){
			try {
				json = in.readLine();

				//Player needs to have turn
				if(playerTurn.equals(playerColor)) {

					//Parsing JSON
					Coordinate clickedCoordinate = gson.fromJson(json, Coordinate.class);

					boolean moved = false;

					if(selectedCoord != null && !Piece.isEmpty(selectedCoord)){

						//Piece needs to be owned by player with turn
						if(Piece.isOwnedBy(selectedCoord,playerTurn)){

							Piece selectedPiece = Piece.getPiece(selectedCoord);

							//Piece needs to have possible moves
							if(selectedPiece.isMovable(clickedCoordinate)){

								selectedPiece.move(clickedCoordinate);
								playerTurn = (playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE; //Switch turn
								moved = true;

							}
						}
					}

					selectedCoord = moved ? null : clickedCoordinate; //If moved none will be selected

					if(moved) {
						sendPositionsBoth();
					}

				}
			} catch (IOException e){}

		}
	}


	/**
	 * Sends positions to client
	 * @param client
	 */
	public void sendPositions(ClientSocketThread client) {
		Positions p = new Positions();

		for (Piece piece : Piece.pieces) {
			p.positionList.add(new DrawPiece(piece.coord, piece.color, piece.toString()));
		}

		String returnJson = Commands.Positions.toString()+"_";
		returnJson += gson.toJson(p);
		client.writeToClient(returnJson);
	}

	/**
	 * Sends positions to both
	 */
	private void sendPositionsBoth() {
		sendPositions(clientList.get(PlayerColor.BLACK));
		sendPositions(clientList.get(PlayerColor.WHITE));
	}


	/**
	 * Sends msg to the client. Will not send null
	 * @param msg
	 */
	private void writeToClient(String msg){
		if(msg != null) 
			out.println(msg);
	}
}