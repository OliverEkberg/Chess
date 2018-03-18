package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Controller extends Thread{

	//Will hold all data from server
	public Json json;

	//The Color of the player
	public PlayerColor color = PlayerColor.WHITE;

	Gson gson = new GsonBuilder().serializeNulls().create();

	private View theView;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 
	



	public Controller(View theView, PlayerColor color, String serverAdress, int port) throws UnknownHostException, IOException{
		this.theView = theView;
		this.color = color;
		Piece.loadImages();

		json = new Json();
		json.createPieces();
		json.afterParsing();
		
		theView.render();
		
		socket = new Socket(serverAdress, port);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		



	}

	public void update(Json json) {
		this.json = json;
		json.afterParsing();
		theView.render();
	}

	public void clickHandler(Coordinate clickedCoordinate) {

		//If its the players turn
		if(color == json.playerTurn) {

			//Add the click to the json object
			json.clickedCoordinate = clickedCoordinate;			

			//Send the json object to the server
			out.println(gson.toJson(json));
		}

	}

	public void run(){
		try {
			while(true){

				String msgFromServer = in.readLine();
				if(msgFromServer.length() > 10) {

					update(gson.fromJson(msgFromServer, Json.class));
				}

			} 

		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null)
				out.close();

			try {
				in.close();
				socket.close();
			} catch (IOException e2) {}
		}
	}



}
