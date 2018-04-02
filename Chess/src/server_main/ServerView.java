package server_main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Graphical interface for the {@link Server server}
 *
 * @author  Oliver Ekberg
 * @since   2018-04-01
 * @version 1.0
 */
public class ServerView extends JFrame {

	private static final long serialVersionUID = 1L;

	
	private JPanel thePanel;
	private JLabel ipLabel, portLabel;
	private JTextField portField;
	private JButton startButton;
	private JButton stopButton;
	
	/**
	 * Creates the GUI
	 * 
	 * @param server		Reference to the controller
	 */
	public ServerView(Server server) {
		
		setSize(400,400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		thePanel = new JPanel();
		thePanel.setLayout(new GridBagLayout());
		
		ipLabel = new JLabel("Server IP: " + server.getIp());
		addComp(thePanel, ipLabel, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		portLabel = new JLabel("Insert desired host port (9001-65500): ");
		addComp(thePanel, portLabel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		portField = new JTextField(5);
		addComp(thePanel, portField, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		startButton = new JButton("Start server");
		addComp(thePanel, startButton, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
		
		stopButton = new JButton("Stop server");
		
		
		/*
		 * ActionListner for the startbutton. Will retrieve port and IP-address from the fields and start the server with these values.
		 * Will also change the GUI.
		 */
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				int port = Integer.parseInt(portField.getText().trim());
				if(port < 9001 || port > 65500) {
					JOptionPane.showMessageDialog(thePanel, "Port not within limits, please try again.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				thePanel.remove(portLabel);
				thePanel.remove(portField);
				thePanel.remove(startButton);
				addComp(thePanel, stopButton, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
				ServerView.this.revalidate();
				ServerView.this.repaint();
				thePanel.setVisible(true);
				
				
				try {
					server.start(port);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(thePanel, "Server could not be started.", "Error", JOptionPane.ERROR_MESSAGE);
					server.stop();
				}
				
			}
		});
		
		/*
		 * ActionListner for stop button. Will stop the server.
		 */
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				server.stop();
			}
			
		});
		

		add(thePanel);
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
	 * Displays an error for the server
	 * 
	 * @param msg	Error message to display
	 */
	public void showError(String msg) {
		JOptionPane.showMessageDialog(thePanel, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
