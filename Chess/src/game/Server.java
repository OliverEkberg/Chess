package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private int port = 9001;
	private ServerSocket serverSocket = null;

	public Server() throws IOException {

		System.out.println("Up and running ...");
		
		Piece.createPieces();
		serverSocket = new ServerSocket(port);


		/*
		 * Connects first player (black)
		 */
		Socket socket = serverSocket.accept();

		ClientSocketThread clientThread = new ClientSocketThread(socket, PlayerColor.BLACK);
		ClientSocketThread.clientList.put(PlayerColor.BLACK, clientThread);

		clientThread.start();

		/*
		 * Connects first player (white)
		 */
		Socket socket2 = serverSocket.accept();

		ClientSocketThread clientThread2 = new ClientSocketThread(socket2, PlayerColor.WHITE);
		ClientSocketThread.clientList.put(PlayerColor.WHITE, clientThread2);

		clientThread2.start();


	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}