package game;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class StartGame {

	public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
		String ipAdress = JOptionPane.showInputDialog("Insert server IP-Adress:");
		String sPort = JOptionPane.showInputDialog("Insert server port:");
		int port = Integer.parseInt(sPort);
		String sColor = JOptionPane.showInputDialog("Insert color:");
		PlayerColor color = sColor.equals("BLACK") ? PlayerColor.BLACK : PlayerColor.WHITE;
		new View(color, ipAdress, port);

	}

}
