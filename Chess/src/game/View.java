package game;




import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import images.ResourceLoader;
import json.DrawPiece;





public class View extends JFrame implements MouseListener{  

	private static final long serialVersionUID = 1L;

	private Controller theController;


	/*
	 * Graphical
	 */
	private BufferStrategy backBuffer;
	private Canvas gameCanvas;  
	private int canvasWidth = 800;
	private int canvasHeight = 800;
	public static HashMap<String, Image> images = new HashMap<>();


	/*
	 * Constructor
	 */
	public View(String serverAdress, int port) throws InterruptedException, UnknownHostException, IOException{  
		super("Chess Game");
		setResizable(false);
		loadImages();
		createWindow(); 
		gameCanvas.addMouseListener(this);
		theController = new Controller(this, serverAdress, port);
		theController.start();
	}  


	/*
	 * Creates the window
	 */
	public void createWindow(){  
		gameCanvas = new Canvas();  
		gameCanvas.setSize(canvasWidth, canvasHeight);  
		gameCanvas.setFocusable(true);  

		this.add(gameCanvas);  
		this.pack();  
		this.setVisible(true);  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);  


		gameCanvas.createBufferStrategy(2); 
		backBuffer = gameCanvas.getBufferStrategy();
	}  

	/*
	 * Renders everything
	 */
	public void render(ArrayList<DrawPiece> pieces){  
		Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();

		drawTiles(g);
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



	public static void loadImages() {
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



	/*
	 * Draws 64 chess tiles
	 */
	public void drawTiles(Graphics g){
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

	/*
	 * Draws all the pieces
	 */
	private void drawPieces(Graphics g, ArrayList<DrawPiece> pieces) {
		for (DrawPiece drawPiece : pieces) {
			Image image = images.get(drawPiece.p + "_" + drawPiece.type);
			g.drawImage(image, drawPiece.c.x*100, drawPiece.c.y*100, null);
		}	
	}

	public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {

		String ipAdress = "127.0.0.1";
		String sPort = "9001";

		new View(ipAdress, Integer.parseInt(sPort));
	}


}
