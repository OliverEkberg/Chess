package game;

import java.io.IOException;
import java.net.Socket;

public class Client {
	private Coordinate selectedCoord = null;
	private PlayerColor color;
	private ClientThread thread;
	
	public Client(PlayerColor color, Socket socket, Server server) throws IOException{
		this.color = color;
		this.thread = new ClientThread(server, socket, this);
		thread.start();
	}

	public Coordinate getSelectedCoord() {
		return selectedCoord;
	}

	public void setSelectedCoord(Coordinate selectedCoord) {
		this.selectedCoord = selectedCoord;
	}

	public PlayerColor getColor() {
		return color;
	}
	
	public void send(String str) {
		thread.writeToClient(str);
	}


}
