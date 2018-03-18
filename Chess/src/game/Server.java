package game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private int port = 9001;
    private ServerSocket serverSocket = null;

    public Server() throws IOException {
    	
        System.out.println("Up and running ...");
        
        serverSocket = new ServerSocket(port);
        
        while(true){
        	
        		//Waits for client to connect
            Socket socket = serverSocket.accept();
            
            ClientSocketThread clientThread = new ClientSocketThread(socket);
            ClientSocketThread.clientList.add(clientThread);
            
            clientThread.start();
         
        }
    }

    public static void main(String[] args) throws IOException {
            new Server();
    }
}