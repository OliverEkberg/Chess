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


public class View extends JFrame implements MouseListener{  

	private static final long serialVersionUID = 1L;

	private Controller theController;


	/*
	 * Canvas related
	 */
	private Canvas gameCanvas;  
	private BufferStrategy backBuffer;
	private int canvasWidth = 800;
	private int canvasHeight = 800;
	
	private HashMap<String, Image> images = new HashMap<>(); //Holds all images
	private boolean firstRender = true;
	private JPanel connectPanel;

	/**
	 * Constructor
	 */
	public View(){  
		super("Chess Game");
		setResizable(false);
		loadImages();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		createStartupMenu();
	}  

	/**
	 * Creates startup menu
	 */
	private void createStartupMenu() {

		this.setSize(300,400);

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
		this.add(connectPanel);
		this.pack();
		this.setVisible(true);

	}
	
	/**
	 * Add component to a panel
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
	 * Starts up the game
	 * @param serverAdress
	 * @param port
	 */
	public void startGame(String serverAdress, int port)  {
		createGameWindow(); 
		gameCanvas.addMouseListener(this);

		theController = new Controller(this, serverAdress, port);
		theController.start();
	}



	/**
	 * Creates the game window
	 */
	public void createGameWindow(){  
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
	 * @param pieces
	 */
	public void render(ArrayList<DrawPiece> pieces){  


		Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

		drawTiles(g);
		drawPieces(g, pieces);


		g.dispose();
		backBuffer.show();

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
	 * @param pieces
	 * @param markers
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
	 * Draws 64 chess tiles
	 */
	private void drawTiles(Graphics g){
		int tileWidth = 100;
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
	 * @param g
	 * @param pieces
	 */
	private void drawPieces(Graphics g, ArrayList<DrawPiece> pieces) {
		for (DrawPiece drawPiece : pieces) {
			Image image = images.get(drawPiece.getPlayerColor() + "_" + drawPiece.getType());
			g.drawImage(image, drawPiece.getCoord().x*100, drawPiece.getCoord().y*100, null);
		}	
	}

	/**
	 * Draws all the markers
	 */
	private void drawMarkers(Graphics g, Markers markers) {
		int tileWidth = 100;

		/*
		 * Selected marker
		 */
		int d = 90;
		Color c = new Color(0,0,255,40);
		g.setColor(c);
		g.fillOval(markers.getSelected().x*tileWidth+tileWidth/2 - Math.round(d/2), markers.getSelected().y*tileWidth+tileWidth/2 - Math.round(d/2), d, d);

		/*
		 * Enemy markers
		 */
		for (Coordinate coord : markers.getEnemyMoves()) {
			d = 90;
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


	public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
		new View();
	}





	public void gameOver(String message) {
		JOptionPane.showMessageDialog(View.this, message, "Game Over", JOptionPane.PLAIN_MESSAGE);
	}
	public void displayError(String error) {
		JOptionPane.showMessageDialog(View.this, error, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
