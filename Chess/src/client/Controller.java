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

public class Controller extends Thread{



	private Gson gson = new GsonBuilder().serializeNulls().create();

	private View theView;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 


	private ArrayList<DrawPiece> positions;


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
	 * Converts clickedCoordinate to JSON and sends it to the server
	 * @param clickedCoordinate
	 */
	public void clickHandler(Coordinate clickedCoordinate) {
		out.println(gson.toJson(clickedCoordinate));
	}

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
