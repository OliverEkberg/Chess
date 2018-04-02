package client;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import images.ResourceLoader;
import shared.Coordinate;
import shared.DrawPiece;
import shared.Markers;

/**
 * Handles all client-side IO. Is a GUI.
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class View extends JFrame implements MouseListener{  

	private static final long serialVersionUID = 1L;
	private Controller theController;

	/*
	 * Canvas related
	 */
	private Canvas gameCanvas;  
	private int canvasWidth = 800;
	private int canvasHeight = 800;
	private int tileWidth = 0;
	private BufferStrategy backBuffer;

	private HashMap<String, Image> images = new HashMap<>(); //Holds all images
	private boolean firstRender = true;

	private JPanel connectPanel;

	
	/**
	 * Starting up the menu
	 * 
	 * @see View#createStartupMenu()
	 */
	public View(){  
		super("Chess Game");
		tileWidth = canvasWidth/8;
		setResizable(false);
		loadImages();
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		createStartupMenu();
	}  
	
	
	/**
	 * Creates startup menu
	 * 
	 * @see View#addComp(JPanel, JComponent, int, int, int, int, int, int)
	 * @see View#startGame(String, int)
	 * @see View#displayError(String)
	 */
	private void createStartupMenu() {

		setSize(300,400);

		connectPanel = new JPanel();
		connectPanel.setLayout(new GridBagLayout());

		JLabel ipLabel = new JLabel("IP-Address:");
		JTextField ipField = new JTextField(10);
		addComp(connectPanel, ipLabel, 0, 0, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addComp(connectPanel, ipField, 1, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

		JLabel portLabel = new JLabel("Port:");
		JTextField portField = new JTextField(10);
		addComp(connectPanel, portLabel, 0, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE);
		addComp(connectPanel, portField, 1, 1, 2, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

		JButton connect = new JButton("Connect");
		
		//Actionlistner for the connect button
		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == connect) {
					String ip = ipField.getText().trim();
					String port = portField.getText().trim();

					try {
						startGame(ip, Integer.parseInt(port));

					} catch (NumberFormatException e1) {
						displayError("Wrong port format. Try again.");
					}


				}

			}
		});
		addComp(connectPanel, connect, 1, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		add(connectPanel);
		pack();
		setVisible(true);

	}
	

	/**
	 * Adds a component to a panel
	 * 
	 * @param thePanel
	 * @param comp
	 * @param xPos
	 * @param yPos
	 * @param compWidth
	 * @param compHeight
	 * @param place
	 * @param stretch
	 */
	private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place, int stretch){

		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = xPos;
		gridConstraints.gridy = yPos;
		gridConstraints.gridwidth = compWidth;
		gridConstraints.gridheight = compHeight;
		gridConstraints.weightx = 100;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5,5,5,5);
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;

		thePanel.add(comp, gridConstraints);
	}


	/**
	 * Starts up the game and creates the {@link Controller}
	 * 
	 * @param serverAdress 	Address of the server
	 * @param port 			Port of the server
	 * @see View#createGameWindow()
	 */
	public void startGame(String serverAdress, int port)  {
		createGameWindow(); 
		gameCanvas.addMouseListener(this);

		theController = new Controller(this, serverAdress, port);
		theController.start();
	}


	/**
	 * Creates the game window and the canvas
	 */
	private void createGameWindow(){  
		this.setSize(canvasWidth, canvasHeight);
		gameCanvas = new Canvas();  
		gameCanvas.setSize(canvasWidth, canvasHeight);  
	
		gameCanvas.setFocusable(true);

		this.repaint();
		this.remove(connectPanel);
		this.add(gameCanvas);  
		this.pack();   

		gameCanvas.createBufferStrategy(2); 
		backBuffer = gameCanvas.getBufferStrategy();
	}  


	/**
	 * Renders the tiles and pieces
	 * 
	 * @param pieces 	List of pieces to render
	 * @see View#firstRender
	 * @see View#drawTiles(Graphics)
	 * @see View#drawPieces(Graphics, ArrayList)
	 */
	public void render(ArrayList<DrawPiece> pieces){  

		Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

		drawTiles(g);
		drawPieces(g, pieces);


		g.dispose();
		backBuffer.show();

		//Will render twice the first time, image bug
		if(firstRender) {
			firstRender = false;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			render(pieces);
		}
	}  
	

	/**
	 * Renders the tiles, pieces and markers
	 * 
	 * @param pieces 	List of pieces to render
	 * @see View#drawTiles(Graphics)
	 * @see View#drawMarkers(Graphics, Markers)
	 * @see View#drawPieces(Graphics, ArrayList)
	 */
	public void render(ArrayList<DrawPiece> pieces, Markers markers){  
		Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

		drawTiles(g);
		drawMarkers(g, markers);
		drawPieces(g, pieces);

		g.dispose();
		backBuffer.show();
	}  

	
	@Override
	public synchronized void mouseClicked(MouseEvent e) {
		int x = Math.floorDiv(e.getX(), 100);
		int y = Math.floorDiv(e.getY(), 100);		
		theController.clickHandler(new Coordinate(x,y));		
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}


	/**
	 * Loads all images
	 * 
	 * @see ResourceLoader#getImage(String)
	 */
	private void loadImages() {
		images.put("BLACK_horse", ResourceLoader.getImage("BLACK_horse.png"));
		images.put("BLACK_king", ResourceLoader.getImage("BLACK_king.png"));
		images.put("BLACK_pawn", ResourceLoader.getImage("BLACK_pawn.png"));
		images.put("BLACK_queen", ResourceLoader.getImage("BLACK_queen.png"));
		images.put("BLACK_runner", ResourceLoader.getImage("BLACK_runner.png"));
		images.put("BLACK_tower", ResourceLoader.getImage("BLACK_tower.png"));

		images.put("WHITE_horse", ResourceLoader.getImage("WHITE_horse.png"));
		images.put("WHITE_king", ResourceLoader.getImage("WHITE_king.png"));
		images.put("WHITE_pawn", ResourceLoader.getImage("WHITE_pawn.png"));
		images.put("WHITE_queen", ResourceLoader.getImage("WHITE_queen.png"));
		images.put("WHITE_runner", ResourceLoader.getImage("WHITE_runner.png"));
		images.put("WHITE_tower", ResourceLoader.getImage("WHITE_tower.png"));
	}


	/**
	 * Draws 64 tiles
	 * 
	 * @param g 		Graphics object getting drawn to
	 * @see View#tileWidth
	 */
	private void drawTiles(Graphics g){
		Color c = Color.DARK_GRAY;
		for(int y = 0; y < 8; y++){
			c = (c == Color.DARK_GRAY) ? Color.GRAY: Color.DARK_GRAY;
			for(int x = 0; x < 8; x++){
				c = (c == Color.DARK_GRAY) ? Color.GRAY: Color.DARK_GRAY;
				g.setColor(c);
				g.fillRect(x*tileWidth, y*tileWidth, tileWidth, tileWidth);

			}
		}
	}

	
	/**
	 * Draws all pieces
	 * 
	 * @param g 			Graphics object getting drawn to
	 * @param pieces		List of pieces that gets drawn
	 * @see View#images
	 */
	private void drawPieces(Graphics g, ArrayList<DrawPiece> pieces) {
		for (DrawPiece drawPiece : pieces) {
			Image image = images.get(drawPiece.getPlayerColor() + "_" + drawPiece.getType());
			g.drawImage(image, drawPiece.getCoord().x*100, drawPiece.getCoord().y*100, null);
		}	
	}

	
	/**
	 * Draws all markers
	 * 
	 * @param g			Graphics object to draw upon
	 * @param markers	Markers to draw
	 */
	private void drawMarkers(Graphics g, Markers markers) {

		/*
		 * Selected marker
		 */
		int d = (int) (tileWidth*0.9);
		Color c = new Color(0,0,255,40);
		g.setColor(c);
		g.fillOval(markers.getSelectedCoordinate().x*tileWidth+tileWidth/2 - Math.round(d/2), markers.getSelectedCoordinate().y*tileWidth+tileWidth/2 - Math.round(d/2), d, d);

		/*
		 * Enemy markers
		 */
		for (Coordinate coord : markers.getEnemyMoves()) {
			d = (int) (tileWidth*0.9);
			c = new Color(255,0,0,40);
			g.setColor(c);
			g.fillOval(coord.x*tileWidth+tileWidth/2 - Math.round(d/2), coord.y*tileWidth+tileWidth/2 - Math.round(d/2), d, d);
		}

		/*
		 * Empty markers
		 */
		for (Coordinate coord : markers.getFreeMoves()) {
			d = Math.round(tileWidth/5);
			c = Color.blue;
			g.setColor(c);
			g.fillOval(coord.x*tileWidth+tileWidth/2 - Math.round(d/2), coord.y*tileWidth+tileWidth/2 - Math.round(d/2), d, d);
		}


	}




	/**
	 * Outputs a game over message
	 * 
	 * @param message 	The message
	 */
	public void gameOver(String message) {
		JOptionPane.showMessageDialog(View.this, message, "Game Over", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Outputs an error
	 * 
	 * @param error		Error information
	 */
	public void displayError(String error) {
		JOptionPane.showMessageDialog(View.this, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
		new View();
	}

}
