package game;




import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;





public class View extends JFrame implements MouseListener{  

	private static final long serialVersionUID = 1L;


	private BufferStrategy backBuffer;


	/*
	 * Canvas setup
	 */
	private Canvas gameCanvas;  
	private int canvasWidth = 800;
	private int canvasHeight = 800;


	private ChessBoard board = new ChessBoard(100);
	private Controller theController;



	/*
	 * Constructor
	 */
	public View(PlayerColor color, String serverAdress, int port) throws InterruptedException, UnknownHostException, IOException{  
		super("Chess Game");
		createWindow(); 
		gameCanvas.addMouseListener(this);
		theController = new Controller(this, color, serverAdress, port);
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
	public void render(){  
		Graphics2D g = (Graphics2D)backBuffer.getDrawGraphics();
		

		board.drawTiles(g);
		Piece.drawAll(g);
		
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

	
	public static void main(String[] args) throws UnknownHostException, InterruptedException, IOException {
		new View(PlayerColor.WHITE, "127.0.0.1", 9001);
	}
	

}
