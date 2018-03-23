package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import json.DrawPiece;
import json.Positions;

public class Controller extends Thread{


	//

	Gson gson = new GsonBuilder().serializeNulls().create();

	private View theView;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out; 


	public ArrayList<DrawPiece> positions;


	public Controller(View theView, String serverAdress, int port) throws UnknownHostException, IOException{
		this.theView = theView;

		socket = new Socket(serverAdress, port);

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);




	}

	/**
	 * Converts clickedCoordinate to JSON and sends it to the server
	 * @param clickedCoordinate
	 */
	public void clickHandler(Coordinate clickedCoordinate) {
		theView.render(positions); //For testing purposes
		out.println(gson.toJson(clickedCoordinate));
	}

	public void run(){
		try {
			while(true){

				String msgFromServer = in.readLine();
				
				if(msgFromServer.length() > 10) {

					String command = msgFromServer.split("_")[0];
					String json = msgFromServer.split("_")[1];


					switch (command) {
					case "Positions":  
						
						positions = gson.fromJson(json, Positions.class).positionList;
						theView.render(positions);
						
						break;
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
		}
	}



}
