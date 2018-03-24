package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{

	private BufferedReader input;
	private PrintWriter output;
	private Client client;
	private Server server;



	public ClientThread(Server server, Socket socket, Client client) throws IOException {
		this.server = server;
		this.client = client;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
	} 

	/**
	 * Input
	 */
	public void run(){

		while(true){
			try {
				String json = input.readLine();
				server.fromClient(json, client);
			} catch (IOException e){}

		}
	}

	/**
	 * Output
	 */
	public void writeToClient(String msg){
		if(msg != null) 
			output.println(msg);
	}

}