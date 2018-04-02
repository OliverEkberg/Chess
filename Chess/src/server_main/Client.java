package server_main;

import java.io.IOException;
import java.net.Socket;

import shared.Coordinate;
import shared.PlayerColor;

/**
 * Holds all client data
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class Client {
	private Coordinate selectedCoord = null;
	private PlayerColor color;
	private ClientThread thread;
	
	
	/**
	 * Sets values and starts the {@link #thread thread}
	 * 
	 * @param color		Color of the player
	 * @param socket		Reference to the socket socket
	 * @param server		Reference to the server
	 * @throws IOException
	 */
	public Client(PlayerColor color, Socket socket, Server server) throws IOException{
		this.color = color;
		this.thread = new ClientThread(server, socket, this);
		thread.start();
	}
	
	
	/**
	 * @param str	String to send to the client via the {@link #thread}
	 * @see			ClientThread#writeToClient(String)
	 */
	public void send(String str) {
		thread.writeToClient(str);
	}
	

	/**
	 * @return	The clients selected coordinate
	 */
	public Coordinate getSelectedCoord() {
		return selectedCoord;
	}
	
	
	/**
	 * @param selectedCoord		The coordinate to set as the selected one
	 */
	public void setSelectedCoord(Coordinate selectedCoord) {
		this.selectedCoord = selectedCoord;
	}


	/**
	 * @return	The color of the player
	 */
	public PlayerColor getColor() {
		return color;
	}
	
}
