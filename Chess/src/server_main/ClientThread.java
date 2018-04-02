package server_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Thread that handles all the IO for a client
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class ClientThread extends Thread{

	private BufferedReader input;
	private PrintWriter output;
	private Client client;
	private Server server;
	private boolean isRunning = true;

	
	/**
	 * Sets variables and creates a input and output
	 * 
	 * @param server		Reference to the server
	 * @param socket		Reference to socket
	 * @param client		Reference to owner
	 * @throws IOException
	 */
	public ClientThread(Server server, Socket socket, Client client) throws IOException {
		this.server = server;
		this.client = client;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
	} 

	
	/**
	 * Kills the thread
	 */
	public void kill() {
		isRunning = false;
	}

	
	/**
	 * While game {@link #isRunning is running} it takes in json from client, formats it and sends it to the {@link Server server}
	 * 
	 * @see Server#fromClient(String, Client)
	 */
	public void run(){

		while(isRunning){
			try {
				String json = input.readLine();
				server.fromClient(json, client);
			} catch (IOException e){}

		}
	}

	
	/**
	 * @param msg	Message to send to the client
	 */
	public void writeToClient(String msg){
		if(msg != null) 
			output.println(msg);
	}

}