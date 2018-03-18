package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ClientSocketThread extends Thread{

	private BufferedReader in;
	private PrintWriter out;
	Gson gson = new GsonBuilder().serializeNulls().create();

	public static ArrayList<ClientSocketThread> clientList = new ArrayList<>(2);

	public ClientSocketThread(Socket socket) throws IOException {
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	} 

	public void run(){
		String msgFromClient = null;

		while(true){
			try {
				msgFromClient = in.readLine();

				Json json = gson.fromJson(msgFromClient, Json.class);
				json.afterParsing();


				boolean moved = false;

				if(json.selectedCoord != null && !Piece.isEmpty(json.selectedCoord)){
					if(Piece.isOwnedBy(json.selectedCoord,json.playerTurn)){
						Piece selectedPiece = Piece.getPiece(json.selectedCoord);
						if(selectedPiece.isMovable(json.clickedCoordinate)){
							selectedPiece.move(json.clickedCoordinate);
							json.playerTurn = (json.playerTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
							moved = true;

						}
					}
				}

				//If moved no coordinate shall be selected
				json.selectedCoord = moved ? null : json.clickedCoordinate;

				json.beforeParsing();
				String returnJson = gson.toJson(json);
				
				//Send to both players
				for (ClientSocketThread clientSocketThread : clientList) {
					clientSocketThread.writeToClient(returnJson);
				}


			} catch (IOException e){}

		}
	}

	public void writeToClient(String msg){

		if(msg != null)  // Vägra skicka null
			out.println(msg);
	}
}