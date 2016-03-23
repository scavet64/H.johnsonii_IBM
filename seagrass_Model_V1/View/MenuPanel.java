package seagrass_Model_V1.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPanel extends JPanel {
	
	private static final Dimension BUTTON_SIZE = new Dimension(200, 25);
	
	// Buttons
	private JButton runSimButton, editOptionsButton, viewPrevButton, quitButton;
	
	

	/**
	 * Constructor for the menuPanel
	 * @param menu
	 */
	public MenuPanel() {
		//set up panel options
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

		//create name label
		JLabel welcomeLabel = new JLabel("What would you like to do?");
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//create buttons
		runSimButton = new JButton("Run Simulation");
		runSimButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		runSimButton.setHorizontalAlignment(SwingConstants.CENTER);
		runSimButton.setMinimumSize(BUTTON_SIZE);
		runSimButton.setMaximumSize(BUTTON_SIZE);
		
		editOptionsButton = new JButton("Change Simulation Options");
		editOptionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		editOptionsButton.setHorizontalAlignment(SwingConstants.CENTER);
		editOptionsButton.setMinimumSize(BUTTON_SIZE);
		editOptionsButton.setMaximumSize(BUTTON_SIZE);
		
		viewPrevButton = new JButton("View Previous Simulation Results");
		viewPrevButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		viewPrevButton.setHorizontalAlignment(SwingConstants.CENTER);
		viewPrevButton.setMinimumSize(BUTTON_SIZE);
		viewPrevButton.setMaximumSize(BUTTON_SIZE);
		
		quitButton = new JButton("Quit");
		quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		quitButton.setHorizontalAlignment(SwingConstants.CENTER);
		quitButton.setMinimumSize(BUTTON_SIZE);
		quitButton.setMaximumSize(BUTTON_SIZE);
		
		//Add buttons and glue
		add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
		add(welcomeLabel);
		add(Box.createRigidArea(new Dimension(0, 2)));
		add(runSimButton);
		add(Box.createRigidArea(new Dimension(0, 2)));
		add(editOptionsButton);
		add(Box.createRigidArea(new Dimension(0, 2)));
		add(viewPrevButton);
		add(Box.createRigidArea(new Dimension(0, 2)));
		add(quitButton);
		add(Box.createRigidArea(new Dimension(0, 2)));
		add(Box.createVerticalGlue());
		add(Box.createHorizontalGlue());
	}
	
	/**
	 * overridden paintComponent method that takes the graphics object of the panel.
	 * draws the background image on the panel.
	 */
	protected void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		//g1.drawImage(new ImageIcon("images//menuBackground.png").getImage(), 0, 0, null);
	}

	/**
	 * @return the runSimButton
	 */
	public JButton getRunSimButton() {
		return runSimButton;
	}

	/**
	 * @return the editOptionsButton
	 */
	public JButton getEditOptionsButton() {
		return editOptionsButton;
	}

	/**
	 * @return the viewPrevButton
	 */
	public JButton getViewPrevButton() {
		return viewPrevButton;
	}

	/**
	 * @return the quitButton
	 */
	public JButton getQuitButton() {
		return quitButton;
	}
	
	

}
