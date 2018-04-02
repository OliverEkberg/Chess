package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import shared.Commands;
import shared.Coordinate;
import shared.DrawPiece;
import shared.Markers;
import shared.Positions;

/**
 * Handles all client-side logic
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Controller extends Thread{

	private Gson gson = new GsonBuilder().serializeNulls().create();

	private View theView;

	/*
	 * Server stuff
	 */
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 


	private ArrayList<DrawPiece> positions;


	/**
	 * Connects to server
	 * 
	 * @param theView 	Reference to view
	 * @param ipAdress 	Address of server
	 * @param port 		Port of server
	 * @see View#displayError(String)
	 */
	public Controller(View theView, String ipAdress, int port){
		this.theView = theView;

		try {
			socket = new Socket(ipAdress, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException  | IllegalArgumentException e) {
			theView.displayError("Could not connect to server.");
			System.exit(0);
		}
	}
	

	/**
	 * Converts coordinate to json and sends it to the server
	 * 
	 * @param clickedCoordinate  Coordinate to send
	 */
	public void clickHandler(Coordinate clickedCoordinate) {
		out.println(gson.toJson(clickedCoordinate));
	}

	
	/**
	 * Waits for and handles incoming commands from the server
	 * 
	 * @see View#render(ArrayList)
	 * @see View#render(ArrayList, Markers)
	 * @see View#gameOver(String)
	 */
	public void run(){
		try {
			while(true){

				String msgFromServer = in.readLine();

				if(msgFromServer != null && msgFromServer.length() > 10) {

					Commands command = Commands.valueOf(msgFromServer.split("_")[0]); //Converts everything before "_" to a command.
					String json = msgFromServer.split("_")[1]; //The data

					switch (command) {
					case Positions:  
						positions = gson.fromJson(json, Positions.class).getPositionList();
						theView.render(positions);
						break;
					
					case Markers:
						Markers markers = gson.fromJson(json, Markers.class);
						theView.render(positions, markers);
						break;

					case GameOver:
						theView.gameOver(json);
						out.close();
						in.close();
						socket.close();
						System.exit(0);
					case Error:
						theView.displayError(json);
						out.close();
						in.close();
						socket.close();
						System.exit(0);
			
					}


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
			theView.displayError("Crashed.");
			System.exit(0);
		}
	}



}
